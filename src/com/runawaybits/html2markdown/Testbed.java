package com.runawaybits.html2markdown;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Testbed {
	public static void main(String[] args) {
		URL url;
		try {
			//url = new URL("http://en.wikipedia.org/wiki/Text_editor");
			url = new URL("http://www.rockpapershotgun.com/2013/12/02/wot-i-think-journey-of-a-roach/");
			String parsedtext = HTML2Md.convert(url, 30000);
			System.out.println(parsedtext);
			
			System.out.println("done");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}