package com.doarte.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.doarte.model.Instituicao;
import com.doarte.model.Usuario;

public class Util {

	//public static Usuario usuario;

//	public static Instituicao instituicao;

	static {
//		usuario = new Usuario();
//		usuario.setId(new Long(3));

//		instituicao = new Instituicao();
//		instituicao.setId(new Long(1));
	}

	public static String teste() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
