package com.example.Login.service;

import com.example.Login.DAO.AppRoleDAO;
import com.example.Login.DAO.AppUserDAO;
import com.example.Login.entity.AppRole;
import com.example.Login.entity.AppUser;
import com.example.Login.repository.UserRepository;
import com.example.Login.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
        List<AppRole> roles = this.appRoleDAO.getRoleNames(appUser.getUserId());
        return UserPrincipal.create(appUser, roles);
    }

    @Transactional
    public UserDetails loadUserById(Long id){
        AppUser appUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id)
                );
        List<AppRole> roles = this.appRoleDAO.getRoleNames(appUser.getUserId());
        return UserPrincipal.create(appUser, roles);
    }
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        AppUser appUser = this.appUserDAO.findUserAccount(userName);
//        if(appUser == null){
//            System.out.println("User Not Found" + userName);
//            throw new UsernameNotFoundException("User" + userName + "was not found in db");
//        }
//        System.out.println("Found User" + appUser);
//
//        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
//        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//        if(roleNames != null){
//            for(String role : roleNames){
//                GrantedAuthority authority = new SimpleGrantedAuthority(role);
//                grantList.add(authority);
//            }
//        }
//        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(),
//                appUser.getEncryptedPassword(), grantList);
//        return userDetails;
//    }
}
