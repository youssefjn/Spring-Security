package spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		 	UserDetails user = User.withDefaultPasswordEncoder()
		 			.username("MyUser")
		 			.password("MyUserPassword")
		 			.roles("USER")
		 			.build();
		 	UserDetails admin = User.withDefaultPasswordEncoder()
		 			.username("MyAdmin")
		 			.password("MyAdminPassword")
		 			.roles("ADMIN")
		 			.build();
		 	return new InMemoryUserDetailsManager(user, admin);
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http
		.csrf(csrf -> csrf.disable())
		.authorizeRequests(auth -> {
			auth.antMatchers("/").permitAll();
			auth.antMatchers("/user").hasRole("USER");
			auth.antMatchers("/admin").hasRole("ADMIN");
		})
		.httpBasic(Customizer.withDefaults())
		.build();	}

	
	
}
