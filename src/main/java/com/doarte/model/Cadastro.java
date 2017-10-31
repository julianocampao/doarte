package com.doarte.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "cadastro")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cadastro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "Senha : precisa ser preenchido")
	private String senha;

	@Column(nullable = false, unique = true)
	@NotBlank(message = "Email : precisa ser preenchido")
	private String email;

	@NotBlank(message = "Contato : precisa ser preenchido")
	private String contato;

	@OneToMany(mappedBy = "cadastro", fetch = FetchType.EAGER)
	private List<UsuarioPermissao> permisoes;

	public List<UsuarioPermissao> getPermisoes() {
		return permisoes;
	}

	public void setPermisoes(List<UsuarioPermissao> permisoes) {
		this.permisoes = permisoes;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@org.springframework.data.annotation.Transient
	public String getUrlImagem() {

		return "teste";
	}

	@Transient
	public String getAcesso() {
		for (UsuarioPermissao permissao : this.permisoes) {

			return permissao.getPermissao().getNome();
		}

		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cadastro other = (Cadastro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
