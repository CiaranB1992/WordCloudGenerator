package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class URLTextSource extends TextSource {

	// Constructor
	public URLTextSource(String url) throws IOException {
		// Take the middle part of the URL and save it as instance variable
		//System.out.println("TEST: " + url);
		this.setName(url);
		
		// First, extract the text content from the URL
		this.setFullText(url);
		
		// Then, extract individual words from the text
		this.extractWords();
		
		// Then, create the word frequency table
		this.setWordFreqTable(this.getWordsInText());
	}

	@Override
	public void setFullText(String url) {
        try {
        	
            URL urlToRead = new URL(url);
            
            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(urlToRead.openStream()));
            ArrayList<String> urlText = new ArrayList<String>();
            		
            String line;
            while ((line = in.readLine()) != null) {
            	urlText.add(line);
            }
            in.close();

            String urlString = "";
            for(String l: urlText){
            	urlString += l+ " ";
            }

            urlString = stripHtmlRegex(urlString);
            
            this.fullText = urlString;

        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
		
	}
	/**
	* replaces all the symbols between < > brakets aswell as symbols specified below with ""
	* when parsing a url, it ensures that the symbols in the html text do not skew the data
	*
	* @author  Ciaran Brennan
	* @version 1.0
	* @since   2015-12-26
	*/
    public static String stripHtmlRegex(String source){
		return source.replaceAll("<.*?>", "");
	}

	@Override
	// Construct the first part of the name to use for the image file:
	// For a URL, remove all non-alphanumeric characters and http/https
	// For example, for URL "http://www.lipsum.com/", extract "wwwlipsumcom",
	// for URL http://stevemiller.net/puretext/, extract "stevemillernetpuretext"
	public void setName(String sourceName){
		this.name = sourceName.replace("http", "").replace("https", "").replaceAll("[^A-Za-z0-9]", "");
	}

}
