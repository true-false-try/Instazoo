package com.example.instazoo.repository;

import com.example.instazoo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserName(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);
}
