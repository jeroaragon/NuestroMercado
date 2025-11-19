import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Producto> productos = new ArrayList<>();

        // ========================================================
        // 4 productos por categoría
        // ========================================================

        // Carne
        productos.add(new Producto(1, "Asado", Categorias.Carne, 5200, 20));
        productos.add(new Producto(2, "Milanesa", Categorias.Carne, 4500, 15));
        productos.add(new Producto(3, "Bife de chorizo", Categorias.Carne, 6000, 10));
        productos.add(new Producto(4, "Pechuga de pollo", Categorias.Carne, 3500, 25));

        // Lácteos
        productos.add(new Producto(5, "Leche entera", Categorias.Lacteos, 1000, 50));
        productos.add(new Producto(6, "Yogur firme", Categorias.Lacteos, 700, 40));
        productos.add(new Producto(7, "Queso cremoso", Categorias.Lacteos, 3800, 30));
        productos.add(new Producto(8, "Manteca", Categorias.Lacteos, 1200, 20));

        // Bebidas
        productos.add(new Producto(9, "Coca Cola 1.5L", Categorias.Bebidas, 1500, 35));
        productos.add(new Producto(10, "Agua mineral 2L", Categorias.Bebidas, 900, 60));
        productos.add(new Producto(11, "Sprite 1.5L", Categorias.Bebidas, 1400, 30));
        productos.add(new Producto(12, "Jugo de naranja", Categorias.Bebidas, 1100, 25));

        // Snacks
        productos.add(new Producto(13, "Papas Lays", Categorias.Snacks, 900, 40));
        productos.add(new Producto(14, "Maní salado", Categorias.Snacks, 700, 35));
        productos.add(new Producto(15, "Galletitas Oreo", Categorias.Snacks, 850, 20));
        productos.add(new Producto(16, "Chizitos", Categorias.Snacks, 500, 50));

        // Limpieza
        productos.add(new Producto(17, "Lavandina", Categorias.Limpieza, 900, 45));
        productos.add(new Producto(18, "Detergente", Categorias.Limpieza, 750, 30));
        productos.add(new Producto(19, "Jabón en polvo", Categorias.Limpieza, 2500, 20));
        productos.add(new Producto(20, "Desodorante de piso", Categorias.Limpieza, 1300, 25));


        // ========================================================
        // GUARDAR EN JSON
        // ========================================================
        JSONGestora.guardarProductos(productos, "data/productos.json");

        System.out.println("Productos guardados correctamente en data/productos.json");
    }
}
