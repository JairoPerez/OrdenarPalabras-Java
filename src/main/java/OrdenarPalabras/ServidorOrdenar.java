package OrdenarPalabras;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServidorOrdenar {

    public static final int PORT = 1111; //Puerto que a a usar el servidor

    public static void main(String[] args){
        try {
            ServerSocket socketServer = new ServerSocket(PORT);
            ;
            System.out.println("Servidor escuchando en el puerto " + PORT);

            Socket socketCliente = socketServer.accept();
            System.out.println("Cliente conectado");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            salida.println("¡Bienvenido al Servidor de Ordenamiento de Palabras!");

            while (true) {
                String mensaje = entrada.readLine();
                if (mensaje == null || mensaje.equalsIgnoreCase("salir")) {
                    break;
                }

                String[] palabras = mensaje.split(",");
                if (palabras.length < 2) {
                    salida.println("Error: Debes enviar dos palabras mínimo.");
                }
                for (int i = 0; i < palabras.length; i++) {
                    palabras[i] = palabras[i].trim();
                }

                Arrays.sort(palabras, String::compareTo);

                salida.println("Palabras ordenadas: " + String.join(", ", palabras));
            }

            socketCliente.close();
            socketServer.close();
            System.out.println("Servidor cerrado.");

        }catch (IOException ex){
            System.out.println("Error en la conexión");
        }
    }

}
