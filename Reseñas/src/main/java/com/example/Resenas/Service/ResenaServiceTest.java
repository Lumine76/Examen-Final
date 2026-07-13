package com.example.Resenas.Service;
import com.example.Resenas.exception.ResourceNotFoundException;
import com.example.Resenas.Model.Reseña;
import com.example.Resenas.Repository.ReseñasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static javax.management.Query.times;
import static jdk.internal.classfile.impl.verifier.VerifierImpl.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hibernate.query.restriction.Restriction.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    @Mock
    private ReseñasRepository resenaRepository;

    @InjectMocks
    private ReseñasService resenaService;

    private Reseña resena;

    @BeforeEach
    void setUp() {
        resena = new Reseña();
        resena.setId(1L);
        resena.setLibroId(1L);
        resena.setUsuarioId(1L);
        resena.setCalificacion(5);
        resena.setComentario("Excelente libro");
        resena.setRecomendado(true);
        resena.setFechaResena(LocalDateTime.now());
    }

    @Test
    void findAll_ShouldReturnListOfResenas() {
        when(resenaRepository.findAll()).thenReturn(Arrays.asList(resena));

        List<Reseña> result = resenaService.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCalificacion()).isEqualTo(5);
        verify(resenaRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenResenaExists_ShouldReturnResena() {
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));

        Reseña result = resenaService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLibroId()).isEqualTo(1L);
        verify(resenaRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenResenaDoesNotExist_ShouldThrowException() {
        when(resenaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> resenaService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Reseña no encontrada con ID: 99");
        verify(resenaRepository, times(1)).findById(99L);
    }

    @Test
    void findByLibroId_ShouldReturnListOfResenas() {
        when(resenaRepository.findByLibroId(1L)).thenReturn(Arrays.asList(resena));

        List<Reseña> result = resenaService.findByLibroId(1L);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLibroId()).isEqualTo(1L);
        verify(resenaRepository, times(1)).findByLibroId(1L);
    }

    @Test
    void findByUsuarioId_ShouldReturnListOfResenas() {
        when(resenaRepository.findByUsuarioId(1L)).thenReturn(Arrays.asList(resena));

        List<Resena> result = resenaService.findByUsuarioId(1L);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsuarioId()).isEqualTo(1L);
        verify(resenaRepository, times(1)).findByUsuarioId(1L);
    }

    @Test
    void findByLibroIdAndUsuarioId_WhenExists_ShouldReturnResena() {
        when(resenaRepository.findByLibroIdAndUsuarioId(1L, 1L)).thenReturn(Optional.of(resena));

        Resena result = resenaService.findByLibroIdAndUsuarioId(1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getLibroId()).isEqualTo(1L);
        assertThat(result.getUsuarioId()).isEqualTo(1L);
        verify(resenaRepository, times(1)).findByLibroIdAndUsuarioId(1L, 1L);
    }

    @Test
    void calcularPromedioCalificacion_ShouldReturnAverage() {
        when(resenaRepository.calcularPromedioCalificacion(1L)).thenReturn(4.5);

        Double result = resenaService.calcularPromedioCalificacion(1L);

        assertThat(result).isEqualTo(4.5);
        verify(resenaRepository, times(1)).calcularPromedioCalificacion(1L);
    }

    @Test
    void calcularPromedioCalificacion_WhenNoResenas_ShouldReturnZero() {
        when(resenaRepository.calcularPromedioCalificacion(1L)).thenReturn(null);

        Double result = resenaService.calcularPromedioCalificacion(1L);

        assertThat(result).isEqualTo(0.0);
        verify(resenaRepository, times(1)).calcularPromedioCalificacion(1L);
    }

    @Test
    void countResenasByLibro_ShouldReturnCount() {
        when(resenaRepository.countResenasByLibro(1L)).thenReturn(5L);

        Long result = resenaService.countResenasByLibro(1L);

        assertThat(result).isEqualTo(5L);
        verify(resenaRepository, times(1)).countResenasByLibro(1L);
    }

    @Test
    void findResenasRecomendadas_ShouldReturnRecommendedResenas() {
        when(resenaRepository.findResenasRecomendadas()).thenReturn(Arrays.asList(resena));

        List<Resena> result = resenaService.findResenasRecomendadas();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCalificacion()).isGreaterThanOrEqualTo(4);
        verify(resenaRepository, times(1)).findResenasRecomendadas();
    }

    @Test
    void existsById_ShouldReturnTrueWhenExists() {
        when(resenaRepository.existsById(1L)).thenReturn(true);

        boolean result = resenaService.existsById(1L);

        assertThat(result).isTrue();
        verify(resenaRepository, times(1)).existsById(1L);
    }

    // ========== PRUEBAS DE ESCRITURA ==========

    @Test
    void crear_ShouldReturnCreatedResena() {
        when(resenaRepository.findByLibroIdAndUsuarioId(1L, 1L)).thenReturn(Optional.empty());
        when(resenaRepository.save(any(Reseña.class))).thenReturn(resena);

        Reseña result = resenaService.crear(resena);

        assertThat(result).isNotNull();
        assertThat(result.getCalificacion()).isEqualTo(5);
        assertThat(result.getRecomendado()).isTrue();
        verify(resenaRepository, times(1)).findByLibroIdAndUsuarioId(1L, 1L);
        verify(resenaRepository, times(1)).save(any(Reseña.class));
    }

    @Test
    void crear_WhenAlreadyExists_ShouldThrowException() {
        when(resenaRepository.findByLibroIdAndUsuarioId(1L, 1L)).thenReturn(Optional.of(resena));

        assertThatThrownBy(() -> resenaService.crear(resena))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario ya ha reseñado este libro");
        verify(resenaRepository, never()).save(any(Reseña.class));
    }

    @Test
    void actualizar_WhenResenaExists_ShouldReturnUpdatedResena() {
        Reseña updateData = new Reseña();
        updateData.setCalificacion(4);
        updateData.setComentario("Buen libro, aunque esperaba más");
        updateData.setRecomendado(false);

        Reseña updatedResena = new Reseña();
        updatedResena.setId(1L);
        updatedResena.setLibroId(1L);
        updatedResena.setUsuarioId(1L);
        updatedResena.setCalificacion(4);
        updatedResena.setComentario("Buen libro, aunque esperaba más");
        updatedResena.setRecomendado(false);
        updatedResena.setFechaResena(LocalDateTime.now());

        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));
        when(resenaRepository.save(any(Reseña.class))).thenReturn(updatedResena);

        Resena result = resenaService.actualizar(1L, updateData);

        assertThat(result).isNotNull();
        assertThat(result.getCalificacion()).isEqualTo(4);
        assertThat(result.getComentario()).isEqualTo("Buen libro, aunque esperaba más");
        assertThat(result.getRecomendado()).isFalse();
        verify(resenaRepository, times(1)).findById(1L);
        verify(resenaRepository, times(1)).save(any(Reseña.class));
    }

    @Test
    void actualizar_WhenResenaDoesNotExist_ShouldThrowException() {
        when(resenaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> resenaService.actualizar(99L, resena))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Reseña no encontrada con ID: 99");
        verify(resenaRepository, never()).save(any(Reseña.class));
    }

    @Test
    void eliminar_WhenResenaExists_ShouldDeleteResena() {
        when(resenaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(resenaRepository).deleteById(1L);

        resenaService.eliminar(1L);

        verify(resenaRepository, times(1)).existsById(1L);
        verify(resenaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminar_WhenResenaDoesNotExist_ShouldThrowException() {
        when(resenaRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> resenaService.eliminar(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Reseña no encontrada con ID: 99");
        verify(resenaRepository, never()).deleteById(anyLong());
    }
    @Test
    void marcarComoRecomendada_ShouldSetRecomendadoTrue() {
        Resena resenaToUpdate = new Resena();
        resenaToUpdate.setId(1L);
        resenaToUpdate.setCalificacion(3);
        resenaToUpdate.setRecomendado(false);
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resenaToUpdate));
        when(resenaRepository.save(any(Reseña.class))).thenReturn(resenaToUpdate);
        resenaService.marcarComoRecomendada(1L);
        assertThat(resenaToUpdate.getRecomendado()).isTrue();
        verify(resenaRepository, times(1)).findById(1L);
        verify(resenaRepository, times(1)).save(any(Reseña.class));
    }

    @Test
    void desmarcarComoRecomendada_ShouldSetRecomendadoFalse() {
        Resena resenaToUpdate = new Reseña();
        resenaToUpdate.setId(1L);
        resenaToUpdate.setCalificacion(5);
        resenaToUpdate.setRecomendado(true);
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resenaToUpdate));
        when(resenaRepository.save(any(Reseña.class))).thenReturn(resenaToUpdate);
        resenaService.desmarcarComoRecomendada(1L);
        assertThat(resenaToUpdate.getRecomendado()).isFalse();
        verify(resenaRepository, times(1)).findById(1L);
        verify(resenaRepository, times(1)).save(any(Reseña.class));
    }
}
