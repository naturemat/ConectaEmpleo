package Grupo12.ConectaEmpleo.Controller;

import Grupo12.ConectaEmpleo.Model.EstadoTrabajo;
import Grupo12.ConectaEmpleo.Model.Trabajo;
import Grupo12.ConectaEmpleo.Model.Usuario;
import Grupo12.ConectaEmpleo.Service.TrabajoService;
import Grupo12.ConectaEmpleo.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trabajo")
public class TrabajoController {

    @Autowired
    private TrabajoService trabajoService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Muestra el formulario de creación de trabajo.
     *
     * @param model Modelo para pasar atributos a la vista
     * @return Nombre de la vista "formulario-trabajo"
     */
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("trabajo", new Trabajo());
        return "formulario-trabajo";
    }

    /**
     * Crea un nuevo trabajo.
     *
     * @param trabajo Trabajo a crear
     * @param session Sesión HTTP
     * @return Redirige al listado de trabajos
     */
    @PostMapping("/crear")
    public String crearTrabajo(@ModelAttribute Trabajo trabajo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        trabajo.setSolicitante(usuario);

        trabajoService.guardarTrabajo(trabajo);
        return "redirect:/trabajo"; // redirecciona a lista de empleos
    }

    /**
     * Muestra todos los trabajos disponibles.
     *
     * @param model Modelo para pasar atributos a la vista
     * @return Nombre de la vista "lista-trabajos"
     */
    @GetMapping
    public String listarTrabajos(Model model) {
        model.addAttribute("trabajos", trabajoService.listarTodos());
        return "lista-trabajos";
    }

    /**
     * Muestra trabajos filtrados por categoría o ubicación.
     *
     * @param model Modelo para pasar atributos a la vista
     * @param categoria Categoría del trabajo
     * @param ubicacion Ubicación del trabajo
     * @return Nombre de la vista "lista-trabajos"
     */
    @GetMapping("/personalizado")
    public String listarTrabajosPersonalizados(Model model,
            @RequestParam(name = "categoria", required = false) String categoria,
            @RequestParam(name = "ubicacion", required = false) String ubicacion) {

        List<Trabajo> trabajos = trabajoService.buscarPorCategoriaOUbicacion(categoria, ubicacion);
        model.addAttribute("trabajos", trabajos);

        return "lista-trabajos";
    }

    /**
     * Finaliza un trabajo y lo redirige al formulario de calificación.
     *
     * @param id ID del trabajo
     * @param session Sesión HTTP
     * @return Redirige al formulario de calificación
     */
    @PostMapping("/{id}/finalizar")
    public String finalizarTrabajo(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        Trabajo trabajo = trabajoService.obtenerPorId(id);
        if (trabajo == null) {
            return "redirect:/usuario/perfil";
        }

        // Marcar el trabajo como finalizado
        trabajo.setEstado(EstadoTrabajo.FINALIZADO);
        trabajoService.guardarTrabajo(trabajo);

        return "redirect:/trabajo/calificar?id=" + id;
    }

    /**
     * Muestra el formulario de calificación.
     *
     * @param id ID del trabajo
     * @param model Modelo para pasar atributos a la vista
     * @return Nombre de la vista "calificar"
     */
    @GetMapping("/calificar")
    public String mostrarFormularioCalificacion(@RequestParam Long id, Model model) {
        model.addAttribute("trabajoId", id);
        return "calificar";
    }

    /**
     * Procesa la calificación de un trabajo.
     *
     * @param trabajoId ID del trabajo
     * @param puntuacion Puntuación del trabajo
     * @param session Sesión HTTP
     * @return Redirige al listado de trabajos
     */
    @PostMapping("/calificar")
    public String calificarTrabajo(
            @RequestParam Long trabajoId,
            @RequestParam Integer puntuacion,
            HttpSession session) {

        if (puntuacion == null || puntuacion < 1 || puntuacion > 5) {
            return "redirect:/trabajo/calificar?id=" + trabajoId;
        }

        System.out.println("=== INICIO DE calificarTrabajo ===");

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        System.out.println("Usuario logueado: " + usuario);

        if (usuario == null) {
            System.out.println("Usuario no autenticado.");
            return "redirect:/usuario/login";
        }

        Trabajo trabajo = trabajoService.obtenerPorId(trabajoId);
        System.out.println("Trabajo obtenido: " + trabajo);
        System.out.println("ID del trabajo: " + trabajoId);

        if (trabajo == null) {
            System.out.println("Trabajo no encontrado.");
            return "redirect:/usuario/perfil";
        }

        // Califica al solicitante
        Usuario solicitante = trabajo.getSolicitante();
        System.out.println("Solicitante: " + solicitante);

        if (solicitante != null) {
            usuarioService.actualizarCalificacionPromedio(solicitante, puntuacion);
            System.out.println("Calificación promedio actualizada.");
        } else {
            System.out.println("No se encontró al solicitante.");
            return "redirect:/usuario/perfil";
        }

        // Elimina el trabajo
        trabajoService.eliminar(trabajo);
        System.out.println("Trabajo eliminado.");

        System.out.println("=== FIN DE calificarTrabajo ===");
        return "redirect:/trabajo";
    }
}