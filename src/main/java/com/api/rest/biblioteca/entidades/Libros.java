package com.api.rest.biblioteca.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "libros", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String nombre;

    //Creo la relacion de manyToOne
    @ManyToOne(fetch = FetchType.LAZY, optional = false) //Traer los datos
    @JoinColumn(name = "biblioteca_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Vara evitar el error comun con Lazy
    private Biblioteca biblioteca;

    //GENERAMOS LOS GETTER AND SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
}
