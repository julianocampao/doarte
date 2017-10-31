package com.doarte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doarte.model.Instituicao;

@Repository
public interface Instituicoes extends JpaRepository<Instituicao, Long> {

}
