package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tblAdres")
public class Address extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long userId; // sonradan eklendi. MS 16.12.2024
	String region;
	String city;
	String district;
	String neighbourhood;
	String street;
	String postalCode;
	String aptNumber;
}