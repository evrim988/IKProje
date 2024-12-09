package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.ikproje.entity.enums.EUserRole;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblUser")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;
    String email;
    String username;
    String password;
    String birthDate;
    Long companyId;
    Double salary;
    String region;
    String address;
    String phone;
    String avatarUrl;

    @Enumerated(EnumType.STRING)
    EUserRole userRole;
}
