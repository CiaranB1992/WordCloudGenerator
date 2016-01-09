package ie.gmit.sw;

import java.io.IOException;

import ie.gmit.utils.WordFrequencyTable;

/**
* puts words in array lists which allows each word to be paired with a value (amount of times words occur etc)
*
* @author  Ciaran Brennan
* @version 1.0
* @since   2015-12-26
*/
public abstract class TextSource {
	
	// The name will be a shortened form of the full file name or URL
	// It will be used to name the tag cloud image file
	protected String name;
	// The full contents of the text source
	protected String fullText;
	
	private String[] wordsInText;	// An array of strings to hold individual words in the text
	private WordFrequencyTable wordFreqTable;	// A table of word frequencies
	
	// Abstract method - all subclasses must implement a method to extract the full text from the source	
	public abstract void setFullText(String text) throws IOException;	
	
	// Abstract method - create a short name from the source - will be different for filename and URL
	public abstract void setName(String sourceName);
	
	// Extract words from the full text and store in an array of Strings
	public void extractWords() {
		
		// Use the String method replaceAll to split the long string into an array of strings (words)
		String[] words = this.fullText.replaceAll("[^a-zA-Z'\\s]","").split("\\s+");
		
		// Set the instance variable
		this.setWordsInText(words);
	}
	
	// Getters and setters
	public String getName() {
		return this.name;
	}

	public String getFullText() {
		return this.fullText;
	}

	public String[] getWordsInText() {
		return wordsInText;
	}

	public void setWordsInText(String[] wordsInText) {
		this.wordsInText = wordsInText;
	}

	public WordFrequencyTable getWordFreqTable() {
		return wordFreqTable;
	}

	public void setWordFreqTable(String[] words) {
		WordFrequencyTable wordFreq = new WordFrequencyTable(words);
		this.wordFreqTable = wordFreq;
	}
	
	public void drawTagCloud() throws IOException {
		this.wordFreqTable.drawTagCloud(this.getName());
	}
}
