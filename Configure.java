import akka.actor.ActorRef;

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
