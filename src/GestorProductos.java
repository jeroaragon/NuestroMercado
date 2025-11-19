import java.util.ArrayList;

public class GestorProductos {

    private ArrayList<Producto> listaProductos;
    private int idActual = 1;  // ID autoincremental

    public GestorProductos() {
        listaProductos = new ArrayList<>();

        // Puedes cargar datos iniciales si querés:
        // agregarProducto("Gaseosa", "Bebidas", 500, 15);
        // agregarProducto("Yerba Mate", "Almacén", 1200, 25);
    }

    // ==============================
    // GET LISTA COMPLETA
    // ==============================
    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    // ==============================
    // AGREGAR PRODUCTO
    // ==============================
    public void agregarProducto(String nombre, String categoria, double precio, int stock) {
        Producto p = new Producto(idActual++, nombre, categoria, precio, stock);
        listaProductos.add(p);
    }

    // ==============================
    // BUSCAR POR ID
    // ==============================
    public Producto buscarPorId(int id) {
        for (Producto p : listaProductos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    // ==============================
    // MODIFICAR PRODUCTO
    // ==============================
    public boolean modificarProducto(int id, String nombre, Categorias categoria, double precio, int stock) {
        Producto p = buscarPorId(id);
        if (p == null) return false;

        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setPrecio(precio);
        p.setStock(stock);
        return true;
    }

    // ==============================
    // ELIMINAR PRODUCTO
    // ==============================
    public boolean eliminarProducto(int id) {
        Producto p = buscarPorId(id);
        if (p == null) return false;

        return listaProductos.remove(p);
    }
}

