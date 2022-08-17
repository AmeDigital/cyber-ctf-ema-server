package br.com.ema.EmaServer.config;

import br.com.ema.EmaServer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class EmaWebSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private AuthService emaAuthenticationService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/robots.txt").permitAll()
                    .antMatchers("/status").permitAll()
                    .antMatchers("/robot.txt").permitAll()
                    .antMatchers("/image/**").permitAll()
                    .antMatchers("/actuator").permitAll()
                    .antMatchers("/backup/**").permitAll()
                    .antMatchers("/admin_utils/**").permitAll()
                    .antMatchers("/v3/**").permitAll()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/address/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        JwtTokenFilter jwtFilter = new JwtTokenFilter(emaAuthenticationService);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(emaAuthenticationService);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
