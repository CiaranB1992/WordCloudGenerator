package ie.gmit.sw;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ie.gmit.utils.WordFrequencyTable;
/**
* Junit tests
*
* @author  Ciaran Brennan
* @version 1.0
* @since   2015-12-26
*/
public class WordCloudFileTest {
	
	TextSource file = null;
	WordFrequencyTable wordTable = null;
	
	@Before
	public void setUp() throws Exception {
		// Create a new object representing a file text source
		file = new FileTextSource("basic.txt");	// Contains the words Assignment, Test, Fred, Test 
												// (no commas, words on separate lines)
		
		// Build the word frequency table from this source
		wordTable = new WordFrequencyTable(file.getWordsInText());
		
		// Call a method on the WordFrequencyTable object to draw a Tag Cloud image for each text source
		// (the name of the image file is obtained using getName() and will end with .png)
		wordTable.drawTagCloud(file.getName());
	}

	@Test
	// Test the words found in the text file
	public void testFileWords() {
		String[] expected = {"Assignment", "Test", "Fred", "Test"};
		String[] actual = file.getWordsInText();
		Assert.assertArrayEquals(expected, actual);
	}
	
	@Test
	// Test one of the word frequencies found in the text file
	public void testFileWordFrequencies() {
		int expectedWordCount = 2;	// The word 'Test' appears twice
		int actualWordCount = file.getWordFreqTable().getWordCount("Test");
		Assert.assertEquals(expectedWordCount, actualWordCount);
	}
	
	@Test
	// Test that the output png file exists
	public void testFilePngExists() {
		File file = new File("basic.png");
		Assert.assertTrue(file.exists());
	}
}
