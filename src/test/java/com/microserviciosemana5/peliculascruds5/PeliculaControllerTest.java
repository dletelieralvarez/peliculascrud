package com.microserviciosemana5.peliculascruds5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microserviciosemana5.peliculascruds5.config.SecurityConfig;
import com.microserviciosemana5.peliculascruds5.controllers.PeliculaController;
import com.microserviciosemana5.peliculascruds5.model.Pelicula;
import com.microserviciosemana5.peliculascruds5.services.PeliculaService;
import com.microserviciosemana5.peliculascruds5.hateoas.PeliculaModelAssembler;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@WebMvcTest(PeliculaController.class)
@Import(SecurityConfig.class) 
@WithMockUser(username = "admin", password = "admin123", roles = { "ADMIN" })
@AutoConfigureMockMvc(addFilters = false) // Deshabilitar filtros de seguridad para pruebas
public class PeliculaControllerTest {
 
    @Autowired
    private MockMvc mockMvc; 
 
    @MockBean
    private PeliculaService peliculaService; // Simulamos el servicio
    //@SuppressWarnings("removal")
    @MockBean
    private PeliculaModelAssembler peliculaAssembler; 


    @Test
    public void testRetornaPeliculaPorID() throws Exception {
        Pelicula pelicula = new Pelicula(1,"Pelicula 1", 2024, "Director 1", "Genero 1", "Sinopsis 1");

        EntityModel<Pelicula> entityModel = EntityModel.of(pelicula,
            linkTo(methodOn(PeliculaController.class).retornaPeliculaPorID(1)).withSelfRel(),
            linkTo(methodOn(PeliculaController.class).eliminaPelicula(1)).withRel("delete"),
            linkTo(methodOn(PeliculaController.class).actualizaPelicula(1, pelicula)).withRel("update"),
            linkTo(methodOn(PeliculaController.class).retornaTodasLasPeliculas()).withRel("all"));

        when(peliculaService.getPeliculaPorID(1)).thenReturn(java.util.Optional.of(pelicula));
        // Simulamos el comportamiento del ensamblador para que devuelva el EntityModel
        when(peliculaAssembler.toModel(pelicula)).thenReturn(entityModel);

        /*
        mockMvc.perform(get("/peliculas/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(result -> {
            // Imprime la respuesta en la consola
            System.out.println(result.getResponse().getContentAsString());
        });
         */
        
        mockMvc.perform(get("/peliculas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.titulo").value("Pelicula 1"))
                .andExpect(jsonPath("$.data.director").value("Director 1"))
                .andExpect(jsonPath("$.data.anio").value(2024))
                .andExpect(jsonPath("$.data.genero").value("Genero 1"))
                .andExpect(jsonPath("$.data.sinopsis").value("Sinopsis 1"))
                //links es un arreglo, por lo que se accederé a los links por su indice, es como lo agregue en mi apiresult
                .andExpect(jsonPath("$.links[0].href").exists()) 
                .andExpect(jsonPath("$.links[1].href").exists()) 
                .andExpect(jsonPath("$.links[2].href").exists())
                .andExpect(jsonPath("$.links[3].href").exists())
                .andDo(result -> {
                    // Imprime la respuesta en la consola
                    System.out.println(result.getResponse().getContentAsString());
                });
        
        //test para pelicula que no existe
        when(peliculaService.getPeliculaPorID(11)).thenReturn(java.util.Optional.empty()); // Simula que no existe                

        //.andExpect(status().isNotFound()) //en caso de que no encuentre la pelicula (es error 404)
        mockMvc.perform(get("/peliculas/11") // ID que no existe
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())  
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.mensaje").value("Pelicula no encontrada con id: 11"))
            .andExpect(jsonPath("$.codigoEstado").value(404))  
            .andDo(result -> {
            System.out.println(result.getResponse().getContentAsString()); // Imprime la respuesta en consola
        });
    }

    /* 
    @Test    
    public void testActualizarPelicula() throws Exception {
    // Datos de la película a actualizar    
    Pelicula peliculaOriginal = new Pelicula(1, "Pelicula 1", 2024, "Director 1", "Genero 1", "Sinopsis 1");

    // Datos actualizados de la película
    Pelicula peliculaActualizada = new Pelicula(1, "Pelicula Actualizada", 2024, "Director Actualizado", "Genero Actualizado", "Sinopsis Actualizada");

    // Simula el  servicio para retornar la película original y la pelicula que estoy actualizando
    when(peliculaService.getPeliculaPorID(1)).thenReturn(java.util.Optional.of(peliculaOriginal));
    when(peliculaService.actualizaPelicula(peliculaActualizada, 1)).thenReturn(peliculaActualizada);

    
    // Ejecuta la solicitud PUT para actualizar la pelicula
    mockMvc.perform(put("/peliculas/{id}",1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"titulo\": \"Pelicula Actualizada\", \"anio\": 2024, \"director\": \"Director Actualizado\", \"genero\": \"Genero Actualizado\", \"sinopsis\": \"Sinopsis Actualizada\" }"))
            .andExpect(status().isOk())  // espero un codigo ok 200
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            //segun la estructura de mi apiresult accedo a las propiedades de la respuesta primero por data y luego por el nombre de la propiedad
            .andExpect(jsonPath("$.data.titulo").value("Pelicula Actualizada"))  
            .andExpect(jsonPath("$.data.director").value("Director Actualizado"))
            .andExpect(jsonPath("$.data.anio").value(2024))  
            .andExpect(jsonPath("$.data.genero").value("Genero Actualizado")) 
            .andExpect(jsonPath("$.data.sinopsis").value("Sinopsis Actualizada")) 
            .andDo(result -> {
                System.out.println(result.getResponse().getContentAsString()); 
                // Imprime la respuesta en la consola
            });

    // Simulo que no existe la película con id 11
    when(peliculaService.getPeliculaPorID(11)).thenReturn(java.util.Optional.empty()); 

    mockMvc.perform(put("/peliculas/{id}",11) // ID que no existe
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"titulo\": \"Pelicula no existe\", \"anio\": 2022, \"director\": \"Director no existe\", \"genero\": \"Genero no existe\", \"sinopsis\": \"Sinopsis no existe\" }"))
            //aqui espero un error codigo 404 ya que el id de pelicula no existe
            .andExpect(status().isNotFound())  
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.mensaje").value("Pelicula no encontrada con id: 11"))  
            .andExpect(jsonPath("$.codigoEstado").value(404))  
            .andDo(result -> {
                System.out.println(result.getResponse().getContentAsString()); 
                // Imprimo la respuesta en consola
            });
    }
            */
}
