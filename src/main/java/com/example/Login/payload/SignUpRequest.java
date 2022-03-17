package com.example.Login.payload;

import javax.validation.constraints.Size;

public class SignUpRequest {

    @Size(min = 4, max = 15)
    private String username;

    @Size(max = 40)
    private String email;

    @Size(min = 6, max = 20)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
