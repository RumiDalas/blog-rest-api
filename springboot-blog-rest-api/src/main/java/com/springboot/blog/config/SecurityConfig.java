package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;



@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService ;
    public SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService ;
    }


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())

		.authorizeRequests(authorize -> authorize
                .requestMatchers(
                        HttpMethod.GET, "/api/**"
                ).permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(
                        "/api/admin/**"
                ).hasRole("ADMIN")  // Routes requiring ADMIN role
                .requestMatchers(
                        "/api/user/**"
                ).hasRole("USER")    // Routes requiring USER role
                .anyRequest().authenticated()
        )
                .httpBasic(withDefaults());  // Use this method for HTTP Basic Authentication

        return http.build();
    }

//    no need for database configuration

//    @Bean
//    public UserDetailsService userDetailsService (){
//        UserDetails dalas = User.builder().username("dalas")
//                .password(passwordEncoder().encode("dalas"))
//                .roles("User").build();
//        UserDetails admin = User.builder().username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(dalas,admin);
//    }
}
