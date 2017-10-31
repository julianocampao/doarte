package com.doarte.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.doarte.model.Cadastro;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private Cadastro usuario;

	public UsuarioSistema(Cadastro usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	public Cadastro getUsuario() {
		return usuario;
	}

}