public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String username, String email, String password) {
        super(nombre, apellido, username, email, password);
    }

    @Override
    public boolean esAdmin() {
        return true;
    }
}