package np.io.devices;

import java.io.*;

import np.common.annotations.Stub;

public class FileIO extends IODevice implements AutoCloseable {
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private ObjectOutputStream objectWriter;
	private ObjectInputStream objectReader;
	
	public FileIO(String file) {
		try {
			writer = new BufferedWriter(new FileWriter(file));
			reader = new BufferedReader(new FileReader(file));

		} catch (EOFException eof) {
			if(objectReader == null) {
				throw new DeviceOpenException();
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
			throw new DeviceOpenException();
		}
	}
	
	@Override
	public void WriteString(String message) {
		try {
			writer.write(message);
			writer.newLine();
			writer.flush();
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
