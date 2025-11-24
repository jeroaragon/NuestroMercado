// hereda de Usuario
public class Administrador extends Usuario {

    //constructor
    public Administrador(String nombre, String apellido, String username, String email, String password) {
        super(nombre, apellido, username, email, password);
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

