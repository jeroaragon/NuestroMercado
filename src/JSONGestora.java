import org.json.*;
import java.nio.file.*;
import java.util.*;

public class JSONGestora {

    // ------------------------------------------------------------
    // CREAR ARCHIVO SI NO EXISTE
    // ------------------------------------------------------------
    private static void crearArchivo(String archivo) throws Exception {
        Path path = Paths.get(archivo);

        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.write(path, "[]".getBytes());
        }
    }

    // ------------------------------------------------------------
    // OBJETO → JSON
    // ------------------------------------------------------------
    public static JSONObject productoToJSON(Producto p) {
        JSONObject o = new JSONObject();
        o.put("id", p.getId());
        o.put("nombre", p.getNombre());
        o.put("categoria", p.getCategoria().name()); // ENUM → String
        o.put("precio", p.getPrecio());
        o.put("stock", p.getStock());
        o.put("activo", p.isActivo());
        return o;
    }

    // ------------------------------------------------------------
    // JSON → OBJETO
    // ------------------------------------------------------------
    public static Producto jsonToProducto(JSONObject o) {

        int id = o.getInt("id");
        String nombre = o.getString("nombre");
        Categorias categoria = Categorias.valueOf(o.getString("categoria"));
        double precio = o.getDouble("precio");
        int stock = o.getInt("stock");
        boolean activo = o.getBoolean("activo");

        Producto p = new Producto(id, nombre, categoria, precio, stock);
        p.setActivo(activo);

        return p;
    }

    // ------------------------------------------------------------
    // GUARDAR LISTA COMPLETA
    // ------------------------------------------------------------
    public static void guardarProductos(List<Producto> lista, String archivo) {

        JSONArray arr = new JSONArray();
        for (Producto p : lista) {
            arr.put(productoToJSON(p));
        }

        try {
            crearArchivo(archivo);
            Files.write(Paths.get(archivo), arr.toString(4).getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ------------------------------------------------------------
    // CARGAR LISTA COMPLETA
    // ------------------------------------------------------------
    public static List<Producto> cargarProductos(String archivo) {

        List<Producto> lista = new ArrayList<>();

        try {
            crearArchivo(archivo);
            String txt = Files.readString(Paths.get(archivo));
            JSONArray arr = new JSONArray(txt);

            for (int i = 0; i < arr.length(); i++) {
                lista.add(jsonToProducto(arr.getJSONObject(i)));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return lista;
    }

    // ------------------------------------------------------------
    // MOSTRAR PARA DEBUG
    // ------------------------------------------------------------
    public static void mostrarProductos(String archivo) {
        cargarProductos(archivo).forEach(System.out::println);
    }
}
