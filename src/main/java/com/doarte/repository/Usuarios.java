package com.doarte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doarte.model.Usuario;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long> {

}
