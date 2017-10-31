package com.doarte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doarte.model.UsuarioPermissao;

@Repository
public interface UsuariosPermissoes extends JpaRepository<UsuarioPermissao, Long> {

}
