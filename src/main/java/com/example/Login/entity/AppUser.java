package com.example.Login.entity;

import com.example.Login.entity.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "App_User",
        uniqueConstraints = {
            @UniqueConstraint(name = "APP_USER_UK1", columnNames = "User_Name"),
                @UniqueConstraint(name = "APP_USER_UK2", columnNames = "Email")
        })
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends DateAudit {

    @Id
    @GeneratedValue
    @Column(name = "User_Id", nullable = false)
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "email", length = 36, nullable = false)
    private String email;

    @Column(name = "Encrypted_Password", length = 128, nullable = false)
    private String encryptedPassword;

    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;

    public AppUser(String username, String email, String password, boolean enabled) {
        this.userName = username;
        this.email = email;
        this.encryptedPassword = password;
        this.enabled = enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
