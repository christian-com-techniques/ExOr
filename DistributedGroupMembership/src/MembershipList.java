import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({MembershipEntry.class})
public class MembershipList {
	
	private ArrayList<MembershipEntry> membershipList = new ArrayList<MembershipEntry>();

	@XmlElement(name = "member")
	public ArrayList<MembershipEntry> getmembershipList() {
	    return membershipList;
	}

	public void addEntry(String ip) {
        MembershipEntry mE = new MembershipEntry(ip);
		this.membershipList.add(mE);
	}

}
