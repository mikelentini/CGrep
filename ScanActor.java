import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * The ScanActor is used to scan a given file for a given pattern. 
 * It receives a Configure message, which explains which file to scan, 
 * which pattern to scan for, and which CollectionActor to report to. 
 * The ScanActor then creates a compiled pattern with the given pattern 
 * to be used in scanning later. If no filename is given, then the ScanActor 
 * reads from stdin.
 */
public class ScanActor extends UntypedActor {
	private String filename;
	private String pattern;
	private ActorRef collectionActor;
	private Pattern patternObj;
	
	public ScanActor() {}

	@Override
	public void onReceive(Object object) throws Exception {
		if (object.getClass() == Configure.class) {
			this.filename = ((Configure) object).filename;
			this.pattern = ((Configure) object).pattern;
			this.collectionActor = ((Configure) object).collectionActor;
			this.patternObj = Pattern.compile(this.pattern);
			
			// Begin reading from the file given or stdin.
			if (this.filename != null) {
				this.scanFile();
			} else {
				this.filename = "stdin";
				this.scanStdIn();
			}
		}
	}
	
	/**
	 * Creates a BufferedReader for stdin and gives it to ScanActor#read() 
	 * to find pattern matches, then sends the Found message to the given 
	 * CollectionActor.
	 */
	private void scanStdIn() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		collectionActor.tell(new Found(this.filename, this.read(in)), this.getSelf());
	}
	
	/**
	 * Creates a BufferedReader for the given file and gives it to 
	 * ScanActor#read() to find pattern matches in the file. Then, it 
	 * sends the results in a Found message to the CollectionActor.
	 */
	private void scanFile() {
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(this.filename));
			
			collectionActor.tell(new Found(this.filename, this.read(br)), this.getSelf());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads using the given BufferedReader and checks for 
	 * pattern matches line by line, storing them in an ArrayList.
	 * Strings in the list have the format: "<line number> <line text>".
	 * 
	 * @param br The BufferedReader to use for reading.
	 * @return A list of matching lines with their line number.
	 */
	private ArrayList<String> read(BufferedReader br) {
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		int lineNum = 0;
		
		try {
			while ((line = br.readLine()) != null) {
				Matcher matcher = patternObj.matcher(line);
				
				if (matcher.find()) {
					lines.add(lineNum + " " + line);
				}
				
				lineNum++;
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
}
