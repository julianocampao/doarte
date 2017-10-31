package com.doarte.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.doarte.security.AppUserDetailsService;
import com.doarte.security.AuthenticationSuccessListener;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationSuccessListener autenticate;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/vendors/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/index").permitAll().antMatchers("/rating/usuario").permitAll()
				.antMatchers("/cadastroInstituicao").permitAll().antMatchers("/feedItensDoados").permitAll()
				.antMatchers("/cadastroUsuario").permitAll().antMatchers("/feedDesastres").permitAll()
				.antMatchers("/cadastroInstituicao").permitAll().antMatchers("/feedInstituicoes").permitAll()
				.antMatchers("/cidade/**").permitAll().antMatchers("/fotos/**").permitAll().antMatchers("/fotos")
				.permitAll().antMatchers("/buscar/**").permitAll().

				anyRequest().authenticated().and().formLogin().loginPage("/login").successHandler(autenticate)
				.defaultSuccessUrl("/dashboard", true).permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and().exceptionHandling()
				.accessDeniedPage("/403").and().sessionManagement().invalidSessionUrl("/login").and().csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}