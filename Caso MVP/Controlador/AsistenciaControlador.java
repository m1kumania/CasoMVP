package Controlador;

import DAO.AsistenciaDao;
import DAO.UsuarioDAO;
import Modelo.Asistencia;
import Modelo.Usuario;
import java.time.LocalDate;
import java.util.List;

public class AsistenciaControlador {
    private AsistenciaDao asistenciaDao;
    private UsuarioDAO usuarioDAO;

    public AsistenciaControlador() {
        this.asistenciaDao = new AsistenciaDao();
        this.usuarioDAO = new UsuarioDAO();
    }

    // Control de Asistencia
    public boolean marcarEntrada(int idUsuario) {
        return asistenciaDao.registrarAsistencia(idUsuario, "ENTRADA");
    }

    public boolean marcarSalida(int idUsuario) {
        return asistenciaDao.registrarAsistencia(idUsuario, "SALIDA");
    }

    // Reportes
    public List<Asistencia> generarReporteAtrasos() {
        return asistenciaDao.obtenerAtrasos();
    }

    public List<Asistencia> generarReporteSalidasAnticipadas() {
        return asistenciaDao.obtenerSalidasAnticipadas();
    }

    public List<Usuario> generarReporteInasistencias(LocalDate fecha) {
        if (fecha == null) {
            fecha = LocalDate.now();
        }
        return asistenciaDao.obtenerInasistencias(fecha);
    }

    // Gestión de Usuarios 
    public boolean registrarUsuario(String nombre, String apellido, String correo, String contrasena, int idRol) {
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || correo.trim().isEmpty() || contrasena.trim().isEmpty()) {
            return false;
        }
        Usuario nuevo = new Usuario(nombre.trim(), apellido.trim(), correo.trim(), contrasena.trim(), idRol);
        return usuarioDAO.crearUsuario(nuevo);
    }

    public boolean actualizarUsuario(int idUsuario, String nombre, String apellido, String correo, String contrasena, int idRol) {
        if (idUsuario <= 0 || nombre.trim().isEmpty() || apellido.trim().isEmpty() || correo.trim().isEmpty()) {
            return false;
        }
        Usuario modificado = new Usuario(idUsuario, nombre.trim(), apellido.trim(), correo.trim(), contrasena.trim(), idRol, true);
        return usuarioDAO.modificarUsuario(modificado);
    }

    public boolean darDeBajaUsuario(int idUsuario) {
        if (idUsuario <= 0) return false;
        return usuarioDAO.eliminarUsuario(idUsuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.listarUsuarios();
    }
}