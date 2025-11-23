import java.util.Objects;

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

        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    // Constructor desde String categoría
    public Producto(int id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = Categorias.valueOf(categoria.toUpperCase()); // convierte String a enum
        this.precio = precio;
        this.stock = stock;
        this.activo = true;

        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    // ================== GETTERS ==================
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Categorias getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public boolean isActivo() { return activo; }

    // ================== SETTERS ==================
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(Categorias categoria) { this.categoria = categoria; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // ================== toString ==================
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

    // ======================================================
    // NECESARIO para que el carrito funcione PERFECTO
    // ======================================================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto p = (Producto) o;
        return id == p.id; // un producto es igual si tiene el mismo ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}



