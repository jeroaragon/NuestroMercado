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
    private JButton botonRefrescar; // ← NUEVO

    private GestorProductos gestor;

    public GestionProductosVisual(GestorProductos gestor) {
        this.gestor = gestor;

        setTitle("Gestión de Productos");
        setSize(700, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());

        // ---------- TABLA ----------
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaProductos);
        add(scroll, BorderLayout.CENTER);

        // ---------- BOTONES ----------
        JPanel panelBotones = new JPanel(new FlowLayout());

        botonAgregar = new JButton("Agregar");
        botonModificar = new JButton("Modificar");
        botonEliminar = new JButton("Eliminar");
        botonRefrescar = new JButton("Refrescar"); // ← NUEVO
        botonVolver = new JButton("Volver");

        panelBotones.add(botonAgregar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonRefrescar);
        panelBotones.add(botonVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        botonAgregar.addActionListener(e -> abrirVentanaAgregar());
        botonModificar.addActionListener(e -> abrirVentanaModificar());
        botonEliminar.addActionListener(e -> eliminarProducto());
        botonRefrescar.addActionListener(e -> refrescar()); // ← NUEVO
        botonVolver.addActionListener(e -> volver());

        cargarProductosEnTabla();
    }

    // ============================== METODOS ==============================

    public void cargarProductosEnTabla() {
        modeloTabla.setRowCount(0);

        for (Producto p : gestor.getListaProductos()) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getCategoria().toString(),
                    p.getPrecio(),
                    p.getStock()
            });
        }
    }

    private void abrirVentanaAgregar() {
        new AgregarProductoVisual(this, gestor).setVisible(true);
    }

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

    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        boolean eliminado = gestor.eliminarProducto(id);

        if (eliminado) {
            modeloTabla.removeRow(fila);
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el producto");
        }
    }

    private void volver() {
        dispose();
        new MenuAdminVisual(gestor).setVisible(true);
    }

    public void refrescar() {
        cargarProductosEnTabla();
    }

    // ============================================
    // MAIN DE PRUEBA — PARA VER DIRECTAMENTE ESTA VENTANA
    // ============================================
    /*public static void main(String[] args) {
        GestorProductos gestor = new GestorProductos();

        // CARGA DE PRUEBA
        gestor.agregarProducto(new Producto(1, "Pan", Categoria.ALIMENTOS, 800, 20));
        gestor.agregarProducto(new Producto(2, "Coca-Cola", Categoria.BEBIDAS, 1200, 10));
        gestor.agregarProducto(new Producto(3, "Lavandina", Categoria.LIMPIEZA, 950, 15));

        SwingUtilities.invokeLater(() -> {
            new GestionProductosVisual(gestor).setVisible(true);
        });
    }*/
}




