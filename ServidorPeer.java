import java.io.*;
import java.net.*;

public class ServidorPeer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        System.out.println("Peer iniciado. Aguardando conexão na porta " + port + "...");

        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Cliente conectado. Aguardando mensagens...");

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Mensagem recebida: " + inputLine);
            String response = processMessage(inputLine);
            out.println(response);
            if (response.equals("Fim da conversa")) {
                break;
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    private String processMessage(String message) {
        // Implemente o processamento da mensagem aqui
        // e retorne a resposta adequada
        if (message.equals("Tchau") || message.equals("tchau")) {
            return "Fim da conversa";
        } else {
            return "Resposta: " + message.toUpperCase();
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 12345;

        ServidorPeer server = new ServidorPeer();
        server.start(port);
    }
}
