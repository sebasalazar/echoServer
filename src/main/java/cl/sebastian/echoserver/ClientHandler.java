package cl.sebastian.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler implements Runnable {

    private static int numConnections;
    private int connectionId = 0;
    public Socket clientSocket;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    public ClientHandler(Socket socket) {
        connectionId = numConnections++;
        LOGGER.info("handling connection, #{}", connectionId);
        clientSocket = socket;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                outputLine = inputLine;
                LOGGER.debug("received: {}", outputLine);
                out.write(outputLine + "\n");
                out.flush();
                if (outputLine.equals("exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al correr socket: {}", e.getMessage());
            LOGGER.debug("Error al correr socket: {}", e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
            try {
                if (in != null) {
                    in.close();
                }
                clientSocket.close();
                LOGGER.info("closing connection, #{}", connectionId);
            } catch (IOException e) {
                LOGGER.error("Error al cerrar socket: {}", e.getMessage());
                LOGGER.debug("Error al cerrar socket: {}", e.getMessage(), e);
            }
        }
    }
}
