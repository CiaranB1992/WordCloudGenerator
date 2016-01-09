package ie.gmit.sw;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
* Takes text from file and prepares it for use by other classes.
* It initiates the process that creates a wordcloud rom a txt file
*
* @author  Ciaran Brennan
* @version 1.0
* @since   2015-12-26
*/
public class FileTextSource extends TextSource {

	// Constructor
	public FileTextSource(String filename) throws IOException {
		// Take the first part of the filename (minus the file extension) and save it as instance variable
		this.setName(filename);
		
		// First, extract the text content of the named file
		this.setFullText(filename);
		
		// Then, extract individual words from the text
		this.extractWords();
		
		// Then, create the word frequency table
		this.setWordFreqTable(this.getWordsInText());
	}

	@Override
	// Strip the file extension off the filename and store as name
	public void setName(String sourceName) {
		this.name = sourceName.substring(0, sourceName.lastIndexOf("."));
	}
	
	@Override
	// Read the full text from the document source text file, into an array of strings (one for each line),
	// then combine these into one long string	
	
	public void setFullText(String filename) throws IOException {
		String textLine = null;
		ArrayList<String> textLines = new ArrayList<String>();
		BufferedReader br = null;
		
		// An efficient way to read a text file is to wrap a BufferedReader around a FileReader
		br = new BufferedReader(new FileReader(filename));
		 
	    // Read the file one line at a time using the readLine method of the BufferedReader
	    // the readLine method returns null when there is nothing else to read.
		while ((textLine = br.readLine()) != null) 
			textLines.add(textLine);
	    
	    // Convert the lines into one long string, and set the instance variable fullText	
		this.fullText = textLines.toString();
		
	    // Free up memory by closing the BufferedReader
		br.close();
	}

}
