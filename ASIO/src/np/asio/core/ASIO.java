package np.asio.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import np.asio.callbacks.JobCompleteCallback;
import np.asio.callbacks.JobProgressChanged;
import np.common.annotations.API;
import np.common.annotations.API.Level;
import np.common.exceptions.FileException;
import np.io.core.IO;
@API
public class ASIO {
	private ASIO() {}
	private static int workerCount = 0;
	
	public static Thread LoadString(String file, JobCompleteCallback<String> onComplete) {
		return ConstructThread(() -> {
			AsyncLoadString(file, onComplete, null);
		});
	}
	
	public static Thread LoadString(String file, JobCompleteCallback<String> onComplete, JobProgressChanged onProgressChanged) {
		return ConstructThread(() -> {
			AsyncLoadString(file, onComplete, onProgressChanged);
		});
	}
	 
	private static void AsyncLoadString(String file, JobCompleteCallback<String> onComplete, JobProgressChanged onProgressChanged) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
			 String line;
			 StringBuffer buffer = new StringBuffer();
			 
			 int count = 0;
			 
			 int length = IO.GetFileSizeBytes(file);
			 
			 while((line = reader.readLine()) != null) {
				 buffer.append(line).append("\n");
				 count += line.length() + 1; //Adjust for '\n'
				 float pct = (float) count / (float) length;
				 if(pct > 1) pct = 1;
				 if(onProgressChanged != null) onProgressChanged.OnProgressChanged((int)(100 * pct));
			 }
			 
			 onComplete.OnComplete(buffer.toString());
		} catch(IOException ioex) {
			throw new FileException();
		}
	}
	
	private static Thread ConstructThread(Runnable runnable) {
		Thread t = new Thread(runnable);
		t.setDaemon(true);
		t.setName("ASIO-Worker-"+workerCount++);
		return t;
	}	
}
