package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.ikproje.entity.enums.EUserDepartmentType;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tblUserDetails")
public class UserDetails extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long userId;
	Double salary;
	Long addressId;
	LocalDate hireDate;
	String tcNo;
	String sgkNo;
	LocalDate birthDate;
	@Enumerated(EnumType.STRING)
	EUserDepartmentType departmentType;
}