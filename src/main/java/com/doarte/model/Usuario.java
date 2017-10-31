package com.doarte.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "usuario")
public class Usuario extends Cadastro {

	@NotBlank(message = "Cpf : precisa ser preenchido")
	private String cpf;

	@NotBlank(message = "Nome : precisa ser preenchido")
	private String nome;

	private String urlImagem;

	@ManyToOne
	@NotNull(message = "Cidade precisa ser preenchida")
	private Cidade cidade;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return "Usuario [cpf=" + cpf + ", nome=" + nome + ", urlImagem=" + urlImagem + ", cidade=" + cidade
				+ ", getCpf()=" + getCpf() + ", getUrlImagem()=" + getUrlImagem() + ", getNome()=" + getNome()
				+ ", getCidade()=" + getCidade() + ", getId()=" + getId() + ", getSenha()=" + getSenha()
				+ ", getEmail()=" + getEmail() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}

}
