import akka.actor.ActorRef;

/**
 * This class is an immutable message that is sent 
 * to the ScanActors so they know which file they are scanning 
 * and what pattern they are scanning for. It also has a reference 
 * to the CollectionActor so the ScanActor can send it a Found message 
 * when it is done scannning the file.
 */
public class Configure {
	public final String filename;
	public final String pattern;
	public final ActorRef collectionActor;
	
	public Configure(String filename, String pattern, ActorRef collectionActor) {
		this.filename = filename;
		this.pattern = pattern;
		this.collectionActor = collectionActor;
	}
}
