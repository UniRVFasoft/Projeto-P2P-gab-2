import java.io.*;
import java.net.*;

public class ClientePeer {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(String ipAddress, int port) throws IOException {
        socket = new Socket(ipAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Conexão estabelecida. Aguardando mensagens...");

        Thread receivingThread = new Thread(() -> {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println("Resposta recebida: " + response);
                    if (response.equals("Fim da conversa")) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread sendingThread = new Thread(() -> {
            try {
                String inputLine;
                while (true) {
                    inputLine = consoleIn.readLine();
                    out.println(inputLine);
                    if (inputLine.equals("Tchau")) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        receivingThread.start();
        sendingThread.start();

        try {
            receivingThread.join();
            sendingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        out.close();
        in.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        String ipAddress = "localhost";
        int port = 12345;

        ClientePeer client = new ClientePeer();
        client.start(ipAddress, port);
    }
}
