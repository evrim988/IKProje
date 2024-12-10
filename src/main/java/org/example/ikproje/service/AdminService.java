package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.LoginRequestDto;
import org.example.ikproje.entity.Admin;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.AdminRepository;
import org.example.ikproje.utility.JwtManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final JwtManager jwtManager;

    public String login(LoginRequestDto dto) {
        Optional<Admin> adminOptional = adminRepository.findByEmailAndPassword(dto.email(),dto.password());
        if (adminOptional.isPresent()) {
            return jwtManager.createAdminToken(adminOptional.get().getId(), dto.email());
        }
        throw new IKProjeException(ErrorType.ADMIN_NOT_FOUND);
    }

    public List<Admin> testFindAll(){
        return adminRepository.findAll();
    }
}
