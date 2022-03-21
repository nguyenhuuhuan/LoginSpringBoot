package com.example.Login.repository;

import com.example.Login.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
//    Optional<AppUser> findByEmail(String email);

//    Optional<AppUser> findByUsername(String userName);

    Optional<AppUser> findByUserNameOrEmail(String userName, String email);

//    List<AppUser> findById(List<Long> userId);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

}
