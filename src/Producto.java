public class Producto {
    //atributos
    protected int id;
    protected String nombre;
    protected Categorias cat;
    protected double precio;
    protected int stock;

    public Producto(int id, String nombre, Categorias cat, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.cat = cat;
        this.precio = precio;
        this.stock = stock;
    }
    public Producto(){}

    //metodos
    public void aumentarStock(int cantidad){
        this.stock += cantidad;
    }
    public void reducirStock(int cantidad){
        this.stock -= cantidad;
    }

    //getter y setter
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public Categorias getCat() {return cat;}
    public void setCat(Categorias cat) {this.cat = cat;}

    public double getPrecio() {return precio;}
    public void setPrecio(double precio) {this.precio = precio;}

    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cat=" + cat +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
