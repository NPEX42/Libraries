package np.io.devices;

import np.common.annotations.API;
import np.common.annotations.API.Level;
import np.common.annotations.Stub;

@API(level = Level.IN_TESTING)
public abstract class IODevice {
	public abstract void WriteString(String message);
	public abstract String ReadString();
	
	public abstract void WriteObject(Object object);
	public abstract <T> T ReadObject();
	
	@Stub
	public void Close() {};
	
	public static IODevice CreateNullDevice() {
		return new IODevice() {
			
			@Override
			public void WriteString(String message) {}
			
			@Override
			public String ReadString() { return ""; }

			@Override
			public void WriteObject(Object object) {}

			@Override
			public <T> T ReadObject() {
				return null;
			}
		};
	}
}
