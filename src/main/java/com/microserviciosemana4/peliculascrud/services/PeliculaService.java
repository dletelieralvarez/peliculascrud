package com.microserviciosemana4.peliculascrud.services;

import org.springframework.stereotype.Service;
import com.microserviciosemana4.peliculascrud.model.Pelicula;

import java.util.List;
import java.util.Optional;

@Service
public interface PeliculaService {

    List<Pelicula> getTodasLasPeliculas();
    Optional<Pelicula> getPeliculaPorID(int id);
    Pelicula insertaPelicula(Pelicula pelicula);
    Pelicula eliminaPelicula(int id); 
    Pelicula actualizaPelicula(Pelicula pelicula, int id);
}
