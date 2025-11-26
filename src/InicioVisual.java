import javax.swing.*;
import java.awt.*;

//menu donde se accede a las cuentas cliente/admin, y si no existe da la posibilidad de registrarse
public class InicioVisual extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLoginAdmin;
    private JButton botonLoginCliente;
    private GestorProductos gestorProductos;

    private final String archivoAdmins = "data/admins.json";
    private final String archivoClientes = "data/clientes.json";

    public InicioVisual(GestorProductos gestorProductos) {
        this.gestorProductos = gestorProductos;

        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(230, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblUsuario, gbc);

        campoUsuario = new JTextField(15);
        gbc.gridx = 1;
        panel.add(campoUsuario, gbc);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblPass, gbc);

        campoPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(campoPassword, gbc);

        botonLoginAdmin = new JButton("Iniciar Sesión (Admin)");
        botonLoginAdmin.setBackground(new Color(152, 251, 152));
        botonLoginAdmin.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(botonLoginAdmin, gbc);

        botonLoginCliente = new JButton("Iniciar Sesión (Cliente)");
        botonLoginCliente.setBackground(new Color(135, 206, 250));
        botonLoginCliente.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridy = 3;
        panel.add(botonLoginCliente, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);

        botonLoginAdmin.addActionListener(e -> procesarLoginAdministrador());
        botonLoginCliente.addActionListener(e -> procesarLoginCliente());
    }

    // ---------------- LOGIN ADMIN ----------------
    private void procesarLoginAdministrador() {
        String user = campoUsuario.getText().trim();
        //apuntes getText devuelve el texto y trim le saca los espacios
        String pass = String.valueOf(campoPassword.getPassword());

        try {
            Administrador admin = JSONGestoraAdmins.login(user, pass, archivoAdmins);
            new MenuAdminVisual(gestorProductos).setVisible(true);
            dispose();

        } catch (Exception e) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    e.getMessage() + "\n¿Desea registrarse como administrador?",
                    "Error de inicio",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                new RegistroVisual(this, gestorProductos);
            }
        }
    }

    // ---------------- LOGIN CLIENTE ----------------
    private void procesarLoginCliente() {
        String user = campoUsuario.getText().trim();
        String pass = String.valueOf(campoPassword.getPassword());

        try {
            Cliente cliente = JSONGestoraClientes.login(user, pass, archivoClientes);
            new ClienteVisual(this, gestorProductos).setVisible(true);
            dispose();

        } catch (Exception e) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    e.getMessage() + "\n¿Desea registrarse como cliente?",
                    "Error de inicio",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                new RegistroClienteVisual(this, gestorProductos);
            }
        }
    }

}









