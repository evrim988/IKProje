package org.example.ikproje.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.dto.request.RegisterRequestDto;
import org.example.ikproje.dto.request.ResetPasswordRequestDto;
import org.example.ikproje.entity.*;
import org.example.ikproje.entity.enums.EIsApproved;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.entity.enums.EUserWorkStatus;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.mapper.*;
import org.example.ikproje.repository.UserRepository;
import org.example.ikproje.utility.EncryptionManager;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwPersonelForUpcomingBirthday;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final CompanyService companyService;
    private final UserDetailsService userDetailsService;
    private final MembershipService membershipService;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final JwtManager jwtManager;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public void register(RegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())){
            throw new IKProjeException(ErrorType.MAIL_ALREADY_EXIST);
        }
        User user = UserMapper.INSTANCE.fromRegisterDto(dto);
        user.setUserRole(EUserRole.COMPANY_MANAGER);
        String encryptedPassword = EncryptionManager.getEncryptedPassword(dto.password());
        user.setEmail(dto.email());
        user.setPassword(encryptedPassword);
        user.setState(EState.PASSIVE);
        Address userAddress = AddressMapper.INSTANCE.toUserAddress(dto);
        Address companyAddress = AddressMapper.INSTANCE.toCompanyAddress(dto);
        addressService.saveAll(Arrays.asList(userAddress, companyAddress));
        UserDetails userDetails = UserDetailsMapper.INSTANCE.fromRegisterDto(dto);
        userDetails.setAddressId(userAddress.getId());
        Company company = CompanyMapper.INSTANCE.fromRegisterDto(dto);
        company.setAddressId(companyAddress.getId());
        companyService.save(company);
        user.setCompanyId(company.getId());
        user.setState(EState.ACTIVE);
        user.setIsApproved(EIsApproved.PENDING);
        user.setGender(dto.gender());
        userRepository.save(user);
        userDetails.setUserId(user.getId());
        userDetailsService.save(userDetails);
        Membership membership = MembershipMapper.INSTANCE.fromRegisterDto(dto);
        membership.setCompanyId(company.getId());
        membership.setPrice(membership.getMembershipType().getDiscountRate()*membership.getPrice());
        membership.setStartDate(membership.getMembershipType().getStartDate());
        membership.setEndDate(membership.getMembershipType().getEndDate());
        membershipService.save(membership);
        //Mail gönderirken alıcı olarak dto'dan gelen (şirket yöneticisi) mail adresi ve oluşturduğum verification
        // token'i giriyorum
        emailService.sendEmail(dto.email(),verificationTokenService.generateVerificationToken(user.getId()));
    }

    /**
     * Bu method'a parametre olarak gelen token, veritabanında var mı diye kontrol ediliyor.
     * Daha sonra token'in süresi dolmuş mu diye kontrol ediliyor.
     * Daha sonra token'in içerisinden companyId alınıp, company'nin varlığı kontrol ediliyor.
     * Eğer tüm şartlar sağlanıyorsa mail onayı gerçekleşiyor ve token'in state'i passive oluyor.
     * @param verificationToken
     */
    public void verifyAccount(String verificationToken){
        Optional<VerificationToken> optVerificationToken = verificationTokenService.findByToken(verificationToken);
        if (optVerificationToken.isEmpty()){
            throw new IKProjeException(ErrorType.INVALID_TOKEN);
        }
        VerificationToken verToken = optVerificationToken.get();
        if (verToken.getState()==EState.PASSIVE){
            throw new IKProjeException(ErrorType.TOKEN_ALREADY_USED);
        }
        long expDate = Long.parseLong(verificationToken.substring(16));
        if (expDate<System.currentTimeMillis()){
            verToken.setState(EState.PASSIVE);
            verificationTokenService.save(verToken);
            throw new IKProjeException(ErrorType.EXP_TOKEN);
        }
        Long userId = verToken.getUserId();
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()){
            throw new IKProjeException(ErrorType.USER_NOTFOUND);
        }
        User user = optUser.get();
        user.setIsMailVerified(true);
        verToken.setState(EState.PASSIVE);
        userRepository.save(user);
        verificationTokenService.save(verToken);
    }


    /**
     * Login metodu ilk yazdığımda çok fazla if içerdiği için çok karmaşık duruyordu.
     * Ben de parçaladım. Aşağıdaki validateUser, checkMailVerification, checkApprovalStatus metotları,
     * login metoduna hizmet ediyor. Gerektiğinde başka metotlarda da kullanılabilir.
     * @param dto
     * @return
     */
    public String login(LoginRequestDto dto) {
        String encryptedPassword = EncryptionManager.getEncryptedPassword(dto.password());

        User user = userRepository.findOptionalByEmailAndPassword(dto.email(), encryptedPassword)
                .orElseThrow(() -> new IKProjeException(ErrorType.INVALID_USERNAME_OR_PASSWORD));

        validateUser(user);

        return jwtManager.createUserToken(user.getId(),user.getUserRole());
    }

    private void validateUser(User user) {
        checkMailVerification(user);
        if (user.getUserRole() == EUserRole.COMPANY_MANAGER) {
            checkApprovalStatus(user);
        }

    }
    private void checkMailVerification(User user) {
        if (!user.getIsMailVerified()) {
            String verificationToken = verificationTokenService.generateVerificationToken(user.getId());
            emailService.sendEmail(user.getEmail(), verificationToken);
            throw new IKProjeException(ErrorType.MAIL_NOT_VERIFIED);
        }
    }

    private void checkApprovalStatus(User user) {
        if (user.getIsApproved() != EIsApproved.APPROVED) {
            throw new IKProjeException(ErrorType.USER_NOT_APPROVED);
        }
    }

    public Boolean forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) throw new IKProjeException(ErrorType.MAIL_NOT_FOUND);
        User user = userOptional.get();
        String token = jwtManager.createResetPasswordToken(user.getId(),email);
        emailService.sendResetPasswordEmail(email,token);
        return true;
    }

    public Boolean resetPassword(String token, ResetPasswordRequestDto dto) {
        User user = getUserByToken(token);
        if(!dto.password().equals(dto.rePassword())) throw new IKProjeException(ErrorType.PASSWORDS_NOT_MATCH);

        user.setPassword(EncryptionManager.getEncryptedPassword(dto.password()));
        userRepository.save(user);
        return true;
    }




    public User getUserByToken(String token){
        Optional<Long> userIdOpt = jwtManager.validateToken(token);
        if (userIdOpt.isEmpty()) throw new IKProjeException(ErrorType.INVALID_TOKEN);
        Optional<User> optUser = userRepository.findById(userIdOpt.get());
        if (optUser.isEmpty()) throw new IKProjeException(ErrorType.USER_NOTFOUND);
        return optUser.get();
    }

    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }
    public void save(User user){
        userRepository.save(user);
    }


    public List<User> findAllPersonelByCompanyId(Long companyId){
        List<User> personelList = userRepository.findAllByCompanyIdAndUserWorkStatusAndStateAndUserRole(companyId, EUserWorkStatus.WORKING, EState.ACTIVE, EUserRole.EMPLOYEE);

        return personelList;
    }



    public void addAvatarToUser(String token,  MultipartFile file) throws IOException {
        User user = getUserByToken(token);
        if (!user.getUserRole().equals(EUserRole.EMPLOYEE)){
            throw new IKProjeException(ErrorType.UNAUTHORIZED);
        }
        user.setAvatarUrl(cloudinaryService.uploadFile(file));
        userRepository.save(user);
    }

    public String findEmailByUserId(Long userId){
        return userRepository.findEmailByUserId(userId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
    }

    public Long findCompanyManagerIdByCompanyId(Long companyId){
        return userRepository.findCompanyManagerIdByCompanyId(companyId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
    }

    public Long getTotalPersonelCountByCompanyManagerId(Long companyManagerId){
        User user = findById(companyManagerId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
        return userRepository.countByCompanyIdAndState(user.getCompanyId(),EState.ACTIVE).orElseThrow(() -> new IKProjeException(ErrorType.COMPANY_NOTFOUND));
    }
    
    // Hem kayıtlı şirket hem de kayıtlı şirket yöneticisi sayısı geliyor.
    public Long activeCompanyCount(){
        return userRepository.countByIsApprovedAndUserRoleAndState(EIsApproved.APPROVED, EUserRole.COMPANY_MANAGER, EState.ACTIVE);
    }
    
    public Long activeEmployeeCount(){
        return userRepository.countByIsApprovedAndUserRoleAndState(EIsApproved.APPROVED,EUserRole.EMPLOYEE,EState.ACTIVE);
    }
    
    public List<VwPersonelForUpcomingBirthday> findUpcomingBirthdays(String token){
        
        Long userId = jwtManager.validateToken(token).orElseThrow(() -> new IKProjeException(ErrorType.INVALID_TOKEN));
        User companyManager = userRepository.findById(userId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
        companyService.findById(companyManager.getCompanyId()).orElseThrow(() -> new IKProjeException(ErrorType.COMPANY_NOTFOUND));
        if (companyManager.getUserRole().equals(EUserRole.EMPLOYEE)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        
        return userDetailsService.findPersonelByIds(userDetailsService.findUserIdsWithUpcomingBirthdays(companyManager.getCompanyId()));
//        return userRepository.findAllById(userDetailsService.findUserIdsWithUpcomingBirthdays(companyManager.getCompanyId()));
    }
}