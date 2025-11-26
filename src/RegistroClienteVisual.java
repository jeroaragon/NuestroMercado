import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class RegistroClienteVisual extends JDialog {

    private JTextField campoNombre, campoApellido, campoUser, campoEmail;
    private JPasswordField campoPassword, campoConfPassword;

    private final String archivoClientes = "data/clientes.json";

    public RegistroClienteVisual(JFrame parent) {
        super(parent, "Registrar Cliente", true);

        setLayout(new GridLayout(7, 2, 10, 10));

        campoNombre = new JTextField();
        campoApellido = new JTextField();
        campoUser = new JTextField();
        campoEmail = new JTextField();
        campoPassword = new JPasswordField();
        campoConfPassword = new JPasswordField();

        add(new JLabel("Nombre:"));
        add(campoNombre);

        add(new JLabel("Apellido:"));
        add(campoApellido);

        add(new JLabel("Usuario:"));
        add(campoUser);

        add(new JLabel("Email:"));
        add(campoEmail);

        add(new JLabel("Contraseña:"));
        add(campoPassword);

        add(new JLabel("Confirmar contraseña:"));
        add(campoConfPassword);

        JButton registrar = new JButton("Registrar");
        JButton cancelar = new JButton("Cancelar");
        add(registrar);
        add(cancelar);

        registrar.addActionListener(e -> registrarCliente());
        cancelar.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void registrarCliente() {

        String nom = campoNombre.getText().trim();
        String ape = campoApellido.getText().trim();
        String user = campoUser.getText().trim();
        String email = campoEmail.getText().trim();
        String pass = new String(campoPassword.getPassword()).trim();
        String conf = new String(campoConfPassword.getPassword()).trim();

        // Validación: todos los campos completos
        if (nom.isEmpty() || ape.isEmpty() || user.isEmpty() || email.isEmpty() ||
                pass.isEmpty() || conf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación: contraseñas iguales
        if (!pass.equals(conf)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación: username único
        Set<Cliente> lista = JSONGestoraClientes.cargarClientes(archivoClientes);
        Cliente nuevo = new Cliente(nom, ape, user, email, pass);

        if (lista.contains(nuevo)) {
            JOptionPane.showMessageDialog(this, "El usuario ya existe.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si todo está OK → agregar y guardar
        lista.add(nuevo);
        JSONGestoraClientes.guardarClientes(lista, archivoClientes);

        JOptionPane.showMessageDialog(this, "Cliente registrado con éxito!",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}





