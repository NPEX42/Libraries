package np.io.core;

import java.io.*;
import java.net.*;

public class IO {
	public static String ReadStringFromReader(BufferedReader refactor) {
		try {
			return refactor.readLine();
		} catch (IOException ioex) {
			return null;
		}
	}
	
	public static String ReadTextFromFile(File file) {
		if(file.exists()) {
			InputStream stream = OpenStream(file);
			BufferedReader reader = OpenReader(stream);
			
			String line;
			StringBuffer buffer = new StringBuffer();
			
			
			while((line = ReadStringFromReader(reader)) != null) {
				buffer.append(line).append('\n');
			}
			
			return buffer.toString();
		} else {
			return "";
		}
	}
	
	public static BufferedReader OpenReader(InputStream stream) {
		if(stream == null) return new BufferedReader(new InputStreamReader(InputStream.nullInputStream()));
		return new BufferedReader(new InputStreamReader(stream));
	}
	
	public static FileInputStream OpenStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public static Socket OpenNetworkSocket(String hostname, int port) {
		try {
			Socket socket = new Socket(hostname, port);
			return socket;
		} catch (IOException ioex) {
			return new Socket(Proxy.NO_PROXY);
		}
	}
	
	public static String ReadStringFromSocket(Socket socket) {
		try {
			InputStream stream = socket.getInputStream();
			BufferedReader reader = OpenReader(stream);
			return ReadStringFromReader(reader);
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean WriteStringToSocket(Socket socket, String message) {
		try {
			BufferedWriter writer = OpenWriter(socket.getOutputStream());
			writer.write(message);
			writer.flush();
			return true;
		} catch (IOException ioex) {
			return false;
		}
	}

	public static BufferedWriter OpenWriter(OutputStream stream) {
		return new BufferedWriter(new OutputStreamWriter(stream));
	}
	
	public static PrintStream GetStdOut() { return System.out; }
	public static InputStream GetStdIn () { return System.in;  }
	public static PrintStream GetStdErr() { return System.err; }
	
	
}
