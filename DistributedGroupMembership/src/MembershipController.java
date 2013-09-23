import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;




public class MembershipController {

	
	
	public static void sendJoinGroup(String contactIP, String contactPort, MembershipList memList) throws JAXBException {

		JAXBContext jc = JAXBContext.newInstance(MembershipList.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(memList, System.out);
        
	}
	
	public static void sendLeaveGroup() {
		
	}
	
	public static void sendGossip() {
		
	}
	
	public static void incrementHeartbeat() {
		
	}
	
}
