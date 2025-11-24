import java.util.ArrayList;
import java.util.List;

//clase generica que se usa en gestor productos
public class RepositorioGenerico<T> {

    protected List<T> lista = new ArrayList<>();

    public void agregar(T obj) {
        lista.add(obj);
    }

    public void eliminar(T obj) {
        lista.remove(obj);
    }

    public List<T> listar() {
        return lista;
    }
}
