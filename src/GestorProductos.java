import java.util.*;

/**
 * Clase que se encarga de manejar todos los productos:
 * - Agregar, modificar, eliminar
 * - Guardar y cargar desde JSON
 * - Manejar IDs disponibles
 * - Proveer la lista actual de productos
 */
public class GestorProductos {

    // Repositorio genérico donde se guardan los productos en memoria
    private RepositorioGenerico<Producto> repositorio = new RepositorioGenerico<>();

    // Ruta del archivo JSON donde se guardan los datos
    private String archivoJSON = "data/productos.json";

    public GestorProductos() {
        cargarDesdeJSON(); // Carga inicial al abrir la aplicación
    }

    // =====================================================
    // 🔥 Reemplazar toda la lista de productos
    // =====================================================
    /**
     * Reemplaza la lista interna del repositorio por otra nueva.
     * No crea un nuevo repositorio, solo limpia y vuelve a cargar.
     */
    public void setLista(List<Producto> nuevaLista) {
        repositorio.listar().clear();

        if (nuevaLista != null) {
            repositorio.listar().addAll(nuevaLista);
        }
    }

    // =====================================================
    // 🔥 Obtener el menor ID libre
    // =====================================================
    /**
     * Busca de forma eficiente el ID más chico disponible.
     * Ej: si existen 1, 2, 3, 5 → devuelve 4.
     */
    private int obtenerMenorIDDisponible() {
        boolean[] usado = new boolean[10000];

        for (Producto p : repositorio.listar()) {
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
    // 🔥 Agregar un producto nuevo
    // =====================================================
    /**
     * Agrega un producto evitando que se repita el nombre.
     */
    public boolean agregarProducto(String nombre, Categorias categoria, double precio, int stock) {

        // Validar nombre repetido
        for (Producto p : repositorio.listar()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return false;
            }
        }

        int id = obtenerMenorIDDisponible();
        Producto nuevo = new Producto(id, nombre, categoria, precio, stock, true);

        repositorio.agregar(nuevo);
        guardarEnJSON();
        return true;
    }

    // =====================================================
    // 🔥 Modificar un producto existente
    // =====================================================
    public boolean modificarProducto(int id, String nombre, Categorias categoria, double precio, int stock) {
        for (Producto p : repositorio.listar()) {
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
    // 🔥 Eliminar un producto por ID
    // =====================================================
    public boolean eliminarProducto(int id) {
        for (Producto p : repositorio.listar()) {
            if (p.getId() == id) {

                repositorio.eliminar(p);
                guardarEnJSON();

                return true;
            }
        }
        return false;
    }

    // =====================================================
    // 🔥 Obtener lista completa de productos
    // =====================================================
    public List<Producto> getListaProductos() {
        return repositorio.listar();
    }

    // =====================================================
    // 🔥 Cargar datos desde JSON al iniciar
    // =====================================================
    private void cargarDesdeJSON() {
        List<Producto> cargados = JSONGestora.cargarProductos(archivoJSON);

        repositorio.listar().clear();

        if (cargados != null) {
            repositorio.listar().addAll(cargados);
        }
    }

    // =====================================================
    // 🔥 Guardar en JSON
    // =====================================================
    private void guardarEnJSON() {
        JSONGestora.guardarProductos(repositorio.listar(), archivoJSON);
    }

    // Guardar desde afuera (no siempre se usa)
    public void guardarEnJSONexterno() {
        JSONGestora.guardarProductos(repositorio.listar(), archivoJSON);
    }

    // =====================================================
    // 🔥 Recargar desde JSON (ClienteVisual lo usa después de pagar)
    // =====================================================
    public void recargarDesdeJSON() {
        List<Producto> cargados = JSONGestora.cargarProductos(archivoJSON);

        repositorio.listar().clear();

        if (cargados != null) {
            repositorio.listar().addAll(cargados);
        }
    }

    // =====================================================
    // 🔥 Buscar producto por ID
    // =====================================================
    public Producto buscarPorId(int id) {
        for (Producto p : repositorio.listar()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}








