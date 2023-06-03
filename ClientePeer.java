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

        String inputLine;
        while (true) {
            inputLine = consoleIn.readLine();
            out.println(inputLine);

            String response = in.readLine();
            System.out.println("Resposta recebida: " + response);

            if (response.equals("Fim da conversa")) {
                break;
            }
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
