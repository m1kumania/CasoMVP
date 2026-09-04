package Vista;

import Controlador.AsistenciaControlador;
import Modelo.Asistencia;
import Modelo.Usuario;
import estilo.Estilo;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaAdmin extends JFrame {
    private final Usuario adminLogueado;
    private final AsistenciaControlador controller;
    private final Estilo estilo = new Estilo();

    private JTextField txtId, txtNombre, txtApellido, txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbRol;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTablaUsuarios;

    private JTable tablaReportes;
    private DefaultTableModel modeloTablaReportes;
    private JTextField txtFechaInasistencia;

    public VistaAdmin(Usuario admin) {
        this.adminLogueado = admin;
        this.controller = new AsistenciaControlador();
        initUI();
        cargarUsuarios();
    }

    private void initUI() {
        estilo.aplicarTema(this);
        setTitle("Panel Administrador - " + adminLogueado.getNombre() + " " + adminLogueado.getApellido());
        setSize(980, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Estilo.GRIS_FONDO);

        JPanel panelSuperior = estilo.crearCabecera(
                "Panel administrativo",
                "Administrador: " + adminLogueado.getCorreo()
        );

        JButton btnCerrar = new JButton("Cerrar sesión");
        estilo.estiloBoton(btnCerrar, false);
        btnCerrar.addActionListener(e -> {
            this.dispose();
            new Login().setVisible(true);
        });

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.setOpaque(false);
        panelAcciones.add(btnCerrar);
        panelSuperior.add(panelAcciones, BorderLayout.EAST);

        JTabbedPane pestanas = new JTabbedPane();
        estilo.estiloPestanas(pestanas);
        pestanas.addTab("Gestión de Usuarios", crearPanelUsuarios());
        pestanas.addTab("Reportes de Asistencia", crearPanelReportes());

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(pestanas, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.CENTER);
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        estilo.estiloPanel(panel);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Estilo.GRIS_PANEL);
        form.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Estilo.GRIS_LINEA), "Datos del usuario"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();
        cbRol = new JComboBox<>(new String[]{"ADMIN", "EMPLEADO"});

        estilo.estiloCampo(txtId);
        estilo.estiloCampo(txtNombre);
        estilo.estiloCampo(txtApellido);
        estilo.estiloCampo(txtCorreo);
        estilo.estiloCampo(txtContrasena);
        cbRol.setBackground(Color.WHITE);
        cbRol.setBorder(BorderFactory.createLineBorder(Estilo.GRIS_LINEA));

        form.add(new JLabel("ID (Automático):"), crearPosicion(0, 0, 1, 1));
        form.add(txtId, crearPosicion(0, 1, 1, 1));
        form.add(new JLabel("Nombre:"), crearPosicion(1, 0, 1, 1));
        form.add(txtNombre, crearPosicion(1, 1, 1, 1));
        form.add(new JLabel("Apellido:"), crearPosicion(2, 0, 1, 1));
        form.add(txtApellido, crearPosicion(2, 1, 1, 1));
        form.add(new JLabel("Correo:"), crearPosicion(3, 0, 1, 1));
        form.add(txtCorreo, crearPosicion(3, 1, 1, 1));
        form.add(new JLabel("Contraseña:"), crearPosicion(4, 0, 1, 1));
        form.add(txtContrasena, crearPosicion(4, 1, 1, 1));
        form.add(new JLabel("Rol:"), crearPosicion(5, 0, 1, 1));
        form.add(cbRol, crearPosicion(5, 1, 1, 1));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(Estilo.GRIS_PANEL);
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        estilo.estiloBotonAccion(btnCrear, Estilo.VERDE_ACCION, Color.WHITE);
        estilo.estiloBotonAccion(btnModificar, Estilo.AZUL_7, Color.WHITE);
        estilo.estiloBotonAccion(btnEliminar, Estilo.ROJO_ACCION, Color.WHITE);
        estilo.estiloBoton(btnLimpiar, false);

        panelBotones.add(btnCrear);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelNorte = new JPanel(new BorderLayout(12, 12));
        panelNorte.setBackground(Estilo.GRIS_PANEL);
        panelNorte.add(form, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        modeloTablaUsuarios = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Correo", "Rol"}, 0);
        tablaUsuarios = new JTable(modeloTablaUsuarios);
        estilo.estiloTabla(tablaUsuarios);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        estilo.estiloScroll(scroll);

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
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        estilo.estiloPanel(panel);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.setBackground(Estilo.GRIS_PANEL);
        panelFiltros.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Estilo.GRIS_LINEA), "Filtros"));

        JButton btnAtrasos = new JButton("Reporte Atrasos");
        JButton btnSalidas = new JButton("Reporte Salidas Anticipadas");
        txtFechaInasistencia = new JTextField(LocalDate.now().toString(), 10);
        JButton btnInasistencias = new JButton("Reporte Inasistencias");

        estilo.estiloBoton(btnAtrasos, false);
        estilo.estiloBoton(btnSalidas, false);
        estilo.estiloBoton(btnInasistencias, true);
        estilo.estiloCampo(txtFechaInasistencia);

        panelFiltros.add(btnAtrasos);
        panelFiltros.add(btnSalidas);
        panelFiltros.add(new JLabel("Fecha:"));
        panelFiltros.add(txtFechaInasistencia);
        panelFiltros.add(btnInasistencias);

        modeloTablaReportes = new DefaultTableModel();
        tablaReportes = new JTable(modeloTablaReportes);
        estilo.estiloTabla(tablaReportes);
        JScrollPane scroll = new JScrollPane(tablaReportes);
        estilo.estiloScroll(scroll);

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

    private GridBagConstraints crearPosicion(int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
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