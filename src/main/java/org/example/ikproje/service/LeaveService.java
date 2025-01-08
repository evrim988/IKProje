package org.example.ikproje.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.NewLeaveRequestDto;
import org.example.ikproje.dto.request.UpdateLeaveRequstDto;
import org.example.ikproje.entity.Leave;
import org.example.ikproje.entity.LeaveDetails;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
import org.example.ikproje.entity.enums.*;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.mapper.LeaveMapper;
import org.example.ikproje.repository.LeaveRepository;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwAllPersonelLeaveList;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtManager jwtManager;
    private final EmailService emailService;
    private final LeaveDetailsService leaveDetailsService;

    @Transactional
    public Boolean createNewLeaveRequest(NewLeaveRequestDto dto){
        double multiplierForAnnualLeave;
        User personel = getUserByToken(dto.token());
        if (personel.getUserRole()==EUserRole.COMPANY_MANAGER){
            throw new IKProjeException(ErrorType.UNAUTHORIZED);
        }
        if (personel.getGender()== EGender.MALE&&dto.leaveType()== ELeaveType.DOGUM_IZNI){
            throw new IKProjeException(ErrorType.NOT_ELIGIBLE_FOR_MATERNITY_LEAVE);
        }
        UserDetails personelDetails = userDetailsService.findByUserId(personel.getId());
        multiplierForAnnualLeave = getMultiplierForAnnualLeave(dto.leaveType(), personelDetails);
        int numberOfDaysPersonelGot = (int) (ELeaveType.YILLIK_IZIN.getNumberOfLeaveDays()*multiplierForAnnualLeave);

        long numberOfLeaveDays = ChronoUnit.DAYS.between(dto.startDate(),dto.endDate());

        Leave leave = LeaveMapper.INSTANCE.fromNewLeaveDto(dto);
        LeaveDetails leaveDetails;

        Optional<LeaveDetails> leaveDetailsOptional = leaveDetailsService.findByUserId(personel.getId());
        //Eğer ilk defa yıllık izin isteğinde bulunuyorsa yeni LeaveDetails oluşturulcak user için, değilse var olan leaveDetails entitysi
        //databaseden çekilecek. Yıllık izinde bulunmak istediği gün miktarı, sahip olduğu yıllık izin sayısından fazlaysa hata fırlatıyor.
        if(leaveDetailsOptional.isPresent()){
            leaveDetails = leaveDetailsOptional.get();
            Optional<Leave> lastAnnualLeaveFromPersonelOpt = leaveRepository.findTopByUserIdAndLeaveStatusAndLeaveTypeOrderByEndDateDesc(personel.getId(),ELeaveStatus.APPROVED,ELeaveType.YILLIK_IZIN);
            if (lastAnnualLeaveFromPersonelOpt.isPresent()){
                Leave lastAnnualLeaveFromPersonel = lastAnnualLeaveFromPersonelOpt.get();
                //Eğer personelin yaptığı son yıllık iznin üzerinden 365 gün geçtiyse, yıllık izin sayısı sıfırlansın (ya da öncekinin üstüne toplansın ?)
                if((ChronoUnit.DAYS.between(LocalDate.now(),lastAnnualLeaveFromPersonel.getEndDate())>365)){
                    leaveDetails.setNumberOfDaysRemainingFromAnnualLeave(numberOfDaysPersonelGot);
                }
            }
            if(leaveDetails.getNumberOfDaysRemainingFromAnnualLeave()<numberOfLeaveDays) throw new IKProjeException(ErrorType.ANNUAL_LEAVE_DAYS_EXCEEDED);

        }
        else{
            leaveDetails = LeaveDetails.builder().userId(personel.getId()).numberOfDaysRemainingFromAnnualLeave(numberOfDaysPersonelGot).build();
            if(leaveDetails.getNumberOfDaysRemainingFromAnnualLeave()<numberOfLeaveDays) throw new IKProjeException(ErrorType.ANNUAL_LEAVE_DAYS_EXCEEDED);
        }

        leaveDetailsService.save(leaveDetails);
        leave.setCompanyId(personel.getCompanyId());
        leave.setUserId(personel.getId());
        leave.setLeaveStatus(ELeaveStatus.PENDING);
        leave.setStatusDate(LocalDate.now());
        leaveRepository.save(leave);

        return true;
    }



    private double getMultiplierForAnnualLeave(ELeaveType leaveType, UserDetails personelDetails) {
        double multiplierForAnnualLeave;
        long numberOfDaysWorked = ChronoUnit.DAYS.between( personelDetails.getHireDate(), LocalDate.now());
        if (numberOfDaysWorked<365){
            if(leaveType.equals(ELeaveType.YILLIK_IZIN)) throw new IKProjeException(ErrorType.NOT_ELIGIBLE_FOR_ANNUAL_LEAVE);
            multiplierForAnnualLeave=0;
        }
        else if (numberOfDaysWorked < 1825) {
            multiplierForAnnualLeave=1;
        }
        else if (numberOfDaysWorked < 2555) {
            multiplierForAnnualLeave=1.5;
        }
        else if (numberOfDaysWorked < 3650) {
            multiplierForAnnualLeave=2;
        }
        else {
            multiplierForAnnualLeave=2.5;
        }
        return multiplierForAnnualLeave;
    }
    
    // Kullanılan izin günleri
    public Integer usedLeaveDays(String token){
        User personel = getUserByToken(token);
        LeaveDetails leaveDetails = leaveDetailsService.findByUserId(personel.getId())
                                                       .orElseThrow(() -> new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        
        Leave lastAnnualLeave = leaveRepository.findTopByUserIdAndLeaveTypeOrderByEndDateDesc(personel.getId(), ELeaveType.YILLIK_IZIN)
                                               .orElseThrow(() -> new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        
        long numberOfLeaveDays = ChronoUnit.DAYS.between(lastAnnualLeave.getStartDate(), lastAnnualLeave.getEndDate());
        return (int) numberOfLeaveDays;
    
    }
    
    // Kalan yıllık izin günleri
    public Integer remainingAnnualLeaveDays(String token) {
        User personel = getUserByToken(token);
        LeaveDetails leaveDetails = leaveDetailsService.findByUserId(personel.getId())
                                                       .orElseThrow(() -> new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        
        return leaveDetails.getNumberOfDaysRemainingFromAnnualLeave();
    }


    //Şirket yöneticisine gelen izin istekleri
    public List<VwAllPersonelLeaveList> getAllLeaveRequests(String token){
        User companyManager = getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        return leaveRepository.findAllVwPersonelLeaveList(companyManager.getCompanyId());
    }

    //personelin talepte bulunduğu izin listesi
    public List<Leave> getPersonelRequestLeaveList(String token){
        User personel = getUserByToken(token);
        if(!personel.getUserRole().equals(EUserRole.EMPLOYEE)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        return leaveRepository.findAllByUserId(personel.getId());
    }

    //personelin talepte bulunduğu izni güncellenmesi
    public Boolean updatePersonelLeaveRequest(UpdateLeaveRequstDto dto){
        User user = getUserByToken(dto.token());
        Leave leaveToUpdate = leaveRepository.findById(dto.leaveId()).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        if(!leaveToUpdate.getUserId().equals(user.getId()) || !leaveToUpdate.getLeaveStatus().equals(ELeaveStatus.PENDING)) return false; //hata fırlatmak yerine direk successi false döndürdüm.
        leaveToUpdate.setLeaveType(dto.leaveType());
        leaveToUpdate.setDescription(dto.description());
        leaveToUpdate.setStartDate(dto.startDate());
        leaveToUpdate.setEndDate(dto.endDate());
        leaveRepository.save(leaveToUpdate);
        return true;
    }

    //Hard delete yapıyorum, state passive almaya gerek yok diye düşündüm.
    public Boolean deletePersonelLeaveRequest(String token,Long leaveId){
        User user = getUserByToken(token);
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        if(!leave.getUserId().equals(user.getId()) || !leave.getLeaveStatus().equals(ELeaveStatus.PENDING)) return false; //PENDING değilse güncelleyip silemesin.
        leaveRepository.delete(leave);
        return true;
    }


    
    @Transactional
    public Boolean approveLeaveRequest(String token,Long leaveId){
        User companyManager = getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        LeaveDetails leaveDetails = leaveDetailsService.findByUserId(leave.getUserId()).orElseThrow(() -> new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        long numberOfLeaveDays = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate());
        updateLeaveDetails(leaveDetails,leave.getLeaveType(),numberOfLeaveDays);
        leave.setLeaveStatus(ELeaveStatus.APPROVED);
        leave.setStatusDate(LocalDate.now());
        leaveRepository.save(leave);
        User user = userService.findById(leave.getUserId()).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        if (Objects.equals(leave.getStartDate(), LocalDate.now()))
        {
            user.setUserWorkStatus(EUserWorkStatus.ON_LEAVE);
        }
        userService.save(user);
        String personelMail = userService.findById(leave.getUserId()).get().getEmail();
        emailService.sendApprovedLeaveNotificationEmail(personelMail,leave);
        return true;
    }
    

    
    public Boolean rejectLeaveRequest(String token,Long leaveId,String rejectionMessage){
        
        User companyManager = getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        leave.setLeaveStatus(ELeaveStatus.REJECTED);
        leave.setStatusDate(LocalDate.now());
        leave.setManagerName(companyManager.getFirstName()+" "+companyManager.getLastName()); //reddeden yöneticinin ad soyadı
        leave.setRejectResponse(rejectionMessage);//ret sebebi
        leaveRepository.save(leave);
        String personelMail = userService.findById(leave.getUserId()).get().getEmail();
        emailService.sendRejectedLeaveNotificationEmail(personelMail,leave,rejectionMessage);
        return true;
    }

    private void updateLeaveDetails(LeaveDetails leaveDetails, ELeaveType leaveType, long numberOfLeaveDays) {
        if (leaveType == ELeaveType.YILLIK_IZIN) {
            leaveDetails.setNumberOfDaysRemainingFromAnnualLeave(
                    (int) (leaveDetails.getNumberOfDaysRemainingFromAnnualLeave() - numberOfLeaveDays)
            );

        }
//        else {
//            throw new IKProjeException(ErrorType.LEAVE_ERROR);
//        }
    }

    public void checkPersonelsWorkStatus(User user){
        Leave leave = leaveRepository.findTopByUserIdOrderByEndDateDesc(user.getId()).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        if(LocalDate.now().isAfter(leave.getEndDate())){
            //Eğer personelin izin süresi bittiyse tekrar workStatusu WORKING yapsın.
            user.setUserWorkStatus(EUserWorkStatus.WORKING);
        }
    }

    // geçersiz token bilgisi olayına bak !!!
    private User getUserByToken(String token){
        Long userId = jwtManager.validateToken(token).orElseThrow(() -> new IKProjeException(ErrorType.INVALID_TOKEN));
        return userService.findById(userId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
    }


}