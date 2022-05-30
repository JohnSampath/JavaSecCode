package org.pchack.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


/**
 * Congifure csrf
 *
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${pchack.security.csrf.enabled}")
    private Boolean csrfEnabled = false;

    @Value("${pchack.security.csrf.exclude.url}")
    private String[] csrfExcludeUrl;

    @Value("${pchack.security.csrf.method}")
    private String[] csrfMethod = {"POST"};
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private RequestMatcher csrfRequestMatcher = new RequestMatcher() {

        @Override
        public boolean matches(HttpServletRequest request) {

            // Configure the request method that requires CSRF verification,
            HashSet<String> allowedMethods = new HashSet<>(Arrays.asList(csrfMethod));
            // return false means not to verify csrf
            if (!csrfEnabled) {
                return false;
            }
            return allowedMethods.contains(request.getMethod());
        }

    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //The default token is stored in the session, and the CookieCsrfTokenRepository is used to change the token to be stored in the cookie.
        // However, there are multiple servers in the backend, and the session cannot be synchronized, so the cookie mode is generally used.
        http.csrf()
                .requireCsrfProtectionMatcher(csrfRequestMatcher)
                .ignoringAntMatchers(csrfExcludeUrl)  // uri without csrf verification, multiple uris are separated by commas
                .csrfTokenRepository(new CookieCsrfTokenRepository());
        http.exceptionHandling().accessDeniedHandler(new CsrfAccessDeniedHandler());
        // http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());«

        http.cors();

        // spring security login settings
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**").permitAll() // permit static resources
                .anyRequest().authenticated().and() // any request authenticated except above static resources
                .formLogin().loginPage("/login").permitAll() // permit all to access /login page
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler()).and()
                .logout().logoutUrl("/logout").permitAll().and()
             // tomcat's default JSESSION session valid time is 30 minutes, so the session will expire if you do not operate for 30 minutes. In order to solve this problem, 
                //the rememberMe function is introduced.
                .rememberMe();
    }

    /**
     * Global cors configure
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        // Set cors origin white list
        ArrayList<String> allowOrigins = new ArrayList<>();
        allowOrigins.add("pchack.org");
        allowOrigins.add("https://test.pchack.me"); // Distinguish between http and https, and will not intercept same-domain requests by default.

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowOrigins);
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/cors/sec/httpCors", configuration); // ant style
        return source;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication() 
                .withUser("pchack").password("{noop}pchack123").roles("USER").and()
                .withUser("admin").password("{noop}admin123").roles("USER", "ADMIN");
    }
}


