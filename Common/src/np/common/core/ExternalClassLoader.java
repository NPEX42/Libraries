package np.common.core;

import java.net.URL;
import java.net.URLClassLoader;

public class ExternalClassLoader extends URLClassLoader {

	public ExternalClassLoader() {
		super(new URL[1], ExternalClassLoader.getSystemClassLoader());
	}
	
	@Override
	protected void addURL(URL url) {
		super.addURL(url);
	}
	
	

}
