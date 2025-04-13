package com.microserviciosemana5.peliculascruds5.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.microserviciosemana5.peliculascruds5.model.Pelicula;

public interface peliculaRepository extends JpaRepository<Pelicula, Integer> {
    
}
