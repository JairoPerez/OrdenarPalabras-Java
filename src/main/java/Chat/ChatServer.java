package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {

    public static final int PORT = 2222;
    private static int contadorCliente = 0;
    private static boolean ejecutandose = true;

    public static void main(String[] args) throws IOException {
        ServerSocket socketServer = new ServerSocket(PORT);;
        System.out.println("Servidor escuchando en el puerto " + PORT);

        // Hilo para escuchar el comando de cierre
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (ejecutandose) {
                String comando = sc.nextLine();
                if (comando.equalsIgnoreCase("quit") || comando.equalsIgnoreCase("salir") || comando.equalsIgnoreCase("exit") || comando.equalsIgnoreCase("close")) {
                    ejecutandose = false;
                    try {
                        socketServer.close();
                        System.out.println("Servidor cerrado correctamente.");
                    } catch (IOException e) {
                        System.err.println("Error al cerrar el servidor.");
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

                new ManejadorClientes(socketCliente, contadorCliente).start();
            }
        } catch (IOException e) {
            if (ejecutandose) {
                System.err.println("Error en el servidor.");
            }
        }
    }
}
