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

    private void procesarPago(String metodo) {
        // 1) Actualizar stock en el repo del GestorProductos (usando buscarPorId)
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto pEnCarrito = entry.getKey();
            int cantidad = entry.getValue();

            // Buscar el producto en el gestor por ID para asegurarnos de modificar la instancia correcta
            Producto pRepo = gestor.buscarPorId(pEnCarrito.getId());
            if (pRepo != null) {
                int nuevoStock = pRepo.getStock() - cantidad;
                if (nuevoStock < 0) nuevoStock = 0; // evita stock negativo
                pRepo.setStock(nuevoStock);
            } else {
                // Si no se encuentra (caso raro), intentamos actualizar la instancia del carrito igualmente
                int nuevoStock = pEnCarrito.getStock() - cantidad;
                if (nuevoStock < 0) nuevoStock = 0;
                pEnCarrito.setStock(nuevoStock);
            }
        }

        // 2) Guardar cambios en JSON (usar el método público del gestor)
        gestor.guardarEnJSONexterno();

        // 3) Crear ticket (pasamos una copia para que el ticket muestre lo comprado incluso si vaciamos el carrito)
        LinkedHashMap<Producto, Integer> copiaCarrito = new LinkedHashMap<>(carrito);
        TicketVisual ticket = new TicketVisual(copiaCarrito, metodo);
        ticket.setVisible(true);

        // 4) Vaciar carrito
        carrito.clear();

        JOptionPane.showMessageDialog(this, "Pago realizado con éxito por " + metodo + "!");

        dispose();
    }
}


