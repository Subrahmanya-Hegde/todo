package com.hegde.todo.repository;

import com.hegde.todo.domain.Task;
import com.hegde.todo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.createdBy = :createdBy OR t.assignedTo = :assignedTo")
    Page<Task> findByCreatedByOrAssignedTo(String createdBy, User assignedTo, PageRequest pageRequest);

    @Query("SELECT t FROM Task t WHERE t.id = :id AND (t.createdBy = :createdBy OR t.assignedTo = :assignedTo)")
    Optional<Task> findByIdAndCreatedByOrAssignedTo(long id, String createdBy, User assignedTo);
}
