package np.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import org.junit.jupiter.api.Test;

import np.io.core.IO;

class IO_Test {

	@Test
	void testReadStringFromReader() {
		FileInputStream stream = IO.OpenStream(new File("res/Test.txt"));
		assertNotNull(stream);
		BufferedReader reader = IO.OpenReader(stream);
		assertNotNull(reader);
		String text = IO.ReadStringFromReader(reader);
		assertNotNull(text);
		
		System.out.println("Result Of ReadStringFromReader(): "+text);
		
	}
	
	@Test
	void testReadStringFromReaderWhenNotFound() {
		FileInputStream stream = IO.OpenStream(new File("res/Testtxt"));
		assertNull(stream);
		BufferedReader reader = IO.OpenReader(stream);
		assertNotNull(reader);
		String text = IO.ReadStringFromReader(reader);
		assertNull(text);
		
		System.out.println("Result Of ReadStringFromReader(): "+text);
		
	}
	
	@Test
	void testReadTextFromFile() {
		String text = IO.ReadTextFromFile(new File("res/Test.txt"));
		assertFalse(text.isEmpty());
		
		System.out.println("Result Of ReadTextFromFile(): "+text);
	}
	
	//@Test
	void testOpenNetworkSocket() {
		System.out.println("Opening Socket To n");
		Socket socket = IO.OpenNetworkSocket("www.google.com", 80);
		assertNotNull(socket);
		assertTrue(IO.WriteStringToSocket(socket,
				"GET / HTTP/1.1\r\nHost: google.com\r\nUser-Agent: java11\r\nAccept: */*"
		));
		String text = IO.ReadStringFromSocket(socket);
		System.out.println(text);
		assertEquals(text, "HTTP/1.1https://www.youtube.com/watch?v=9X8g5ST7RwU&list=PLgdDRsRHtD0pJ0Tw7s-kPtmNtj6j-kHjb&index=1 200 OK\r\n");
	}
	
	@Test
	void testStdStreams() {
		IO.GetStdOut().println("StandardOut");
		IO.GetStdErr().println("StandardErr");
	}
	
	

}
