import javax.swing.*;
import java.awt.*;

public class AgregarProductoVisual extends JDialog {

    private JTextField campoNombre, campoPrecio, campoStock;
    private JComboBox<Categorias> comboCategoria;

    public AgregarProductoVisual(JFrame parent, GestorProductos gestor) {
        super(parent, "Agregar Producto", true);

        setSize(350, 300);
        setLayout(new GridLayout(6, 2, 5, 5));
        setLocationRelativeTo(parent);

        // CAMPOS
        add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        add(campoNombre);

        add(new JLabel("Categoría:"));
        comboCategoria = new JComboBox<>(Categorias.values());
        add(comboCategoria);

        add(new JLabel("Precio:"));
        campoPrecio = new JTextField();
        add(campoPrecio);

        add(new JLabel("Stock:"));
        campoStock = new JTextField();
        add(campoStock);

        // BOTÓN
        JButton botonGuardar = new JButton("Guardar");
        add(botonGuardar);

        JButton botonCancelar = new JButton("Cancelar");
        add(botonCancelar);

        // EVENTO GUARDAR
        botonGuardar.addActionListener(e -> {
            try {
                String nombre = campoNombre.getText();
                Categorias categoria = (Categorias) comboCategoria.getSelectedItem();
                double precio = Double.parseDouble(campoPrecio.getText());
                int stock = Integer.parseInt(campoStock.getText());

                gestor.agregarProducto(nombre, categoria, precio, stock);

                JOptionPane.showMessageDialog(this, "Producto agregado correctamente");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        botonCancelar.addActionListener(e -> dispose());
    }
}


