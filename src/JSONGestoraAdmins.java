import org.json.*;
import java.nio.file.*;
import java.util.*;
import java.io.IOException;

//gestiona el archivo de admins
public class JSONGestoraAdmins {

    private static void crearArchivo(String archivo) throws Exception {
        Path path = Paths.get(archivo);

        if (!Files.exists(path)) {
            if (path.getParent() != null)
                Files.createDirectories(path.getParent());
            Files.write(path, "[]".getBytes());
        }
    }

    private static JSONObject adminToJSON(Administrador a) {
        JSONObject o = null;
        try {
             o = new JSONObject();
            o.put("nombre", a.getNombre());
            o.put("apellido", a.getApellido());
            o.put("username", a.getUsername());
            o.put("email", a.getEmail());
            o.put("password", a.getPassword());
            o.put("activo", a.isActivo());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return o;
    }


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

    // GUARDAR LISTA DE ADMINS
    public static void guardarAdmins(List<Administrador> lista, String archivo) {
        try {
            //no sobreescribir con lista vacía si ya existe un archivo con datos
            Path path = Paths.get(archivo);
            boolean fileExiste = Files.exists(path);
            boolean fileContenido = false;
            if (fileExiste) {
                try {
                    fileContenido= Files.size(path) > 0;
                } catch (IOException ignore) { fileContenido = true; }
            }

            if ((lista == null || lista.isEmpty()) && fileContenido) {
                // Si hay contenido en el archivo y la lista que intentamos guardar está vacía,
                // evitamos sobreescribirlo accidentalmente.
                // nos paso varias veces xD
                System.err.println("intento de guardar lista vacía sobre archivo existente. Operación cancelada.");
                return;
            }

            // Preparar JSONArray
            JSONArray arr = new JSONArray();
            if (lista != null) {
                for (Administrador a : lista) {
                    arr.put(adminToJSON(a));
                }
            }

            crearArchivo(archivo); // asegura existencia
            Files.write(Paths.get(archivo), arr.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // CARGAR LISTA DE ADMINS
    public static List<Administrador> cargarAdmins(String archivo) {
        List<Administrador> lista = new ArrayList<>();

        try {
            crearArchivo(archivo);

            String txt = Files.readString(Paths.get(archivo));

            // Si el archivo es vacío o contiene sólo espacios o "[]", devolvemos lista vacía.
            if (txt == null || txt.trim().isEmpty() || txt.trim().equals("[]")) {
                return lista;
            }

            // Intentar parsear
            JSONArray arr;
            try {
                arr = new JSONArray(txt);
            } catch (JSONException je) {
                // No parseable: NO sobreescribir el archivo; informar y devolver lista vacía.
                System.err.println("JSONGestoraAdmins: error al parsear admins JSON. Se evita sobrescribir el archivo.");
                je.printStackTrace();
                return lista;
            }

            for (int i = 0; i < arr.length(); i++) {
                lista.add(jsonToAdmin(arr.getJSONObject(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


    // LOGIN
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

    // Comprueba si existe username
    public static boolean existeUsername(String username, String archivo) {
        List<Administrador> admins = cargarAdmins(archivo);
        for (Administrador a : admins) {
            if (a.getUsername().equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    // Agrega un admin si no existe y guarda
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



