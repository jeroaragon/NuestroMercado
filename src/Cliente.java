public class Cliente extends Usuario {

    private Carrito carrito = new Carrito();

    public Cliente(String nombre, String apellido, String username, String email, String password) {
        super(nombre, apellido, username, email, password);
    }

    public Cliente(String username, String password, String nombre) {
    }

    public Carrito getCarrito() {
        return carrito;
    }

    @Override
    public boolean esAdmin() {
        return false;
    }
    
    @Override
    public String getTipoUsuario() {
        return "Cliente";
    }
}