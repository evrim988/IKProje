package org.example.ikproje.repository;

import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EIsApproved;
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
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findOptionalByEmailAndPassword(String email, String password);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByPhone(String phone);
	
	Optional<User> findByEmail(String email);

	List<User> findAllByStateAndUserRoleAndCompanyIdAndUserWorkStatus(EState state, EUserRole role, Long companyId, EUserWorkStatus userWorkStatus);

	List<User> findAllByCompanyIdAndUserWorkStatusAndStateAndUserRole(Long companyId, EUserWorkStatus userWorkStatus, EState state, EUserRole role);

	@Query("SELECT new org.example.ikproje.view.VwPersonel(u.id,u.firstName,u.lastName,u.email,c.name,u.phone,u.avatarUrl,u.userRole,a.region,a.city,a.district,a.neighbourhood,a.street,a.postalCode,a.aptNumber,ud.hireDate,ud.tcNo,ud.sgkNo,ud.birthDate,ud.departmentType) FROM User u JOIN Company c ON u.companyId=c.id JOIN UserDetails ud ON u.id=ud.userId JOIN Address a ON ud.addressId=a.id WHERE u.id=?1")
	Optional<VwPersonel> findVwPersonelByUserId(Long id);

	@Query("SELECT new org.example.ikproje.view.VwCompanyManager(u.id,u.companyId,u.firstName,u.lastName,u.email,u.phone,u.avatarUrl,u.userRole,a.region,a.city,a.district,a.neighbourhood,a.street,a.postalCode,a.aptNumber,ud.hireDate,ud.tcNo,ud.sgkNo,ud.birthDate,ud.departmentType,ca.region,ca.city,ca.district,ca.neighbourhood,ca.street,ca.postalCode,ca.aptNumber,c.logo,c.name,c.phone,c.foundationDate,c.industry) FROM User u JOIN Company c ON u.companyId=c.id JOIN UserDetails ud ON u.id=ud.userId JOIN Address a ON ud.addressId=a.id JOIN Address ca ON c.addressId=ca.id WHERE u.id=?1")
	Optional<VwCompanyManager> findVwCompanyManagerByUserId(Long id);

	@Query("SELECT new org.example.ikproje.view.VwPersonel(u.id,u.firstName,u.lastName,u.email,c.name,u.phone,u.avatarUrl,u.userRole,a.region,a.city,a.district,a.neighbourhood,a.street,a.postalCode,a.aptNumber,ud.hireDate,ud.tcNo,ud.sgkNo,ud.birthDate,ud.departmentType) FROM User u JOIN Company c ON u.companyId=c.id JOIN UserDetails ud ON u.id=ud.userId JOIN Address a ON ud.addressId=a.id " +
			"WHERE c.id=?1 AND u.userRole=?2 ORDER BY u.id")
	List<VwPersonel> findAllVwPersonelByCompanyId(Long companyId, EUserRole userRole);

	@Query("SELECT NEW org.example.ikproje.view.VwUnapprovedAccounts(u.id,u.firstName,u.lastName,u.phone,u.email,c.name) FROM User u JOIN Company c ON u.companyId=c.id WHERE (u.isMailVerified=true AND u.isApproved=org.example.ikproje.entity.enums.EIsApproved.PENDING) ")
	List<VwUnapprovedAccounts> getAllUnapprovedAccounts();

	@Query("SELECT new org.example.ikproje.view.VwPersonelSummary(u.id,u.firstName,u.lastName, u.email,ud.birthDate,ud.hireDate,ud.departmentType,u.state,u.userWorkStatus) FROM User u JOIN UserDetails ud ON u.id=ud.userId WHERE u.companyId=?1 AND u.userRole=org.example.ikproje.entity.enums.EUserRole.EMPLOYEE AND u.state=org.example.ikproje.entity.enums.EState.ACTIVE ORDER BY u.id")
	List<VwPersonelSummary> findAllVwPersonelSummary(Long companyId);

	@Query("SELECT u.email FROM User u WHERE u.id=?1")
	Optional<String> findEmailByUserId(Long id);

	@Query("SELECT u.id FROM User u WHERE u.companyId=?1 AND u.userRole=org.example.ikproje.entity.enums.EUserRole.COMPANY_MANAGER")
	Optional<Long> findCompanyManagerIdByCompanyId(Long companyId);

	Optional<Long> countByCompanyIdAndState(Long companyId,EState state);
	
	Long countByIsApprovedAndUserRoleAndState(EIsApproved isApproved, EUserRole userRole, EState state);

	Long countByUserWorkStatusAndStateAndCompanyId(EUserWorkStatus userWorkStatus, EState state,Long companyId);

	@Query("SELECT ud.departmentType, Count(u.id) FROM User u JOIN UserDetails ud ON ud.userId=u.id WHERE u.companyId=?1 GROUP BY ud.departmentType")
	List<Object[]> findAllDepartmentsInCompany(Long companyId);
	
	@Query("SELECT u.gender, COUNT(u.id) FROM User u WHERE u.companyId=?1 GROUP BY u.gender ORDER BY  u.gender DESC")
	List<Object[]> findGenderDistribution(Long companyId);


	@Query("SELECT u.id FROM User u WHERE u.companyId=?1")
	List<Long> findAllPersonelIdByCompanyId(Long companyId);

}