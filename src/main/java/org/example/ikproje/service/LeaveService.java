package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.NewLeaveRequestDto;
import org.example.ikproje.entity.Leave;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EGender;
import org.example.ikproje.entity.enums.ELeaveStatus;
import org.example.ikproje.entity.enums.ELeaveType;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.mapper.LeaveMapper;
import org.example.ikproje.repository.LeaveRepository;
import org.example.ikproje.utility.JwtManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final UserService userService;
    private final JwtManager jwtManager;
    private final EmailService emailService;

    public Boolean createNewLeaveRequest(NewLeaveRequestDto dto){
        User personel = getUserByToken(dto.token());
        if (personel.getUserRole()==EUserRole.COMPANY_MANAGER){
            throw new IKProjeException(ErrorType.UNAUTHORIZED);
        }
        if (personel.getGender()== EGender.MALE&&dto.leaveType()== ELeaveType.DOGUM_IZNI){
            throw new IKProjeException(ErrorType.UNAUTHORIZED);
        }
        Leave leave = LeaveMapper.INSTANCE.fromNewLeaveDto(dto);
        leave.setCompanyId(personel.getCompanyId());
        leave.setUserId(personel.getId());
        leave.setLeaveStatus(ELeaveStatus.PENDING);
        leave.setStatusDate(LocalDate.now());
        leaveRepository.save(leave);
        return true;
    }


    //Şirket yöneticisine gelen izin istekleri
    public List<Leave> getAllLeaveRequests(String token){
        User companyManager = getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        return leaveRepository.findAllByLeaveStatusAndCompanyId(ELeaveStatus.PENDING,companyManager.getCompanyId());
    }


    public Boolean approveLeaveRequest(String token,Long leaveId){
        User companyManager = getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
        leave.setLeaveStatus(ELeaveStatus.APPROVED);
        leave.setStatusDate(LocalDate.now());
        leaveRepository.save(leave);
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
        leaveRepository.save(leave);
        String personelMail = userService.findById(leave.getUserId()).get().getEmail();
        emailService.sendRejectedLeaveNotificationEmail(personelMail,leave,rejectionMessage);
        return true;
    }

    // geçersiz token bilgisi olayına bak !!!
    private User getUserByToken(String token){
        Long userId = jwtManager.validateToken(token).orElseThrow(() -> new IKProjeException(ErrorType.INVALID_TOKEN));
        return userService.findById(userId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
    }
}