package cl.sebastian.echoserver;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(7777);
            while (true) {
                Socket client = server.accept();
                EchoHandler handler = new EchoHandler(client);
                handler.start();
            }

        } catch (Exception e) {
            LOGGER.error("Error en la ejecución: {}", e.toString());
            LOGGER.debug("Error en la ejecución: {}", e.toString(), e);
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error en el servidor: {}", e.toString());
                LOGGER.debug("Error en el servidor: {}", e.toString(), e);
            }
        }
    }
}
