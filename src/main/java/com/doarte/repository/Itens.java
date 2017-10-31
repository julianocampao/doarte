package com.doarte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doarte.model.Categoria;
import com.doarte.model.Instituicao;
import com.doarte.model.Item;
import com.doarte.model.StatusItem;
import com.doarte.model.Usuario;

public interface Itens extends JpaRepository<Item, Long> {

	public List<Item> findByUsuario(Usuario usuario);

	public List<Item> findByInstituicao(Instituicao instituicao);

	public List<Item> findByTipoItem(StatusItem statusItem);

	public Long countByTipoItem(StatusItem statusItem);

	public Long countByTipoItemAndUsuario(StatusItem statusItem, Usuario usuario);

	public List<Item> findByTipoItemAndUsuario(StatusItem statusItem, Usuario usuario);

	public List<Item> findByDescricaoContainingAndTipoItem(String texto, StatusItem statusItem);

	public List<Item> findByDescricaoContainingAndTipoItemAndCategoria(String texto, StatusItem statusItem,Categoria categoria);

	public List<Item> findByTipoItemAndCategoria(StatusItem statusItem,Categoria categoria);

	public List<Item> findByCategoria(Categoria cat);

	
}
