public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private boolean activo;

    public Producto(int id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.activo = true;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public boolean isActivo() { return activo; }

    // Setters
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPrecio(double precio) { this.precio = precio; }

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
