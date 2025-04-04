package com.microserviciosemana4.peliculascrud.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.microserviciosemana4.peliculascrud.model.Pelicula;

public interface peliculaRepository extends JpaRepository<Pelicula, Integer> {
    
}
