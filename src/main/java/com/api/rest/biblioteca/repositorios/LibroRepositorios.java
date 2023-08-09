package com.api.rest.biblioteca.repositorios;

import com.api.rest.biblioteca.entidades.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepositorios extends JpaRepository<Libros, Integer> {

}
