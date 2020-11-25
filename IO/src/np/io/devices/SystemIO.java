package np.io.devices;

import java.io.IOException;
import java.util.Scanner;

public class SystemIO extends IODevice {

	@Override
	public void WriteString(String message) {
		System.out.println(message);
	}

	@Override
	public String ReadString() {
		try {
			Scanner scanner = new Scanner(System.in);
			String line = scanner.next();
			scanner.close();
			return line;
		} catch (Exception ex) {
			throw new DeviceException();
		}
	}

	@Override
	public void WriteObject(Object object) {
		System.out.println(object);
	}

	@Override
	public <T> T ReadObject() {
		throw new DeviceException();
	}

}
