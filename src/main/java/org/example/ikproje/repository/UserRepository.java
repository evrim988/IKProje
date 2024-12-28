package org.example.ikproje.repository;

import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.entity.enums.EUserWorkStatus;
import org.example.ikproje.view.VwCompanyManager;
import org.example.ikproje.view.VwPersonel;
import org.example.ikproje.view.VwPersonelSummary;
import org.example.ikproje.view.VwUnapprovedAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findOptionalByEmailAndPassword(String email, String password);
	
	Boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

	List<User> findAllByStateAndUserRoleAndCompanyIdAndUserWorkStatus(EState state, EUserRole role, Long companyId, EUserWorkStatus userWorkStatus);

	@Query("SELECT new org.example.ikproje.view.VwPersonel(u.id,u.firstName,u.lastName,u.email,c.name,u.phone,u.avatarUrl,u.userRole,a.region,a.city,a.district,a.neighbourhood,a.street,a.postalCode,a.aptNumber,ud.hireDate,ud.tcNo,ud.sgkNo,ud.birthDate,ud.departmentType) FROM User u JOIN Company c ON u.companyId=c.id JOIN UserDetails ud ON u.id=ud.userId JOIN Address a ON ud.addressId=a.id WHERE u.id=?1")
	Optional<VwPersonel> findVwPersonelByUserId(Long id);

	@Query("SELECT new org.example.ikproje.view.VwCompanyManager(u.id,u.companyId,u.firstName,u.lastName,u.email,u.phone,u.avatarUrl,u.userRole,a.region,a.city,a.district,a.neighbourhood,a.street,a.postalCode,a.aptNumber,ud.hireDate,ud.tcNo,ud.sgkNo,ud.birthDate,ud.departmentType,ca.region,ca.city,ca.district,ca.neighbourhood,ca.street,ca.postalCode,ca.aptNumber,c.logo,c.name,c.phone,c.foundationDate,c.industry) FROM User u JOIN Company c ON u.companyId=c.id JOIN UserDetails ud ON u.id=ud.userId JOIN Address a ON ud.addressId=a.id JOIN Address ca ON c.addressId=ca.id WHERE u.id=?1")
	Optional<VwCompanyManager> findVwCompanyManagerByUserId(Long id);

	@Query("SELECT new org.example.ikproje.view.VwPersonel(u.id,u.firstName,u.lastName,u.email,c.name,u.phone,u.avatarUrl,u.userRole,a.region,a.city,a.district,a.neighbourhood,a.street,a.postalCode,a.aptNumber,ud.hireDate,ud.tcNo,ud.sgkNo,ud.birthDate,ud.departmentType) FROM User u JOIN Company c ON u.companyId=c.id JOIN UserDetails ud ON u.id=ud.userId JOIN Address a ON ud.addressId=a.id " +
			"WHERE c.id=?1 AND u.userRole=?2")
	List<VwPersonel> findAllVwPersonelByCompanyId(Long companyId, EUserRole userRole);

	@Query("SELECT NEW org.example.ikproje.view.VwUnapprovedAccounts(u.id,u.firstName,u.lastName,u.phone,u.email,c.name) FROM User u JOIN Company c ON u.companyId=c.id WHERE (u.isMailVerified=true AND u.isApproved=org.example.ikproje.entity.enums.EIsApproved.PENDING) ")
	List<VwUnapprovedAccounts> getAllUnapprovedAccounts();

	@Query("SELECT new org.example.ikproje.view.VwPersonelSummary(u.id,u.firstName,u.lastName,ud.birthDate,ud.hireDate,ud.departmentType,u.state,u.userWorkStatus) FROM User u JOIN UserDetails ud ON u.id=ud.userId WHERE u.companyId=?1 AND u.userRole=org.example.ikproje.entity.enums.EUserRole.EMPLOYEE AND u.state=org.example.ikproje.entity.enums.EState.ACTIVE")
	List<VwPersonelSummary> findAllVwPersonelSummary(Long companyId);

	@Query("SELECT u.email FROM User u WHERE u.id=?1")
	Optional<String> findEmailByUserId(Long id);

	@Query("SELECT u.id FROM User u WHERE u.companyId=?1 AND u.userRole=org.example.ikproje.entity.enums.EUserRole.COMPANY_MANAGER")
	Optional<Long> findCompanyManagerIdByCompanyId(Long companyId);

}