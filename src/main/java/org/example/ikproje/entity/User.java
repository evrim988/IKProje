package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.ikproje.entity.enums.EGender;
import org.example.ikproje.entity.enums.EIsApproved;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.entity.enums.EUserWorkStatus;

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
    String password;
    Long companyId;
    String phone;
    String avatarUrl;
    @Enumerated(EnumType.STRING)
    EGender gender;
    @Enumerated(EnumType.STRING)
    EUserRole userRole;
    @Builder.Default
    Boolean isMailVerified=false;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    EIsApproved isApproved=EIsApproved.REJECTED;
    @Builder.Default
    EUserWorkStatus userWorkStatus = EUserWorkStatus.WORKING;


}