package cl.sebastian.echoserver;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        int port = 7777;
        ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByAddress(new byte[]{0x00, 0x00, 0x00, 0x00}));
        LOGGER.info("Servidor iniciado en puerto {} ", port);

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                LOGGER.info("Se ha aceptado la conexión desde la ip: {}", clientSocket.getRemoteSocketAddress());

                In in = new In(clientSocket);
                Out out = new Out(clientSocket);

                String s;
                while ((s = in.readLine()) != null) {
                    LOGGER.info("{}", s);
                    out.println(s);
                }

                LOGGER.info("Se cierra la conexión desde la ip: {}", clientSocket.getRemoteSocketAddress());
                out.close();
                in.close();
            }
        }
    }

}
