package com.example.libreria.Repository;
import com.example.libreria.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByAutorId(Long autorId);
    List<Libro> findLibrosDisponibles();
    List<Libro> buscarPorTitulo(String titulo);
    List<Libro> findByGenero(String genero);
    List<Libro> findByAnioPublicacion(Integer anio);
}
