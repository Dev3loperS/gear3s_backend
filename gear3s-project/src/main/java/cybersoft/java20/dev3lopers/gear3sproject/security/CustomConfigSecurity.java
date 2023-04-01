package cybersoft.java20.dev3lopers.gear3sproject.security;

import cybersoft.java20.dev3lopers.gear3sproject.filter.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomConfigSecurity {
    @Autowired
    CustomAuthentication authentication;

    @Autowired
    AuthTokenFilter authTokenFilter;

    @Bean
    public AuthenticationManager authManager(HttpSecurity httpSecurity) throws Exception{
        AuthenticationManagerBuilder managerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.authenticationProvider(authentication);

        return managerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/admin/login/**","/login/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();

        httpSecurity.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
