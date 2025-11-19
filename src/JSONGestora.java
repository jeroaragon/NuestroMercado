import org.json.*;
import java.nio.file.*;
import java.util.*;

public class JSONGestora {

    // ============================================================
    // CREAR ARCHIVO SI NO EXISTE
    // ============================================================
    private static void crearArchivo(String archivo) throws Exception {
        Path path = Paths.get(archivo);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.write(path, "[]".getBytes());       // JSON vacío
        }
    }

    // ============================================================
    // CONVERTIR OBJETOS → JSON
    // ============================================================
    public static JSONObject usuarioToJSON(Usuario u) {
        JSONObject o = new JSONObject();
        o.put("nombre", u.getNombre());
        o.put("apellido", u.getApellido());
        o.put("username", u.getUsername());
        o.put("email", u.getEmail());
        o.put("password", u.getPassword());
        o.put("activo", u.isActivo());
        o.put("tipo", u.getTipoUsuario());
        return o;
    }

    public static JSONObject productoToJSON(Producto p) {
        JSONObject o = new JSONObject();
        o.put("id", p.getId());
        o.put("nombre", p.getNombre());
        o.put("categoria", p.getCategoria());
        o.put("precio", p.getPrecio());
        o.put("stock", p.getStock());
        o.put("activo", p.isActivo());
        return o;
    }

    // ============================================================
    // CONVERTIR JSON → OBJETOS
    // ============================================================
    public static Usuario jsonToUsuario(JSONObject o) {
        String nombre = o.getString("nombre");
        String apellido = o.getString("apellido");
        String username = o.getString("username");
        String email = o.getString("email");
        String password = o.getString("password");
        boolean activo = o.getBoolean("activo");
        String tipo = o.getString("tipo");

        Usuario u = tipo.equals("Administrador")
                ? new Administrador(nombre, apellido, username, email, password)
                : new Cliente(nombre, apellido, username, email, password);

        u.setActivo(activo);
        return u;
    }

    public static Producto jsonToProducto(JSONObject o) {
        int id = o.getInt("id");
        String nombre = o.getString("nombre");
        String categoria = o.getString("categoria");
        double precio = o.getDouble("precio");
        int stock = o.getInt("stock");
        boolean activo = o.getBoolean("activo");

        Producto p = new Producto(id, nombre, categoria, precio, stock);
        p.setActivo(activo);

        return p;
    }

    // ============================================================
    // GUARDAR LISTAS COMPLETAS
    // ============================================================
    public static void guardarUsuarios(List<Usuario> lista, String archivo) {
        JSONArray array = new JSONArray();
        for (Usuario u : lista) {
            array.put(usuarioToJSON(u));
        }

        try {
            crearArchivo(archivo);
            Files.write(Paths.get(archivo), array.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void guardarProductos(List<Producto> lista, String archivo) {
        JSONArray array = new JSONArray();
        for (Producto p : lista) {
            array.put(productoToJSON(p));
        }

        try {
            crearArchivo(archivo);
            Files.write(Paths.get(archivo), array.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // CARGAR LISTAS COMPLETAS
    // ============================================================
    public static List<Usuario> cargarUsuarios(String archivo) {
        List<Usuario> lista = new ArrayList<>();

        try {
            crearArchivo(archivo);
            String contenido = Files.readString(Paths.get(archivo));
            JSONArray array = new JSONArray(contenido);

            for (int i = 0; i < array.length(); i++) {
                lista.add(jsonToUsuario(array.getJSONObject(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static List<Producto> cargarProductos(String archivo) {
        List<Producto> lista = new ArrayList<>();

        try {
            crearArchivo(archivo);
            String contenido = Files.readString(Paths.get(archivo));
            JSONArray array = new JSONArray(contenido);

            for (int i = 0; i < array.length(); i++) {
                lista.add(jsonToProducto(array.getJSONObject(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ============================================================
    // MOSTRAR ARCHIVOS JSON POR CONSOLA
    // ============================================================
    public static void mostrarUsuarios(String archivo) {
        List<Usuario> usuarios = cargarUsuarios(archivo);
        usuarios.forEach(u -> System.out.println(u));
    }

    public static void mostrarProductos(String archivo) {
        List<Producto> productos = cargarProductos(archivo);
        productos.forEach(p -> System.out.println(p));
    }

    // ============================================================
    // ACTUALIZAR STOCK (cuando se compra)
    // ============================================================
    public static void actualizarStock(String archivoProductos, int idProducto, int cantidadComprada) {

        List<Producto> productos = cargarProductos(archivoProductos);

        for (Producto p : productos) {
            if (p.getId() == idProducto) {
                int nuevoStock = p.getStock() - cantidadComprada;
                p.setStock(Math.max(nuevoStock, 0)); // Nunca negativo
            }
        }

        guardarProductos(productos, archivoProductos);
    }
}
