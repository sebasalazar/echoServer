package cl.experti.echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina
 */
public class EchoHandler extends Thread {

    private Socket client = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoHandler.class);

    EchoHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            InputStream is = client.getInputStream();
            while (true) {
                if (is.available() == 0) {
                    break;
                } else {
                    byte[] buffer = new byte[128];
                    is.read(buffer);
                    OutputStream os = client.getOutputStream();
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            LOGGER.debug("Excepcion lanzada: cliente desconectado: ", e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                LOGGER.error(e.toString());
            }
        }
    }
}
