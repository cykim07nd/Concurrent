/*Chan-Young Kim, Seungjun Han
 * EE422C
 * ck23586, sh36992
 * Group # 34
 */


package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicketServer {
	static int PORT = 2222;
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;
	static Venue concert2 = new Venue();

	public static void start(int portNumber) throws IOException {
		PORT = portNumber;
		Runnable serverThread = new ThreadedTicketServer();
		Thread t = new Thread(serverThread);
		t.start();
	}
}

class ThreadedTicketServer implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	boolean request = false;
	String testcase;
	public Venue concert = new Venue();
	TicketClient sc;
	boolean soldprint = false;
	String SeatLetter[] =new  String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA"};
	String booth[] = {"Ticket booth A", "Ticketbooth B"};

	public void run() {
		// TODO 422C
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(TicketServer.PORT);
			while(true){
				boolean success = false;
				Socket clientSocket = serverSocket.accept();				//accept the client thread
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				int thread = Integer.parseInt(in.readLine());				//get which thread sent the request
				String seat = new String();
				while(!success){											//stay on same thread until it can purchase a seat for it
					seat = concert.bestavailableSeat();						//find the best seat
					String seatloc[] = seat.split(" ");
					int x1 = Integer.parseInt(seatloc[0]);
					int x2 = Integer.parseInt(seatloc[1]);
					if(x1==-1){												//if the best seat is assigned -1, there are no seats left
						out.println("NA");
						if(!soldprint){
							success = true;
							System.out.println("Sorry, we're sold out!");
							soldprint = true;
						}
					}
					else
					{
						if(concert.purchaseSeat(x1, x2,thread)){		//if we are able to purchase the seat, we will prepare to exit the loop
							success = true;
						}
						else{
							System.out.println(booth[thread-1] + " failed to secure seat "+ SeatLetter[x2] + x1+100);	//output the failure of purchase
						}
					}	
				}
				out.println(seat);										//return the purchased seat number to the client
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}