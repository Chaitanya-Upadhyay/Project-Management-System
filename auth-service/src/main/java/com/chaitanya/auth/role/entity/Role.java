package com.chaitanya.auth.role.entity;


import com.chaitanya.auth.common.entity.BaseEntity;
import com.chaitanya.auth.common.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_role_name",
                        columnNames = "name"
                )
        }
)
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 50)
    private RoleType name;

    @Column(name = "description", length = 255)
    private String description;
}
