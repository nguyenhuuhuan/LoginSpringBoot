package com.example.Login.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPasswordUtils {

    public static String encryptePassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public void main(String[] args){
        String abc = "123";
        String encryptedPassword = encryptePassword(abc);
        System.out.println("Encrypted Password" + encryptedPassword);
    }
}
