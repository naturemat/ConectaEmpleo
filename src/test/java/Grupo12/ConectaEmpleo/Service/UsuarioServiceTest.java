package Grupo12.ConectaEmpleo.Service;

import Grupo12.ConectaEmpleo.Model.Usuario;
import Grupo12.ConectaEmpleo.Repository.UsuarioRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepo;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void testCorreoExiste() {
        when(usuarioRepo.existsByCorreo("a@b.com")).thenReturn(true);

        assertTrue(usuarioService.correoExiste("a@b.com"));
        assertFalse(usuarioService.correoExiste("otro@b.com"));

        verify(usuarioRepo).existsByCorreo("a@b.com");
    }

    @Test
    public void testRegistrar() {
        Usuario usuario = new Usuario();
        when(usuarioRepo.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.registrar(usuario);

        assertSame(usuario, resultado);
        verify(usuarioRepo).save(usuario);
    }

    @Test
    public void testAutenticarConCredencialesValidas() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("a@b.com");
        usuario.setContrasena("secreto");
        when(usuarioRepo.findByCorreo("a@b.com")).thenReturn(usuario);

        Usuario resultado = usuarioService.autenticar("a@b.com", "secreto");

        assertSame(usuario, resultado);
    }

    @Test
    public void testAutenticarConContrasenaIncorrecta() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("a@b.com");
        usuario.setContrasena("secreto");
        when(usuarioRepo.findByCorreo("a@b.com")).thenReturn(usuario);

        assertNull(usuarioService.autenticar("a@b.com", "mal"));
    }

    @Test
    public void testAutenticarConCorreoInexistente() {
        when(usuarioRepo.findByCorreo("nadie@b.com")).thenReturn(null);

        assertNull(usuarioService.autenticar("nadie@b.com", "x"));
    }

    @Test
    public void testActualizarCalificacionPromedioGuardaElUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCalificacionPromedio(new BigDecimal("4.00"));

        usuarioService.actualizarCalificacionPromedio(usuario, 5);

        assertNotNull(usuario.getCalificacionPromedio());
        verify(usuarioRepo).save(usuario);
    }
}