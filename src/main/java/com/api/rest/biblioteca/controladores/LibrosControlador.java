package com.api.rest.biblioteca.controladores;

import com.api.rest.biblioteca.entidades.Biblioteca;
import com.api.rest.biblioteca.entidades.Libros;
import com.api.rest.biblioteca.repositorios.BibliotecaRepositorio;
import com.api.rest.biblioteca.repositorios.LibroRepositorios;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibrosControlador {

    @Autowired
    private LibroRepositorios libroRepositorios;

    @Autowired
    private BibliotecaRepositorio bibliotecaRepositorio;

    @GetMapping
    public ResponseEntity<Page<Libros>> listarLibros(Pageable pageable) {
        return ResponseEntity.ok(libroRepositorios.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Libros> guardarLibros(@Valid @RequestBody Libros libros) {
        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepositorio.findById(libros.getBiblioteca().getId());

        //Si no existe
        if (!bibliotecaOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        //Si existe
        libros.setBiblioteca(bibliotecaOptional.get());
        Libros libroGuardados = libroRepositorios.save(libros);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(libroGuardados.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(libroGuardados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libros> actualizarLibros(@Valid @RequestBody Libros libros, @PathVariable Integer id) {
        //PARA BIBLIOTECA
        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepositorio.findById(libros.getBiblioteca().getId());
        //Si no existe
        if (!bibliotecaOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        //PARA LIBROS
        Optional<Libros> librosOpcional = libroRepositorios.findById(id);
        //Si no existe
        if (!librosOpcional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        //Si existe
        libros.setBiblioteca(bibliotecaOptional.get());
        libros.setId(librosOpcional.get().getId());
        libroRepositorios.save(libros);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Libros> eliminarLibros(@PathVariable Integer id) {
        Optional<Libros> librosOptional = libroRepositorios.findById(id);

        //Si no existe
        if (!librosOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        libroRepositorios.delete(librosOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libros> obtenerLibrosPorId(@PathVariable Integer id) {
        Optional<Libros> librosOptional = libroRepositorios.findById(id);

        //Si no existe
        if (!librosOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(librosOptional.get());
    }

}
