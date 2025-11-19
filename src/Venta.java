import java.time.LocalDate;
import java.util.List;

public class Venta {

    private int id;
    private Cliente cliente;
    private List<Producto> productos;
    private double total;
    private LocalDate fecha;

    public Venta(int id, Cliente cliente, List<Producto> productos, double total) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
        this.total = total;
        this.fecha = LocalDate.now();
    }

    // Getters
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<Producto> getProductos() { return productos; }
    public double getTotal() { return total; }
    public LocalDate getFecha() { return fecha; }
}
