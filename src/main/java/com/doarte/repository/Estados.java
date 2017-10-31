package com.doarte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doarte.model.Estado;

@Repository
public interface Estados extends JpaRepository<Estado, Long> {

	

}
