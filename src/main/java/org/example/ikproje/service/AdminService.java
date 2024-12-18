package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.entity.Admin;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EIsApproved;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.AdminRepository;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwUnapprovedAccounts;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final CompanyService companyService;
    private final UserService userService;
    private final JwtManager jwtManager;
    private final EmailService emailService;

    public String login(LoginRequestDto dto) {
        Optional<Admin> adminOptional = adminRepository.findByEmailAndPassword(dto.email(),dto.password());
        if (adminOptional.isPresent()) {
            return jwtManager.createAdminToken(adminOptional.get().getId(), dto.email());
        }
        throw new IKProjeException(ErrorType.ADMIN_NOT_FOUND);
    }
	
	
	public void approveAccount(Long userId,String confirmationMessage) {
        Optional<User> optUser = userService.findById(userId);
        if (optUser.isPresent()){
                User user = optUser.get();
                if (user.getIsApproved() == EIsApproved.PENDING && user.getIsMailVerified()){
                    user.setIsApproved(EIsApproved.APPROVED);
                    userService.save(user);
                    emailService.sendAdminConfirmationEmail(user.getEmail(),confirmationMessage);
                }
                else {
                    throw new IKProjeException(ErrorType.ACCOUNT_ALREADY_APPROVED);
                }
            }
            else {
                throw new IKProjeException(ErrorType.USER_NOTFOUND);
            }
    }
    
    
    public List<VwUnapprovedAccounts> getAllUnapprovedCompanies() {
       return userService.getAllUnapprovedAccounts();
    }
    
    public void rejectAccount(Long userId,String rejectionMessage) {
        Optional<User> optUser = userService.findById(userId);
        if (optUser.isPresent()){
            User user = optUser.get();
            if (user.getIsApproved() == EIsApproved.PENDING && user.getIsMailVerified()){
                user.setIsApproved(EIsApproved.REJECTED);
                userService.save(user);
                emailService.sendAdminConfirmationEmail(user.getEmail(),rejectionMessage);
            }
            else {
                throw new IKProjeException(ErrorType.ACCOUNT_ALREADY_APPROVED);
            }
        }
        else {
            throw new IKProjeException(ErrorType.USER_NOTFOUND);
        }
    }
}