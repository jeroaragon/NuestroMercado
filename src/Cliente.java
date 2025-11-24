import java.util.Objects;

//hereda de Usuario
public class Cliente extends Usuario {

    public Cliente(String nombre, String apellido, String username, String email, String password) {
        super(nombre, apellido, username, email, password);
    }

    //nunca esta de mas
    public Cliente() {}

    @Override
    public boolean esAdmin() {
        return false;
    }

    @Override
    public String getTipoUsuario() {
        return "Cliente";
    }

    // Necesarios para Set (evitan duplicados por username)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return username.equalsIgnoreCase(cliente.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username.toLowerCase());
    }
}

