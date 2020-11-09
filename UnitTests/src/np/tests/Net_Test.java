package np.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.Socket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import np.io.core.*;
import np.common.exceptions.*;

class Net_Test {
	@Test
	void testNetworkException() {
		try {
			throw new NetworkException("Unable To Open Server...");
		} catch (NetworkException netex) {
			netex.printStackTrace();
		}
	}

}
