import java.util.HashMap;
import java.util.Map;

public class Carrito {

    private Map<Producto, Integer> items = new HashMap<>();

    public void agregarProducto(Producto p, int cantidad) throws StockInsuficienteException {

        if (cantidad > p.getStock()) {
            throw new StockInsuficienteException(
                    "Intentaste agregar " + cantidad + " unidades pero solo hay " + p.getStock()
            );
        }

        // si pasa el control, agregamos
        items.put(p, items.getOrDefault(p, 0) + cantidad);
    }

    public void quitarProducto(Producto p) {
        items.remove(p);
    }

    public double calcularTotal() {
        double total = 0;
        for (Map.Entry<Producto, Integer> e : items.entrySet()) {
            total += e.getKey().getPrecio() * e.getValue();
        }
        return total;
    }

    public void limpiar() {
        items.clear();
    }

    public Map<Producto, Integer> getItems() {
        return items;
    }
}
