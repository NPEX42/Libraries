package np.common.core;

import java.util.Locale;

import np.common.exceptions.ThreadException;

public class SYS {
	
	private static String OS;
	
	public static void SleepMillis(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException intex) {
			throw new ThreadException();
		} 
	}
	
	public static OperatingSystemType GetOSType() {
		if(OS == null) OS = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		if(isWindows()) return OperatingSystemType.WINDOWS;
		if(isUnix()) return OperatingSystemType.UNIX;
		if(isMac()) return OperatingSystemType.MACOS;
		return OperatingSystemType.UNKNOWN;
	}
	
	public static String GetEnvironmentVariable(String key) {
		return System.getenv(key);
	}
	
	
	public static enum OperatingSystemType {
		UNKNOWN,
		WINDOWS,
		MACOS,
		UNIX,
		LINUX
	}
	
	 private static boolean isWindows() { return (OS.indexOf("win") >= 0); }
	 private static boolean isMac() { return (OS.indexOf("mac") >= 0); }
	 private static boolean isUnix() { return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ); }
	 private static boolean isSolaris() { return (OS.indexOf("sunos") >= 0); }
}
