package com.doarte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doarte.model.Incidente;

public interface Incidentes extends JpaRepository<Incidente, Long> {

}
