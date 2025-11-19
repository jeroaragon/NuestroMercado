import javax.swing.*;
import java.awt.*;

public class ModificarProductoVisual extends JDialog {

    private JTextField campoNombre, campoPrecio, campoStock;
    private JComboBox<Categorias> comboCategoria;

    public ModificarProductoVisual(JFrame parent, GestorProductos gestor,
                                   int id, String nombre, String categoria,
                                   double precio, int stock) {

        super(parent, "Modificar Producto", true);

        setSize(350, 300);
        setLayout(new GridLayout(6, 2, 5, 5));
        setLocationRelativeTo(parent);

        // CAMPOS
        add(new JLabel("Nombre:"));
        campoNombre = new JTextField(nombre);
        add(campoNombre);

        add(new JLabel("Categoría:"));
        comboCategoria = new JComboBox<>(Categorias.values());
        comboCategoria.setSelectedItem(Categorias.valueOf(categoria));
        add(comboCategoria);

        add(new JLabel("Precio:"));
        campoPrecio = new JTextField(String.valueOf(precio));
        add(campoPrecio);

        add(new JLabel("Stock:"));
        campoStock = new JTextField(String.valueOf(stock));
        add(campoStock);

        // BOTONES
        JButton botonGuardar = new JButton("Guardar cambios");
        add(botonGuardar);

        JButton botonCancelar = new JButton("Cancelar");
        add(botonCancelar);

        botonGuardar.addActionListener(e -> {
            try {
                String newNombre = campoNombre.getText();
                Categorias newCategoria = (Categorias) comboCategoria.getSelectedItem();
                double newPrecio = Double.parseDouble(campoPrecio.getText());
                int newStock = Integer.parseInt(campoStock.getText());

                gestor.modificarProducto(id, newNombre, newCategoria, newPrecio, newStock);

                JOptionPane.showMessageDialog(this, "Producto modificado correctamente");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        botonCancelar.addActionListener(e -> dispose());
    }
}



