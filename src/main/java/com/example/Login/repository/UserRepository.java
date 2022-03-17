package com.example.Login.repository;

import com.example.Login.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmailOrUsername(String email, String username);

    List<AppUser> findByIn(List<Long> userId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
