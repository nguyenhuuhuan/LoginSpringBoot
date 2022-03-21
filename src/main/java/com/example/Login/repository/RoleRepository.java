package com.example.Login.repository;

import com.example.Login.entity.AppRole;
import com.example.Login.entity.RoleName;
import com.example.Login.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByRoleName(RoleName RoleName);
}
