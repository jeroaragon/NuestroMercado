import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana para que el administrador pueda:
 * - Ver todos los productos
 * - Agregar productos
 * - Modificar productos
 * - Eliminar productos
 * - Refrescar la tabla
 */
public class GestionProductosVisual extends JFrame {

    // Tabla y modelo
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    // Botones
    private JButton botonAgregar;
    private JButton botonModificar;
    private JButton botonEliminar;
    private JButton botonVolver;
    private JButton botonRefrescar;

    // Gestor que maneja la lista de productos (inyectado desde el menú)
    private GestorProductos gestor;

    public GestionProductosVisual(GestorProductos gestor) {
        this.gestor = gestor;

        setTitle("Gestión de Productos");
        setSize(700, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // -----------------------------------------------------------
        // TABLA
        // -----------------------------------------------------------
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // Ninguna celda se puede editar
            }
        };

        tablaProductos = new JTable(modeloTabla);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // -----------------------------------------------------------
        // PANEL DE BOTONES
        // -----------------------------------------------------------
        JPanel panelBotones = new JPanel(new FlowLayout());

        botonAgregar = new JButton("Agregar");
        botonModificar = new JButton("Modificar");
        botonEliminar = new JButton("Eliminar");
        botonRefrescar = new JButton("Refrescar");
        botonVolver = new JButton("Volver");

        // Agregamos los botones al panel
        panelBotones.add(botonAgregar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonRefrescar);
        panelBotones.add(botonVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // -----------------------------------------------------------
        // EVENTOS DE LOS BOTONES
        // -----------------------------------------------------------
        botonAgregar.addActionListener(e -> abrirVentanaAgregar());
        botonModificar.addActionListener(e -> abrirVentanaModificar());
        botonEliminar.addActionListener(e -> eliminarProducto());
        botonRefrescar.addActionListener(e -> refrescarTabla());
        botonVolver.addActionListener(e -> volver());

        // Cargar la tabla apenas se abre la ventana
        cargarProductosEnTabla();
    }

    // ===============================================================
    // CARGAR TABLA
    // ===============================================================
    /**
     * Llena la tabla con todos los productos del gestor.
     */
    public void cargarProductosEnTabla() {
        modeloTabla.setRowCount(0); // Borra filas existentes

        for (Producto producto : gestor.getListaProductos()) {
            modeloTabla.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    (producto.getCategoria() != null ? producto.getCategoria().toString() : "SIN CATEGORÍA"),
                    producto.getPrecio(),
                    producto.getStock()
            });
        }
    }

    // ===============================================================
    // ABRIR VENTANA: AGREGAR PRODUCTO
    // ===============================================================
    private void abrirVentanaAgregar() {
        new AgregarProductoVisual(this, gestor).setVisible(true);
    }

    // ===============================================================
    // ABRIR VENTANA: MODIFICAR PRODUCTO
    // ===============================================================
    private void abrirVentanaModificar() {
        int filaSeleccionada = tablaProductos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para modificar.");
            return;
        }

        // Obtenemos todos los datos de la fila seleccionada
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        String categoria = modeloTabla.getValueAt(filaSeleccionada, 2).toString();
        double precio = Double.parseDouble(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
        int stock = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString());

        // Abrimos la ventana de modificar con esos datos
        new ModificarProductoVisual(this, gestor, id, nombre, categoria, precio, stock).setVisible(true);
    }

    // ===============================================================
    // ELIMINAR PRODUCTO
    // ===============================================================
    private void eliminarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        if (gestor.eliminarProducto(id)) {
            modeloTabla.removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el producto.");
        }
    }

    // ===============================================================
    // VOLVER AL MENÚ ADMIN
    // ===============================================================
    private void volver() {
        dispose(); // Cierra esta ventana
        new MenuAdminVisual(gestor).setVisible(true); // Vuelve al menú
    }

    // ===============================================================
    // REFRESCAR TABLA
    // ===============================================================
    /**
     * Recarga la tabla con los datos actuales del gestor.
     */
    public void refrescarTabla() {
        cargarProductosEnTabla();
    }
}






