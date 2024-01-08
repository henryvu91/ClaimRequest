package com.vn.config;

import com.vn.services.WorkingService;
import com.vn.utils.CurrentUserUtils;
import com.vn.utils.auth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.ModelAndView;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;


    private final WorkingService workingService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, WorkingService workingService) {
        this.userDetailsService = userDetailsService;
        this.workingService = workingService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/stylesheets/**", "/assets/**", "/javascript/**", "/node_modules/**", "/register").permitAll()
                                .requestMatchers("/project/**", "/staff/**").hasRole("ADMIN")
                                .requestMatchers("/claim/approved/**", "/claim/paid/**").hasRole("FINANCE")
                                .requestMatchers("/claim/pendingApproval/**","/claim/approvedOrPaid/**")
                                .access((authentication, object) -> {
                                    Boolean checkedRecord = workingService.checkRecord((CurrentUserUtils.getCurrentUserInfo().getId()));
                                    return new AuthorizationDecision(checkedRecord);
                                })
                                .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/", true))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(86400)
                        .key("remember-me-key"));
        return http.build();
    }


}
