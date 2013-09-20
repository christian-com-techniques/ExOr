import java.util.Date;

public class MembershipEntry {
    private int heartbeatCounter;
    private long tstamp;
    private String ipAddress;

    public MembershipEntry(String ipAddress) {
        this.heartbeatCounter = 0;
        this.tstamp = new Date().getTime()/1000;
        this.ipAddress = ipAddress;
    }

}
