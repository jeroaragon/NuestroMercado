import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

public class JSONGestoraClientes {

    // ---------------- CARGAR CLIENTES ----------------
    public static Set<Cliente> cargarClientes(String archivo) {
        Set<Cliente> lista = new LinkedHashSet<>();

        try {
            if (!Files.exists(Paths.get(archivo))) return lista;

            String contenido = new String(Files.readAllBytes(Paths.get(archivo)));
            JSONArray arr = new JSONArray(contenido);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);

                lista.add(new Cliente(
                        o.getString("nombre"),
                        o.getString("apellido"),
                        o.getString("username"),
                        o.getString("email"),
                        o.getString("password")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ---------------- GUARDAR CLIENTES ----------------
    public static void guardarClientes(Set<Cliente> lista, String archivo) {
        JSONArray arr = new JSONArray();

        for (Cliente c : lista) {
            JSONObject o = new JSONObject();
            o.put("nombre", c.getNombre());
            o.put("apellido", c.getApellido());
            o.put("username", c.getUsername());
            o.put("email", c.getEmail());
            o.put("password", c.getPassword());
            arr.put(o);
        }

        try {
            Files.write(Paths.get(archivo), arr.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- LOGIN ----------------
    public static Cliente login(String username, String password, String archivo)
            throws UsuarioNoEncontradoException {

        Set<Cliente> lista = cargarClientes(archivo);

        for (Cliente c : lista) {
            if (c.getUsername().equalsIgnoreCase(username) &&
                    c.getPassword().equals(password)) {
                return c;
            }
        }

        throw new UsuarioNoEncontradoException("Cliente no encontrado o contraseña incorrecta");
    }
}



