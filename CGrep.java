import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * CGrep is the driving class in this application. 
 * It checks that the command line arguments are in the 
 * correct format, then checks how many files we are checking. 
 * The ActorSystem is created and the CollectionActor is added to it. 
 * Then, we send a FileCount message to the CollectionActor. If no 
 * files are provided, we tell one ScanActor to scan stdin. If files 
 * are provided, we create one ScanActor per file and send each 
 * a Configure message.
 */
public class CGrep {
    public static void main(String... args) {
    	int filecount = 0;
    	
    	if (args.length == 0) {
    		System.out.println("Usage: java -jar CGrep pattern [file...]");
    		System.exit(0);
    	}
    	
    	String pattern = args[0];
    	ArrayList<String> filenames = new ArrayList<String>();
    	
    	if (args.length > 1) {
    		for (int i = 1; i < args.length; i++) {
    			filenames.add(args[i]);
    		}
    		
    		filecount = filenames.size();
    	}
    	
        ActorSystem system = ActorSystem.create("CGrep");

        ActorRef collectionActor = system.actorOf(new Props(CollectionActor.class));
        collectionActor.tell(new FileCount(filecount), collectionActor);
        
        if (!filenames.isEmpty()) {
	        for (int i = 0; i < filecount; i++) {
	        	ActorRef scanActor = system.actorOf(new Props(ScanActor.class));
	        	scanActor.tell(new Configure(filenames.get(i), pattern, collectionActor), collectionActor);
	        }
        } else {
        	ActorRef scanActor = system.actorOf(new Props(ScanActor.class));
        	scanActor.tell(new Configure(null, pattern, collectionActor), collectionActor);
        }
    }
}
