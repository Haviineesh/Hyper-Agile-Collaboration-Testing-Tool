package demo_ver.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import demo_ver.demo.service.ManageUserService;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfiguration {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public ManageUserService manageUserService(PasswordEncoder passwordEncoder) {
                return new ManageUserService(passwordEncoder);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.userDetailsService(manageUserService(passwordEncoder()))
                                .passwordEncoder(passwordEncoder());
                return http
                                .formLogin(form -> form
                                                .loginPage("/login").permitAll())
                                .logout(logout -> logout
                                                .invalidateHttpSession(true)
                                                .clearAuthentication(true)
                                                .logoutUrl("/logout").permitAll())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/login", "/forgotpassword").permitAll()
                                                .requestMatchers("/resources/**", "/static/**", "/webjars/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .csrf(AbstractHttpConfigurer::disable)
                                .logout(logout -> logout.addLogoutHandler((request, response, authentication) -> {
                                        SecurityContextHolder.clearContext();
                                }))
                                .build();
        }

}
