package fr.univ.rouen.xmas.config;

import fr.univ.rouen.xmas.services.UserServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Autowired
    UserServiceImp userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint() {
                })
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/lutins").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/users/{\\d+}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/exportpdf").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/{\\d+}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/{\\d+}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/{\\d+}/name").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/{\\d+}/email").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/{\\d+}/password").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/skills").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/skills/{\\d+}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/skills").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/skills/{\\d+}").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/toys").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/toys/{\\d+}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/toys").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/toys/{\\d+}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/toys/{\\d+}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/toys/{\\d+}/name").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/items").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/items/{\\d+}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/items/user/{\\d+}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/items/exportpdf").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/items").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.PUT, "/items/{\\d+}/finish").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/items/{\\d+}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/items/{\\d+}").hasRole("ADMIN");


    }
}