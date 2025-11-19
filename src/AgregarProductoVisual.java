import javax.swing.*;
import java.awt.*;

public class AgregarProductoVisual extends JFrame {

    private JTextField txtNombre, txtCategoria, txtPrecio, txtStock;
    private JButton botonGuardar, botonCancelar;
    private GestionProductosVisual padre;

    public AgregarProductoVisual(GestionProductosVisual padre) {
        this.padre = padre;

        setTitle("Agregar Producto");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        add(txtCategoria);

        add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        add(txtPrecio);

        add(new JLabel("Stock:"));
        txtStock = new JTextField();
        add(txtStock);

        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");

        add(botonGuardar);
        add(botonCancelar);

        botonGuardar.addActionListener(e -> guardar());
        botonCancelar.addActionListener(e -> dispose());
    }

    private void guardar() {

        // -------- TOMAR DATOS ----------
        String nombre = txtNombre.getText();
        String categoria = txtCategoria.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        int stock = Integer.parseInt(txtStock.getText());

        // ------------------------------
        // ACÁ LLAMÁS A gestor.agregarProducto(...)
        // ------------------------------

        JOptionPane.showMessageDialog(this, "Producto agregado");

        padre.refrescar();
        dispose();
    }
}

