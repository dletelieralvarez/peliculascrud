package com.microserviciosemana5.peliculascruds5.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; // Importa funciones para generar enlaces
import com.microserviciosemana5.peliculascruds5.controllers.PeliculaController;
import com.microserviciosemana5.peliculascruds5.model.Pelicula;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PeliculaModelAssembler implements RepresentationModelAssembler<Pelicula, EntityModel<Pelicula>> {
    //convierte el objeto pelicula a un objeto EntityModel<Pelicula> que contiene los enlaces
    @Override
    public @NonNull EntityModel<Pelicula> toModel(@NonNull Pelicula pelicula) {

        return EntityModel.of(pelicula,
                linkTo(methodOn(PeliculaController.class).retornaPeliculaPorID(pelicula.getID())).withSelfRel(),
                linkTo(methodOn(PeliculaController.class).eliminaPelicula(pelicula.getID())).withRel("delete"),
                linkTo(methodOn(PeliculaController.class).actualizaPelicula(pelicula.getID(), pelicula)).withRel("update"),
                linkTo(methodOn(PeliculaController.class).retornaTodasLasPeliculas()).withRel("all"));
    }
}
