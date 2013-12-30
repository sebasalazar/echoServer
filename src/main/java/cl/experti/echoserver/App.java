package cl.experti.echoserver;

import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App implements Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(7777);
            while (true) {
                Socket client = server.accept();
                EchoHandler handler = new EchoHandler(client);
                handler.start();
            }
        } catch (Exception e) {
            logger.error(e.toString());
            logger.debug("Excepcion Capturada", e);
        }
    }
}
