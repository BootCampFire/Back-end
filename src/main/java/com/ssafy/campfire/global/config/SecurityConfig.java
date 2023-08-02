package com.ssafy.campfire.global.config;

import com.ssafy.campfire.global.oauth2.service.PrincipalOauth2UserService;
import com.ssafy.campfire.user.domain.Role;
import com.ssafy.campfire.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // 인증
                .antMatchers("/users/info").authenticated()
                // 인가
                .antMatchers("/users/admin/**").hasAuthority(Role.ADMIN.name())
                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                // OAuth 로그인
                .and()
                .oauth2Login()
                .loginPage("/users/login")
                .defaultSuccessUrl("http://localhost:3000/")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
        ;
    }
}