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
        String usuarioIngresado = campoUsuario.getText();
        String passwordIngresada = String.valueOf(campoPassword.getPassword());

        try {
            if (validarCredenciales(usuarioIngresado, passwordIngresada)) {
                new MenuAdminVisual(gestorProductos).setVisible(true);
                dispose();
            }
        } catch (UsuarioNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer data/admins.json");
        }
    }

    private boolean validarCredenciales(String username, String password) throws IOException {

        // → Ruta corregida
        String rutaJSON = "data/admins.json";

        if (!Files.exists(Paths.get(rutaJSON))) {
            throw new IOException("No se encontró el archivo: " + rutaJSON);
        }

        String jsonTexto = Files.readString(Paths.get(rutaJSON));

        JSONArray arreglo = new JSONArray(jsonTexto);

        for (int i = 0; i < arreglo.length(); i++) {
            JSONObject obj = arreglo.getJSONObject(i);

            String user = obj.getString("username");
            String pass = obj.getString("password");
            boolean activo = obj.getBoolean("activo");

            if (user.equals(username) && pass.equals(password) && activo) {
                return true;
            }
        }

        throw new UsuarioNoEncontradoException("Usuario o contraseña incorrectos.");
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






