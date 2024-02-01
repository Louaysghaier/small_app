package com.test.smallapp.Security;

import com.test.smallapp.JWT.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
   private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    private final String[] PUBLIC_ENDPOINTS={

            "/api/auth/signup/employee",
            "/api/auth/signIn",
            "/api/works/sum_carbo_works",
            "/api/Bilan/list-Bilan",
            "/api/message/add-message",
            "/api/message/list-message",
            "/api/Solution/list-Solution",
            "/api/Solution/add-solution",
            "/api/works/sum_carbo_works",
            "/api/user/validate-user/{idUser}",
            "/api/user/list-user",
            "/api/user/list-Userco2/ASC",
            "/api/user/list-RolesName/{RolesName}",
            "/api/user/validate-user/{iduser}",
            "/api/user/delete-user/{iduser}",
            "/api/user/getUser/{idUser}",
            "/v2/api-docs",
            "/configuration/ui",
            "/-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/index.html",
            "/swagger-resources",
            "/configuration/security",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
   http
           .cors().configurationSource(corsConfigurationSource).and()
           .csrf().disable()
           .sessionManagement()
           .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           .and()
           .authorizeRequests()
           .antMatchers("/**").permitAll()
           .anyRequest().authenticated()
           .and()
           .httpBasic();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
