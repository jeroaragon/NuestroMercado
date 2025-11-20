import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarritoVisual extends JFrame {

    private Map<Producto, Integer> carrito;

    private DefaultListModel<String> modeloLista;
    private JList<String> lista;

    private JButton btnEliminar1;
    private JButton btnEliminarTodo;
    private JButton btnVaciar;

    public CarritoVisual(Map<Producto, Integer> carrito) {
        this.carrito = carrito;

        setTitle("Carrito de Compras");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloLista = new DefaultListModel<>();
        lista = new JList<>(modeloLista);

        add(new JScrollPane(lista), BorderLayout.CENTER);

        // --- BOTONES ---
        btnEliminar1 = new JButton("Eliminar 1 unidad");
        btnEliminarTodo = new JButton("Eliminar producto completo");
        btnVaciar = new JButton("Vaciar carrito");

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.add(btnEliminar1);
        panelBotones.add(btnEliminarTodo);
        panelBotones.add(btnVaciar);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnEliminar1.addActionListener(e -> eliminarUnaUnidad());
        btnEliminarTodo.addActionListener(e -> eliminarProductoCompleto());
        btnVaciar.addActionListener(e -> vaciarCarrito());

        refrescar();
    }

    // ------------------------------------------------
    // MÉTODOS
    // ------------------------------------------------

    private void refrescar() {
        modeloLista.clear();

        for (Producto p : carrito.keySet()) {
            modeloLista.addElement(p.getNombre() + "  x" + carrito.get(p));
        }
    }

    private Producto obtenerProductoSeleccionado() {
        int index = lista.getSelectedIndex();
        if (index == -1) return null;

        int i = 0;
        for (Producto p : carrito.keySet()) {
            if (i == index) return p;
            i++;
        }
        return null;
    }

    // Eliminar UNA unidad
    private void eliminarUnaUnidad() {
        Producto p = obtenerProductoSeleccionado();
        if (p == null) return;

        int cant = carrito.get(p);
        if (cant > 1) carrito.put(p, cant - 1);
        else carrito.remove(p);

        refrescar();
    }

    // Eliminar TODAS las unidades del producto
    private void eliminarProductoCompleto() {
        Producto p = obtenerProductoSeleccionado();
        if (p == null) return;

        carrito.remove(p);
        refrescar();
    }

    private void vaciarCarrito() {
        carrito.clear();
        refrescar();
    }
}


