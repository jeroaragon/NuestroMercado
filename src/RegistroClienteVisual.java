import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class RegistroClienteVisual extends JDialog {

    // Campos de texto para los datos del cliente
    private JTextField campoNombre, campoApellido, campoUser, campoEmail;
    private JPasswordField campoPassword;

    // Archivo donde se guardarán los clientes registrados
    private final String archivoClientes = "data/clientes.json";

    // Constructor: recibe la ventana principal y el gestor de productos
    public RegistroClienteVisual(JFrame parent, GestorProductos gestorProductos) {
        // true indica que es un JDialog modal (bloquea la ventana principal)
        super(parent, "Registrar Cliente", true);

        // Diseño de la interfaz con un GridLayout simple
        setLayout(new GridLayout(6, 2, 10, 10));

        // Inicialización de campos de texto
        campoNombre = new JTextField();
        campoApellido = new JTextField();
        campoUser = new JTextField();
        campoEmail = new JTextField();
        campoPassword = new JPasswordField();

        // Se agregan las etiquetas y los campos al formulario
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

        // Botón para confirmar el registro
        JButton registrar = new JButton("Registrar");
        add(registrar);

        // Acción del botón Registrar
        registrar.addActionListener(e -> registrarCliente());

        // Ajusta el tamaño y centra la ventana en pantalla
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    // Método que se ejecuta cuando se hace clic en el botón Registrar
    private void registrarCliente() {

        // Obtener los datos ingresados por el usuario
        String nom = campoNombre.getText();
        String ape = campoApellido.getText();
        String user = campoUser.getText();
        String email = campoEmail.getText();
        String pass = String.valueOf(campoPassword.getPassword());

        // Crear el objeto Cliente con los datos del formulario
        Cliente nuevo = new Cliente(nom, ape, user, email, pass);

        // Cargar la lista de clientes desde el archivo JSON
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

        // Cerrar ventana después del registro exitoso
        dispose();
    }
}




