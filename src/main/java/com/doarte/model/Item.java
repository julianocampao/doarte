package com.doarte.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@NotNull(message = "Categoria precisa ser preenchido")
	private Categoria categoria;

	private String urlImagem;

	@ManyToOne
	private Usuario usuario;

	@ManyToOne
	private Instituicao instituicao;

	private StatusItem tipoItem;

	private Integer avaliacaoInstituicao;

	private Integer avaliacaoUsuario;

	@NotBlank(message = "Descrição precisa ser preenchido")
	private String descricao;

	public Integer getAvaliacaoUsuario() {
		return avaliacaoUsuario;
	}

	public void setAvaliacaoUsuario(Integer avaliacaoUsuario) {
		this.avaliacaoUsuario = avaliacaoUsuario;
	}

	public Integer getAvaliacaoInstituicao() {
		return avaliacaoInstituicao;
	}

	public void setAvaliacaoInstituicao(Integer avaliacaoInstituicao) {
		this.avaliacaoInstituicao = avaliacaoInstituicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public StatusItem getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(StatusItem tipoItem) {
		this.tipoItem = tipoItem;
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", categoria=" + categoria + ", urlImagem=" + urlImagem + ", usuario=" + usuario
				+ ", instituicao=" + instituicao + ", tipoItem=" + tipoItem + ", descricao=" + descricao + "]";
	}

}
