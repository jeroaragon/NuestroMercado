import javax.swing.*;

public class Main_Jero_eze {
    public static void main(String[] args) {

        //El gestor se crea en el main porque tiene toda la lógica de los productos. Se pasa a las ventanas para que todas usen el mismo gestor y compartan la misma información sin perder datos.
        GestorProductos gestor = new GestorProductos();

        //damas y caballeros , niños y niñas ahora veran el proyecto mas copado de la historia de la UTN
            InicioVisual login = new InicioVisual(gestor);
            login.setVisible(true);

    }
}



