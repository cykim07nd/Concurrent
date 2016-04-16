package assignment6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClient sc;
	String SeatLetter[] =new  String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA"};
	String booth[] = {"Ticketbooth A", "Ticketbooth B"};

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public boolean run2(int thread) {
		System.out.flush();
		try {
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			PrintWriter out =
			new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			/////////////////////////////////////////////////////////////////////////////
			out.println(thread);
			String result = in.readLine();
			if(result.equals("NA")){
				echoSocket.close();
				return false;
			}
			else{
				String results[] = result.split(" ");
				int x1 = Integer.parseInt(results[0]);
				int x2 = Integer.parseInt(results[1]);
				System.out.println(booth[thread-1] +" purchased Seat " + SeatLetter[x2]+ (x1+100));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void run() {
		System.out.flush();
		try {
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			PrintWriter out =
			new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			/////////////////////////////////////////////////////////////////////////////
	echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class TicketClient {
	String SeatLetter[] =new  String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA"};
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";

	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name);
	}

	TicketClient() {
		this("localhost", "unnamed client");
	}

	void requestTicket(int a) {
		// TODO thread.run()
		tc.run();
		System.out.println(hostName + "," + threadName + " got one ticket");
	}
	
	void requestTicket2(int thread) {
		// TODO thread.run()
		boolean tickets_left = true;
		while(tickets_left){
			Random number = new Random();
			int count = number.nextInt(900)+100;
			for(int i =0; i<count ;i++){
				tickets_left = tc.run2(thread);
				if(!tickets_left){
					break;
				}
			}
			
		}		
	}
	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}



//tc.run(thread);
/*while(!test.soldout)
{
	Random num = new Random();
	int line = num.nextInt(900)+100;
	boolean success = false;
	for(int i = 0;i<line;i++){
		while(!success)
		{
			Seat seat = test.bestavailableSeat();
			if(seat.x1 !=-1)
			{
				String out = SeatLetter[seat.x2];
				if (test.purchaseSeat(seat, thread)) {
					System.out.println(threadName + ": Reserved Seat " + out+ seat.x1);
				} else {
					System.out.println(threadName + ": Failed to reserve Seat " + out+ seat.x1 );
				}	
			}
			else{
				test.soldout = true;
				break;
			}	
			if(test.soldout==true)
			{
				break;
			}
		}
	}
}
if (!test.output) {
	System.out.println("Tickets are gone, sorry!");
	test.output = true;
}*/
////////////////////////////////////////////////////////////////////////////
