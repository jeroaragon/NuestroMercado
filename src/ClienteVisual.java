import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class ClienteVisual extends JFrame {

    private GestorProductos gestor;
    private JComboBox<String> comboCategorias;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    private JButton botonAgregarCarrito;
    private JButton botonVerCarrito;

    // Carrito → Mapa: Producto → Cantidad
    private Map<Producto, Integer> carrito = new LinkedHashMap<>();

    public ClienteVisual(GestorProductos gestor) {
        this.gestor = gestor;

        setTitle("Catálogo de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 380);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // ---------- PANEL SUPERIOR ----------
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboCategorias = new JComboBox<>();
        comboCategorias.setPreferredSize(new Dimension(200, 30));

        panelSuperior.add(new JLabel("Categoría: "));
        panelSuperior.add(comboCategorias);
        add(panelSuperior, BorderLayout.NORTH);

        // ---------- TABLA ----------
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Precio", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // ---------- BOTONES INFERIORES ----------
        JPanel panelInferior = new JPanel(new FlowLayout());

        botonAgregarCarrito = new JButton("Agregar al Carrito");
        botonVerCarrito = new JButton("Ver Carrito");

        panelInferior.add(botonAgregarCarrito);
        panelInferior.add(botonVerCarrito);

        add(panelInferior, BorderLayout.SOUTH);

        // Eventos
        comboCategorias.addActionListener(e -> cargarProductosPorCategoria());
        botonAgregarCarrito.addActionListener(e -> agregarAlCarrito());
        botonVerCarrito.addActionListener(e -> abrirCarrito());

        cargarCategorias();
    }

    // ====================== CATEGORÍAS ======================

    private void cargarCategorias() {
        Set<String> categorias = new TreeSet<>();

        for (Producto p : gestor.getListaProductos()) {
            categorias.add(p.getCategoria().toString());
        }

        for (String cat : categorias) {
            comboCategorias.addItem(cat);
        }
    }

    private void cargarProductosPorCategoria() {
        String seleccion = (String) comboCategorias.getSelectedItem();
        modeloTabla.setRowCount(0);

        for (Producto p : gestor.getListaProductos()) {
            if (p.getCategoria().toString().equals(seleccion)) {
                modeloTabla.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.getStock()
                });
            }
        }
    }

    // ====================== CARRITO ======================

    private void agregarAlCarrito() {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para agregar.");
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

        Producto producto = gestor.buscarPorId(id);

        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Error al buscar el producto.");
            return;
        }

        carrito.put(producto, carrito.getOrDefault(producto, 0) + 1);

        JOptionPane.showMessageDialog(this,
                "Producto agregado al carrito.\nCantidad: " + carrito.get(producto));
    }

    private void abrirCarrito() {
        new CarritoVisual(carrito).setVisible(true);
    }
}

