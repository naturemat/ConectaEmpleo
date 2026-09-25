package Grupo12.ConectaEmpleo.Service;

import Grupo12.ConectaEmpleo.Model.Usuario;
import Grupo12.ConectaEmpleo.Repository.UsuarioRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio que maneja la lógica de negocio relacionada con los usuarios:
 * - Registro
 * - Autenticación
 * - Actualización de calificación promedio
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    /**
     * Verifica si ya existe un usuario con el correo dado.
     *
     * @param correo Correo del usuario a verificar
     * @return true si el correo ya está registrado, false en caso contrario
     */
    public boolean correoExiste(String correo) {
        return usuarioRepo.existsByCorreo(correo);
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuario Usuario a registrar
     * @return El usuario guardado
     */
    public Usuario registrar(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    /**
     * Autentica al usuario verificando correo y contraseña.
     *
     * @param correo Correo del usuario
     * @param contrasena Contraseña del usuario
     * @return El usuario autenticado o null si no se encuentra
     */
    public Usuario autenticar(String correo, String contrasena) {
        Usuario usuario = usuarioRepo.findByCorreo(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return usuario;
        }
        return null;
    }

    /**
     * Actualiza la calificación promedio de un usuario.
     *
     * @param usuario Usuario al que se le actualiza la calificación
     * @param nuevaPuntuacion Nueva puntuación (1-5)
     */
    public void actualizarCalificacionPromedio(Usuario usuario, Integer nuevaPuntuacion) {
        int conteoPrevio = usuario.getCalificacionesCount() != null ? usuario.getCalificacionesCount() : 0;
        BigDecimal promedioPrevio = usuario.getCalificacionPromedio();

        if (promedioPrevio == null) {
            usuario.setCalificacionPromedio(BigDecimal.valueOf(nuevaPuntuacion));
        } else {
            BigDecimal suma = promedioPrevio.multiply(BigDecimal.valueOf(conteoPrevio))
                    .add(BigDecimal.valueOf(nuevaPuntuacion));
            usuario.setCalificacionPromedio(
                    suma.divide(BigDecimal.valueOf(conteoPrevio + 1), 2, RoundingMode.HALF_UP));
        }

        usuario.setCalificacionesCount(conteoPrevio + 1);
        usuarioRepo.save(usuario);
    }
}