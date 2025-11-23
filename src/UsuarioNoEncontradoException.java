
//si el usuario no es encontrado en el archivo salta esta excepcion
public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}
