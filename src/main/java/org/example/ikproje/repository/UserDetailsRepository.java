package org.example.ikproje.repository;

import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserDetails;
import org.example.ikproje.view.VwPersonelForUpcomingBirthday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
	Optional<UserDetails> findByUserId(Long userId);
	
	@Query(value = """
			 SELECT ud.user_id
             FROM tbl_user_details ud
             INNER JOIN tbl_user u ON ud.user_id = u.id
             WHERE
                 u.company_id = :companyId
             AND (
                 EXTRACT(DOY FROM ud.birth_date) - EXTRACT(DOY FROM CURRENT_DATE) BETWEEN 0 AND 10
             OR
                 (EXTRACT(DOY FROM ud.birth_date) < 10 AND EXTRACT(DOY FROM CURRENT_DATE) > EXTRACT(DOY FROM '2024-12-31'::DATE)))
			""", nativeQuery = true)
	List<Long> findUserIdsWithUpcomingBirthdays(@Param("companyId") Long companyId);
	
	@Query("SELECT new org.example.ikproje.view.VwPersonelForUpcomingBirthday(u.firstName, u.lastName, ud.birthDate, ud" +
			".departmentType) " +
			"FROM UserDetails ud " +
			"JOIN User u ON ud.userId=u.id " +
			"WHERE ud.userId IN :ids")
	List<VwPersonelForUpcomingBirthday> findPersonelByIds(@Param("ids") List<Long> ids);
}