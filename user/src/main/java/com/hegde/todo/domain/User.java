package com.hegde.todo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String userName;

    @Column
    private String email;

    @Column
    private String uuid;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private String login;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column
    private LocalDateTime lastUpdated;

    @PrePersist
    private void setDateCreated(){
        this.role = UserRole.USER;
        this.uuid = "USR-" + UUID.randomUUID();
        this.dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    private void setDateUpdated(){
        this.lastUpdated = LocalDateTime.now();
    }
}
