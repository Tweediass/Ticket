package com.tweedia.ticket.config;

import com.tweedia.ticket.service.UserService;
import com.tweedia.ticket.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description: 
 * @Author: Tweedia
 * @Date: 2019/9/17
 */
@Configuration
@EnableWebSecurity//不一定要
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启后，controller那边的@PreAuthorize(“hasAuthority(‘admin’)”)才会生效
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    @Bean
    public JwtTokenFilter authenticationTokenFilterBean () throws Exception {
        return new JwtTokenFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean () throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//        auth.inMemoryAuthentication().withUser("admin")
//                .password("123").roles("admin")
//                .and()
//                .withUser("sang")
//                .password("456")
//                .roles("user");
    }


    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers(HttpMethod.POST, "/authentication/**")
                    .permitAll()
                    .antMatchers(HttpMethod.POST)
                    .authenticated()
                    .antMatchers(HttpMethod.PUT)
                    .authenticated()
                    .antMatchers(HttpMethod.DELETE)
                    .authenticated()
                    .antMatchers(HttpMethod.GET)
                    .authenticated();
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().cacheControl();
//        httpSecurity.authorizeRequests()
//                .antMatchers("/hello").hasRole("user")
//                .antMatchers("/admin").hasRole("admin")
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(authenticationTokenFilterBean(),UsernamePasswordAuthenticationFilter.class)
////                .addFilterBefore(new JwtFilter(),UsernamePasswordAuthenticationFilter.class)
//                .csrf().disable();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/**");
    }

}
