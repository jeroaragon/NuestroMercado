import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/*
  Ventana para que el administrador pueda:
   Ver todos los productos
   Agregar productos
   Modificar productos
   Eliminar productos
   Activar / Desactivar productos
   Refrescar la tabla
 */

public class GestionProductosVisual extends JFrame {


    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;


    private JButton botonAgregar;
    private JButton botonModificar;
    private JButton botonEliminar;
    private JButton botonVolver;
    private JButton botonRefrescar;
    private JButton botonToggleActivo;

    // Gestor que maneja la lista de productos
    private GestorProductos gestor;

    public GestionProductosVisual(GestorProductos gestor) {
        this.gestor = gestor;

        setTitle("Gestión de Productos");
        setSize(750, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());


        // TABLA

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock", "Activo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);


        // PANEL DE BOTONES

        JPanel panelBotones = new JPanel(new FlowLayout());

        botonAgregar = new JButton("Agregar");
        botonModificar = new JButton("Modificar");
        botonEliminar = new JButton("Eliminar");
        botonRefrescar = new JButton("Refrescar");
        botonToggleActivo = new JButton("Activar/Desactivar"); // NUEVO
        botonVolver = new JButton("Volver");


        panelBotones.add(botonAgregar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonRefrescar);
        panelBotones.add(botonToggleActivo);
        panelBotones.add(botonVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // EVENTOS DE LOS BOTONES

        botonAgregar.addActionListener(e -> abrirVentanaAgregar());
        botonModificar.addActionListener(e -> abrirVentanaModificar());
        botonEliminar.addActionListener(e -> eliminarProducto());
        botonRefrescar.addActionListener(e -> refrescarTabla());
        botonToggleActivo.addActionListener(e -> toggleActivo()); // NUEVO
        botonVolver.addActionListener(e -> volver());

        // Cargar la tabla apenas se abre la ventana
        cargarProductosEnTabla();
    }


    // CARGAR TABLA


      // Llena la tabla con todos los productos del gestor.
    public void cargarProductosEnTabla() {
        modeloTabla.setRowCount(0);

        for (Producto producto : gestor.getListaProductos()) {
            modeloTabla.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    (producto.getCategoria() != null ? producto.getCategoria().toString() : "SIN CATEGORÍA"),
                    producto.getPrecio(),
                    producto.getStock(),
                    (producto.isActivo() ? "Activo" : "Inactivo")
            });
        }
    }


    // ABRIR VENTANA: AGREGAR PRODUCTO

    private void abrirVentanaAgregar() {
        new AgregarProductoVisual(this, gestor).setVisible(true);
    }


    // ABRIR VENTANA: MODIFICAR PRODUCTO
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

    // ELIMINAR PRODUCTO
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


   // ACTIVAR / DESACTIVAR PRODUCTO
    private void toggleActivo() {
        int filaSeleccionada = tablaProductos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        Producto p = gestor.buscarPorId(id);

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Error: no se encontró el producto.");
            return;
        }

        // Cambiamos el estado
        p.setActivo(!p.isActivo());

        // Guardamos cambios en archivo JSON
        gestor.guardarProductos();

        // Refrescamos tabla
        refrescarTabla();

        JOptionPane.showMessageDialog(this,
                "Estado actualizado: ahora está " + (p.isActivo() ? "Activo" : "Inactivo"));
    }


    // VOLVER AL MENÚ ADMIN
    private void volver() {
        dispose();
        new MenuAdminVisual(gestor).setVisible(true);
    }

    //Recarga la tabla con los datos actuales del gestor.
    public void refrescarTabla() {
        cargarProductosEnTabla();
    }
}






