package np.net;

import java.util.*;

import np.common.exceptions.NetworkException;
import np.io.core.IO;

import java.net.*;
import java.io.*;
/**
 * @author george
 */
public class NET {
	public static StringConnection OpenConnection(String host, int port) {
			return new StringConnection(host, port);
	}

	public static void SendStringOverSocket(String message, Socket socket) {
		PrintWriter writer = OpenPrintWriterFromSocket(socket);
		writer.println(message+"\r");
		writer.flush();
	}

	private static PrintStream OpenPrintStreamFromSocket(Socket socket) {
		try {
			return new PrintStream(socket.getOutputStream());
		} catch (IOException ioex) {
			ThrowOutputStreamException();
			return null;
		} 
	}
	
	private static PrintWriter OpenPrintWriterFromSocket(Socket socket) {
		try {
			return new PrintWriter(socket.getOutputStream());
		} catch (IOException ioex) {
			ThrowOutputStreamException();
			return null;
		} 
	}
	 
	private static void ThrowOutputStreamException() {
		throw new NetworkException("Unable To Open OutputStream...");
	}
}
