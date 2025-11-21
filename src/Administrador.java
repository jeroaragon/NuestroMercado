public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String username, String email, String password) {
        super(nombre, apellido, username, email, password);
    }

    public Administrador(String username, String password, String nombre) {
    }

    @Override
    public boolean esAdmin() {
        return true;
    }

    @Override
    public String getTipoUsuario() {
        return "Administrador";
    }
}