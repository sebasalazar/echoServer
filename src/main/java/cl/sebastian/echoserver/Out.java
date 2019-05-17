package cl.sebastian.echoserver;

import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Out {

    private PrintWriter out;
    private static final Logger LOGGER = LoggerFactory.getLogger(Out.class);

    public Out(Socket socket) {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            LOGGER.error("Error al obtener generar la salida: {}", e.toString());
            LOGGER.debug("Error al obtener generar la salida: {}", e.toString(), e);
        }
    }

    public void close() {
        out.close();
    }

    public void println(Object x) {
        out.println(x);
        out.flush();
    }
}
