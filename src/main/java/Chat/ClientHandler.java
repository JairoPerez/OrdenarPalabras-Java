package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private Socket socketCliente;
    private BufferedReader entrada;
    private PrintWriter salida;
    private int clienteId;

    public ClientHandler(Socket socket, int clienteId) {
        this.socketCliente = socket;
        this.clienteId = clienteId;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            salida = new PrintWriter(socketCliente.getOutputStream(), true);

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Cliente "+ clienteId + ": " + mensaje);

                if (mensaje.equalsIgnoreCase("exit")) {
                    System.out.println("El cliente " + clienteId +" ha cerrado la conexión.");
                    break;
                }
                salida.println("Servidor: " + mensaje);
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente " + clienteId + e.getMessage());
        } finally {
            try {
                entrada.close();
                salida.close();
                socketCliente.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
