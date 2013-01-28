/**
 * This class is used as a message to be sent to 
 * the CollectionActor before the ScanActors are made. 
 * This class holds an integer of how many files are 
 * going to be scanned, which is immutable.
 */
public class FileCount {
	public final int numFiles;
	
	public FileCount(int numFiles) {
		this.numFiles = numFiles;
	}
}
