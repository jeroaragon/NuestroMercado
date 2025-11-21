import javax.swing.*;
import java.awt.*;

public class AgregarProductoVisual extends JDialog {

    private JTextField campoNombre, campoPrecio, campoStock;
    private JComboBox<Categorias> comboCategoria;

    private GestionProductosVisual padre;
    private GestorProductos gestor;

    public AgregarProductoVisual(GestionProductosVisual parent, GestorProductos gestor) {
        super(parent, "Agregar Producto", true);

        this.padre = parent;
        this.gestor = gestor;

        setSize(350, 300);
        setLayout(new GridLayout(6, 2, 5, 5));
        setLocationRelativeTo(parent);

        // ------------------ CAMPOS ------------------
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

        // ------------------ BOTONES ------------------
        JButton botonGuardar = new JButton("Guardar");
        JButton botonCancelar = new JButton("Cancelar");

        add(botonGuardar);
        add(botonCancelar);

        // ------------------ EVENTO GUARDAR ------------------
        botonGuardar.addActionListener(e -> {
            try {
                String nombre = campoNombre.getText().trim();
                String precioTxt = campoPrecio.getText().trim();
                String stockTxt = campoStock.getText().trim();

                // VALIDACIÓN BÁSICA
                if (nombre.isEmpty() || precioTxt.isEmpty() || stockTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Todos los campos son obligatorios.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double precio = Double.parseDouble(precioTxt);
                int stock = Integer.parseInt(stockTxt);
                Categorias categoria = (Categorias) comboCategoria.getSelectedItem();

                // VALIDAR PRODUCTO REPETIDO
                boolean agregado = gestor.agregarProducto(nombre, categoria, precio, stock);

                if (!agregado) {
                    JOptionPane.showMessageDialog(this,
                            "El producto ya existe. No se puede agregar.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Producto agregado correctamente");

                // REFRESCA TABLA DEL PADRE
                padre.refrescar();

                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Precio y Stock deben ser números válidos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // ------------------ BOTÓN CANCELAR ------------------
        botonCancelar.addActionListener(e -> dispose());
    }
}




