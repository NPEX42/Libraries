package np.io.devices;

import java.io.*;
import java.net.Socket;

import np.common.annotations.Stub;

public class NetworkIO extends IODevice implements AutoCloseable {
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private ObjectOutputStream objectWriter;
	private ObjectInputStream objectReader;
	public NetworkIO(String host, int port) {
		try {
			Socket socket = new Socket();
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException ioex) {
			throw new DeviceOpenException();
		}
	}
	
	@Override
	public void WriteString(String message) {
		try {
			writer.write(message + "\r\n");
		} catch (IOException ioex) {
			throw new DeviceException();
		}
	}

	@Override
	public String ReadString() {
		try {
			return reader.readLine();
		} catch (IOException ioex) {
			throw new DeviceException();
		}
	}
	
	@Override
	public void close() throws Exception {
		writer.close();
		reader.close();
	}
	
	@Override
	public void Close() {
		try {
			close();
		} catch (Exception e) {
			throw new DeviceCloseException();
		}
	}

	@Stub
	@Override
	public void WriteObject(Object object) {
		
	}

	@Stub
	@Override
	public <T> T ReadObject() {
		return null;
	}
	
}
