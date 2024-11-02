package telran.net;

import java.net.*;
import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    private static final int PORT = 9090;
    
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
    
        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }
            
    private static void runSession(Socket socket) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream writer = new PrintStream(socket.getOutputStream())
        ) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                Request request = objectMapper.readValue(line, Request.class);
                String response = handleRequest(request);

                writer.println(response);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String handleRequest(Request request) {
        String response = null;
        switch (request.getType()) {
            case REVERSE:
                response = getReversedString(request.getValue());
                break;
            case LENGTH:
                response = getStringLength(request.getValue());
                break;
            default:
                break;
        }
        return response;
    }

    private static String getStringLength(String str) {
        return String.valueOf(str.length());
    }

    private static String getReversedString(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}