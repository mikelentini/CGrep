import java.util.ArrayList;

public class Found {
	public final String filename;
	public final ArrayList<String> matchingLines;
	
	public Found(String filename, ArrayList<String> matchingLines) {
		this.filename = filename;
		this.matchingLines = matchingLines;
	}
}
