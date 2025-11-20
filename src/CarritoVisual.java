import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarritoVisual extends JFrame {

    private LinkedHashMap<Producto, Integer> carrito;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaProductos;
    private JLabel labelTotal;

    public CarritoVisual(LinkedHashMap<Producto, Integer> carrito) {
        this.carrito = carrito;

        setTitle("Carrito de Compras");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Carrito", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaProductos = new JList<>(modeloLista);
        listaProductos.setFont(new Font("Arial", Font.PLAIN, 16));
        add(new JScrollPane(listaProductos), BorderLayout.CENTER);

        actualizarLista();

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 5, 5));

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        panelBotones.add(btnVolver);

        JButton btnEliminarUno = new JButton("Eliminar 1 unidad");
        btnEliminarUno.addActionListener(this::eliminarUnaUnidad);
        panelBotones.add(btnEliminarUno);

        JButton btnEliminarTodos = new JButton("Eliminar producto completo");
        btnEliminarTodos.addActionListener(this::eliminarProductoCompleto);
        panelBotones.add(btnEliminarTodos);

        JButton btnVaciar = new JButton("Vaciar carrito");
        btnVaciar.addActionListener(e -> {
            carrito.clear();
            actualizarLista();
        });
        panelBotones.add(btnVaciar);

        add(panelBotones, BorderLayout.EAST);

        JPanel panelInferior = new JPanel(new BorderLayout());

        labelTotal = new JLabel("Total: $0.00");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 18));
        labelTotal.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelInferior.add(labelTotal, BorderLayout.WEST);

        JButton btnPagar = new JButton("Pagar");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPagar.addActionListener(e -> pagar());
        panelInferior.add(btnPagar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        actualizarTotal();
    }

    private void actualizarLista() {
        modeloLista.clear();
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();
            modeloLista.addElement(p.getNombre() + " x" + cantidad + " - $" + p.getPrecio());
        }
        actualizarTotal();
    }

    private void actualizarTotal() {
        double total = 0;
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            total += entry.getKey().getPrecio() * entry.getValue();
        }
        labelTotal.setText("Total: $" + String.format("%.2f", total));
    }

    private void eliminarUnaUnidad(ActionEvent e) {
        int index = listaProductos.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto");
            return;
        }

        Producto seleccionado = (Producto) carrito.keySet().toArray()[index];

        int cantidad = carrito.get(seleccionado);

        if (cantidad > 1) {
            carrito.put(seleccionado, cantidad - 1);
        } else {
            carrito.remove(seleccionado);
        }

        actualizarLista();
    }

    private void eliminarProductoCompleto(ActionEvent e) {
        int index = listaProductos.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto");
            return;
        }

        Producto seleccionado = (Producto) carrito.keySet().toArray()[index];
        carrito.remove(seleccionado);

        actualizarLista();
    }

    private void pagar() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Procesando pago...\n(Después programamos qué hacer aquí)");

        // Aquí podrás agregar: guardar venta, restar stock, escribir en JSON, etc.
    }
}




