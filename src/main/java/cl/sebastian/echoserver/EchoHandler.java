package cl.sebastian.echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.commons.io.IOUtils;
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
            if (client != null) {
                InputStream is = client.getInputStream();
                while (true) {
                    if (is.available() == 0) {
                        break;
                    } else {
                        byte[] buffer = new byte[128];
                        IOUtils.readFully(is, buffer);

                        OutputStream os = client.getOutputStream();
                        os.write(buffer);
                        LOGGER.info("{}", buffer);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Excepcion lanzada: cliente desconectado: {}", e.toString());
            LOGGER.debug("Excepcion lanzada: cliente desconectado: {}", e.toString(), e);
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error al leer socket: {}", e.toString());
                LOGGER.debug("Error al leer socket: {}", e.toString(), e);
            }
        }
    }
}
