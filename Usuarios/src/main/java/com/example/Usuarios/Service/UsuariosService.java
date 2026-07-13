package com.example.Usuarios.Service;
import com.example.Usuarios.Repository.UsuariosRepository;
import com.example.Usuarios.exception.ResourceNotFoundException;
import com.example.Usuarios.Model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UsuariosService {

    private final UsuariosRepository usuarioRepository;

    // ========== MÉTODOS DE CONSULTA ==========

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        log.info("Obteniendo todos los usuarios");
        return usuarioRepository.findAllOrderedByApellido();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public Usuario findByEmail(String email) {
        log.info("Buscando usuario por email: {}", email);
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNombre(String nombre) {
        log.info("Buscando usuarios por nombre: {}", nombre);
        return usuarioRepository.buscarPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Usuario> findActivos() {
        log.info("Obteniendo usuarios activos");
        return usuarioRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        log.info("Verificando existencia de usuario con ID: {}", id);
        return usuarioRepository.existsById(id);
    }

    // ========== MÉTODOS DE ESCRITURA ==========

    public Usuario crear(Usuario usuario) {
        log.info("Creando nuevo usuario: {} {}", usuario.getNombre(), usuario.getApellido());

        // Validar que no exista un usuario con el mismo email
        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
                });

        // Por defecto, el usuario está activo
        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        Usuario saved = usuarioRepository.save(usuario);
        log.info("Usuario creado exitosamente con ID: {}", saved.getId());
        return saved;
    }

    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        log.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        // Validar que no exista otro usuario con el mismo email
        usuarioRepository.findByEmail(usuarioActualizado.getEmail())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new IllegalArgumentException("Ya existe otro usuario con el email: "
                                + usuarioActualizado.getEmail());
                    }
                });

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setTelefono(usuarioActualizado.getTelefono());
        usuario.setDireccion(usuarioActualizado.getDireccion());
        usuario.setActivo(usuarioActualizado.getActivo());

        Usuario updated = usuarioRepository.save(usuario);
        log.info("Usuario actualizado exitosamente con ID: {}", updated.getId());
        return updated;
    }

    public void eliminar(Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
        log.info("Usuario eliminado exitosamente con ID: {}", id);
    }

    // ========== MÉTODOS DE VALIDACIÓN DE NEGOCIO ==========

    public void activarUsuario(Long id) {
        log.info("Activando usuario con ID: {}", id);
        Usuario usuario = findById(id);
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
        log.info("Usuario activado exitosamente");
    }

    public void desactivarUsuario(Long id) {
        log.info("Desactivando usuario con ID: {}", id);
        Usuario usuario = findById(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        log.info("Usuario desactivado exitosamente");
    }
}