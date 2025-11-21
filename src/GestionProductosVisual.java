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
    private JButton botonRefrescar;

    private GestorProductos gestor; // ← USAMOS GESTORPRODUCTOS

    public GestionProductosVisual(GestorProductos gestor) {
        this.gestor = gestor; // ← INYECTADO POR EL MENU ADMIN

        setTitle("Gestión de Productos");
        setSize(700, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ================= TABLA =================
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel(new FlowLayout());

        botonAgregar = new JButton("Agregar");
        botonModificar = new JButton("Modificar");
        botonEliminar = new JButton("Eliminar");
        botonRefrescar = new JButton("Refrescar");
        botonVolver = new JButton("Volver");

        panelBotones.add(botonAgregar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonRefrescar);
        panelBotones.add(botonVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // ================= EVENTOS =================
        botonAgregar.addActionListener(e -> abrirVentanaAgregar());
        botonModificar.addActionListener(e -> abrirVentanaModificar());
        botonEliminar.addActionListener(e -> eliminarProducto());
        botonRefrescar.addActionListener(e -> refrescar());
        botonVolver.addActionListener(e -> volver());

        // Cargar los datos iniciales
        cargarProductosEnTabla();
    }

    // ===============================================================
    // CARGAR TABLA
    // ===============================================================
    public void cargarProductosEnTabla() {
        modeloTabla.setRowCount(0);

        for (Producto p : gestor.getListaProductos()) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    (p.getCategoria() != null ? p.getCategoria().toString() : "SIN CATEGORÍA"),
                    p.getPrecio(),
                    p.getStock()
            });
        }
    }

    // ===============================================================
    // ABRIR VENTANA AGREGAR
    // ===============================================================
    private void abrirVentanaAgregar() {
        new AgregarProductoVisual(this, gestor).setVisible(true);
    }

    // ===============================================================
    // ABRIR VENTANA MODIFICAR
    // ===============================================================
    private void abrirVentanaModificar() {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para modificar");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = modeloTabla.getValueAt(fila, 1).toString();
        String categoria = modeloTabla.getValueAt(fila, 2).toString();
        double precio = Double.parseDouble(modeloTabla.getValueAt(fila, 3).toString());
        int stock = Integer.parseInt(modeloTabla.getValueAt(fila, 4).toString());

        new ModificarProductoVisual(this, gestor, id, nombre, categoria, precio, stock).setVisible(true);
    }

    // ===============================================================
    // ELIMINAR
    // ===============================================================
    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        if (gestor.eliminarProducto(id)) {
            modeloTabla.removeRow(fila);
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar producto");
        }
    }

    // ===============================================================
    // VOLVER
    // ===============================================================
    private void volver() {
        dispose();
        new MenuAdminVisual(gestor).setVisible(true);
    }

    // ===============================================================
    // REFRESCAR TABLA
    // ===============================================================
    public void refrescar() {
        cargarProductosEnTabla();
    }
}





