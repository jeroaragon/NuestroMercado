import java.util.List;

public class GestorProductos {

    private Almacen almacen;
    private String archivoJSON;

    public GestorProductos(Almacen almacen, String archivoJSON) {
        this.almacen = almacen;
        this.archivoJSON = archivoJSON;
    }

    // ==========================================
    // AGREGAR PRODUCTO
    // ==========================================
    public void agregarProducto(String nombre, Categorias categoria, double precio, int stock) {
        Producto p = new Producto(nombre, categoria, precio, stock);
        almacen.agregarProducto(p);
        guardar();
    }

    // ==========================================
    // MODIFICAR PRODUCTO
    // ==========================================
    public boolean modificarProducto(int id, String nombre, double precio, int stock) {
        Producto p = almacen.buscarPorId(id);
        if (p != null) {
            p.setNombre(nombre);
            p.setPrecio(precio);
            p.setStock(stock);
            guardar();
            return true;
        }
        return false;
    }

    // ==========================================
    // ELIMINAR LÓGICAMENTE
    // ==========================================
    public boolean eliminarProducto(int id) {
        boolean ok = almacen.eliminarProducto(id);
        if (ok) guardar();
        return ok;
    }

    // ==========================================
    // BUSCAR
    // ==========================================
    public Producto buscar(int id) {
        return almacen.buscarPorId(id);
    }

    public List<Producto> listar() {
        return almacen.getProductos();
    }

    // ==========================================
    // JSON GUARDAR
    // ==========================================
    public void guardar() {
        JSONGestora.guardarProductos(almacen.getProductos(), archivoJSON);
    }

    // ==========================================
    // JSON CARGAR
    // ==========================================
    public void cargar() {
        List<Producto> lista = JSONGestora.cargarProductos(archivoJSON);
        almacen = new Almacen(lista);
    }
}


