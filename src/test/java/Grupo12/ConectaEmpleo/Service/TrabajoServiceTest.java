package Grupo12.ConectaEmpleo.Service;

import Grupo12.ConectaEmpleo.Model.Trabajo;
import Grupo12.ConectaEmpleo.Repository.TrabajoRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrabajoServiceTest {

    @Mock
    private TrabajoRepository trabajoRepo;

    @InjectMocks
    private TrabajoService trabajoService;

    @Test
    public void testGuardarTrabajo() {
        Trabajo trabajo = new Trabajo();
        trabajoService.guardarTrabajo(trabajo);
        verify(trabajoRepo).save(trabajo);
    }

    @Test
    public void testListarTodos() {
        when(trabajoRepo.findAll()).thenReturn(List.of(new Trabajo(), new Trabajo()));

        assertEquals(2, trabajoService.listarTodos().size());
        verify(trabajoRepo).findAll();
    }

    @Test
    public void testObtenerPorIdCuandoExiste() {
        Trabajo trabajo = new Trabajo();
        when(trabajoRepo.findById(1L)).thenReturn(Optional.of(trabajo));

        assertSame(trabajo, trabajoService.obtenerPorId(1L));
    }

    @Test
    public void testObtenerPorIdCuandoNoExiste() {
        when(trabajoRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trabajoService.obtenerPorId(99L));
    }

    @Test
    public void testBuscarPorCategoria() {
        when(trabajoRepo.findByCategoriaContainingIgnoreCase("tech")).thenReturn(List.of(new Trabajo()));

        assertEquals(1, trabajoService.buscarPorCategoriaOUbicacion("tech", null).size());
        verify(trabajoRepo).findByCategoriaContainingIgnoreCase("tech");
    }

    @Test
    public void testBuscarPorUbicacion() {
        when(trabajoRepo.findByUbicacionContainingIgnoreCase("medellin")).thenReturn(List.of());

        assertTrue(trabajoService.buscarPorCategoriaOUbicacion("", "medellin").isEmpty());
        verify(trabajoRepo).findByUbicacionContainingIgnoreCase("medellin");
    }

    @Test
    public void testBuscarSinFiltrosDevuelveTodo() {
        when(trabajoRepo.findAll()).thenReturn(List.of(new Trabajo()));

        assertEquals(1, trabajoService.buscarPorCategoriaOUbicacion(null, null).size());
        verify(trabajoRepo).findAll();
    }

    @Test
    public void testObtenerCategoriasConMasOfertas() {
        Trabajo tecnologia = new Trabajo();
        tecnologia.setCategoria("Tecnologia");
        Trabajo tecnologia2 = new Trabajo();
        tecnologia2.setCategoria("Tecnologia");
        Trabajo sinCategoria = new Trabajo();
        sinCategoria.setCategoria("  ");
        when(trabajoRepo.findAll()).thenReturn(List.of(tecnologia, tecnologia2, sinCategoria));

        Map<String, Long> resultado = trabajoService.obtenerCategoriasConMasOfertas();

        assertEquals(2L, resultado.get("Tecnologia"));
        verify(trabajoRepo).findAll();
    }

    @Test
    public void testEliminar() {
        Trabajo trabajo = new Trabajo();
        trabajoService.eliminar(trabajo);
        verify(trabajoRepo).delete(trabajo);
    }
}