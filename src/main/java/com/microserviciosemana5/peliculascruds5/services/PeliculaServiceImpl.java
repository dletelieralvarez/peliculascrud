package com.microserviciosemana5.peliculascruds5.services;
import org.springframework.stereotype.Service;

import com.microserviciosemana5.peliculascruds5.model.Pelicula;
import com.microserviciosemana5.peliculascruds5.repository.peliculaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PeliculaServiceImpl implements PeliculaService {
    @Autowired
    private peliculaRepository peliculaRepository;

    @Override
    public List<Pelicula> getTodasLasPeliculas() {
        log.debug("Servicio : getTodasLasPeliculas()"); 
        return peliculaRepository.findAll();
    }
    @Override
    public Optional<Pelicula> getPeliculaPorID(int id) {
        log.debug("Servicio : getPeliculaPorID()"); 
        return peliculaRepository.findById(id);
    }
    @Override
    public Pelicula actualizaPelicula(Pelicula pelicula, int id) {
        log.debug("Servicio : actualizaPelicula()"); 
        return peliculaRepository.save(pelicula);
    }
    @Override
    public Pelicula insertaPelicula(Pelicula pelicula) {
        log.debug("Servicio : insertaPelicula()"); 
       if (peliculaRepository.existsById(pelicula.getID())) {
            throw new IllegalArgumentException("La pelicula ya existe con el ID: " + pelicula.getID());
        }
        return peliculaRepository.save(pelicula);
    }
    @Override   
    public Pelicula eliminaPelicula(int id) {
        log.debug("Servicio : eliminaPelicula()"); 
        Optional<Pelicula> pelicula = peliculaRepository.findById(id);
        if (pelicula.isPresent()) {
            peliculaRepository.deleteById(id);
            return pelicula.get();
        } else {
            return null;
        }
    }
    
} 
