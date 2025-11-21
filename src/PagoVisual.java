import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PagoVisual extends JFrame {

    private LinkedHashMap<Producto, Integer> carrito;
    private GestorProductos gestor;

    public PagoVisual(LinkedHashMap<Producto, Integer> carrito, GestorProductos gestor) {
        this.carrito = carrito;
        this.gestor = gestor;

        setTitle("Método de Pago");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel titulo = new JLabel("Seleccione un método de pago", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo);

        JButton btnTarjeta = new JButton("Tarjeta");
        btnTarjeta.addActionListener(e -> procesarPago("Tarjeta"));
        add(btnTarjeta);

        JButton btnEfectivo = new JButton("Efectivo");
        btnEfectivo.addActionListener(e -> procesarPago("Efectivo"));
        add(btnEfectivo);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        add(btnVolver);
    }

    /**
     * Procesa el pago, verificando stock y generando ticket.
     */
    private void procesarPago(String metodoPago) {
        try {
            validarStock();
            descontarStock();
            gestor.guardarEnJSONexterno();

            // Copia para generar ticket sin afectar el carrito real
            LinkedHashMap<Producto, Integer> copia = new LinkedHashMap<>(carrito);

            new TicketVisual(copia, metodoPago).setVisible(true);
            carrito.clear();

            JOptionPane.showMessageDialog(this, "Pago realizado con éxito por " + metodoPago + "!");
            dispose();

        } catch (StockInsuficienteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Stock insuficiente", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Verifica que todos los productos tengan stock suficiente.
     */
    private void validarStock() throws StockInsuficienteException {
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();

            if (cantidad > p.getStock()) {
                throw new StockInsuficienteException(
                        "No hay suficiente stock del producto: " + p.getNombre()
                );
            }
        }
    }

    /**
     * Descuenta el stock en el repositorio del gestor.
     */
    private void descontarStock() {
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto pCarrito = entry.getKey();
            int cantidad = entry.getValue();

            Producto pRepo = gestor.buscarPorId(pCarrito.getId());

            if (pRepo != null) {
                pRepo.setStock(pRepo.getStock() - cantidad);
            } else {
                // Caso improbable, se descuenta en la instancia del carrito igual
                pCarrito.setStock(pCarrito.getStock() - cantidad);
            }
        }
    }
}



