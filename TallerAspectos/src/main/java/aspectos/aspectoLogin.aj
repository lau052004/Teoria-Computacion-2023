package aspectos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

public aspect aspectoLogin {

    // Se crea el pointcut
    pointcut autenticar(): execution(* hr.fer.zemris.java.hw16.jvdraw.JVDraw.main(String[]));

    // Este se ejecuta antes que el main (Osea antes de que cree la pantalla del paint principal)
    before(): autenticar() {
        if (!autenticarUsuario()) {
            System.out.println("Autenticación fallida. Saliendo del programa.");
            System.exit(0);
        }
    }

    // Funcion para autenticar el usuario, lee del archivo, abre la ventana para pedir nombre de usuario y contraseña y valida.
    // Retorna verdadero o falso.
    private boolean autenticarUsuario() {
        Properties properties = new Properties();
        try {
            // Lee los nombres de usuario y contraseñas desde el archivo de propiedades
            FileInputStream input = new FileInputStream("usuarios.properties.txt");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Mostrar la ventana de inicio de sesion
        String username = JOptionPane.showInputDialog("Ingrese su nombre de usuario:"); // Se guarda usuario
        if (username == null) {
            // Simplemente se cerro la ventana, se sale del programa
            return false;
        }

        // Mostrar la ventana de contraseña
        String password = JOptionPane.showInputDialog("Ingrese su contraseña:"); // Se guarda contraseña
        if (password == null) {
            // Simplemente se cerro la ventana, se sale del programa
            return false;
        }

        // Verifica las credenciales
        String storedPassword = properties.getProperty(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
