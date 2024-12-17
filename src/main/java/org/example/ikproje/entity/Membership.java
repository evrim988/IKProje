package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.ikproje.entity.enums.EMembershipType;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tblMembership")
public class Membership extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long companyId;
	@Enumerated(EnumType.STRING)
	EMembershipType membershipType;
	@Builder.Default
	Double price=500.0;
	LocalDate startDate;
	LocalDate endDate;
}