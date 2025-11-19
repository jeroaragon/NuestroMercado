public class Producto {

    private static int contadorId = 1; // ID autoincrementable

    private int id;
    private String nombre;
    private Categorias categoria;
    private double precio;
    private int stock;
    private boolean activo;

    // Constructor normal (cuando creas un producto nuevo)
    public Producto(String nombre, Categorias categoria, double precio, int stock) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.activo = true;
    }

    // Constructor completo (para reconstruir desde JSON)
    public Producto(int id, String nombre, Categorias categoria, double precio, int stock, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;

        // IMPORTANTE: mantener sincronizado el contador
        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    // GETTERS
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Categorias getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public boolean isActivo() { return activo; }

    // SETTERS
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(Categorias categoria) { this.categoria = categoria; }
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

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", precio=" + precio +
                ", stock=" + stock +
                ", activo=" + activo +
                '}';
    }
}

