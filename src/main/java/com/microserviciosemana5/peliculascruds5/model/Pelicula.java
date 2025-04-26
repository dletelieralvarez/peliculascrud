package com.microserviciosemana5.peliculascruds5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column; 
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Positive;


@Data //genera los set y get
@NoArgsConstructor //constructor vacio
@AllArgsConstructor //constructor con los atributos
@Entity //indica que es una entidad de la base de datos
@Table(name = "PELICULA") //nombre de la tabla en la base de datos
public class Pelicula
 {
    @Id //indica que es la llave primaria    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") //nombre de la columna en la base de datos
    @JsonProperty("ID") //nombre del atributo en el json
    private int ID;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 1, max = 200, message = "El título debe tener entre 1 y 200 caracteres")
    @Column(name = "TITULO") //nombre de la columna en la base de datos
    @JsonProperty("TITULO")
    private String TITULO;
    
    @Min(value = 1990, message = "El año debe ser mayor o igual a 1900")
    @Max(value = 2025, message = "El año debe ser menor o igual a 2025")
    @Column(name = "ANIO") //nombre de la columna en la base de datos
    @JsonProperty("ANIO")
    private int ANIO;   

    @NotBlank(message = "Director no puede estar vacío")
    @Size(min = 1, max = 200, message = "El director debe tener entre 1 y 200 caracteres")
    @Column(name = "DIRECTOR") //nombre de la columna en la base de datos
    @JsonProperty("DIRECTOR")
    private String DIRECTOR;
    
    @NotBlank(message = "El género no puede estar vacío")
    @Size(min = 1, max = 300, message = "El género debe tener entre 1 y 300 caracteres")
    @Column(name = "GENERO") //nombre de la columna en la base de datos
    @JsonProperty("GENERO")
    private String GENERO;  

    @NotBlank(message = "Sinopsis no puede estar vacía")
    @Size(min = 1, max = 300, message = "La sinopsis debe tener entre 1 y 300 caracteres")
    @Column(name = "SINOPSIS") //nombre de la columna en la base de datos   
    @JsonProperty("SINOPSIS")
    private String SINOPSIS;

     
}