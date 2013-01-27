import akka.actor.UntypedActor;

public class CollectionActor extends UntypedActor {
	private int numFiles;
	private int foundsReceived = 0;
	
	public CollectionActor() {}

	@Override
	public void onReceive(Object object) throws Exception {
		if (object.getClass() == FileCount.class) {
			this.numFiles = ((FileCount) object).numFiles;
		} else if (object.getClass() == Found.class) {
			Found found = (Found) object;
			
			if (!found.matchingLines.isEmpty()) { 
				System.out.println("Matching lines for " + found.filename + ":");
				
				for (String line : found.matchingLines) {
					System.out.println("\t" + line);
				}
			} else {
				System.out.println("No matching lines for " + found.filename + ".");
			}
			
			if (++foundsReceived >= numFiles) {
				this.getContext().system().shutdown();
			}
		}
	}
}
