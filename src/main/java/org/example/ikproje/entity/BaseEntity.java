package org.example.ikproje.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.ikproje.entity.enums.EState;
import org.hibernate.annotations.CreationTimestamp;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    Long createAt;

    Long updateAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EState state = EState.ACTIVE;

    @PrePersist
    protected void onCreate() {
        long now = System.currentTimeMillis();
        this.createAt = now;
        this.updateAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = System.currentTimeMillis();
    }
}
