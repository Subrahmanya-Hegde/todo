package com.hegde.todo.repository;

import com.hegde.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findByUuidIn(List<String> userIds);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);

}
