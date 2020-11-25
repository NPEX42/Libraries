package np.asio.core;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import np.common.exceptions.FileException;
import np.common.exceptions.ThreadException;
import np.common.exceptions.WatchableException;
public abstract class FileWatcher {
	private WatchService watcher;
	public FileWatcher() {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			Thread modificationWatcher = new Thread(() -> {AsyncWatchFiles();}); 
			modificationWatcher.setDaemon(true);
			modificationWatcher.setName("File Watcher");
			modificationWatcher.start();
		} catch (IOException ioex) {
			throw new FileException();
		}
	}
	
	public Thread Start() {
		Thread t = new Thread(() -> {
			AsyncWatchFiles();
		});
		
		t.setDaemon(true);
		t.setName("File Watcher");
		t.start();
		
		return t;
	}
	
	public void Register(String path) {
		try {
		Path p = Paths.get(path);
		WatchKey key = p.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		} catch (IOException ioex) {
			throw new WatchableException();
		}
	}
	
	private void AsyncWatchFiles() {
		for(;;) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch(InterruptedException intex) {
				throw new ThreadException();
			}
			
			for(WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				
				WatchEvent<Path> path = (WatchEvent<Path>) event;
				Path fileName = path.context();
				
				File file = fileName.toFile();
				
				if(kind == OVERFLOW) { continue; }
				if(kind == ENTRY_CREATE) { OnFileCreated(file, fileName); }
				if(kind == ENTRY_MODIFY) { OnFileEdited(file, fileName); }
				if(kind == ENTRY_DELETE) { OnFileDeleted(file, fileName); }
				
				}
			
			boolean valid = key.reset();
		    if (!valid) {
		        break;
		    }
			
		}
		
	}
	
	public abstract void OnFileCreated(File file, Path path);
	public abstract void OnFileEdited(File file, Path path);
	public abstract void OnFileDeleted(File file, Path path);
}
