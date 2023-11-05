package com.hegde.todo.repository;

import com.hegde.todo.domain.DashboardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardUserRepository extends JpaRepository<DashboardUser, Long> {
}
