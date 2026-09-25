package Grupo12.ConectaEmpleo.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Entidad que representa a un usuario en el sistema.
 * Contiene los datos del usuario y sus relaciones con otras entidades.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Column(unique = true)
    private String correo;
    private String contrasena;
    private String ubicacion;
    private String experiencia;
    private BigDecimal calificacionPromedio;
    private Integer calificacionesCount;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    // Relaciones
    @OneToMany(mappedBy = "solicitante", cascade = CascadeType.ALL)
    private List<Trabajo> trabajosPublicados;

    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    private List<Postulacion> postulaciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Notificacion> notificaciones;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public BigDecimal getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(BigDecimal calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public Integer getCalificacionesCount() {
        return calificacionesCount;
    }

    public void setCalificacionesCount(Integer calificacionesCount) {
        this.calificacionesCount = calificacionesCount;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public List<Trabajo> getTrabajosPublicados() {
        return trabajosPublicados;
    }

    public void setTrabajosPublicados(List<Trabajo> trabajosPublicados) {
        this.trabajosPublicados = trabajosPublicados;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }
}