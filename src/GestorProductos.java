import java.util.ArrayList;
import java.util.List;

public class GestorProductos {

    private ArrayList<Producto> listaProductos;
    private final String ARCHIVO = "data/productos.json";

    public GestorProductos() {
        // Cargar productos del JSON
        List<Producto> cargados = JSONGestora.cargarProductos(ARCHIVO);
        listaProductos = new ArrayList<>(cargados);
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void guardar() {
        JSONGestora.guardarProductos(listaProductos, ARCHIVO);
    }

    public void agregarProducto(String nombre, Categorias categoria, double precio, int stock) {
        Producto p = new Producto(nombre, categoria, precio, stock);
        listaProductos.add(p);
        guardar();
    }

    public Producto buscarPorId(int id) {
        for (Producto p : listaProductos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public boolean modificarProducto(int id, String nombre, Categorias categoria, double precio, int stock) {
        Producto p = buscarPorId(id);
        if (p == null) return false;

        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setPrecio(precio);
        p.setStock(stock);
        guardar();
        return true;
    }

    public boolean eliminarProducto(int id) {
        Producto p = buscarPorId(id);
        if (p == null) return false;

        listaProductos.remove(p);
        guardar();
        return true;
    }

    public void agregarProducto(String nombre, String categoria, double precio, int stock) {
    }
}




