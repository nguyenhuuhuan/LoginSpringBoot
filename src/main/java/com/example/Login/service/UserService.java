package com.example.Login.service;

import com.example.Login.entity.AppRole;
import com.example.Login.entity.AppUser;
import com.example.Login.entity.Enum.Provider;
import com.example.Login.entity.Enum.RoleName;
import com.example.Login.exception.AppException;
import com.example.Login.repository.RoleRepository;
import com.example.Login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void processOAuthPostLogin(String username) {
        try {
            AppUser appUser = userRepository.getUserByUsername(username);

            AppRole role = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));
            roleRepository.save(role);

            if (appUser == null) {
                AppUser newUser = new AppUser();
                newUser.setUserName(username);
                newUser.setProvider(Provider.GOOGLE);
                newUser.setEnabled(true);
                userRepository.save(newUser);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
