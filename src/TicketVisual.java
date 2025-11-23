import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class TicketVisual extends JFrame {

    // Constructor: recibe el carrito con productos y metodo de pago
    public TicketVisual(LinkedHashMap<Producto, Integer> carrito, String metodoPago) {

        setTitle("Ticket de Compra");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        double total = 0;
        StringBuilder sb = new StringBuilder();

        sb.append("========= TICKET DE COMPRA =========\n\n");

        // Recorremos cada producto del carrito
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto p = entry.getKey();              // Producto actual
            int cant = entry.getValue();              // Cantidad comprada
            double subtotal = p.getPrecio() * cant;   // Cálculo del subtotal


            sb.append(String.format("%-20s x%d   $%.2f\n", p.getNombre(), cant, subtotal));
            total += subtotal;
        }


        sb.append("\n------------------------------------\n");

        sb.append(String.format("TOTAL: $%.2f\n", total));
        sb.append("Método: " + metodoPago + "\n");

        sb.append("Gracias por su compra!\n");
        sb.append("====================================\n");

        area.setText(sb.toString());

        add(new JScrollPane(area));
    }
}


