package Grupo12.ConectaEmpleo.Service;

import Grupo12.ConectaEmpleo.Model.Capacitacion;
import Grupo12.ConectaEmpleo.Model.ParticipanteCapacitacion;
import Grupo12.ConectaEmpleo.Model.Usuario;
import Grupo12.ConectaEmpleo.Repository.ParticipanteCapacitacionRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParticipanteCapacitacionServiceTest {

    @Mock
    private ParticipanteCapacitacionRepository repository;

    @InjectMocks
    private ParticipanteCapacitacionService participanteService;

    @Test
    public void testInscribirUsuarioEnCapacitacionCreaInscripcion() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);
        Capacitacion capacitacion = new Capacitacion();
        capacitacion.setId(20L);
        when(repository.existsById(any())).thenReturn(false);

        participanteService.inscribirUsuarioEnCapacitacion(usuario, capacitacion);

        ArgumentCaptor<ParticipanteCapacitacion> captor = ArgumentCaptor.forClass(ParticipanteCapacitacion.class);
        verify(repository).save(captor.capture());
        assertEquals(usuario.getId(), captor.getValue().getId().getUsuarioId());
        assertEquals(capacitacion.getId(), captor.getValue().getId().getCapacitacionId());
        assertFalse(captor.getValue().getCertificado());
    }

    @Test
    public void testInscribirUsuarioNoDuplica() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);
        Capacitacion capacitacion = new Capacitacion();
        capacitacion.setId(20L);
        when(repository.existsById(any())).thenReturn(true);

        participanteService.inscribirUsuarioEnCapacitacion(usuario, capacitacion);

        verify(repository, never()).save(any());
    }

    @Test
    public void testFindByTrabajador() {
        Usuario usuario = new Usuario();
        when(repository.findByUsuario(usuario)).thenReturn(List.of(new ParticipanteCapacitacion()));

        assertEquals(1, participanteService.findByTrabajador(usuario).size());
        verify(repository).findByUsuario(usuario);
    }
}