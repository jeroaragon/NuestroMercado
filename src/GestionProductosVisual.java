import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestionProductosVisual extends JFrame {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    private JButton botonAgregar;
    private JButton botonModificar;
    private JButton botonEliminar;
    private JButton botonVolver;

    public GestionProductosVisual() {
        setTitle("Gestión de Productos");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());

        // ---------- TABLA ----------
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0
        );
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaProductos);

        add(scroll, BorderLayout.CENTER);

        // ---------- BOTONES ----------
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        botonAgregar = new JButton("Agregar");
        botonModificar = new JButton("Modificar");
        botonEliminar = new JButton("Eliminar");
        botonVolver = new JButton("Volver");

        panelBotones.add(botonAgregar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        botonAgregar.addActionListener(e -> abrirVentanaAgregar());
        botonModificar.addActionListener(e -> abrirVentanaModificar());
        botonEliminar.addActionListener(e -> eliminarProducto());
        botonVolver.addActionListener(e -> volver());

        // Cargar productos en tabla (llamado a tu lógica)
        cargarProductosEnTabla();
    }

    // ============================== METODOS ==============================

    private void cargarProductosEnTabla() {
        modeloTabla.setRowCount(0);

        // ---------------------------------------------
        // ACA CARGÁS LOS PRODUCTOS DESDE GestorProductos
        //
        // Ejemplo:
        // for (Producto p : gestor.getLista()) {
        //     modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        // }
        //
        // ---------------------------------------------

        // Ejemplo temporal:
        modeloTabla.addRow(new Object[]{1, "Gaseosa", "Bebidas", 500.0, 15});
        modeloTabla.addRow(new Object[]{2, "Yerba Mate", "Almacén", 1200.0, 25});
    }

    private void abrirVentanaAgregar() {
       new AgregarProductoVisual(this).setVisible(true);
    }

    private void abrirVentanaModificar() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para modificar");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);
        String categoria = (String) modeloTabla.getValueAt(fila, 2);
        double precio = (double) modeloTabla.getValueAt(fila, 3);
        int stock = (int) modeloTabla.getValueAt(fila, 4);

        //new VentanaModificarProducto(this, id, nombre, categoria, precio, stock).setVisible(true);
    }

    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        // ------------------------------
        // ACÁ LLAMÁS A gestor.eliminarProducto(id)
        // ------------------------------

        JOptionPane.showMessageDialog(this, "Producto eliminado");

        modeloTabla.removeRow(fila);
    }

    private void volver() {
        dispose();
        new MenuAdminVisual().setVisible(true);
    }

    // Para actualizar tabla desde otras ventanas
    public void refrescar() {
        cargarProductosEnTabla();
    }
}

