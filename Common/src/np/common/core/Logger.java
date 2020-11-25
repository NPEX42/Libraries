package np.common.core;

import java.io.PrintStream;

public class Logger {
	private String name, formatting;
	private boolean info, warn, debug, fatal;
	private PrintStream out;
	
	private static int loggerCount = 0;
	private static final String defaultFormat = "%TIME - [%THREAD/%NAME/%LEVEL] - %MESSAGE";
	
	public Logger(Class<?> host) {
		this(host.getName(), System.out, defaultFormat);
	}
	
	public Logger(String name, PrintStream out, String format) {
		this.name = name;
		this.out = out;
		this.formatting = format;
	}
	
	public Logger() {
		this("Logger-"+loggerCount++, System.out, defaultFormat);
	}
	
	public void setInfo(boolean info) {
		this.info = info;
	}
	public void setWarn(boolean warn) {
		this.warn = warn;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}
	
	public void SetOutputToStream(PrintStream stream) {
		if(stream == null) out = System.out;
		out = stream;
	}
	
	private void Printfln(String level, Object... args) {
		String format = formatting;
		String message = "";
		for(Object o : args) message += o;
		
		format = format.replaceAll("%MESSAGE", message);
		
		format = format.replaceAll("%TIME", SYS.GetCurrentTimeGMT());
		format = format.replaceAll("%THREAD", SYS.GetCurrentThreadName());
		format = format.replaceAll("%NAME", name);
		format = format.replaceAll("%LEVEL", level);
		
		out.println(format);
	}
	
	public void Info(Object... args) {
		if(info) Printfln("INFO", args);
	}
	
	public void Warn(Object... args) {
		if(warn) Printfln("WARN", args);
	}
	
	public void Debug(Object... args) {
		if(debug) Printfln("DEBUG", args);
	}
	
	public void Fatal(Object... args) {
		if(fatal) Printfln("FATAL", args);
	}
	
	public void Exception(Exception ex) {
		out.println(ex);
	} 
	
	
	
	
	
	
}
