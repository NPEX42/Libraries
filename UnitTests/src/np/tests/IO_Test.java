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
	
	@Test
	void testStdStreams() {
		IO.GetStdOut().println("StandardOut");
		IO.GetStdErr().println("StandardErr");
	}
	
	

}
