package np.xml.core;

import java.io.File;

import np.common.annotations.Factory;

@Factory(type = XML.class)
public class XMLFactory {
	public static XML Create(File file) {
		return new XML().SetDocumentDataFromDisk(file.getAbsolutePath());
	}
	
	public static XML Create() {
		return new XML().CreateEmptyDocument();
	}
}
