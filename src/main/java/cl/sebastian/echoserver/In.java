package cl.sebastian.echoserver;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class In {

    private Scanner scanner;

    private static final Logger LOGGER = LoggerFactory.getLogger(In.class);

    public In(java.net.Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("No fue posible obtener un socket: {}", e.toString());
            LOGGER.debug("No fue posible obtener un socket: {}", e.toString(), e);
        }
    }

    public String readLine() {
        String line;
        try {
            line = StringUtils.trimToEmpty(scanner.nextLine());
        } catch (Exception e) {
            line = null;
            LOGGER.error("Error al obtener la línea: {}", e.toString());
            LOGGER.debug("Error al obtener la línea: {}", e.toString(), e);
        }
        return line;
    }

    public void close() {
        scanner.close();
    }
}
