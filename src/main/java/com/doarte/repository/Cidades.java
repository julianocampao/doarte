package com.doarte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doarte.model.Cidade;
import com.doarte.model.Estado;

public interface Cidades extends JpaRepository<Cidade, Long> {

	List<Cidade> findByEstado(Estado estado);

}
