import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Main_Jero {
    public static void main(String[] args) {

        GestorProductos gestor = new GestorProductos();
        // inicializar UI
        SwingUtilities.invokeLater(() -> {
            new MenuAdminVisual(gestor); // si MenuAdminVisual crea ventana, ok (no .setVisible aquí ya que se usa abajo)
        });

        InicioVisual login = new InicioVisual(gestor);
        login.setVisible(true);

        // Sólo guardar admins por defecto si el archivo no existe o está vacío.
        String adminsPath = "data/admins.json";
        boolean guardarPorDefecto = false;
        try {
            if (!Files.exists(Paths.get(adminsPath))) {
                guardarPorDefecto = true;
            } else {
                // si existe pero está vacío o contiene sólo espacios/brackets vacíos -> permitir escribir defaults
                String contenido = Files.readString(Paths.get(adminsPath));
                if (contenido == null || contenido.trim().isEmpty() || contenido.trim().equals("[]")) {
                    guardarPorDefecto = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error con el archivo, evitamos sobrescribir
            guardarPorDefecto = false;
        }

        if (guardarPorDefecto) {
            List<Administrador> admins = new ArrayList<>();
            admins.add(new Administrador("Juan", "Gomez", "admin1", "admin1@gmail.com", "1234"));
            admins.add(new Administrador("Ana", "Perez", "admin2", "ana@gmail.com", "abcd"));

            JSONGestoraAdmins.guardarAdmins(admins, adminsPath);
            System.out.println("Admins por defecto guardados en " + adminsPath);
        } else {
            System.out.println("Archivo de admins ya existe y tiene contenido — no se sobrescribe.");
        }
    }
}


