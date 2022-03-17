package com.example.Login.repository;

import com.example.Login.entity.AppRole;
import com.example.Login.entity.RoleName;
import com.example.Login.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByName(RoleName name);
}
