package Vista;

import Controlador.AsistenciaControlador;
import Modelo.Asistencia;
import Modelo.Usuario;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaUsuario extends JFrame {
    private Usuario adminLogueado;
    private AsistenciaControlador controller;

    //  CRUD
    private JTextField txtId, txtNombre, txtApellido, txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbRol;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTablaUsuarios;

    // Reportes
    private JTable tablaReportes;
    private DefaultTableModel modeloTablaReportes;
    private JTextField txtFechaInasistencia;

    public VistaUsuario(Usuario admin) {
        this.adminLogueado = admin;
        this.controller = new AsistenciaControlador();
        initUI();
        cargarUsuarios();
    }

    private void initUI() {
        setTitle("Panel Administrador - " + adminLogueado.getNombre() + " " + adminLogueado.getApellido());
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Gestión de Usuarios", crearPanelUsuarios());
        pestanas.addTab("Reportes de Asistencia", crearPanelReportes());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel(" Sesión activa: Administrador (" + adminLogueado.getCorreo() + ")", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitulo.setForeground(Color.BLACK);
        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.addActionListener(e -> {
            this.dispose();
            new Login().setVisible(true);
        });

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(btnCerrar, BorderLayout.EAST);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(panelSuperior, BorderLayout.NORTH);
        add(pestanas, BorderLayout.CENTER);
        aplicarColorTexto(getContentPane());
    }

    private void aplicarColorTexto(Component componente) {
        componente.setForeground(Color.BLACK);
        if (componente instanceof Container) {
            Container contenedor = (Container) componente;
            for (Component hijo : contenedor.getComponents()) {
                aplicarColorTexto(hijo);
            }
        }
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();
        cbRol = new JComboBox<>(new String[]{"ADMIN", "EMPLEADO"});

        form.add(new JLabel("ID (Automático):"));
        form.add(txtId);
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Apellido:"));
        form.add(txtApellido);
        form.add(new JLabel("Correo:"));
        form.add(txtCorreo);
        form.add(new JLabel("Contraseña:"));
        form.add(txtContrasena);
        form.add(new JLabel("Rol:"));
        form.add(cbRol);

        // Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(form, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        // Tabla Usuarios
        modeloTablaUsuarios = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Correo", "Rol"}, 0);
        tablaUsuarios = new JTable(modeloTablaUsuarios);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);

        // Evento click tabla para llenar formulario
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modeloTablaUsuarios.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTablaUsuarios.getValueAt(fila, 1).toString());
                txtApellido.setText(modeloTablaUsuarios.getValueAt(fila, 2).toString());
                txtCorreo.setText(modeloTablaUsuarios.getValueAt(fila, 3).toString());
                cbRol.setSelectedItem(modeloTablaUsuarios.getValueAt(fila, 4).toString());
            }
        });

        // Acciones
        btnCrear.addActionListener(e -> {
            int rolId = cbRol.getSelectedItem().equals("ADMIN") ? 1 : 2;
            boolean ok = controller.registrarUsuario(txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(), new String(txtContrasena.getPassword()), rolId);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.");
                limpiarCampos();
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear usuario. Verifica los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnModificar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            int id = Integer.parseInt(txtId.getText());
            int rolId = cbRol.getSelectedItem().equals("ADMIN") ? 1 : 2;
            boolean ok = controller.actualizarUsuario(id, txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(), new String(txtContrasena.getPassword()), rolId);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Usuario modificado exitosamente.");
                limpiarCampos();
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            int id = Integer.parseInt(txtId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar a este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = controller.darDeBajaUsuario(id);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                    limpiarCampos();
                    cargarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton btnAtrasos = new JButton("Reporte Atrasos");
        JButton btnSalidas = new JButton("Reporte Salidas Anticipadas");
        
        txtFechaInasistencia = new JTextField(LocalDate.now().toString(), 8);
        JButton btnInasistencias = new JButton("Reporte Inasistencias");

        panelFiltros.add(btnAtrasos);
        panelFiltros.add(btnSalidas);
        panelFiltros.add(new JLabel("Fecha:"));
        panelFiltros.add(txtFechaInasistencia);
        panelFiltros.add(btnInasistencias);

        modeloTablaReportes = new DefaultTableModel();
        tablaReportes = new JTable(modeloTablaReportes);
        JScrollPane scroll = new JScrollPane(tablaReportes);

        btnAtrasos.addActionListener(e -> {
            modeloTablaReportes.setColumnIdentifiers(new String[]{"ID Empleado", "Nombre", "Tipo", "Fecha y Hora (> 09:30)"});
            modeloTablaReportes.setRowCount(0);
            List<Asistencia> lista = controller.generarReporteAtrasos();
            for (Asistencia a : lista) {
                modeloTablaReportes.addRow(new Object[]{a.getIdUsuario(), a.getNombreCompleto(), a.getTipo(), a.getFechaHora()});
            }
        });

        btnSalidas.addActionListener(e -> {
            modeloTablaReportes.setColumnIdentifiers(new String[]{"ID Empleado", "Nombre", "Tipo", "Fecha y Hora (< 17:30)"});
            modeloTablaReportes.setRowCount(0);
            List<Asistencia> lista = controller.generarReporteSalidasAnticipadas();
            for (Asistencia a : lista) {
                modeloTablaReportes.addRow(new Object[]{a.getIdUsuario(), a.getNombreCompleto(), a.getTipo(), a.getFechaHora()});
            }
        });

        btnInasistencias.addActionListener(e -> {
            try {
                LocalDate fecha = LocalDate.parse(txtFechaInasistencia.getText().trim());
                modeloTablaReportes.setColumnIdentifiers(new String[]{"ID Empleado", "Nombre", "Apellido", "Correo"});
                modeloTablaReportes.setRowCount(0);
                List<Usuario> lista = controller.generarReporteInasistencias(fecha);
                for (Usuario u : lista) {
                    modeloTablaReportes.addRow(new Object[]{u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getCorreo()});
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa AAAA-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(panelFiltros, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void cargarUsuarios() {
        modeloTablaUsuarios.setRowCount(0);
        List<Usuario> lista = controller.obtenerTodosLosUsuarios();
        for (Usuario u : lista) {
            modeloTablaUsuarios.addRow(new Object[]{
                u.getIdUsuario(),
                u.getNombre(),
                u.getApellido(),
                u.getCorreo(),
                u.getIdRol() == 1 ? "ADMIN" : "EMPLEADO"
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtContrasena.setText("");
        cbRol.setSelectedIndex(1);
        tablaUsuarios.clearSelection();
    }
}