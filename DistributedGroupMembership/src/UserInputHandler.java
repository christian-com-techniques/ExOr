
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;


public class UserInputHandler implements Runnable {

    // Starts a new thread to get user input while waiting for connections in the primary thread
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private boolean shouldRun = true;
    private Config conf;
    
    UserInputHandler(Config conf) {
        this.conf = conf;
    }
    
    public void kill() {
        this.shouldRun = false;
    }
    
    public static void main(String[] args) throws IOException {
        
        Config conf = new Config("Config/distGrep.conf");
        UserInputHandler handler = new UserInputHandler(conf);
        printOutput(handler.processInput(args));
    }
    
    public void run() {
        
        System.out.println("[" + this.getClass().toString() + "]: Waiting for user input: Started");
	
        while(shouldRun) {
            
            System.out.print("> ");
            
            String userinput = null;
            try {
                userinput = reader.readLine();
            } catch (IOException e) {
                System.err.println("Failed to get user input. " + e);
                continue;
            }
			
            if(userinput.equalsIgnoreCase("exit"))
            {
                System.out.println("[" + this.getClass().toString() + "] Signalling shutdown.");
                break;
            }


            printOutput(processInput(userinput.split(" ")));

        }

        System.out.println("[" + this.getClass().toString() + "] is dying.");
    }

    public static void printOutput(Collector[] output){
        if(output == null) {
            System.out.println("Invalid input.");
            return;
        }
        for(Collector c: output){
            System.out.println(c.getOutput().toString());
        }

    }

    public Collector[] processInput(String[] splitInput) {

        // If the first word is "search", we go this way
        if(splitInput[0].equalsIgnoreCase("search")) {
            String machines = splitInput[1];
            String searchterm = "";

            if(splitInput[2].equalsIgnoreCase("key")) {
                searchterm += "<key>"+splitInput[3].replace("\"", "")+"</key>";
            } else if(splitInput[2].equalsIgnoreCase("value")) {
                searchterm += "<value>"+splitInput[3].replace("\"", "")+"</value>";
            }

            if(splitInput.length > 4) {
                if(splitInput[4].equalsIgnoreCase("or") || splitInput[4].equalsIgnoreCase("||") || splitInput[4].equalsIgnoreCase("|")) {
                    searchterm += "<operator>or</operator>";
                } else if(splitInput[4].equalsIgnoreCase("and") || splitInput[4].equalsIgnoreCase("&&") || splitInput[4].equalsIgnoreCase("&")) {
                    searchterm += "<operator>and</operator>";
                }
                else {
                    String[] newSplit = joinInputAt(splitInput,3);
                    return processInput(newSplit);
                }

                while (splitInput.length > 7) {
                    splitInput = joinInputAt(splitInput, 6);
                }

                if(splitInput[5].equalsIgnoreCase("key")) {
                    searchterm += "<key>"+splitInput[6].replace("\"", "")+"</key>";
                } else if(splitInput[5].equalsIgnoreCase("value")) {
                    searchterm += "<value>"+splitInput[6].replace("\"", "")+"</value>";
                }

            }

            //If the second parameter is "all", we do a broadcast
            if(splitInput[1].equalsIgnoreCase("all")) {
                Connection con = new Connection(conf);
                try {
                    return con.sendBroadcast(searchterm, "searchrequest");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //If the second parameter is a list of machine-IPs, we split them up as an array
            } else {
                if(machines.startsWith(" ")) {
                    machines = machines.substring(1);
                }

                String[] machinearray = machines.split(",");
                Connection con = new Connection(conf);
                try {
                    return con.sendMessage(searchterm, "searchrequest", machinearray);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String[] joinInputAt(String[] input, int index) {
        String[] newSplit = new String[input.length-1];
        for(int i = 0; i < input.length-1; i++) {
            if(i < index)
                newSplit[i] = input[i];
            else if(i == index)
                newSplit[i] = input[i] + " " + input[i+1];
            else
                newSplit[i] = input[i+1];
        }

        return newSplit;
    }

}
