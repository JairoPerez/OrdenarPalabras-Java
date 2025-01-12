package OrdenarPalabras;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteOrdenar {

    public static void main(String[] args) {
        try {
            // Conectar al servidor en localhost y puerto 12345
            Socket socket = new Socket("localhost", 1111);
            System.out.println("Conectado al servidor.");

            // Crear los flujos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            // Leer y mostrar el mensaje de bienvenida
            System.out.println(entrada.readLine());

            while (true) {
                // Pedir al usuario que ingrese varias palabras
                System.out.print("Introduce palabras separadas por coma (o 'salir' para terminar): ");
                String palabras = teclado.readLine();

                if (palabras.equalsIgnoreCase("salir")) {
                    break;
                }

                // Enviar las palabras al servidor
                salida.println(palabras);

                // Recibir la respuesta del servidor y mostrarla
                System.out.println(entrada.readLine());
            }

            // Cerrar la conexión
            socket.close();
            System.out.println("Conexión cerrada.");

        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}
