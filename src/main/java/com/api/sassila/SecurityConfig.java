//package com.api.sassila;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled=true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    public void globalConfig(AuthenticationManagerBuilder auth) throws Exception
//    {
//        auth.inMemoryAuthentication().withUser("sidi mohamed").password("{noop}naruto").roles("ADMIN","CLIENT","VENDEUR");
//        auth.inMemoryAuthentication().withUser("Ilyess").password("{noop}aaa").roles("ADMIN","CLIENT");
//        auth.inMemoryAuthentication().withUser("aida").password("{noop}111").roles("CLIENT");
//        auth.inMemoryAuthentication().withUser("cl2").password("{noop}112").roles("CLIENT");
//        auth.inMemoryAuthentication().withUser("vend1").password("{noop}333").roles("VENDEUR");
//        auth.inMemoryAuthentication().withUser("vend2").password("{noop}332").roles("VENDEUR");
//    }
//
//
////    @Autowired
////    public void globalConfig(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception
////    {
////        auth.jdbcAuthentication()
////                .dataSource(dataSource).passwordEncoder(passwordEncoder())
////                .usersByUsernameQuery("select cin as principal, password as credentials, actived from personne where cin = ?")
////                .authoritiesByUsernameQuery("select personne_cin as principal, roles_role as role from personne_roles where personne_cin = ?")
////                .rolePrefix("ROLE_");
////    }
//
////    @SuppressWarnings("deprecation")
////    @Bean
////    public NoOpPasswordEncoder passwordEncoder() {
////        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
////    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception
//    {
//        http
//                .csrf().disable();
//
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login")
//                .permitAll()
//                .defaultSuccessUrl("/admin")
//                .and()
//                .httpBasic();
//    }
//
//}
