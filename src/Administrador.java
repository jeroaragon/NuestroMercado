public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String username, String email, String password) {
        super(nombre, apellido, username, email, password);
    }

    // Constructor alternativo (no eliminar, lo mantuve usable)
    public Administrador(String username, String password, String nombre) {
        super(nombre, "", username, "", password);
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

