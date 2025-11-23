import javax.swing.*;
import java.awt.*;

/*
  Ventana para agregar un nuevo producto al sistema.
  Implementa IGestionProducto para mantener la estructura del proyecto.
 */
public class AgregarProductoVisual extends JDialog implements IGestionProducto {

    // Campos de texto para ingresar los datos del producto
    private JTextField campoNombre, campoPrecio, campoStock;

    // Combo para elegir la categoría del producto
    private JComboBox<Categorias> comboCategoria;

    // Referencia al gestor de productos (donde realmente se guarda)
    private GestorProductos gestor;

    /*
      Constructor de la ventana
     */
    public AgregarProductoVisual(JFrame parent, GestorProductos gestor) {
        super(parent, "Agregar Producto", true); // true = ventana modal
        this.gestor = gestor;

        // Configuración básica de la ventana
        setSize(350, 300);
        setLayout(new GridLayout(6, 2, 5, 5));
        setLocationRelativeTo(parent); // centrar la ventana

        // ================================
        //  CAMPOS DE INGRESO
        // ================================

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

        // Botones
        JButton botonAgregar = new JButton("Agregar");
        JButton botonCancelar = new JButton("Cancelar");

        add(botonAgregar);
        add(botonCancelar);

        // ================================
        //  ACCIÓN DEL BOTÓN AGREGAR
        // ================================

        botonAgregar.addActionListener(e -> {

            // 1. Primero validamos que los datos estén correctos
            if (!validarDatos()) {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
                return;
            }

            // 2. Intentamos agregar el producto al gestor
            boolean agregado = gestor.agregarProducto(
                    campoNombre.getText().trim(),
                    (Categorias) comboCategoria.getSelectedItem(),
                    Double.parseDouble(campoPrecio.getText()),
                    Integer.parseInt(campoStock.getText())
            );

            // 3. Si el producto ya existe, mostramos el mensaje
            if (!agregado) {
                JOptionPane.showMessageDialog(this, "Producto ya existente");
                return;
            }

            // 4. Si todo salió bien
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente");
            dispose(); // cerrar ventana
        });

        // Acción del botón cancelar
        botonCancelar.addActionListener(e -> dispose());
    }

    // =====================================================
    //  IMPLEMENTACIÓN DE LA INTERFAZ IGestionProducto
    // =====================================================

    /*
      Método que carga los datos en el gestor.
      Ya no se usa directamente desde el botón, pero se mantiene por la interfaz.
     */
    @Override
    public void cargarDatosProducto() {
        String nombre = campoNombre.getText().trim();
        Categorias categoria = (Categorias) comboCategoria.getSelectedItem();
        double precio = Double.parseDouble(campoPrecio.getText());
        int stock = Integer.parseInt(campoStock.getText());

        gestor.agregarProducto(nombre, categoria, precio, stock);
    }

    /*
      Valida que los datos ingresados sean correctos.
      - El nombre no debe estar vacío
      - Precio debe ser double
      - Stock debe ser int
     */
    @Override
    public boolean validarDatos() {
        try {
            if (campoNombre.getText().trim().isEmpty()) return false;

            // Intentamos parsear para verificar que sean números válidos
            Double.parseDouble(campoPrecio.getText());
            Integer.parseInt(campoStock.getText());

            return true; // todo correcto
        } catch (Exception e) {
            return false; // algún dato no es válido
        }
    }
}





