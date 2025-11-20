import javax.swing.*;
import java.awt.*;

public class InicioVisual extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLogin;
    private JButton botonCliente;
    private GestorProductos gestorProductos;

    // --- CONSTRUCTOR PRINCIPAL FIXEADO ---
    public InicioVisual(GestorProductos gestorProductos) {
        this.gestorProductos = gestorProductos;

        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal con padding para evitar problemas en diferentes PCs
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // margen universal
        panel.setBackground(new Color(230, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // ───────── Usuario ─────────
        JLabel lblUsuario = new JLabel("Usuario administrador:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblUsuario, gbc);

        campoUsuario = new JTextField(15);
        gbc.gridx = 1;
        panel.add(campoUsuario, gbc);

        // ───────── Contraseña ─────────
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblPass, gbc);

        campoPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(campoPassword, gbc);

        // ───────── Botón Login Admin ─────────
        botonLogin = new JButton("Iniciar Sesión (Admin)");
        botonLogin.setBackground(new Color(152, 251, 152));
        botonLogin.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(botonLogin, gbc);

        // ───────── Botón Cliente ─────────
        botonCliente = new JButton("Soy Cliente");
        botonCliente.setBackground(new Color(135, 206, 250));
        botonCliente.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridy = 3;
        panel.add(botonCliente, gbc);

        add(panel);

        // AUTOMÁTICAMENTE ajusta ventana al contenido → evita cortes
        pack();
        setLocationRelativeTo(null); // centrar siempre

        // Acciones
        botonLogin.addActionListener(e -> procesarLoginAdministrador());
        botonCliente.addActionListener(e -> entrarComoCliente());
    }


    // --- MÉTODOS ---
    private void procesarLoginAdministrador() {
        String usuario = campoUsuario.getText();
        String password = String.valueOf(campoPassword.getPassword());

        if (usuario.equals("eze") && password.equals("2112")) {
            new MenuAdminVisual(gestorProductos).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales de administrador incorrectas");
        }
    }

    private void entrarComoCliente() {
        JOptionPane.showMessageDialog(this, "Entrando como cliente...");
    }

    // --- MAIN CORRECTO ---
    public static void main(String[] args) {
        GestorProductos gestor = new GestorProductos();

        SwingUtilities.invokeLater(() ->
                new InicioVisual(gestor).setVisible(true)
        );
    }
}




