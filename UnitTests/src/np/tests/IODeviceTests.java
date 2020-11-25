package np.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import np.io.devices.FileIO;
import np.io.devices.IODevice;
import np.io.devices.NetworkIO;
import np.io.devices.SystemIO;

class IODeviceTests {

	@Test
	void testSystemIO() {
		IODevice io = new SystemIO();
		io.WriteString("Hello");
		io.ReadString();
		
		io.WriteObject(io);
	}
	
	@SuppressWarnings("resource")
	@Test
	void testFileIO() {
		IODevice io = new FileIO("res/Test.txt");
		io.WriteString("Hello");
		io.ReadString();
		io.Close();
		
		io = new FileIO("res/Object.bin");
		io.WriteObject(io);
		IODevice loaded = io.ReadObject();
		io.Close();
	}
	
	void testNetworkIO() {
		IODevice io = IODevice.CreateNullDevice();
		io.WriteString("Hello");
		io.ReadString();
		io.Close();
	}

}
