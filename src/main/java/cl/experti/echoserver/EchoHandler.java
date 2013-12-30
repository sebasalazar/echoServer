package cl.experti.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina <sebasalazar@gmail.com>
 */
public class EchoHandler extends Thread {

    private Socket client = null;
    private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);

    EchoHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            /*
             BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
             //writer.println("[type 'bye' to disconnect]");

             while (true) {
             String line = reader.readLine();
             if (StringUtils.isEmpty(line)) {
             logger.info("bye!");
             break;
             }
             logger.info("[echo] " + line);
             writer.println(line);
             }
             */
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
            logger.error(e.toString());
            logger.debug("Excepcion lanzada: cliente desconectado: ", e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }
}
