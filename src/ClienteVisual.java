import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

/*
  Ventana principal del cliente donde se muestran:
  - Categorías de productos
  - Tabla con productos
  - Botón para agregar al carrito
  - Botón para ver el carrito
 */
public class ClienteVisual extends JFrame {

    private GestorProductos gestor;
    private InicioVisual ventanaInicio; // 🔥 referencia al inicio

    // Componentes de la interfaz
    private JComboBox<String> comboCategorias;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    private JButton botonAgregarCarrito;
    private JButton botonVerCarrito;
    private JButton botonVolver;   // 🔥 NUEVO BOTÓN

    // Carrito de compras (Producto → Cantidad)
    private LinkedHashMap<Producto, Integer> carrito = new LinkedHashMap<>();

    // 🔥 CONSTRUCTOR NUEVO (recibe InicioVisual)
    public ClienteVisual(InicioVisual inicio, GestorProductos gestor) {
        this.ventanaInicio = inicio;
        this.gestor = gestor;

        // Siempre cargar productos desde el archivo
        gestor.recargarDesdeJSON();

        setTitle("Catálogo de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // ---------------- PANEL SUPERIOR ----------------
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboCategorias = new JComboBox<>();
        comboCategorias.setPreferredSize(new Dimension(200, 30));

        panelSuperior.add(new JLabel("Categoría: "));
        panelSuperior.add(comboCategorias);

        add(panelSuperior, BorderLayout.NORTH);

        // ---------------- TABLA DE PRODUCTOS ----------------
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Precio", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // ---------------- BOTONES INFERIORES ----------------
        JPanel panelInferior = new JPanel(new FlowLayout());

        botonAgregarCarrito = new JButton("Agregar al Carrito");
        botonVerCarrito = new JButton("Ver Carrito");
        botonVolver = new JButton("Volver");

        panelInferior.add(botonAgregarCarrito);
        panelInferior.add(botonVerCarrito);
        panelInferior.add(botonVolver);

        add(panelInferior, BorderLayout.SOUTH);

        // Eventos
        comboCategorias.addActionListener(e -> cargarProductosPorCategoria());
        botonAgregarCarrito.addActionListener(e -> agregarProductoAlCarrito());
        botonVerCarrito.addActionListener(e -> abrirCarrito());

        // 🔥 BOTÓN VOLVER → vuelve al inicio
        botonVolver.addActionListener(e -> {
            ventanaInicio.setVisible(true);
            dispose(); // cierra ClienteVisual
        });

        cargarCategorias();
    }


    // ====================== CARGAR CATEGORÍAS ======================
    private void cargarCategorias() {
        comboCategorias.removeAllItems();

        Set<String> categorias = new TreeSet<>();

        for (Producto producto : gestor.getListaProductos()) {

            // 🔥 ignorar productos inactivos
            if (!producto.isActivo()) continue;

            categorias.add(producto.getCategoria().toString());
        }

        for (String categoria : categorias) {
            comboCategorias.addItem(categoria);
        }

        if (comboCategorias.getItemCount() > 0) {
            comboCategorias.setSelectedIndex(0);
            cargarProductosPorCategoria();
        }
    }

    // ====================== CARGAR PRODUCTOS ======================
    private void cargarProductosPorCategoria() {
        String categoriaSeleccionada = (String) comboCategorias.getSelectedItem();
        modeloTabla.setRowCount(0);

        if (categoriaSeleccionada == null) return;

        for (Producto producto : gestor.getListaProductos()) {

            if (!producto.isActivo()) continue;

            if (producto.getCategoria().toString().equals(categoriaSeleccionada)) {
                modeloTabla.addRow(new Object[]{
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock()
                });
            }
        }
    }

    // ====================== CARRITO ======================
    private void agregarProductoAlCarrito() {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para agregar.");
            return;
        }

        int idProducto = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        Producto producto = gestor.buscarPorId(idProducto);

        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Error al buscar el producto.");
            return;
        }

        carrito.put(producto, carrito.getOrDefault(producto, 0) + 1);

        JOptionPane.showMessageDialog(this,
                "Producto agregado.\nCantidad en carrito: " + carrito.get(producto));
    }

    public void refrescarDatos() {
        gestor.recargarDesdeJSON();
        cargarCategorias();
    }

    private void abrirCarrito() {
        CarritoVisual ventanaCarrito = new CarritoVisual(carrito, gestor);

        ventanaCarrito.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                refrescarDatos();
            }
        });

        ventanaCarrito.setVisible(true);
    }
}





