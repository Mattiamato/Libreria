
import java.net.*;
import java.io.*;

public class multiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Listening on port: " + serverSocket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        
        while (listening)
        	new multiServerThread(serverSocket.accept()).start();
        
        serverSocket.close();
    }
}