import javax.swing.*;
import java.awt.*;
import java.util.Set;

//ventana para llenar datos del nuevo cliente
public class RegistroClienteVisual extends JDialog {


    private JTextField campoNombre, campoApellido, campoUser, campoEmail;
    private JPasswordField campoPassword;


    private final String archivoClientes = "data/clientes.json";


    public RegistroClienteVisual(JFrame parent, GestorProductos gestorProductos) {

        super(parent, "Registrar Cliente", true);


        setLayout(new GridLayout(6, 2, 10, 10));


        campoNombre = new JTextField();
        campoApellido = new JTextField();
        campoUser = new JTextField();
        campoEmail = new JTextField();
        campoPassword = new JPasswordField();


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


        JButton registrar = new JButton("Registrar");
        add(registrar);

        // Acción del botón Registrar
        registrar.addActionListener(e -> registrarCliente());


        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    // registrar cliente
    private void registrarCliente() {

        // Obtener los datos ingresados por el usuario
        String nom = campoNombre.getText();
        String ape = campoApellido.getText();
        String user = campoUser.getText();
        String email = campoEmail.getText();
        String pass = String.valueOf(campoPassword.getPassword());


        Cliente nuevo = new Cliente(nom, ape, user, email, pass);


        Set<Cliente> lista = JSONGestoraClientes.cargarClientes(archivoClientes);

        // Verificar si el usuario ya existe en la lista
        if (lista.contains(nuevo)) {
            JOptionPane.showMessageDialog(
                    this,
                    "El usuario ya existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Si no existe, se agrega y se guarda la lista actualizada
        lista.add(nuevo);
        JSONGestoraClientes.guardarClientes(lista, archivoClientes);

        JOptionPane.showMessageDialog(this, "Cliente registrado con éxito");


        dispose();
    }
}




