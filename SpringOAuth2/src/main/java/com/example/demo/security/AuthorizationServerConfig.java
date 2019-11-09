package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import com.example.demo.service.IUserService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IUserService userService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		//IdCliente: andoidApp, androidAppLectura, webAppLecturaEscritura, iOSApp ...
		.withClient("androidApp")
		//claveCliente
		.secret(bCryptPasswordEncoder.encode("123"))
		//tipos de autorización
		.authorizedGrantTypes("password", "refresh_token")
		//accesos
		.scopes("read", "write")
		//tiempo valido token acceso (recomendado corto, segundos/minutos)
		.accessTokenValiditySeconds(1*60)
		//tiempo valido token refresco (varia dependiendo de la seguridad, min/horas/dias/semanas o incluso años)
		//ponemos uno corto para probar rapido
		.refreshTokenValiditySeconds(2*60);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception {
		endpoint.authenticationManager(authenticationManager).userDetailsService((UserDetailsService)userService);
	}
}
