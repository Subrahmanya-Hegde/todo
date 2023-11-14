package com.hegde.todo.repository;

import com.hegde.todo.domain.Task;
import com.hegde.todo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByCreatedByAndAssignedTo(String createdBy, User assignedTo, PageRequest pageRequest);

}
