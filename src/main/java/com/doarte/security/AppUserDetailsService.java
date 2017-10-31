package com.doarte.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.doarte.model.Cadastro;
import com.doarte.repository.Cadastros;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Cadastros usuarios;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Cadastro> usuarioOptional = usuarios.findByEmail(email);
		Cadastro usuario = usuarioOptional
				.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));

		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Cadastro usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		usuario.getPermisoes().forEach(p -> {

			authorities.add(new SimpleGrantedAuthority(p.getPermissao().getNome()));
		});

		return authorities;
	}

}