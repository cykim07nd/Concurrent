/*Chan-Young Kim, Seungjun Han
 * EE422C
 * ck23586, sh36992
 * Group # 34
 */

package assignment6;

import static org.junit.Assert.fail;

import org.junit.Test;

public class TestTicketOffice {
	public static int score = 0;

	// @Test
	public void basicServerTest() {
		try {
			TicketServer.start(16789);
		} catch (Exception e) {
			fail();
		}
		TicketClient client = new TicketClient();
		client.requestTicket(0);
	}

	// @Test
	public void testServerCachedHardInstance() {
		try {
			TicketServer.start(16790);
		} catch (Exception e) {
			fail();
		}
		TicketClient client1 = new TicketClient("localhost", "c1");
		TicketClient client2 = new TicketClient("localhost", "c2");
		client1.requestTicket(1);
		client2.requestTicket(2);
		
	}

	// @Test
	public void twoNonConcurrentServerTest() {
		try {
			TicketServer.start(16791);
		} catch (Exception e) {
			fail();
		}
		TicketClient c1 = new TicketClient("nonconc1");
		TicketClient c2 = new TicketClient("nonconc2");
		TicketClient c3 = new TicketClient("nonconc3");
		c1.requestTicket(0);
		c2.requestTicket(0);
		c3.requestTicket(0);
	}

	@Test
	public void twoConcurrentServerTest() {
		try {
			TicketServer.start(16792);				//initialize server
		} catch (Exception e) {
			fail();
		}
		final TicketClient c1 = new TicketClient("Ticket Office A");	//initialize the threads
		final TicketClient c2 = new TicketClient("Ticket Office B");
		Thread t1 = new Thread() {										//define run for both threads
			public void run() {
				c1.requestTicket2(1);
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				c2.requestTicket2(2);		
			}
		};
			t1.start();												//begin the two threads
			t2.start();
			try {
				t1.join();											//bring them together
				t2.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
