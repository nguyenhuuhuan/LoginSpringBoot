package com.example.Login.entity;

import javax.persistence.*;

@Entity
@Table(name = "App_Role",
        uniqueConstraints = {
            @UniqueConstraint(name = "APP_ROLE_PK", columnNames = "Role_Name")
        })
public class AppRole {

    @Id
    @GeneratedValue
    @Column(name = "Role_Id", nullable = false)
    private Long roleId;

    @Column(name = "Role_Name", length = 30, nullable = false)
    private RoleName roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
