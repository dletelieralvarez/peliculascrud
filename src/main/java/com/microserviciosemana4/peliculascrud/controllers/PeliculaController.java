package com.microserviciosemana4.peliculascrud.controllers;
import com.microserviciosemana4.peliculascrud.model.Pelicula;
import com.microserviciosemana4.peliculascrud.services.PeliculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> insertaPelicula(@RequestBody Pelicula pelicula) {
       try
       {
            peliculaService.insertaPelicula(pelicula);
            return ResponseEntity.ok("Pelicula insertada con exito"); 
       }
       catch (Exception e){    
        return ResponseEntity.status(500).body("Error al insertar la pelicula: " + e.getMessage()); 
       }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizaPelicula(@RequestBody Pelicula pelicula, @PathVariable int id) {
        try
        {
            peliculaService.actualizaPelicula(pelicula, id);
            return ResponseEntity.ok("Pelicula Actualizada con exito ID : " + id); 
        }
        catch (Exception e){    
            return ResponseEntity.status(500).body("Error al actualizar la pelicula: " + e.getMessage()); 
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminaPelicula(@PathVariable int id) {
        try{
            peliculaService.eliminaPelicula(id); 
            return ResponseEntity.ok("Pelicula eliminada con exito ID : " + id);    
        }
        catch (Exception e){    
            return ResponseEntity.status(500).body("Error al eliminar la pelicula: " + e.getMessage()); 
        }   
    }

    
}
