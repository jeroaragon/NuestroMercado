import java.util.ArrayList;
import java.util.List;

public class Almacen {

    private List<Producto> productos;

    public Almacen() {
        productos = new ArrayList<>();
    }

    public Almacen(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean eliminarProducto(int id) {
        Producto p = buscarPorId(id);
        if (p != null) {
            p.setActivo(false);
            return true;
        }
        return false;
    }

    public boolean modificarStock(int id, int nuevoStock) {
        Producto p = buscarPorId(id);
        if (p != null) {
            p.setStock(nuevoStock);
            return true;
        }
        return false;
    }

    public void mostrarProductos() {
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    public void guardar(String archivo) {
        JSONGestora.guardarProductos(productos, archivo);
    }

    public void cargar(String archivo) {
        productos = JSONGestora.cargarProductos(archivo);
    }
}
