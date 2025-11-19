
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

    // ==========================================
    // GETTER PRINCIPAL
    // ==========================================
    public List<Producto> getProductos() {
        return productos;
    }

    // ==========================================
    // AGREGAR PRODUCTO
    // ==========================================
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    // ==========================================
    // BUSCAR POR ID
    // ==========================================
    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; // no encontrado
    }

    // ==========================================
    // ELIMINAR (lógico = setActivo(false))
    // ==========================================
    public boolean eliminarProducto(int id) {
        Producto p = buscarPorId(id);
        if (p != null) {
            p.setActivo(false);
            return true;
        }
        return false;
    }

    // ==========================================
    // MODIFICAR STOCK
    // ==========================================
    public boolean modificarStock(int id, int nuevoStock) {
        Producto p = buscarPorId(id);
        if (p != null) {
            p.setStock(nuevoStock);
            return true;
        }
        return false;
    }

    // ==========================================
    // MOSTRAR PRODUCTOS (para consola)
    // ==========================================
    public void mostrarProductos() {
        for (Producto p : productos) {
            System.out.println(p.getId() + " - " + p.getNombre() +
                    " | Stock: " + p.getStock() +
                    " | Precio: " + p.getPrecio() +
                    " | Activo: " + p.isActivo());
        }
    }

    // ==========================================
    // GUARDAR A JSON
    // ==========================================
    public void guardar(String archivo) {
        JSONGestora.guardarProductos(productos, archivo);
    }

    // ==========================================
    // CARGAR DESDE JSON
    // ==========================================
    public void cargar(String archivo) {
        productos = JSONGestora.cargarProductos(archivo);
    }
}
