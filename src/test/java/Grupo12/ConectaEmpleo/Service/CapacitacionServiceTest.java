package Grupo12.ConectaEmpleo.Service;

import Grupo12.ConectaEmpleo.Model.Capacitacion;
import Grupo12.ConectaEmpleo.Repository.CapacitacionRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CapacitacionServiceTest {

    @Mock
    private CapacitacionRepository capacitacionRepo;

    @InjectMocks
    private CapacitacionService capacitacionService;

    @Test
    public void testObtenerTodas() {
        when(capacitacionRepo.findAll()).thenReturn(List.of(new Capacitacion(), new Capacitacion()));

        assertEquals(2, capacitacionService.obtenerTodas().size());
        verify(capacitacionRepo).findAll();
    }

    @Test
    public void testGuardar() {
        Capacitacion capacitacion = new Capacitacion();
        capacitacionService.guardar(capacitacion);
        verify(capacitacionRepo).save(capacitacion);
    }

    @Test
    public void testObtenerPorIdCuandoExiste() {
        Capacitacion capacitacion = new Capacitacion();
        when(capacitacionRepo.findById(3L)).thenReturn(Optional.of(capacitacion));

        assertSame(capacitacion, capacitacionService.obtenerPorId(3L));
    }

    @Test
    public void testObtenerPorIdCuandoNoExiste() {
        when(capacitacionRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> capacitacionService.obtenerPorId(99L));
    }
}