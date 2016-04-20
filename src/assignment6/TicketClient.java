/*Chan-Young Kim, Seungjun Han
 * EE422C
 * ck23586, sh36992
 * Group # 34
 */


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
	String booth[] = {"Box Office A:", "Box Office B:"};

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public boolean run(int thread) {
		System.out.flush();
		try {
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			PrintWriter out =
			new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			out.println(thread); 					//send request for purchase of the best seat to server
			String result = in.readLine();			//result retrieved
			if(result.equals("NA")){				//if NA is receieved, no seats left, return false
				echoSocket.close();					
				return false;
			}
			else{
				String results[] = result.split(" ");	//the server has returned the seat it purchased let's print it out
				int x1 = Integer.parseInt(results[0]);
				int x2 = Integer.parseInt(results[1]);
				System.out.println(booth[thread-1] +" reserved Seat " + SeatLetter[x2]+ (x1+101));
				echoSocket.close();
				return true;
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
		boolean tickets_left = true;		//we assume that there are tickets left
		while(tickets_left){				
			Random number = new Random();
			int count = number.nextInt(900)+100;		//generate a line between 100~1000
			for(int i =0; i<count ;i++){
				tickets_left = tc.run(thread);			//run the thread and if there are no tickets left, it should set tickets left to false
				if(!tickets_left){						//if there are no tickets left, exit the loop
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



