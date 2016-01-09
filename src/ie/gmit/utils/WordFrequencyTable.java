package ie.gmit.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

// This class represents a table of words found in a text source, and their frequencies.
// It builds this table from an array of strings passed to the constructor,
// and excludes common words ('stop words'), taken from a text file. 
// Two methods provide output: toString() outputs the table in text form,
// and drawTagCloud() outputs the data in visual form.
/**
* 
* tests how frequently the words are used
* puts them in hashmap which paires them with a value. the wordloud uses words with the highest values
* @author  Ciaran Brennan
* @version 1.0
* @since   2015-12-26
*/


public class WordFrequencyTable {
	
	// There is only one list of stop words that applies to all tag clouds, 
	// so the stop word variables can be static (once for the class)
	static ArrayList<String> stopWords;
	static String stopWordsFile = "stopwords.txt";

	private HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
	
	// Constructors
	public WordFrequencyTable() {
		
		// Extract stop words from a separate text file - once only
		if (stopWords == null) {
			try {
				setStopWords();
			} catch (IOException e) {
				System.out.println("Problem reading stop words from file. No words will be excluded as insignificant.");
			}
		} else {
			System.out.println("Stop words file already read, no need to repeat the process.");
		}

	}

	public WordFrequencyTable(String[] wordsInText) {
		
		// Call single-argument constructor (to get stop words)
		this();
		
		// Now construct the word frequency table by counting the occurrences of 
		// words and storing the the words and counts in a hash map.
		HashMap<String,Integer> wordCount = new HashMap<String,Integer>();
		
        for (String word : wordsInText) {
            Integer count = wordCount.get(word);
            // Note: The ArrayList contains() method is case sensitive.
            // The stop words are in lower case, so a match will not
            // necessarily be made - future enhancement.
            if (!stopWords.contains(word.toLowerCase())) {
	            if (count == null) {
	                wordCount.put(word, 1);
	            } else {
	                wordCount.put(word, ++count);
	            }
            }
        }
        
        // TEST
        System.out.println("number of entries: " + wordCount.size());

        // Remove all entries with value 1 as singly-occurring words are not significant
        wordCount.values().removeAll(Collections.singleton(1));
        
		this.wordFreq = wordCount;		
	}
	/**
	* extracts from a text file a list of common words to exclude from the tag cloud
	* 
	* @author  Ciaran Brennan
	* @version 1.0
	* @since   2015-12-26
	*/
	// Extracts from a text file a list of common words to exclude from the tag cloud
	public void setStopWords() throws IOException{
		
		// Read the stop words from text file to an ArrayList of strings
		String word = null;
		ArrayList<String> words = new ArrayList<String>();
		BufferedReader br = null;
		
		// An efficient way to read a text file is to wrap a BufferedReader around a FileReader
		br = new BufferedReader(new FileReader(stopWordsFile));
		 
	    // Read the file one line at a time using the readLine method of the BufferedReader
	    // the readLine method returns null when there is nothing else to read.
		while ((word = br.readLine()) != null) 
			words.add(word.toLowerCase());	// Whatever the source, convert the words to lower case
											// (later, words to compare with these will be converted
											// to the same case)
	    
	    // Set static variable
	    stopWords = words;
	    
	    // Free up memory by closing the BufferedReader
		br.close();	    
	}
	/**
	* Return a string representation of the contents of the word frequency table
	* It initiates the processes involved in generating tag clouds from individual documents.
	*
	* @author  Ciaran Brennan
	* @version 1.0
	* @since   2015-12-26
	*/
	@Override
	public String toString() {
		String wordTable= "";
		for (HashMap.Entry<String, Integer> entry : this.wordFreq.entrySet()) {
		    String key = entry.getKey();
		    Integer value = entry.getValue();
		    wordTable += "The word " + key + " occurred " + value + " times\n";
		}
		return wordTable;
	}
	
	/**
	* draws the tag cloud
	* adapted from assignment sample code given
	*
	* @author  Ciaran Brennan
	* @version 1.0
	* @since   2015-12-26
	*/
	public void drawTagCloud(String imageName) throws IOException {
		
		BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.getGraphics();
		
		// Create arrays of colours
		Color[] colors = {Color.red, Color.yellow, Color.blue, Color.green, Color.magenta, Color.cyan, Color.white, Color.lightGray};
		// Create arrays of font names and styles
		String[] fontNames = {Font.SANS_SERIF, Font.SERIF, Font.MONOSPACED};
		int[] fontStyles = {Font.BOLD, Font.ITALIC, Font.PLAIN};
		
		int vspace = 0;	// Sets the vertical space between words
		Random randomGenerator = new Random();	// Used to generate random y co-ordinate and array indices for font name, colour and style
		for(HashMap.Entry<String, Integer> entry : this.wordFreq.entrySet()){
			// Choose a different colour each time
			graphics.setColor(colors[randomGenerator.nextInt(8)]);
			// Create a font each time, name and style different each time, with size determined by word frequency
			Font font = new Font(fontNames[randomGenerator.nextInt(3)], fontStyles[randomGenerator.nextInt(3)], entry.getValue() * 25);
			//System.out.println(entry.getValue() + ", total: " + totalFreq + ", size: " + entry.getValue()/totalFreq * 600);
			graphics.setFont(font);
			vspace += entry.getValue() * 25 + 5;
			// Note: 2nd and 3rd arguments of drawString are x- and y-coordinates, respectively
			// Choose x (horizontal) position randomly (2nd argument below)
			// Choose y (vertical, downward) position so that it depends on the font size, which is determined by word frequency (3rd argument below):
			graphics.drawString(entry.getKey(), randomGenerator.nextInt(300), vspace);
		}

		// Finalise and write the image file
		graphics.dispose();
		ImageIO.write(image, "png", new File(imageName + ".png"));
		
	}
	
	// Find frequency of a given word
	public int getWordCount(String word) {
		System.out.println(this.wordFreq.toString());
		int wordCount = 0;
		if (this.wordFreq.containsKey(word))
			wordCount = this.wordFreq.get(word);
		return wordCount;
	}
	
	// Getters and setters
	public HashMap<String, Integer> getWordFreq() {
		return wordFreq;
	}

	public void setWordFreq(HashMap<String, Integer> wordFreq) {
		this.wordFreq = wordFreq;
	} 
	
}
