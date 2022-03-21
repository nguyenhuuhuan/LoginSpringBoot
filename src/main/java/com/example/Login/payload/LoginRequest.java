package com.example.Login.payload;

public class LoginRequest {

    private String usernameOrEmail;

    private String password;

    public String getUserNameOrEmail() {
        return usernameOrEmail;
    }

    public void setUserNameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
