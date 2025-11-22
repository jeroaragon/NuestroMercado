import org.json.*;
import java.nio.file.*;
import java.util.*;

public class JSONGestoraAdmins {

    private static void crearArchivo(String archivo) throws Exception {
        Path path = Paths.get(archivo);

        if (!Files.exists(path)) {
            if (path.getParent() != null)
                Files.createDirectories(path.getParent());

            Files.write(path, "[]".getBytes());
        }
    }

    // -----------------------------------------
    // Admin → JSON
    // -----------------------------------------
    private static JSONObject adminToJSON(Administrador a) {
        JSONObject o = new JSONObject();
        o.put("nombre", a.getNombre());
        o.put("apellido", a.getApellido());
        o.put("username", a.getUsername());
        o.put("email", a.getEmail());
        o.put("password", a.getPassword());
        o.put("activo", a.isActivo());
        return o;
    }

    // -----------------------------------------
    // JSON → Admin
    // -----------------------------------------
    private static Administrador jsonToAdmin(JSONObject o) {
        Administrador a = new Administrador(
                o.getString("nombre"),
                o.getString("apellido"),
                o.getString("username"),
                o.getString("email"),
                o.getString("password")
        );
        a.setActivo(o.optBoolean("activo", true));
        return a;
    }

    // -----------------------------------------
    // GUARDAR LISTA DE ADMINS
    // -----------------------------------------
    public static void guardarAdmins(List<Administrador> lista, String archivo) {
        JSONArray arr = new JSONArray();

        for (Administrador a : lista) {
            arr.put(adminToJSON(a));
        }

        try {
            crearArchivo(archivo);
            Files.write(Paths.get(archivo), arr.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------------
    // CARGAR LISTA DE ADMINS
    // -----------------------------------------
    public static List<Administrador> cargarAdmins(String archivo) {
        List<Administrador> lista = new ArrayList<>();

        try {
            crearArchivo(archivo);

            String txt = Files.readString(Paths.get(archivo));
            if (txt == null || txt.trim().isEmpty()) txt = "[]";
            JSONArray arr = new JSONArray(txt);

            for (int i = 0; i < arr.length(); i++) {
                lista.add(jsonToAdmin(arr.getJSONObject(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // -----------------------------------------
    // LOGIN → usa UsuarioNoEncontradoException
    // -----------------------------------------
    public static Administrador login(String username, String password, String archivo) {

        List<Administrador> admins = cargarAdmins(archivo);

        for (Administrador a : admins) {
            if (a.getUsername().equals(username)) {

                if (!a.getPassword().equals(password))
                    throw new UsuarioNoEncontradoException("Contraseña incorrecta.");

                if (!a.isActivo())
                    throw new UsuarioNoEncontradoException("El administrador está inactivo.");

                return a;
            }
        }

        throw new UsuarioNoEncontradoException("El usuario no existe.");
    }

    // -----------------------------------------
    // Comprueba si existe username
    // -----------------------------------------
    public static boolean existeUsername(String username, String archivo) {
        List<Administrador> admins = cargarAdmins(archivo);
        for (Administrador a : admins) {
            if (a.getUsername().equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    // -----------------------------------------
    // Agrega un admin si no existe y guarda
    // -----------------------------------------
    public static boolean agregarAdmin(Administrador nuevo, String archivo) {
        List<Administrador> admins = cargarAdmins(archivo);

        for (Administrador a : admins) {
            if (a.getUsername().equalsIgnoreCase(nuevo.getUsername())) {
                return false;
            }
        }

        admins.add(nuevo);
        guardarAdmins(admins, archivo);
        return true;
    }
}


