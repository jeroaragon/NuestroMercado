import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Producto> productos = new ArrayList<>();

        // ---- Carne ----
        productos.add(new Producto("Asado", Categorias.Carne, 5500, 20));
        productos.add(new Producto("Milanesa", Categorias.Carne, 4800, 30));
        productos.add(new Producto("Carne Picada", Categorias.Carne, 4200, 25));
        productos.add(new Producto("Costillas", Categorias.Carne, 6000, 15));

        // ---- Lácteos ----
        productos.add(new Producto("Leche", Categorias.Lacteos, 1200, 100));
        productos.add(new Producto("Queso", Categorias.Lacteos, 2800, 40));
        productos.add(new Producto("Yogur", Categorias.Lacteos, 900, 60));
        productos.add(new Producto("Manteca", Categorias.Lacteos, 1500, 30));

        // ---- Bebidas ----
        productos.add(new Producto("Coca-Cola", Categorias.Bebidas, 1700, 50));
        productos.add(new Producto("Agua", Categorias.Bebidas, 900, 80));
        productos.add(new Producto("Jugo", Categorias.Bebidas, 1300, 40));
        productos.add(new Producto("Energizante", Categorias.Bebidas, 2200, 25));

        // ---- Snacks ----
        productos.add(new Producto("Papas Fritas", Categorias.Snacks, 1400, 70));
        productos.add(new Producto("Maní", Categorias.Snacks, 1100, 50));
        productos.add(new Producto("Galletitas", Categorias.Snacks, 900, 60));
        productos.add(new Producto("Chocolate", Categorias.Snacks, 2000, 40));

        // ---- Limpieza ----
        productos.add(new Producto("Lavandina", Categorias.Limpieza, 800, 50));
        productos.add(new Producto("Detergente", Categorias.Limpieza, 900, 45));
        productos.add(new Producto("Jabón en Polvo", Categorias.Limpieza, 2500, 30));
        productos.add(new Producto("Desodorante", Categorias.Limpieza, 1500, 35));

        // ---- Frutas ----
        productos.add(new Producto("Manzana", Categorias.Frutas, 700, 80));
        productos.add(new Producto("Banana", Categorias.Frutas, 650, 90));
        productos.add(new Producto("Naranja", Categorias.Frutas, 600, 70));
        productos.add(new Producto("Pera", Categorias.Frutas, 750, 60));

        // GUARDAR EN JSON
        JSONGestora.guardarProductos(productos, "data/productos.json");

        System.out.println("Productos guardados correctamente en data/productos.json");
    }
}

