package Vista;

import Controlador.AsistenciaControlador;
import Modelo.Usuario;
import java.awt.*;
import javax.swing.*;

public class VistaAdmin extends JFrame {
    private Usuario usuarioActual;
    private AsistenciaControlador asistenciaControlador;
    private JButton btnEntrada;
    private JButton btnSalida;
    private JButton btnCerrarSesion;

    public VistaAdmin(Usuario usuario) {
        this.usuarioActual = usuario;
        this.asistenciaControlador = new AsistenciaControlador();
        initUI();
    }

    private void initUI() {
        setTitle("Panel de Marcación - " + usuarioActual.getNombre() + " " + usuarioActual.getApellido());
        setSize(420, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        JLabel lblBienvenida = new JLabel("Empleado: " + usuarioActual.getNombre() + " " + usuarioActual.getApellido(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 14));
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 10));
        add(lblBienvenida, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 15, 0));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        btnEntrada = new JButton("Marcar Entrada");
        btnEntrada.setBackground(new Color(46, 139, 87));
        btnEntrada.setForeground(Color.WHITE);

        btnSalida = new JButton("Marcar Salida");
        btnSalida.setBackground(new Color(178, 34, 34));
        btnSalida.setForeground(Color.WHITE);

        panelBotones.add(btnEntrada);
        panelBotones.add(btnSalida);
        add(panelBotones, BorderLayout.CENTER);

        btnCerrarSesion = new JButton("Cerrar Sesión");
        JPanel panelSur = new JPanel();
        panelSur.add(btnCerrarSesion);
        add(panelSur, BorderLayout.SOUTH);

        // Eventos
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