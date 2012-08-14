

import java.net.*;
import java.io.*;

public class server {
	
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Listening on port: " + serverSocket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

        Socket clientSocket = null;
        
        try {
            clientSocket = serverSocket.accept();
            System.err.println("Client: " + clientSocket.getInetAddress() +" connected.");
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        String inputLine, outputLine;
        
        exchangeProtocol exProt = new exchangeProtocol();

        outputLine = exProt.processInput(null);
        out.println(outputLine);

        while ((inputLine = in.readLine()) != null) {
        	 System.out.println("Request: " + inputLine);
        	 System.out.println("Processing...");
             outputLine = exProt.processInput(inputLine);
             System.out.println("Sending..");
             out.println(outputLine);
             System.err.println("Sent.");
             
             if (outputLine.equals("Bye.")) {
            	 System.err.println("Connection closed.");
            	 break;
             }
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}