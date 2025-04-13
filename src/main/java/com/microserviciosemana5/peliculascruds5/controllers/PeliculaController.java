package com.microserviciosemana5.peliculascruds5.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microserviciosemana5.peliculascruds5.model.ApiResult;
import com.microserviciosemana5.peliculascruds5.model.Pelicula;
import com.microserviciosemana5.peliculascruds5.services.PeliculaService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/peliculas")
@CrossOrigin(origins = "*")

public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<ApiResult<List<Pelicula>>> retornaTodasLasPeliculas() {
        try {
            List<Pelicula> peliculas = peliculaService.getTodasLasPeliculas();
            //si la lista esta en blanco, mostrará el mensaje 
            if (peliculas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResult<>("No se encontraron películas", null, HttpStatus.NOT_FOUND.value()));
            }
            //si la lista tiene datos retorna el mensaje de exito, la lista y el estado
            return ResponseEntity.ok(
                    new ApiResult<>("Películas encontradas", peliculas, HttpStatus.OK.value())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResult<>("Error al obtener las películas", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    //public List<Pelicula> retornaTodasLasPeliculas() {
        //return peliculaService.getTodasLasPeliculas(); 
    //}

    @GetMapping("/{id}")
    public Pelicula retornaPeliculaPorID(@PathVariable int id) {
        Optional<Pelicula> pelicula = peliculaService.getPeliculaPorID(id); 
        return pelicula.orElse(null); 
    }
    @PostMapping
    public ResponseEntity<ApiResult<?>> insertaPelicula(@Valid @RequestBody Pelicula pelicula) {
        try {
            Pelicula nueva = peliculaService.insertaPelicula(pelicula);
    
            ApiResult<List<Pelicula>> respuesta = new ApiResult<>(
                "Película insertada con éxito",
                List.of(nueva),
                HttpStatus.CREATED.value()
            );
    
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

        } catch (Exception e) {
            ApiResult<Object> error = new ApiResult<>(
                "Error al insertar la película: " + e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<List<Pelicula>>> actualizaPelicula(@PathVariable int id, @Valid @RequestBody Pelicula pelicula) {
        try {
            Optional<Pelicula> buscaPelicula = peliculaService.getPeliculaPorID(id);
    
            if(!buscaPelicula.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResult<>("Película no encontrada", null, HttpStatus.NOT_FOUND.value()));
            }

            //si pelicula existe actualizo, rescato los datos desde la BD (getPeliculaPorID)
            //con get tomo los valores desde postman y los seteo en la pelicula que rescate de la BD
            Pelicula actualiza = peliculaService.getPeliculaPorID(id).get();
            actualiza.setTITULO(pelicula.getTITULO());
            actualiza.setANIO(pelicula.getANIO());
            actualiza.setDIRECTOR(pelicula.getDIRECTOR());
            actualiza.setGENERO(pelicula.getGENERO());            
            actualiza.setSINOPSIS(pelicula.getSINOPSIS());
            //guardar la modificacion
            Pelicula peliculaActualizada = peliculaService.actualizaPelicula(actualiza, id);
            //retorno el resultado con apiresult
            return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResult<>("Película actualizada con éxito", List.of(peliculaActualizada), HttpStatus.OK.value()));
        
        } catch (Exception e) {
            // En caso de error 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResult<>("Error al actualizar la película", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

    }
    //public ResponseEntity<String> actualizaPelicula(@RequestBody Pelicula pelicula, @PathVariable int id) {
    //    try
    //    {
    //        peliculaService.actualizaPelicula(pelicula, id);
    //        return ResponseEntity.ok("Pelicula Actualizada con exito ID : " + id); 
    //    }
    //    catch (Exception e){    
    //        return ResponseEntity.status(500).body("Error al actualizar la pelicula: " + e.getMessage()); 
    //    }
    //}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<String>> eliminaPelicula(@PathVariable int id) {
        try{

            //retorna los datos de la pelicula segun el id
            Optional<Pelicula> peliculaExistente = peliculaService.getPeliculaPorID(id);
            //si no la encuentra entra en el if y devuelve el mensaje
            if(!peliculaExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResult<>("Pelicula no encontrada", null, HttpStatus.NOT_FOUND.value()));
            }
            //si la encuentra la elimina y retorna el mensaje de exito
            peliculaService.eliminaPelicula(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResult<>("Película eliminada con éxito", "ID eliminado: " + id, HttpStatus.OK.value()));


            //peliculaService.eliminaPelicula(id); 
            //return ResponseEntity.ok("Pelicula eliminada con exito ID : " + id);    
        }
        catch (Exception e){    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResult<>("Error al eliminar la película", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }   
    }    
}
