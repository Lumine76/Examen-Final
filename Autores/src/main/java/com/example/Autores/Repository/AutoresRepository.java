package com.example.Autores.Repository;
import com.example.Autores.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> buscarPorNombre(String nombre);
    List<Autor> findByNacionalidad(String nacionalidad);
    List<Autor> findAllOrderedByApellido();
    Optional<Autor> findByNombreAndApellido(String nombre, String apellido);
}
