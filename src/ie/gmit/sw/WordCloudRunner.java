package ie.gmit.sw;

import java.io.IOException;

import ie.gmit.utils.WordFrequencyTable;

public class WordCloudRunner {
	/**
	* runner class takes all the methods from other classes and outputs the results
	*
	* @author  Ciaran Brennan
	* @version 1.0
	* @since   2015-12-26
	*/
	public static void main(String[] args) {
		
		try {
		
			// Create new objects representing a file and a URL text source (polymorph creation)
			TextSource file = new FileTextSource("fatherted.txt");
			TextSource webpage = new URLTextSource("http://www.alexander.ie");
			
			// Build the word frequency tables from these sources
			WordFrequencyTable wordTable1 = new WordFrequencyTable(file.getWordsInText());
			WordFrequencyTable wordTable2 = new WordFrequencyTable(webpage.getWordsInText());
			
			// Call a method on the WordFrequencyTable object to draw a Tag Cloud image for each text source
			// (the name of the image file is obtained using getName() and will end with .png)
			wordTable1.drawTagCloud(file.getName());
			wordTable2.drawTagCloud(webpage.getName());
			
			// Check the word frequency tables for each text source on the console
			System.out.println(wordTable1.toString());
			System.out.println(wordTable2.toString());
			
		} catch (IOException e) {
			System.out.println("There was a problem reading source files or writing image files.");
		}
	}

}
