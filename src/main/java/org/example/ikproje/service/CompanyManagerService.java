package org.example.ikproje.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.CreateNewPersonelRequestDto;
import org.example.ikproje.dto.request.UpdateCompanyManagerProfileRequestDto;
import org.example.ikproje.dto.request.UpdatePersonelStateRequestDto;
import org.example.ikproje.entity.Address;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
import org.example.ikproje.entity.enums.ELeaveType;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.UserRepository;
import org.example.ikproje.utility.EncryptionManager;
import org.example.ikproje.utility.JwtManager;
import org.example.ikproje.view.VwCompanyManager;
import org.example.ikproje.view.VwPersonelSummary;
import org.example.ikproje.view.VwUnapprovedAccounts;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyManagerService {
    private final UserRepository userRepository;
    private final JwtManager jwtManager;
    private final CloudinaryService cloudinaryService;
    private final CompanyService companyService;
    private final AddressService addressService;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final UserDetailsService userDetailsService;

    public void addLogoToCompany(String token, MultipartFile file) throws IOException {
        User user = getUserByToken(token);
        if (!user.getUserRole().equals(EUserRole.COMPANY_MANAGER)){
            throw new IKProjeException(ErrorType.UNAUTHORIZED);
        }
        Optional<Company> optCompany = companyService.findById(user.getCompanyId());
        if (optCompany.isEmpty()){
            throw new IKProjeException(ErrorType.COMPANY_NOTFOUND);
        }
        Company company = optCompany.get();
        company.setLogo(cloudinaryService.uploadFile(file));
        companyService.save(company);
    }




    public VwCompanyManager getCompanyManagerProfile(String token){
        Optional<Long> userIdOpt = jwtManager.validateToken(token);
        if (userIdOpt.isEmpty()) throw new IKProjeException(ErrorType.INVALID_TOKEN);
        Optional<VwCompanyManager> vwCompanyManagerOptional = userRepository.findVwCompanyManagerByUserId(userIdOpt.get());
        if(vwCompanyManagerOptional.isEmpty()) throw new IKProjeException(ErrorType.USER_NOTFOUND);
        return vwCompanyManagerOptional.get();
    }

    @Transactional
    public Boolean addNewPersonel(CreateNewPersonelRequestDto dto){
        if(userRepository.existsByEmail(dto.email())) throw new IKProjeException(ErrorType.MAIL_ALREADY_EXIST);
        if(!dto.password().equals(dto.rePassword())) throw new IKProjeException(ErrorType.PASSWORDS_NOT_MATCH);
        User companyManager = getUserByToken(dto.token());
        User personel = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(EncryptionManager.getEncryptedPassword(dto.password()))
                .phone(dto.phone())
                .gender(dto.gender())
                .companyId(companyManager.getCompanyId())
                .userRole(EUserRole.EMPLOYEE)
                .build();
        userRepository.save(personel);
        Address personelAddress = Address.builder().build();
        addressService.save(personelAddress); // adres ile ilgili bilgileri personel kendi sayfasında doldursun.
        UserDetails personelDetails = UserDetails.builder()
                .userId(personel.getId())
                .addressId(personelAddress.getId())
                .birthDate(dto.birthDate())
                .departmentType(dto.departmentType())
                .hireDate(dto.hireDate())
                .salary(dto.salary())
                .sgkNo(dto.sgkNo())
                .tcNo(dto.tcNo())
                .build();
        userDetailsService.save(personelDetails);
        emailService.sendEmail(personel.getEmail(),verificationTokenService.generateVerificationToken(personel.getId()));
        return true;
    }

    //personeli aktif ya da pasif hale getirmek için. Komple silmek için ayrı bir metod gerekli mi ?
    public Boolean updatePersonalState(UpdatePersonelStateRequestDto dto){
        User companyManager = getUserByToken(dto.token());
        Optional<User> personelOpt = userRepository.findById(dto.personelId());
        if (personelOpt.isEmpty()) throw new IKProjeException(ErrorType.USER_NOTFOUND);
        User personel = personelOpt.get();
        if(!companyManager.getCompanyId().equals(personel.getCompanyId())) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        if(personel.getState().equals(EState.PASSIVE) || dto.stateToChange().equals(EState.ACTIVE)){
            emailService.sendPersonelActivationConfirmationEmail(personel.getEmail());
        }
        personel.setState(dto.stateToChange());
        userRepository.save(personel);
        return true;
    }

    public List<VwPersonelSummary> getPersonelList(String token){
        User companyManager = getUserByToken(token);
        return userRepository.findAllVwPersonelSummary(companyManager.getCompanyId());
    }

    @Transactional
    public Boolean updateCompanyManagerProfile(UpdateCompanyManagerProfileRequestDto dto){
        User companyManager = getUserByToken(dto.token());
        if(!dto.id().equals(companyManager.getId())) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        companyManager.setFirstName(dto.firstName());
        companyManager.setLastName(dto.lastName());
        companyManager.setEmail(dto.email());
        companyManager.setPhone(dto.phone());
        companyManager.setUpdateAt(System.currentTimeMillis());
        userRepository.save(companyManager);

        UserDetails companyManagerDetails = userDetailsService.findByUserId(companyManager.getId());
        companyManagerDetails.setSalary(dto.salary());
        companyManagerDetails.setHireDate(dto.hireDate());
        companyManagerDetails.setTcNo(dto.tcNo());
        companyManagerDetails.setSgkNo(dto.sgkNo());
        companyManagerDetails.setBirthDate(dto.birthDate());
        companyManagerDetails.setDepartmentType(dto.departmentType());
        companyManagerDetails.setUpdateAt(System.currentTimeMillis());
        userDetailsService.save(companyManagerDetails);

        Address companyManagerAddress = addressService.findById(companyManagerDetails.getAddressId());
        companyManagerAddress.setRegion(dto.region());
        companyManagerAddress.setCity(dto.city());
        companyManagerAddress.setDistrict(dto.district());
        companyManagerAddress.setNeighbourhood(dto.neighbourhood());
        companyManagerAddress.setStreet(dto.street());
        companyManagerAddress.setPostalCode(dto.postalCode());
        companyManagerAddress.setAptNumber(dto.aptNumber());
        companyManagerAddress.setUpdateAt(System.currentTimeMillis());
        addressService.save(companyManagerAddress);

        Company company = companyService.findById(companyManager.getCompanyId()).get();
        company.setName(dto.companyName());
        company.setPhone(dto.companyPhone());
        company.setFoundationDate(dto.companyFoundationDate());
        company.setIndustry(dto.companyIndustry());

        Address companyAddress = addressService.findById(company.getAddressId());
        companyAddress.setRegion(dto.companyRegion());
        companyAddress.setCity(dto.companyCity());
        companyAddress.setDistrict(dto.companyDistrict());
        companyAddress.setStreet(dto.companyStreet());
        companyAddress.setNeighbourhood(dto.companyNeighbourhood());
        companyAddress.setPostalCode(dto.companyPostalCode());
        companyAddress.setAptNumber(dto.companyAptNumber());
        companyAddress.setUpdateAt(System.currentTimeMillis());
        addressService.save(companyAddress);
        return true;
    }


    public List<VwUnapprovedAccounts> getAllUnapprovedAccounts(){
        return userRepository.getAllUnapprovedAccounts();
    }

    private User getUserByToken(String token){
        Optional<Long> userIdOpt = jwtManager.validateToken(token);
        if (userIdOpt.isEmpty()) throw new IKProjeException(ErrorType.INVALID_TOKEN);
        Optional<User> optUser = userRepository.findById(userIdOpt.get());
        if (optUser.isEmpty()) throw new IKProjeException(ErrorType.USER_NOTFOUND);
        return optUser.get();
    }


}