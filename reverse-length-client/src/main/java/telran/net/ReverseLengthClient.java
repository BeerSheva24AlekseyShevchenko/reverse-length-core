package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ReverseLengthClient {
    private Socket socket;
    private PrintStream writer;
    private BufferedReader reader;

    public ReverseLengthClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getLength(String str) {
        return sendRequest(RequestType.LENGTH, str);
    }

    public String getReversedString(String str) {
        return sendRequest(RequestType.REVERSE, str);
    }

    private String sendRequest(RequestType type, String value) {
        try {
            writer.println(new Request(type, value));
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
