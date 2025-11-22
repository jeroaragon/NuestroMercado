import org.json.*;
import java.nio.file.*;
import java.util.*;

public class JSONGestoraClientes {

    private static void crearArchivo(String archivo) throws Exception {
        Path path = Paths.get(archivo);

        if (!Files.exists(path)) {
            if (path.getParent() != null)
                Files.createDirectories(path.getParent());

            Files.write(path, "[]".getBytes()); // JSON vacío
        }
    }

    // -----------------------------------------
    // Cliente → JSON
    // -----------------------------------------
    private static JSONObject clienteToJSON(Cliente c) {
        JSONObject o = new JSONObject();
        o.put("nombre", c.getNombre());
        o.put("apellido", c.getApellido());
        o.put("username", c.getUsername());
        o.put("email", c.getEmail());
        o.put("password", c.getPassword());
        o.put("activo", c.isActivo());
        return o;
    }

    // -----------------------------------------
    // JSON → Cliente
    // -----------------------------------------
    private static Cliente jsonToCliente(JSONObject o) {
        Cliente c = new Cliente(
                o.getString("nombre"),
                o.getString("apellido"),
                o.getString("username"),
                o.getString("email"),
                o.getString("password")
        );
        c.setActivo(o.optBoolean("activo", true));
        return c;
    }

    // -----------------------------------------
    // CARGAR CLIENTES
    // -----------------------------------------
    public static List<Cliente> cargarClientes(String archivo) {
        List<Cliente> lista = new ArrayList<>();

        try {
            crearArchivo(archivo);

            String txt = Files.readString(Paths.get(archivo));
            if (txt == null || txt.trim().isEmpty()) txt = "[]";

            JSONArray arr = new JSONArray(txt);

            for (int i = 0; i < arr.length(); i++) {
                lista.add(jsonToCliente(arr.getJSONObject(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // -----------------------------------------
    // GUARDAR CLIENTES
    // -----------------------------------------
    public static void guardarClientes(List<Cliente> lista, String archivo) {
        JSONArray arr = new JSONArray();

        for (Cliente c : lista) {
            arr.put(clienteToJSON(c));
        }

        try {
            crearArchivo(archivo);
            Files.write(Paths.get(archivo), arr.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------------
    // LOGIN DE CLIENTE
    // -----------------------------------------
    public static Cliente login(String username, String password, String archivo)
            throws Exception {

        List<Cliente> clientes = cargarClientes(archivo);

        for (Cliente c : clientes) {
            if (c.getUsername().equals(username)) {

                if (!c.getPassword().equals(password))
                    throw new Exception("Contraseña incorrecta.");

                if (!c.isActivo())
                    throw new Exception("El cliente está inactivo.");

                return c;
            }
        }

        throw new Exception("El usuario no existe.");
    }

    // -----------------------------------------
    // VER SI EXISTE USERNAME
    // -----------------------------------------
    public static boolean existeUsername(String username, String archivo) {
        List<Cliente> clientes = cargarClientes(archivo);

        for (Cliente c : clientes) {
            if (c.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // -----------------------------------------
    // AGREGAR CLIENTE
    // -----------------------------------------
    public static boolean agregarCliente(Cliente nuevo, String archivo) {
        List<Cliente> clientes = cargarClientes(archivo);

        for (Cliente c : clientes) {
            if (c.getUsername().equalsIgnoreCase(nuevo.getUsername())) {
                return false;
            }
        }

        clientes.add(nuevo);
        guardarClientes(clientes, archivo);
        return true;
    }
}

