package com.hegde.todo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
public class Dashboard {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String project;

    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL)
    private Set<DashboardUser> dashboardUsers;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @PrePersist
    private void setDateCreated(){
        this.dateCreated = OffsetDateTime.now();
        this.lastUpdated = OffsetDateTime.now();
    }

    @PreUpdate
    private void setDateUpdated(){
        this.lastUpdated = OffsetDateTime.now();
    }
}
