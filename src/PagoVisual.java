import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        // 1) Actualizar stock
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();
            p.setStock(p.getStock() - cantidad);
        }

        // 2) Guardar cambios en JSON
        gestor.guardarEnJSONexterno();

        // 3) Crear ticket
        new TicketVisual(carrito, metodo).setVisible(true);

        // 4) Vaciar carrito
        carrito.clear();

        JOptionPane.showMessageDialog(this, "Pago realizado con éxito por " + metodo + "!");

        dispose();
    }
}

