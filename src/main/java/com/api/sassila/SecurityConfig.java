package com.api.sassila;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalConfig(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication().withUser("sidi mohamed").password("{noop}naruto").roles("ADMIN","CLIENT","VENDEUR");
        auth.inMemoryAuthentication().withUser("sassila-admin").password("{noop}sassila").roles("ADMIN");
    }


//    @Autowired
//    public void globalConfig(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception
//    {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource).passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("select cin as principal, password as credentials, actived from personne where cin = ?")
//                .authoritiesByUsernameQuery("select personne_cin as principal, roles_role as role from personne_roles where personne_cin = ?")
//                .rolePrefix("ROLE_");
//    }

//    @SuppressWarnings("deprecation")
//    @Bean
//    public NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/assets/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/css/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/js/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/webfonts/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/admin")
                .and()
                .httpBasic();
    }

}
