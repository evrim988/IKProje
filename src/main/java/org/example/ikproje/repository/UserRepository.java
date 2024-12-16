package org.example.ikproje.repository;

import org.example.ikproje.entity.User;
import org.example.ikproje.view.VwPersonel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findOptionalByEmailAndPassword(String email, String password);
	
	Boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

	@Query("SELECT new org.example.ikproje.view.VwPersonel(u.id,u.firstName,u.lastName,u.email,c.name,u.phone,u.avatarUrl,u.userRole,a.region,a.city,a.district,a.neighbourhood,a.street,a.postalCode,a.aptNumber,ud.hireDate,ud.tcNo,ud.sgkNo,ud.birthDate,ud.departmentType) FROM User u JOIN Company c ON u.companyId=c.id JOIN UserDetails ud ON u.id=ud.userId JOIN Address a ON u.id=a.userId WHERE u.id=?1")
	Optional<VwPersonel> findVwPersonelByUserId(Long id);
}