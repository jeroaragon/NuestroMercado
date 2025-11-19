import javax.swing.*;
import java.awt.*;

public class InicioVisual extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLogin;
    private JButton botonCliente;

    public InicioVisual() {
        setTitle("Inicio de Sesión");
        setSize(360, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(230, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ───────── Usuario ─────────
        JLabel lblUsuario = new JLabel("Usuario administrador:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblUsuario, gbc);

        campoUsuario = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(campoUsuario, gbc);

        // ───────── Contraseña ─────────
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblPass, gbc);

        campoPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(campoPassword, gbc);

        // ───────── Botón Login Admin ─────────
        botonLogin = new JButton("Iniciar Sesión (Admin)");
        botonLogin.setBackground(new Color(152, 251, 152));
        botonLogin.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(botonLogin, gbc);

        // ───────── Botón Cliente ─────────
        botonCliente = new JButton("Soy Cliente");
        botonCliente.setBackground(new Color(135, 206, 250));
        botonCliente.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(botonCliente, gbc);

        add(panel);

        // Acciones
        botonLogin.addActionListener(e -> procesarLoginAdministrador());
        botonCliente.addActionListener(e -> entrarComoCliente());
    }

    private void procesarLoginAdministrador() {
        String usuario = campoUsuario.getText();
        String password = String.valueOf(campoPassword.getPassword());

        // Ejemplo: conectá esto con tu GestorUsuarios
        if (usuario.equals("eze") && password.equals("2112")) {
            // Abrir menú admin:
            new MenuAdminVisual().setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Credenciales de administrador incorrectas");
        }
    }

    private void entrarComoCliente() {
        JOptionPane.showMessageDialog(this, "Entrando como cliente...");

        // Abrir menú cliente real:
        // new MenuCliente().setVisible(true);
        // dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InicioVisual().setVisible(true));
    }
}


