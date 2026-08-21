package Controlador;

import DAO.UsuarioDAO;
import Modelo.Usuario;

public class LoginControlador {
    private UsuarioDAO usuarioDAO;

    public LoginControlador() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String correo, String contrasena) {
        // Validacion
        if (correo == null || correo.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            return null;
        }
        return usuarioDAO.login(correo.trim(), contrasena.trim());
    }

    public boolean esAdministrador(Usuario usuario) {
        // 1 = ADMIN, 2 = EMPLEADO
        return usuario != null && usuario.getIdRol() == 1;
    }
}