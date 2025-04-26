package com.microserviciosemana5.peliculascruds5;

import com.microserviciosemana5.peliculascruds5.controllers.PeliculaController;
import com.microserviciosemana5.peliculascruds5.model.Pelicula;
import com.microserviciosemana5.peliculascruds5.services.PeliculaService;
import com.microserviciosemana5.peliculascruds5.hateoas.PeliculaModelAssembler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@WebMvcTest(PeliculaController.class)
public class PeliculaControllerTest {
    
    @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private PeliculaService peliculaService; // Simulamos el servicio
    //@SuppressWarnings("removal")
    @MockBean
    private PeliculaModelAssembler peliculaAssembler; 

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = { "ADMIN" })
    
    public void testRetornaPeliculaPorID() throws Exception {
        Pelicula pelicula = new Pelicula(1,"Pelicula 1", 2020, "Director 1", "Genero 1", "Sinopsis 1");

        EntityModel<Pelicula> entityModel = EntityModel.of(pelicula,
            linkTo(methodOn(PeliculaController.class).retornaPeliculaPorID(1)).withSelfRel(),
            linkTo(methodOn(PeliculaController.class).eliminaPelicula(1)).withRel("delete"),
            linkTo(methodOn(PeliculaController.class).actualizaPelicula(1, pelicula)).withRel("update"),
            linkTo(methodOn(PeliculaController.class).retornaTodasLasPeliculas()).withRel("all"));

        when(peliculaService.getPeliculaPorID(1)).thenReturn(java.util.Optional.of(pelicula));
        // Simulamos el comportamiento del ensamblador para que devuelva el EntityModel
        when(peliculaAssembler.toModel(pelicula)).thenReturn(entityModel);

        mockMvc.perform(get("/peliculas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.TITULO").value("Pelicula 1"))
                .andExpect(jsonPath("$.DIRECTOR").value("Director 1"))
                .andExpect(jsonPath("$.ANIO").value(2020))
                .andExpect(jsonPath("$.GENERO").value("Accion"))
                .andExpect(jsonPath("$.SINOPSIS").value("Sinopsis 1"))
                .andExpect(jsonPath("$._links.self.href").exists()) 
                .andExpect(jsonPath("$._links.delete.href").exists()) 
                .andExpect(jsonPath("$._links.update.href").exists());

    }
}
