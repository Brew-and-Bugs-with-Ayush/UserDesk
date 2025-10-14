package com.ayush.userdesk.repository;

import com.ayush.userdesk.domain.entity.User;
import com.ayush.userdesk.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Object> findByRolesContaining(Role role);
}
