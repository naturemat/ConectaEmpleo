package Grupo12.ConectaEmpleo.Service;

import Grupo12.ConectaEmpleo.Model.ParticipanteCapacitacion;
import Grupo12.ConectaEmpleo.Model.ParticipanteCapacitacionId;
import Grupo12.ConectaEmpleo.Model.Usuario;
import Grupo12.ConectaEmpleo.Model.Capacitacion;
import Grupo12.ConectaEmpleo.Repository.ParticipanteCapacitacionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio que maneja la lógica del negocio relacionada con las inscripciones a capacitaciones:
 * - Inscribir usuario a una capacitación
 * - Listar inscripciones de un usuario
 */
@Service
public class ParticipanteCapacitacionService {

    @Autowired
    private ParticipanteCapacitacionRepository repository;

    /**
     * Inscripción de un usuario a una capacitación.
     *
     * @param usuario Usuario que se inscribe
     * @param capacitacion Capacitación a la que se inscribe
     */
    public void inscribirUsuarioEnCapacitacion(Usuario usuario, Capacitacion capacitacion) {
        ParticipanteCapacitacionId id = new ParticipanteCapacitacionId();
        id.setUsuarioId(usuario.getId());
        id.setCapacitacionId(capacitacion.getId());

        if (repository.existsById(id)) {
            return;
        }

        ParticipanteCapacitacion pc = new ParticipanteCapacitacion();
        pc.setId(id);
        pc.setUsuario(usuario);
        pc.setCapacitacion(capacitacion);
        pc.setCertificado(false);
        pc.setCalificacionAlumno(null);

        repository.save(pc);
    }

    /**
     * Obtiene todas las inscripciones de un usuario.
     *
     * @param usuario Usuario a buscar
     * @return Lista de inscripciones
     */
    public List<ParticipanteCapacitacion> findByTrabajador(Usuario usuario) {
        return repository.findByUsuario(usuario);
    }
}