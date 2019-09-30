package cl.sebastian.echoserver;

import java.net.ServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private ServerSocket serverSocket;
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public App() {
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(7777);
            while (true) {
                Thread clientThread = new Thread(new ClientHandler(serverSocket.accept()));
                clientThread.start();
            }
        } catch (Exception e) {
            LOGGER.error("Error: {}", e.getMessage());
            LOGGER.debug("Error: {}", e.getMessage(), e);
        } finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
                LOGGER.error("Error: {}", e.getMessage());
                LOGGER.debug("Error: {}", e.getMessage(), e);
            }
        }

    }

    public static void main(String[] args) {
        App run = new App();
        run.start();
    }

}
