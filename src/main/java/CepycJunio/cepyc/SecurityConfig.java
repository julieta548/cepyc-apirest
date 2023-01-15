package CepycJunio.cepyc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import CepycJunio.cepyc.Servicios.AdminServicio;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminServicio adminServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminServicio).passwordEncoder(new BCryptPasswordEncoder(4));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/chequear-login", "/producto/single/{id}", "/producto/todos", "/registro").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").permitAll()
                    .loginProcessingUrl("/chequear-login")
                    .usernameParameter("usuario")
                    .passwordParameter("contrasenia")
                    .defaultSuccessUrl("/editable-portada")
                .and().logout()
                    .logoutUrl("/logout").permitAll()
                    .logoutSuccessUrl("/")
                    .permitAll();
    }

    @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/error","/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**","/forms/**","/forms/images/**","/forms/js/**","/font-awesome-4.7.0/**","/font-awesome-4.7.0/css/**","/font-awesome-4.7.0/fonts/**","/font-awesome-4.7.0/less/**","/font-awesome-4.7.0/scss/**","/assets/**","/assets/js/**","/assets/vendor/**");
        }
}