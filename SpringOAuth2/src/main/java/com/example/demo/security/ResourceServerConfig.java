package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
//habilita un filtro de spring security
@EnableResourceServer
//implementa metodos para ajustar las reglas de acceso o rutas de acceso
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.anonymous().disable()
		//ruta protegida, /api no y /api/oauth2 si
		.authorizeRequests().antMatchers("/api/oauth2/**")
		.authenticated()
		.and()
		.exceptionHandling()
		//gestiona los accesos denegados
		.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
