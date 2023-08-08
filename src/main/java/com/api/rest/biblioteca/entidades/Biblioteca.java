package com.api.rest.biblioteca.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "biblioteca")
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "biblioteca", cascade = CascadeType.ALL)
    private Set<Libros> libros = new HashSet<>();

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

    public Set<Libros> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libros> libros) {
        this.libros = libros;
        //Añadimos de manera manual un ForEach
        for (Libros libro : libros) {
            libro.setBiblioteca(this);
        }
    }
}


