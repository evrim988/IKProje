package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.ikproje.entity.enums.EExpenseStatus;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_expense")
public class Expense extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userId;
    Long approverId;
    @Enumerated(EnumType.STRING)
    EExpenseStatus status;
    Double amount;
    String description;
    String receiptUrl; //image ya da dijital bir fiş adresi olabilir

}
