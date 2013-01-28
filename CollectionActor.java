import akka.actor.UntypedActor;

/**
 * This class is used to gather all of the Found messages and 
 * decide when to shut down the system. ScanActors send this 
 * class their Found messages for their file, which this class 
 * prints out. Then, when the CollectionActor has gathered all 
 * of the Found messages, it shuts down the system.
 */
public class CollectionActor extends UntypedActor {
	private int numFiles;
	private int foundsReceived = 0;
	
	public CollectionActor() {}

	@Override
	public void onReceive(Object object) throws Exception {
		// If the message is a FileCount message, store the number 
		// of files we are scanning.
		if (object.getClass() == FileCount.class) {
			this.numFiles = ((FileCount) object).numFiles;
		} else if (object.getClass() == Found.class) {
			// If this is a Found message, we have to print out the contents
			// and then check if we have received all of the found messages.
			Found found = (Found) object;
			
			if (!found.matchingLines.isEmpty()) { 
				System.out.println("Matching lines for " + found.filename + ":");
				
				for (String line : found.matchingLines) {
					System.out.println("\t" + line);
				}
			} else {
				System.out.println("No matching lines for " + found.filename + ".");
			}
			
			// If we have received all of the messages, we should shut down.
			if (++foundsReceived >= numFiles) {
				this.getContext().system().shutdown();
			}
		}
	}
}
