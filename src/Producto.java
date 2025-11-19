public class Producto {

    private int id;
    private String nombre;
    private Categorias categoria;  // Tipo correcto
    private double precio;
    private int stock;
    private boolean activo;

    public Producto(int id, String nombre, Categorias categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.activo = true;
    }

    // GETTERS
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Categorias getCategoria() { return categoria; }  // Devuelve tipo Categorias
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public boolean isActivo() { return activo; }

    // SETTERS
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(Categorias categoria) { this.categoria = categoria; } // Recibe Categorias
    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // Métodos de stock controlado
    public void reducirStock(int cantidad) throws StockInsuficienteException {
        if (cantidad > stock) {
            throw new StockInsuficienteException(
                    "No hay stock suficiente para el producto: " + nombre
            );
        }
        stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }
}
