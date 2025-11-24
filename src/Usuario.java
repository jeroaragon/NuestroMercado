//clase abstracta que heredan los diferentes tipos de usuarios (admin/cliente)
abstract class Usuario {

    // Atributos
    protected String nombre;
    protected String apellido;
    protected String username;
    protected String email;
    protected String password;
    protected boolean activo;

    // Constructor
    public Usuario(String nombre, String apellido, String username, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.password = password;
        this.activo = true;
    }

    public Usuario() {}

    // Getters y setters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }


    public abstract boolean esAdmin();

    public abstract String getTipoUsuario();
}
