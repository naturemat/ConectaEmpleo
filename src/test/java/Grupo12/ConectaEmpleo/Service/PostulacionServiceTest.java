package Grupo12.ConectaEmpleo.Service;

import Grupo12.ConectaEmpleo.Model.EstadoPostulacion;
import Grupo12.ConectaEmpleo.Model.EstadoTrabajo;
import Grupo12.ConectaEmpleo.Model.Postulacion;
import Grupo12.ConectaEmpleo.Model.Trabajo;
import Grupo12.ConectaEmpleo.Model.Usuario;
import Grupo12.ConectaEmpleo.Repository.PostulacionRepository;
import Grupo12.ConectaEmpleo.Repository.TrabajoRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostulacionServiceTest {

    @Mock
    private PostulacionRepository postulacionRepo;

    @Mock
    private TrabajoRepository trabajoRepo;

    @InjectMocks
    private PostulacionService postulacionService;

    @Test
    public void testPostularseCreaUnaPostulacion() {
        Usuario trabajador = new Usuario();
        Trabajo trabajo = new Trabajo();
        when(postulacionRepo.existsByTrabajadorAndTrabajo(trabajador, trabajo)).thenReturn(false);

        postulacionService.postularse(trabajador, trabajo);

        ArgumentCaptor<Postulacion> captor = ArgumentCaptor.forClass(Postulacion.class);
        verify(postulacionRepo).save(captor.capture());
        assertEquals(trabajador, captor.getValue().getTrabajador());
        assertEquals(trabajo, captor.getValue().getTrabajo());
        assertEquals(EstadoPostulacion.PENDIENTE, captor.getValue().getEstado());
    }

    @Test
    public void testPostularseNoDuplica() {
        Usuario trabajador = new Usuario();
        Trabajo trabajo = new Trabajo();
        when(postulacionRepo.existsByTrabajadorAndTrabajo(trabajador, trabajo)).thenReturn(true);

        postulacionService.postularse(trabajador, trabajo);

        verify(postulacionRepo, never()).save(any());
    }

    @Test
    public void testFindByTrabajador() {
        Usuario trabajador = new Usuario();
        when(postulacionRepo.findByTrabajador(trabajador)).thenReturn(List.of(new Postulacion()));

        assertEquals(1, postulacionService.findByTrabajador(trabajador).size());
        verify(postulacionRepo).findByTrabajador(trabajador);
    }

    @Test
    public void testAceptarPostulacion() {
        Postulacion postulacion = new Postulacion();
        postulacion.setEstado(EstadoPostulacion.PENDIENTE);
        Trabajo trabajo = new Trabajo();
        trabajo.setEstado(EstadoTrabajo.ACTIVO);
        postulacion.setTrabajo(trabajo);
        when(postulacionRepo.findById(1L)).thenReturn(Optional.of(postulacion));
        when(postulacionRepo.save(postulacion)).thenReturn(postulacion);

        Postulacion aceptada = postulacionService.aceptarPostulacion(1L);

        assertEquals(EstadoPostulacion.ACEPTADO, aceptada.getEstado());
        verify(trabajoRepo).save(trabajo);
    }

    @Test
    public void testAceptarPostulacionRechazaReprocesar() {
        Postulacion postulacion = new Postulacion();
        postulacion.setEstado(EstadoPostulacion.ACEPTADO);
        when(postulacionRepo.findById(1L)).thenReturn(Optional.of(postulacion));

        assertThrows(RuntimeException.class, () -> postulacionService.aceptarPostulacion(1L));
        verify(trabajoRepo, never()).save(any());
    }

    @Test
    public void testAceptarPostulacionInexistente() {
        when(postulacionRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> postulacionService.aceptarPostulacion(99L));
    }
}