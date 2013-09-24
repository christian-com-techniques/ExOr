import java.util.ArrayList;

import javax.xml.bind.JAXBException;


public class DstrGroupMembership {

	//Define the address of the contact-machine for the initialization
	private static String contactIP = "192.168.56.102";	
	private static int contactPort = 61233;
	private static int TGossip = 1000;
	private static boolean running = true;
	
	public static void main(String[] args) throws InterruptedException, JAXBException {

        ConnectionHandler connectionHandler = new ConnectionHandler();
        Thread handlerThread = new Thread(connectionHandler, "Connection Handler");
        handlerThread.start();
        
        MembershipList memList = new MembershipList();
        memList.addEntry("192.168.56.101");
        
        MembershipController.sendJoinGroup(contactIP, contactPort, memList);
        
        while(running) {
        	MembershipController.incrementHeartbeat();
        	MembershipController.sendGossip();
        	Thread.sleep(TGossip);
        	
        	//TODO implement a counter to resend sendJoinGroup after t seconds, if no membership-list is received
        }

        
        handlerThread.interrupt();
	}

}
