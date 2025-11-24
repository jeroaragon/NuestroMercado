//si el usuario no se encuentra en el archivo salta esta excepcion
public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}
