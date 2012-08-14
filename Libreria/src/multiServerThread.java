
import java.net.*;
import java.io.*;

public class multiServerThread extends Thread {
    private Socket socket = null;

    public multiServerThread(Socket socket) {
    	super("multiServerThread");
    	this.socket = socket;
    	
    	System.out.println("--------------------------------------");
    	System.err.println("Client: " + socket.getInetAddress() +" connected.");
    	System.out.println("--------------------------------------");
    }

    public void run() {

	try {
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    String inputLine, outputLine;
	    exchangeProtocol kkp = new exchangeProtocol();
	    outputLine = kkp.processInput(null);
	    out.println(outputLine);

	    while ((inputLine = in.readLine()) != null) {
	    	System.out.println("Request from: " + socket.getInetAddress());
	    	System.out.println("Processing...");	    	
	    	outputLine = kkp.processInput(inputLine);
	    	System.out.println("Sending..");
            out.println(outputLine);
            System.err.println("Sent.");
            System.out.println();
            
	    	if (outputLine.equals("Bye.") || inputLine.equals("Bye"))
	    		break;
	    }
	    
	    System.err.println("Client: " + socket.getInetAddress() + " disconnected.");
	    out.close();
	    in.close();
	    socket.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}