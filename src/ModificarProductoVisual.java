import javax.swing.*;
import java.awt.*;

public class ModificarProductoVisual extends JFrame {

    private JTextField txtNombre, txtCategoria, txtPrecio, txtStock;
    private JButton botonGuardar, botonCancelar;
    private GestionProductosVisual padre;

    private int id;

    public ModificarProductoVisual(GestionProductosVisual padre, int id, String nombre, String categoria, double precio, int stock) {
        this.padre = padre;
        this.id = id;

        setTitle("Modificar Producto");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField(nombre);
        add(txtNombre);

        add(new JLabel("Categoría:"));
        txtCategoria = new JTextField(categoria);
        add(txtCategoria);

        add(new JLabel("Precio:"));
        txtPrecio = new JTextField(String.valueOf(precio));
        add(txtPrecio);

        add(new JLabel("Stock:"));
        txtStock = new JTextField(String.valueOf(stock));
        add(txtStock);

        botonGuardar = new JButton("Guardar Cambios");
        botonCancelar = new JButton("Cancelar");

        add(botonGuardar);
        add(botonCancelar);

        botonGuardar.addActionListener(e -> modificar());
        botonCancelar.addActionListener(e -> dispose());
    }

    private void modificar() {

        String nuevoNombre = txtNombre.getText();
        String nuevaCategoria = txtCategoria.getText();
        double nuevoPrecio = Double.parseDouble(txtPrecio.getText());
        int nuevoStock = Integer.parseInt(txtStock.getText());

        // ---------------------------------------------
        // ACÁ LLAMÁS A gestor.modificarProducto(id, ...)
        // ---------------------------------------------

        JOptionPane.showMessageDialog(this, "Producto modificado");

        padre.refrescar();
        dispose();
    }
}

