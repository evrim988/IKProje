package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblComment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    //Her şirketin yöneticisinin 1 tane commenti olmalı
    @Column(nullable = false,unique = true)
    Long companyManagerId;
    @Column(nullable = false)
    String content;

    //Bu ikisini belki profilde olandan farklı resim yüklemek isterler diye koydum
    String managerPhoto;
    String companyLogo;

}
