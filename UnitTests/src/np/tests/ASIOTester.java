package np.tests;

import np.asio.core.ASIO;
import np.common.core.SYS;
import np.io.core.IO;

public class ASIOTester {
	private static boolean complete;
	public static void main(String[] args) {
		System.out.println(SYS.GetOSType());
		
		ASIO.LoadString("res/Test.txt", ASIOTester::Complete, ASIOTester::Progress).start();;
		
		while(!complete) {
		System.out.println("DOING STUFF...");
		SYS.SleepMillis(1000);
		}
	}
	
	private static void Complete(String file) {
		complete = true;
		System.out.println("Loaded File...");
	}
	
	private static void Progress(int progress) {
		System.err.println("Progress: "+progress);
	}

}
