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
	Venue concert = new Venue();
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
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				int thread = Integer.parseInt(in.readLine());
				String seat = new String();
				while(!success){
					seat = concert.bestavailableSeat2();
					String seatloc[] = seat.split(" ");
					int x1 = Integer.parseInt(seatloc[0]);
					int x2 = Integer.parseInt(seatloc[1]);
					if(x1==-1){
						out.println("NA");
						if(!soldprint){
							success = true;
							System.out.println("Sorry, we're sold out!");
							soldprint = true;
						}
					}
					else
					{
						if(concert.purchaseSeat2(x1, x2,thread)){
							success = true;
						}
						else{
							System.out.println(booth[thread-1] + " failed to secure seat "+ SeatLetter[x2] + x1+100);
						}
					}	
				}
				out.println(seat);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}