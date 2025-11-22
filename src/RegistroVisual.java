import javax.swing.*;
import java.awt.*;

public class RegistroVisual extends JDialog {

    private JTextField campoNombre, campoApellido, campoUsername, campoEmail;
    private JPasswordField campoPass, campoConfPass;
    private final String archivoAdmins = "data/admins.json";
    private GestorProductos gestorProductos; // si querés pasar referencia (no obligatorio)

    public RegistroVisual(Window parent, GestorProductos gestorProductos) {
        super(parent, "Registro de Administrador", ModalityType.APPLICATION_MODAL);
        this.gestorProductos = gestorProductos;

        setSize(380, 360);
        setLayout(new GridLayout(8, 2, 8, 8));
        setLocationRelativeTo(parent);

        add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        add(campoNombre);

        add(new JLabel("Apellido:"));
        campoApellido = new JTextField();
        add(campoApellido);

        add(new JLabel("Username:"));
        campoUsername = new JTextField();
        add(campoUsername);

        add(new JLabel("Email:"));
        campoEmail = new JTextField();
        add(campoEmail);

        add(new JLabel("Contraseña:"));
        campoPass = new JPasswordField();
        add(campoPass);

        add(new JLabel("Confirmar contraseña:"));
        campoConfPass = new JPasswordField();
        add(campoConfPass);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnRegistrar);
        add(btnCancelar);

        btnRegistrar.addActionListener(e -> registrar());
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void registrar() {
        String nombre = campoNombre.getText().trim();
        String apellido = campoApellido.getText().trim();
        String username = campoUsername.getText().trim();
        String email = campoEmail.getText().trim();
        String pass = new String(campoPass.getPassword()).trim();
        String conf = new String(campoConfPass.getPassword()).trim();

        if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || email.isEmpty()
                || pass.isEmpty() || conf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pass.equals(conf)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // verificar existencia
        if (JSONGestoraAdmins.existeUsername(username, "data/admins.json")) {
            JOptionPane.showMessageDialog(this, "El username ya existe. Elija otro.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Administrador nuevo = new Administrador(nombre, apellido, username, email, pass);
        boolean ok = JSONGestoraAdmins.agregarAdmin(nuevo, "data/admins.json");

        if (ok) {
            JOptionPane.showMessageDialog(this, "Registro exitoso. Ya puede iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

