package com.doarte.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Service
public class UsuarioLogado {

	private Cadastro cadastro;

	private Authentication context;

	public UsuarioLogado() {
		System.out.println("Fui iniciado");

	}

	public Authentication getContext() {
		return context;
	}

	public void setContext(Authentication context) {
		this.context = context;
	}

	public Cadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}

	public boolean temAutorizacao(String autho) {

		for (GrantedAuthority a : context.getAuthorities()) {
			System.out.println(a);
			if (a.getAuthority().toUpperCase().equals(autho.toUpperCase())) {
				return true;
			}
		}

		return false;
	}

}
