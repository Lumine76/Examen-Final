package com.example.Usuarios.Repository;
import com.example.Usuarios.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    List<Usuario> buscarPorNombre(String nombre);
    List<Usuario> findByActivoTrue();
    List<Usuario> findAllOrderedByApellido();
}