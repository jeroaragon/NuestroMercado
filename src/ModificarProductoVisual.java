import javax.swing.*;
import java.awt.*;
/*
 Ventana de diálogo para modificar un producto existente.
 Implementa la interfaz IGestionProducto para mantener un estándar
 con las demás ventanas que modifican o cargan productos.
 */
public class ModificarProductoVisual extends JDialog implements IGestionProducto {
    // Campos de texto para editar los datos del producto
    private JTextField campoNombre, campoPrecio, campoStock;
    // ComboBox para seleccionar la categoría
    private JComboBox<Categorias> comboCategoria;
    // Referencia al gestor para aplicar los cambios al JSON
    private GestorProductos gestor;

    // ID del producto que se está modificando
    private int id;



    // Constructor de la ventana. Recibe el producto que será modificado para mostrar su información actual.

    public ModificarProductoVisual(JFrame parent, GestorProductos gestor,
                                   int id, String nombre, String categoria,
                                   double precio, int stock) {

        super(parent, "Modificar Producto", true);
        this.gestor = gestor;
        this.id = id;

        setSize(350, 300);
        setLayout(new GridLayout(6, 2, 5, 5));
        setLocationRelativeTo(parent);

        // Campos
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

        // Botones
        JButton botonGuardar = new JButton("Guardar cambios");
        JButton botonCancelar = new JButton("Cancelar");

        add(botonGuardar);
        add(botonCancelar);
        // Acción del botón "Guardar"
        botonGuardar.addActionListener(e -> {
            if (validarDatos()) {
                cargarDatosProducto();
                JOptionPane.showMessageDialog(this, "Producto modificado correctamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        botonCancelar.addActionListener(e -> dispose());
    }



     // Guarda los cambios ingresados por el usuario   en el gestor de productos.
    @Override
    public void cargarDatosProducto() {
        String newNombre = campoNombre.getText();
        Categorias newCategoria = (Categorias) comboCategoria.getSelectedItem();
        double newPrecio = Double.parseDouble(campoPrecio.getText());
        int newStock = Integer.parseInt(campoStock.getText());

        gestor.modificarProducto(id, newNombre, newCategoria, newPrecio, newStock);
    }



      //Valida que los campos tengan datos correctos.El nombre no debe estar vacío.Precio debe ser número double.Stock debe ser entero.
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




