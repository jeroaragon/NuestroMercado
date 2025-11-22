import javax.swing.*;
import java.awt.*;

public class AgregarProductoVisual extends JDialog implements IGestionProducto {

    private JTextField campoNombre, campoPrecio, campoStock;
    private JComboBox<Categorias> comboCategoria;

    private GestorProductos gestor;

    public AgregarProductoVisual(JFrame parent, GestorProductos gestor) {
        super(parent, "Agregar Producto", true);
        this.gestor = gestor;

        setSize(350, 300);
        setLayout(new GridLayout(6, 2, 5, 5));
        setLocationRelativeTo(parent);

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

        JButton botonAgregar = new JButton("Agregar");
        JButton botonCancelar = new JButton("Cancelar");

        add(botonAgregar);
        add(botonCancelar);

        botonAgregar.addActionListener(e -> {
            if (validarDatos()) {
                cargarDatosProducto();
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        botonCancelar.addActionListener(e -> dispose());
    }

    @Override
    public void cargarDatosProducto() {
        String nombre = campoNombre.getText();
        Categorias categoria = (Categorias) comboCategoria.getSelectedItem();
        double precio = Double.parseDouble(campoPrecio.getText());
        int stock = Integer.parseInt(campoStock.getText());

        gestor.agregarProducto(nombre, categoria, precio, stock);
    }

    @Override
    public boolean validarDatos() {
        try {
            if (campoNombre.getText().trim().isEmpty()) return false;
            Double.parseDouble(campoPrecio.getText());
            Integer.parseInt(campoStock.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}




