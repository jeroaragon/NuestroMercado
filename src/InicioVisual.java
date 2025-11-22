import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

public class InicioVisual extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLogin;
    private JButton botonCliente;
    private GestorProductos gestorProductos;
    private final String archivoAdmins = "data/admins.json";

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

        JLabel lblUsuario = new JLabel("Usuario administrador:");
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

        botonLogin = new JButton("Iniciar Sesión (Admin)");
        botonLogin.setBackground(new Color(152, 251, 152));
        botonLogin.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(botonLogin, gbc);

        botonCliente = new JButton("Soy Cliente");
        botonCliente.setBackground(new Color(135, 206, 250));
        botonCliente.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridy = 3;
        panel.add(botonCliente, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);

        botonLogin.addActionListener(e -> procesarLoginAdministrador());
        botonCliente.addActionListener(e -> entrarComoCliente());
    }

    // ---------------- VALIDACIÓN CONTRA /data/admins.json ----------------
    private void procesarLoginAdministrador() {
        String usuarioIngresado = campoUsuario.getText().trim();
        String passwordIngresada = String.valueOf(campoPassword.getPassword());

        try {
            Administrador a = JSONGestoraAdmins.login(usuarioIngresado, passwordIngresada, archivoAdmins);
            // Si no lanza excepción, login OK
            new MenuAdminVisual(gestorProductos).setVisible(true);
            dispose();
        } catch (Exception ex) {
            // Si falla, ofrecer registrarse
            int opcion = JOptionPane.showConfirmDialog(this,
                    ex.getMessage() + "\n¿Desea registrarse como administrador?",
                    "Login fallido",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                // abrir diálogo de registro
                RegistroVisual dialog = new RegistroVisual(this, gestorProductos);
                // al cerrar el dialog, si se registró, podés autocompletar el usuario
                // (no sabemos si autocompletó, así que simplemente dejamos los campos)
            }
        }
    }

    private void entrarComoCliente() {
        new ClienteVisual(gestorProductos).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        GestorProductos gestor = new GestorProductos();

        SwingUtilities.invokeLater(() ->
                new InicioVisual(gestor).setVisible(true)
        );
    }
}







