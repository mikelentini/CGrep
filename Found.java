import java.util.ArrayList;

/**
 * This class is used as a message to be sent from a ScanActor 
 * to the CollectionActor. This message is made to be immutable.
 * It holds a filename and a list of matching lines found in 
 * that file.
 */
public class Found {
	public final String filename;
	public final ArrayList<String> matchingLines;
	
	public Found(String filename, ArrayList<String> matchingLines) {
		this.filename = filename;
		this.matchingLines = matchingLines;
	}
}
