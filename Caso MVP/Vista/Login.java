package Vista;

import Controlador.LoginControlador;
import Modelo.Usuario;
import estilo.Estilo;
import java.awt.*;
import javax.swing.*;

public class Login extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private LoginControlador authController;
    private final Estilo estilo = new Estilo();

    public Login() {
        authController = new LoginControlador();
        initUI();
    }

    private void initUI() {
        estilo.aplicarTema(this);
        setTitle("Sistema de Asistencia");
        setSize(430, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(0, 0));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Estilo.GRIS_FONDO);

        JPanel panelHeader = estilo.crearCabecera("Iniciar sesión", "Sistema de asistencia");
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        JPanel panelLogin = new JPanel(new BorderLayout(15, 15));
        panelLogin.setBackground(Estilo.GRIS_FONDO);
        panelLogin.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Estilo.GRIS_FONDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblCorreo = estilo.crearEtiquetaTitulo("Correo electrónico");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 0.3;
        panelFormulario.add(lblCorreo, gbc);

        txtCorreo = new JTextField();
        estilo.estiloCampo(txtCorreo);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 0.7;
        panelFormulario.add(txtCorreo, gbc);

        JLabel lblContrasena = estilo.crearEtiquetaTitulo("Contraseña");
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panelFormulario.add(lblContrasena, gbc);

        txtContrasena = new JPasswordField();
        estilo.estiloCampo(txtContrasena);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        panelFormulario.add(txtContrasena, gbc);

        btnIngresar = new JButton("Iniciar sesión");
        estilo.estiloBoton(btnIngresar, true);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Estilo.GRIS_FONDO);
        panelBoton.add(btnIngresar);

        panelLogin.add(panelFormulario, BorderLayout.CENTER);
        panelLogin.add(panelBoton, BorderLayout.SOUTH);
        panelPrincipal.add(panelLogin, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);
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
                new VistaAdmin(usuario).setVisible(true);
            } else {
                new VistaUsuario(usuario).setVisible(true);
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