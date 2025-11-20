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

    // ===============================================================
    // AGREGAR PRODUCTO CON VALIDACIÓN DE DUPLICADOS
    // ===============================================================
    public boolean agregarProducto(String nombre, Categorias categoria, double precio, int stock) {

        // Verificar si ya existe el nombre
        for (Producto p : listaProductos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return false; // producto repetido
            }
        }

        // Crear y agregar
        Producto nuevo = new Producto(nombre, categoria, precio, stock);
        listaProductos.add(nuevo);
        guardar();
        return true;
    }

    // Para cuando recibís la categoría como String (por ejemplo en interfaz vieja)
    public boolean agregarProducto(String nombre, String categoriaStr, double precio, int stock) {
        Categorias categoria = Categorias.valueOf(categoriaStr.toUpperCase());
        return agregarProducto(nombre, categoria, precio, stock);
    }

    // ===============================================================
    // BUSCAR POR ID
    // ===============================================================
    public Producto buscarPorId(int id) {
        for (Producto p : listaProductos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    // ===============================================================
    // MODIFICAR PRODUCTO
    // ===============================================================
    public boolean modificarProducto(int id, String nombre, Categorias categoria, double precio, int stock) {
        Producto p = buscarPorId(id);
        if (p == null) return false;

        // Validar nombre repetido con otro producto
        for (Producto prod : listaProductos) {
            if (prod.getNombre().equalsIgnoreCase(nombre) && prod.getId() != id) {
                return false; // nombre en uso por otro producto
            }
        }

        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setPrecio(precio);
        p.setStock(stock);

        guardar();
        return true;
    }

    // ===============================================================
    // ELIMINAR PRODUCTO
    // ===============================================================
    public boolean eliminarProducto(int id) {
        Producto p = buscarPorId(id);
        if (p == null) return false;

        listaProductos.remove(p);
        guardar();
        return true;
    }
}





