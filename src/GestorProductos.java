import java.util.*;

public class GestorProductos {

    private RepositorioGenerico<Producto> repo = new RepositorioGenerico<>();
    private String archivoJSON = "data/productos.json";

    public GestorProductos() {
        cargarDesdeJSON();
    }

    // =====================================================
    // 🔥 Nuevo setLista corregido (sin romper el repo)
    // =====================================================
    public void setLista(List<Producto> nuevaLista) {
        repo.listar().clear();
        if (nuevaLista != null) {
            repo.listar().addAll(nuevaLista);
        }
    }

    // =====================================================
    // 🔥 Obtener el menor ID disponible
    // =====================================================
    private int obtenerMenorIDDisponible() {
        boolean[] usado = new boolean[10000];
        for (Producto p : repo.listar()) {
            if (p.getId() < usado.length) {
                usado[p.getId()] = true;
            }
        }

        for (int i = 1; i < usado.length; i++) {
            if (!usado[i]) return i;
        }
        return usado.length;
    }

    // =====================================================
    // 🔥 Crear producto
    // =====================================================
    public boolean agregarProducto(String nombre, Categorias categoria, double precio, int stock) {

        for (Producto p : repo.listar()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return false; // repetido
            }
        }

        int id = obtenerMenorIDDisponible();
        Producto nuevo = new Producto(id, nombre, categoria, precio, stock, true);

        repo.agregar(nuevo);
        guardarEnJSON();
        return true;
    }

    // =====================================================
    // 🔥 Modificar producto
    // =====================================================
    public boolean modificarProducto(int id, String nombre, Categorias categoria, double precio, int stock) {
        for (Producto p : repo.listar()) {
            if (p.getId() == id) {
                p.setNombre(nombre);
                p.setCategoria(categoria);
                p.setPrecio(precio);
                p.setStock(stock);
                guardarEnJSON();
                return true;
            }
        }
        return false;
    }

    // =====================================================
    // 🔥 Eliminar producto
    // =====================================================
    public boolean eliminarProducto(int id) {
        for (Producto p : repo.listar()) {
            if (p.getId() == id) {
                repo.eliminar(p);
                guardarEnJSON();
                return true;
            }
        }
        return false;
    }

    // =====================================================
    // 🔥 Obtener lista
    // =====================================================
    public List<Producto> getListaProductos() {
        return repo.listar();
    }

    // =====================================================
    // 🔥 Persistencia con JSON
    // =====================================================
    private void cargarDesdeJSON() {
        List<Producto> cargados = JSONGestora.cargarProductos(archivoJSON);

        repo.listar().clear();

        if (cargados != null) {
            repo.listar().addAll(cargados);
        }
    }

    private void guardarEnJSON() {
        JSONGestora.guardarProductos(repo.listar(), archivoJSON);
    }

    public void guardarEnJSONexterno() {
        JSONGestora.guardarProductos(repo.listar(), archivoJSON);
    }

    // =====================================================
    // 🔥 Recargar desde JSON (llamado por ClienteVisual)
    // =====================================================
    public void recargarDesdeJSON() {
        List<Producto> cargados = JSONGestora.cargarProductos(archivoJSON);

        repo.listar().clear();

        if (cargados != null) {
            repo.listar().addAll(cargados);
        }
    }

    // =====================================================
    // 🔥 Buscar por ID
    // =====================================================
    public Producto buscarPorId(int id) {
        for (Producto p : repo.listar()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}







