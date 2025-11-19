public class GestorProductos extends RepositorioGenerico<Producto> {

    public Producto buscarPorId(int id) {
        for (Producto p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
