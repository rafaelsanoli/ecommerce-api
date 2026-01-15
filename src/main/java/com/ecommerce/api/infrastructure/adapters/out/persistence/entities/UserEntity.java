package com.ecommerce.api.infrastructure.adapters.out.persistence.entities;
}
    }
        ADMIN
        CUSTOMER,
    public enum RoleEnum {

    }
        updatedAt = LocalDateTime.now();
    protected void onUpdate() {
    @PreUpdate

    }
        }
            active = true;
        if (active == null) {
        createdAt = LocalDateTime.now();
    protected void onCreate() {
    @PrePersist

    private LocalDateTime updatedAt;
    @Column(name = "updated_at")

    private LocalDateTime createdAt;
    @Column(name = "created_at", nullable = false, updatable = false)

    private Boolean active;
    @Column(nullable = false)

    private RoleEnum role;
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)

    private String fullName;
    @Column(name = "full_name", length = 100)

    private String password;
    @Column(nullable = false)

    private String email;
    @Column(unique = true, nullable = false, length = 100)

    private String username;
    @Column(unique = true, nullable = false, length = 50)

    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

public class UserEntity {
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "users")
@Entity

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;


