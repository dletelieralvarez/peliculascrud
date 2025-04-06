package com.microserviciosemana4.peliculascrud.controllers;
import com.microserviciosemana4.peliculascrud.model.Pelicula;
import com.microserviciosemana4.peliculascrud.services.PeliculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/peliculas")
@CrossOrigin(origins = "*")

public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<Pelicula> retornaTodasLasPeliculas() {
        return peliculaService.getTodasLasPeliculas(); 
    }

    @GetMapping("/{id}")
    public Pelicula retornaPeliculaPorID(@PathVariable int id) {
        Optional<Pelicula> pelicula = peliculaService.getPeliculaPorID(id); 
        return pelicula.orElse(null); 
    }
    @PostMapping
    public Pelicula insertaPelicula(@RequestBody Pelicula pelicula) {
        Pelicula peliculaGuardada = peliculaService.insertaPelicula(pelicula);
        return peliculaGuardada;
    }
    @PutMapping("/{id}")
    public Pelicula actualizaPelicula(@RequestBody Pelicula pelicula, @PathVariable int id) {
        return peliculaService.actualizaPelicula(pelicula, id); 
    }
    @DeleteMapping("/{id}")
    public Pelicula eliminaPelicula(@PathVariable int id) {
        return peliculaService.eliminaPelicula(id); 
    }

    
}
