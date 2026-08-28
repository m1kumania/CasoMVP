package Vista;

import Controlador.LoginControlador;
import Modelo.Usuario;
import java.awt.*;
import javax.swing.*;

public class Login extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private LoginControlador authController;

    public Login() {
        authController = new LoginControlador();
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Asistencia - Login");
        setSize(380, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCentro = new JPanel(new GridLayout(3, 2, 8, 12));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(25, 25, 10, 25));

        panelCentro.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField();
        panelCentro.add(txtCorreo);

        panelCentro.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panelCentro.add(txtContrasena);

        btnIngresar = new JButton("Iniciar Sesión");
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnIngresar);

        add(panelCentro, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        btnIngresar.addActionListener(e -> procesarLogin());
    }

    private void procesarLogin() {
        String correo = txtCorreo.getText();
        String pass = new String(txtContrasena.getPassword());

        Usuario usuario = authController.autenticar(correo, pass);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido(a) " + usuario.getNombre() + " " + usuario.getApellido());
            this.dispose(); 

            if (authController.esAdministrador(usuario)) {
                try {
                    Class<?> vistaUsuario = Class.forName("Vista.VistaUsuario");
                    Object ventana = vistaUsuario
                            .getConstructor(Usuario.class)
                            .newInstance(usuario);
                    vistaUsuario.getMethod("setVisible", boolean.class).invoke(ventana, true);
                } catch (ReflectiveOperationException ex) {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo abrir el panel de administrador.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } // Panel de Administrador
            } else {
                new VistaAdmin(usuario).setVisible(true); // Panel de Empleado
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas o usuario inactivo.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}