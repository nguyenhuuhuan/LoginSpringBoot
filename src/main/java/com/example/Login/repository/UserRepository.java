package com.example.Login.repository;

import com.example.Login.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
//    Optional<AppUser> findByEmail(String email);

    @Query(value = "SELECT u FROM app_user u WHERE u.user_name = :user_name", nativeQuery = true)
    AppUser getUserByUsername(@Param("user_name") String userName);

    @Query(value ="SELECT * FROM app_user m WHERE m.user_name = ?1 or m.email = ?2", nativeQuery = true)
    AppUser findByUserNameOrEmail(String userName, String email);

//    List<AppUser> findById(List<Long> userId);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

}
