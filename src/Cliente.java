public class Cliente extends Usuario {

    public Cliente(String nombre, String apellido, String username, String email, String password) {
        super(nombre, apellido, username, email, password);
        this.activo = true;
    }

    public Cliente() {}

    @Override
    public boolean esAdmin() {
        return false;
    }

    @Override
    public String getTipoUsuario() {
        return "Cliente";
    }
}

