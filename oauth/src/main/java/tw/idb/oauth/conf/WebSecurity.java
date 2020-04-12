package tw.idb.oauth.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/oauth/v1.0/authorization_page").permitAll()
                .and()
                .formLogin()
                .loginPage("/oauth2/login")
                .usernameParameter("account")
                .passwordParameter("pw")
                .failureUrl("/oauth2/error_test")
                .permitAll();
    }
}
