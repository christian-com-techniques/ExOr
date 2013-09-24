
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConnectionHandler implements Runnable {

    private boolean shouldRun = true;
    private int port = 61233;
    private int bufferSize = 2048;

    public ConnectionHandler() {
    }
    
    public ConnectionHandler(int port, int bufferSize) {
    	this.port = port;
    	this.bufferSize = bufferSize;
    }

    public void kill() {
        this.shouldRun = false;
    }

    public void run() {

    	DatagramSocket rcvSocket = null;
		try {
			rcvSocket = new DatagramSocket(port);
		} catch (SocketException e1) {
			System.out.println("Can't listen on port "+port);
		}
    	byte[] buffer = new byte[bufferSize];
    	DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        System.out.println("Waiting for UDP packets: Started");

        while(shouldRun) {
        	try {
				rcvSocket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

        	String msg = new String(buffer, 0, packet.getLength());
            System.out.println(packet.getAddress().getHostAddress()+ ": "+ msg);
            
            
            if(msg == "join") {
            	
            } else if(msg == "leave") {
            	
            } else if(msg == "updateList") {
            	
            }
            
            
            

        }

        System.out.println("[" + this.getClass().toString() + "] is dying.");
    }

}