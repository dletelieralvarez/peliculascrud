package com.microserviciosemana4.peliculascrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Column; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data //genera los set y get
@NoArgsConstructor //constructor vacio
@AllArgsConstructor //constructor con los atributos

@Entity //indica que es una entidad de la base de datos
//@EntityScan //escanea el paquete para encontrar entidades
@Table(name = "PELICULA") //nombre de la tabla en la base de datos
public class Pelicula
 {
    @Id //indica que es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //indica que es autoincrementable
    @Column(name = "ID") //nombre de la columna en la base de datos
    private int id;
    @Column(name = "TITULO") //nombre de la columna en la base de datos
    private String titulo;
    @Column(name = "ANIO") //nombre de la columna en la base de datos
    private int anio;   
    @Column(name = "DIRECTOR") //nombre de la columna en la base de datos
    private String director;
    @Column(name = "GENERO") //nombre de la columna en la base de datos
    private String genero;  
    @Column(name = "SINOPSIS") //nombre de la columna en la base de datos   
    private String sinopsis;
}