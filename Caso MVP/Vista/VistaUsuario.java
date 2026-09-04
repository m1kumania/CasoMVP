package Vista;

import Controlador.AsistenciaControlador;
import Modelo.Usuario;
import estilo.Estilo;
import java.awt.*;
import javax.swing.*;

public class VistaUsuario extends JFrame {
    private Usuario usuarioActual;
    private AsistenciaControlador asistenciaControlador;
    private JButton btnEntrada;
    private JButton btnSalida;
    private JButton btnCerrarSesion;
    private final Estilo estilo = new Estilo();

    public VistaUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        this.asistenciaControlador = new AsistenciaControlador();
        initUI();
    }

    private void initUI() {
        estilo.aplicarTema(this);
        setTitle("Panel de Marcación - " + usuarioActual.getNombre() + " " + usuarioActual.getApellido());
        setSize(520, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Estilo.GRIS_FONDO);

        JPanel panelHeader = estilo.crearCabecera(
                "Marcador de asistencia",
                usuarioActual.getNombre() + " " + usuarioActual.getApellido()
        );
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(20, 20));
        panelCentro.setBackground(Estilo.GRIS_FONDO);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel lblBienvenida = new JLabel("Bienvenido(a)", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(30, 58, 92));
        panelCentro.add(lblBienvenida, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 20, 0));
        panelBotones.setBackground(Estilo.GRIS_FONDO);
        btnEntrada = new JButton("Marcar entrada");
        btnSalida = new JButton("Marcar salida");
        estilo.estiloBotonAccion(btnEntrada, Estilo.VERDE_ACCION, Color.WHITE);
        estilo.estiloBotonAccion(btnSalida, Estilo.ROJO_ACCION, Color.WHITE);
        panelBotones.add(btnEntrada);
        panelBotones.add(btnSalida);
        panelCentro.add(panelBotones, BorderLayout.CENTER);

        btnCerrarSesion = new JButton("Cerrar sesión");
        estilo.estiloBoton(btnCerrarSesion, false);
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(Estilo.GRIS_FONDO);
        panelSur.add(btnCerrarSesion);
        panelCentro.add(panelSur, BorderLayout.SOUTH);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.CENTER);

        btnEntrada.addActionListener(e -> {
            boolean ok = asistenciaControlador.marcarEntrada(usuarioActual.getIdUsuario());
            if (ok) JOptionPane.showMessageDialog(this, "Entrada registrada exitosamente.");
            else JOptionPane.showMessageDialog(this, "Error al registrar entrada.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        btnSalida.addActionListener(e -> {
            boolean ok = asistenciaControlador.marcarSalida(usuarioActual.getIdUsuario());
            if (ok) JOptionPane.showMessageDialog(this, "Salida registrada exitosamente.");
            else JOptionPane.showMessageDialog(this, "Error al registrar salida.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        btnCerrarSesion.addActionListener(e -> {
            this.dispose();
            new Login().setVisible(true);
        });
    }
}