package ie.gmit.sw;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ie.gmit.utils.WordFrequencyTable;


public class WordCloudURLTest {
	
	TextSource webpage = null;
	WordFrequencyTable wordTable = null;
	/**
	* Junit URL test
	*
	* @author  Ciaran Brennan
	* @version 1.0
	* @since   2015-12-26
	*/
	@Before
	public void setUp() throws Exception {
		// Create a new object representing a URL text source
		webpage = new URLTextSource("http://csb.stanford.edu/class/public/pages/sykes_webdesign/05_simple.html");
		
		// Build the word frequency table from this source
		wordTable = new WordFrequencyTable(webpage.getWordsInText());
		
		// Call a method on the WordFrequencyTable object to draw a Tag Cloud image for each text source
		// (the name of the image file is obtained using getName() and will end with .png)
		wordTable.drawTagCloud(webpage.getName());
	}

	@Test
	// Test the number of words found in the URL
	// NB: This test will fail if the contents of the URL change:
	// better to test a URL that is under programmer's control
	public void testURLWords() {
		int expected = 198;	// 198 words in the URL text
		int actual = webpage.getWordsInText().length;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	// Test one of the word frequencies found in the URL file
	public void testWordFrequencies() {
		int expectedWordCount = 3;	// The word 'text' appears 3 times
		int actualWordCount = webpage.getWordFreqTable().getWordCount("text");
		Assert.assertEquals(expectedWordCount, actualWordCount);
	}
	
	@Test
	// Test that the output png file exists
	public void testPngExists() {
		File file = new File("csbstanfordeduclasspublicpagessykeswebdesign05simplehtml.png");
		Assert.assertTrue(file.exists());
	}
}
