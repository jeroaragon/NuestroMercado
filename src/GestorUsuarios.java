public class GestorUsuarios extends RepositorioGenerico<Usuario> {

    public Usuario buscarPorUsername(String username) throws UsuarioNoEncontradoException {
        for (Usuario u : lista) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        throw new UsuarioNoEncontradoException(
                "El usuario '" + username + "' no existe en el sistema."
        );
    }

    public Usuario login(String username, String password) throws UsuarioNoEncontradoException {

        Usuario u = buscarPorUsername(username);

        if (!u.getPassword().equals(password)) {
            throw new UsuarioNoEncontradoException("Contraseña incorrecta.");
        }

        if (!u.isActivo()) {
            throw new UsuarioNoEncontradoException("El usuario está inactivo.");
        }

        return u;
    }
}
