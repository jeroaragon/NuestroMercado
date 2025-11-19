import javax.swing.*;
import java.awt.*;

public class MenuAdminVisual extends JFrame {

    private JButton botonGestionProductos;
    private JButton botonCerrarSesion;
    private GestorProductos gestor;

    public MenuAdminVisual(GestorProductos gestor) {
        this.gestor = gestor;
        setTitle("Menú Administrador");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);

        // Título
        JLabel titulo = new JLabel("Panel de Administración");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titulo, gbc);

        // Botón para gestionar productos
        botonGestionProductos = new JButton("Gestionar Productos");
        botonGestionProductos.setFont(new Font("Arial", Font.PLAIN, 16));
        botonGestionProductos.setPreferredSize(new Dimension(220, 40));
        gbc.gridy = 1;
        panel.add(botonGestionProductos, gbc);

        // Botón cerrar sesión
        botonCerrarSesion = new JButton("Cerrar Sesión");
        botonCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 16));
        botonCerrarSesion.setPreferredSize(new Dimension(220, 40));
        gbc.gridy = 2;
        panel.add(botonCerrarSesion, gbc);

        add(panel);

        // Listeners
        botonGestionProductos.addActionListener(e -> {
            new GestionProductosVisual(gestor).setVisible(true);
            dispose();
        });
        botonCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void abrirGestionProductos() {
        // ACÁ ES DONDE VOS VAS A PONER TU INTERFAZ DE PRODUCTOS
        // Ejemplo:
        // new ModificarProducto().setVisible(true);

        new GestionProductosVisual(gestor).setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        new InicioVisual().setVisible(true); // Vuelve al login
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuAdminVisual().setVisible(true));
    }*/
}
