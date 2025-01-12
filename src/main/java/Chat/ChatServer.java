package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {

    public static final int PORT = 2222; //Puerto que a a usar el servidor
    private static int contadorCliente = 0;
    private static boolean ejecutandose = true; // Variable para controlar el bucle del servidor

    public static void main(String[] args) throws IOException {
        ServerSocket socketServer = new ServerSocket(PORT);;
        System.out.println("Servidor escuchando en el puerto " + PORT);

        // Hilo para escuchar el comando de cierre
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (ejecutandose) {
                String comando = scanner.nextLine();
                if (comando.equalsIgnoreCase("quit") || comando.equalsIgnoreCase("salir") || comando.equalsIgnoreCase("exit") || comando.equalsIgnoreCase("close")) {
                    ejecutandose = false;
                    try {
                        socketServer.close();
                        System.out.println("Servidor cerrado correctamente.");
                    } catch (IOException e) {
                        System.err.println("Error al cerrar el servidor: " + e.getMessage());
                    }
                    break;
                }
            }
        }).start();

        try {
            while (ejecutandose) {
                Socket socketCliente = socketServer.accept();
                contadorCliente++;
                System.out.println("Cliente " + contadorCliente + " conectado");

                new ClientHandler(socketCliente, contadorCliente).start();
            }
        } catch (IOException e) {
            if (ejecutandose) {
                System.err.println("Error en el servidor: " + e.getMessage());
            }
        }
    }
}
