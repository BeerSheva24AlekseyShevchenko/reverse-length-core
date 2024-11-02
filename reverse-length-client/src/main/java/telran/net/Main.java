package telran.net;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardIO;

public class Main {
    private static ReverseLengthClient client;

    public static void main(String[] args) {
        connectToServer();
        renderMenu();
    }

    private static void connectToServer() {
        Properties config = loadAppConfig();
        String serverUrl = config.getProperty("server.url");
        int serverPort = Integer.parseInt(config.getProperty("server.port"));
        client = new ReverseLengthClient(serverUrl, serverPort);
    }

    private static Properties loadAppConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("file config.properties not found");
        }
        return properties;
    }

    private static void renderMenu() {
        Item[] items = {
            Item.of("Get string length", Main::getStringLength),
            Item.of("Get reversed string ", Main::getReversedString),
            Item.of("Exit", Main::stopSession, true),
        };
        Menu menu = new Menu("Echo Application", items);
        menu.perform(new StandardIO());
    }

    private static void getStringLength(InputOutput io) {
        String str = io.readString("Enter any string");
        String response = client.getLength(str);
        io.writeLine(response);
    }

    private static void getReversedString(InputOutput io) {
        String str = io.readString("Enter any string");
        String response = client.getReversedString(str);
        io.writeLine(response);
    }

    private static void stopSession(InputOutput io) {
        if (client != null) {
            client.close();
        }
    }
}