package com.example.Prestamos.Repository;
import com.example.Prestamos.Model.PrestamosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamosRepository extends JpaRepository<PrestamosModel, Long> {

    List<PrestamosModel> findPrestamosActivos();
    List<PrestamosModel> findPrestamosAtrasados(LocalDate fecha);
    List<PrestamosModel> findByLibroId(Long libroId);
    List<PrestamosModel> findByUsuarioId(Long usuarioId);
    List<PrestamosModel> findPrestamosActivosByUsuario(Long usuarioId);
    List<PrestamosModel> findPrestamosActivosByLibro(Long libroId);
}