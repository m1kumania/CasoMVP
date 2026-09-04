package estilo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

public class Estilo {
    public static final Color AZUL_7 = new Color(25, 115, 210);
    public static final Color AZUL_7_OSCURO = new Color(15, 63, 112);
    public static final Color GRIS_FONDO = new Color(238, 240, 243);
    public static final Color GRIS_PANEL = new Color(247, 248, 250);
    public static final Color GRIS_LINEA = new Color(180, 186, 196);
    public static final Color GRIS_TEXTO = new Color(70, 76, 86);
    public static final Color BLANCO = new Color(255, 255, 255);
    public static final Color VERDE_ACCION = new Color(101, 188, 94);
    public static final Color ROJO_ACCION = new Color(215, 92, 92);

    public void aplicarTema(JFrame frame) {
        frame.setBackground(GRIS_FONDO);
        frame.setForeground(Color.BLACK);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("PasswordField.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 11));
        UIManager.put("TabbedPane.font", new Font("Segoe UI", Font.BOLD, 12));
        UIManager.put("TabbedPane.foreground", Color.BLACK);
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("PasswordField.foreground", Color.BLACK);
        UIManager.put("ComboBox.foreground", Color.BLACK);
        UIManager.put("TableHeader.foreground", Color.BLACK);
    }

    public JPanel crearCabecera(String titulo, String textoDerecha) {
        JPanel panel = new JPanel(new java.awt.BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(228, 235, 245), 0, getHeight(), new Color(211, 220, 232));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
                g2.dispose();
            }
        };

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, GRIS_LINEA),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
        ));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.BLACK);

        panel.add(lblTitulo, java.awt.BorderLayout.CENTER);

        if (textoDerecha != null && !textoDerecha.trim().isEmpty()) {
            JLabel lblSub = new JLabel(textoDerecha, SwingConstants.RIGHT);
            lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblSub.setForeground(Color.BLACK);
            panel.add(lblSub, java.awt.BorderLayout.EAST);
        }

        return panel;
    }

    public void estiloBoton(JButton boton, boolean primario) {
        boton.setFocusPainted(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(130, 145, 170), 1),
                BorderFactory.createEmptyBorder(7, 16, 7, 16)
        ));
        boton.setBackground(primario ? AZUL_7 : new Color(243, 244, 246));
        boton.setForeground(Color.BLACK);
        boton.setOpaque(true);
    }

    public void estiloBotonAccion(JButton boton, Color fondo, Color texto) {
        boton.setFocusPainted(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(130, 145, 170), 1),
                BorderFactory.createEmptyBorder(7, 14, 7, 14)
        ));
        boton.setBackground(fondo);
        boton.setForeground(Color.BLACK);
        boton.setOpaque(true);
    }

    public void estiloCampo(JTextField campo) {
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 178, 191), 1),
                BorderFactory.createEmptyBorder(5, 6, 5, 6)
        ));
        campo.setBackground(BLANCO);
        campo.setForeground(Color.BLACK);
        campo.setCaretColor(AZUL_7);
    }

    public void estiloCampo(JPasswordField campo) {
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 178, 191), 1),
                BorderFactory.createEmptyBorder(5, 6, 5, 6)
        ));
        campo.setBackground(BLANCO);
        campo.setForeground(Color.BLACK);
        campo.setCaretColor(AZUL_7);
    }

    public void estiloPanel(JPanel panel) {
        panel.setBackground(GRIS_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void estiloTabla(JTable tabla) {
        tabla.setFillsViewportHeight(true);
        tabla.setGridColor(new Color(222, 225, 230));
        tabla.setSelectionBackground(new Color(194, 220, 245));
        tabla.setSelectionForeground(Color.BLACK);
        tabla.setRowHeight(28);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(222, 228, 236));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GRIS_LINEA));
    }

    public void estiloScroll(JScrollPane scroll) {
        scroll.getViewport().setBackground(BLANCO);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_LINEA));
    }

    public void estiloPestanas(JTabbedPane tabbedPane) {
        tabbedPane.setBackground(GRIS_PANEL);
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
    }

    public JLabel crearEtiquetaTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.LEFT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }

    public void aplicarTemaBase(Component componente) {
        if (componente instanceof JComponent) {
            ((JComponent) componente).setBackground(GRIS_PANEL);
        }
        if (componente instanceof JButton button) {
            estiloBoton(button, true);
        }
        if (componente instanceof JTextField field) {
            estiloCampo(field);
        }
        if (componente instanceof JPasswordField field) {
            estiloCampo(field);
        }
        if (componente instanceof JTable table) {
            estiloTabla(table);
        }
    }
}
