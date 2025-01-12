package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 2222);
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);

        System.out.println("Conexion con el servidor realizada. Bienvenido al chat. Para salir tiene que escribir 'exit'");

        String mensajeUsuario;
        while (true) {
            // Leer mensaje del usuario
            mensajeUsuario = sc.nextLine();
            // Enviar mensaje al servidor
            salida.println(mensajeUsuario);
            // Si el usuario escribe "exit", salir
            if (mensajeUsuario.equalsIgnoreCase("exit")) {
                System.out.println("Cerrando conexión...");
                break;
            }
            // Leer respuesta del servidor
            String respuestaServidor = entrada.readLine();
            System.out.println(respuestaServidor);
        }

        // Cerrar recursos
        entrada.close();
        salida.close();
        sc.close();
        socket.close();
    }
}
