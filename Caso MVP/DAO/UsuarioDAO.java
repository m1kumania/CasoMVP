package DAO;

import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Validación
    public Usuario login(String correo, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ? AND activo = TRUE";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getInt("id_rol"),
                    rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return null;
    }

    // Crear Usuario
    public boolean crearUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena, id_rol, activo) VALUES (?, ?, ?, ?, ?, TRUE)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            ps.setInt(5, u.getIdRol());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    // Modificar Usuario
    public boolean modificarUsuario(Usuario u) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, id_rol = ? WHERE id_usuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            ps.setInt(5, u.getIdRol());
            ps.setInt(6, u.getIdUsuario());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar usuario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar Usuario 
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "UPDATE usuarios SET activo = FALSE WHERE id_usuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // Listar todos los usuarios activos
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE activo = TRUE";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getInt("id_rol"),
                    rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }
}