package com.arshil.springbootdemo.studentfee.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/students/list").hasAnyRole("ADMIN", "USER")
                .antMatchers("/").permitAll()
                .antMatchers("/students/showFormForAdd").hasRole("ADMIN")
                .antMatchers("/students/showUpdateForm").hasRole("ADMIN")
                .antMatchers("/students/delete").hasRole("ADMIN")
                .antMatchers("/students/search").hasRole("ADMIN")
                .antMatchers("/students/savePassword").hasRole("USER")
                .antMatchers("/students/changePasswordForm").hasRole("USER")
                .antMatchers("/students/paymentForm").hasRole("USER")
                .antMatchers("/students/paymentConfirmation").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .logoutSuccessUrl("/login/showMyLoginPage?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/login/access-denied");
    }

}
