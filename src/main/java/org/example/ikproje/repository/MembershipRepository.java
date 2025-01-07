package org.example.ikproje.repository;

import jakarta.persistence.SqlResultSetMapping;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

	@Query("SELECT DISTINCT m.companyId FROM Membership m WHERE m.endDate BETWEEN :now AND :oneMonthLater")
	List<Long> findExpiringCompanyIds(
			@Param("now")LocalDate now,@Param("oneMonthLater") LocalDate oneMonthLater);

}