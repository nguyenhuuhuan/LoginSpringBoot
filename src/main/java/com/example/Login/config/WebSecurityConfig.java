package com.example.Login.config;

import com.example.Login.security.JwtAuthenticationEntryPoint;
import com.example.Login.security.JwtAuthenticationFilter;
import com.example.Login.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    AppConfig appConfig = new AppConfig();

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(appConfig.passwordEncoder());
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.csrf().disable();
//
//        //all pages don't need login
//        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
//
//        //all pages login by role
//        http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
//
//        //all pages just login with admin role
//        http.authorizeRequests().antMatchers("/admin").access("hasAnyRole('ROLE_ADMIN')");
//
//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
//
//        http.authorizeRequests().and().formLogin()
//                .loginProcessingUrl("j_spring_security_check")
//                .loginPage("/login")
//                .defaultSuccessUrl("/userAccountInfo")
//                .failureUrl("/login?error=true")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
//
//        http.authorizeRequests().and()
//                .rememberMe().tokenRepository(this.persistentTokenRepository())
//                .tokenValiditySeconds(1*24*60*60);
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
                .permitAll()
                .anyRequest()
                .authenticated();

            http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}
