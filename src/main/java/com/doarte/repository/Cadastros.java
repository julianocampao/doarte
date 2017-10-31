package com.doarte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doarte.model.Cadastro;

@Repository
public interface Cadastros extends JpaRepository<Cadastro, Long> {

	public Optional<Cadastro> findByEmail(String email);

}
