package DAO;

import Modelo.Asistencia;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDao {

    // Registrar Entrada o Salida
    public boolean registrarAsistencia(int idUsuario, String tipo) {
        String sql = "INSERT INTO registros_asistencia (id_usuario, tipo, fecha_hora) VALUES (?, ?, NOW())";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            ps.setString(2, tipo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar asistencia: " + e.getMessage());
            return false;
        }
    }

    // Reporte de atrasos
    public List<Asistencia> obtenerAtrasos() {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT r.id_registro, r.id_usuario, CONCAT(u.nombre, ' ', u.apellido) AS empleado, " +
                     "r.tipo, r.fecha_hora " +
                     "FROM registros_asistencia r " +
                     "JOIN usuarios u ON r.id_usuario = u.id_usuario " +
                     "WHERE r.tipo = 'ENTRADA' AND TIME(r.fecha_hora) > '09:30:00' " +
                     "ORDER BY r.fecha_hora DESC";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Asistencia a = new Asistencia();
                a.setIdRegistro(rs.getInt("id_registro"));
                a.setIdUsuario(rs.getInt("id_usuario"));
                a.setNombreCompleto(rs.getString("empleado"));
                a.setTipo(rs.getString("tipo"));
                a.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                lista.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error en reporte de atrasos: " + e.getMessage());
        }
        return lista;
    }

    // Reporte de salidas anticipadas 
    public List<Asistencia> obtenerSalidasAnticipadas() {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT r.id_registro, r.id_usuario, CONCAT(u.nombre, ' ', u.apellido) AS empleado, " +
                     "r.tipo, r.fecha_hora " +
                     "FROM registros_asistencia r " +
                     "JOIN usuarios u ON r.id_usuario = u.id_usuario " +
                     "WHERE r.tipo = 'SALIDA' AND TIME(r.fecha_hora) < '17:30:00' " +
                     "ORDER BY r.fecha_hora DESC";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Asistencia a = new Asistencia();
                a.setIdRegistro(rs.getInt("id_registro"));
                a.setIdUsuario(rs.getInt("id_usuario"));
                a.setNombreCompleto(rs.getString("empleado"));
                a.setTipo(rs.getString("tipo"));
                a.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                lista.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error en reporte de salidas anticipadas: " + e.getMessage());
        }
        return lista;
    }

    // Reporte de inasistencias en una fecha determinada
    public List<Usuario> obtenerInasistencias(LocalDate fecha) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre, u.apellido, u.correo " +
                     "FROM usuarios u " +
                     "WHERE u.id_rol = 2 AND u.activo = TRUE " +
                     "AND u.id_usuario NOT IN (" +
                     "    SELECT DISTINCT r.id_usuario FROM registros_asistencia r " +
                     "    WHERE DATE(r.fecha_hora) = ?" +
                     ")";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error en reporte de inasistencias: " + e.getMessage());
        }
        return lista;
    }
}