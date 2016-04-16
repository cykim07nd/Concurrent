package assignment6;


//0~6 = left
//7~20 = middle
//21~27 = right
//0~25 rows
public class Venue {
	int Bates[][] = new int[28][27];
	boolean soldout = false;
	int currentthread = 1;
	boolean threadrunning = false;
	int currenti =0;
	boolean output = false;
	int currentj =7;
	Seat bestavailableSeat()
	{
		
		for(int i = currenti; i <27;i++)
		{
			if(6<currentj && currentj<21)
			{
				for(int j =7; j<21; j++)
				{
					if(Bates[j][i] == 0)
					{
						currenti=i;
						currentj=(j+1);
						if(j==20)
						{
							currentj=0;
						}
						return new Seat(j,i);
					}
				}
			}
			else if(0<=currentj && currentj<=6){
				for (int j = 0; j < 7; j++) {
					if (Bates[j][i] == 0) {
						currenti = i;
						currentj = (j + 1);
						if (j == 6) {
							currentj = 21;
						}
						return new Seat(j, i);
					}
				}
			} else {
				for (int j = 21; j < 28; j++) {
					if (Bates[j][i] == 0) {
						currenti = i;
						currentj = j;
						if(j==27)
						{
							currentj=7;
							currenti= (i+1);
						}
						return new Seat(j, i);
					}
				}
			}
		}
		soldout = true;
		return new Seat(-1,-1);
	}
	
	boolean purchaseSeat(Seat seat, int thread) {
		while (threadrunning && currentthread!=thread) {
			// wait;
		}
		currentthread = thread;
		threadrunning = true;
		if (Bates[seat.x1][seat.x2] == 0) {
			Bates[seat.x1][seat.x2] = 1;
			currentthread = -1;
			threadrunning = false;
			return true;
		}
		return false;
	}
	
	boolean purchaseSeat2(int x1, int x2, int thread) {
		while (threadrunning && currentthread!=thread) {
			// wait;
		}
		currentthread = thread;
		threadrunning = true;
		if (Bates[x1][x2] == 0) {
			Bates[x1][x2] = 1;
			currentthread = -1;
			threadrunning = false;
			return true;
		}
		return false;
	}

	String bestavailableSeat2()
	{
		for(int i = currenti; i <27;i++)
		{
			if(6<currentj && currentj<21)
			{
				for(int j =7; j<21; j++)
				{
					if(Bates[j][i] == 0)
					{
						currenti=i;
						currentj=(j+1);
						if(j==20)
						{
							currentj=0;
						}
						return new String(j + " "+ i);
					}
				}
			}
			else if(0<=currentj && currentj<=6){
				for (int j = 0; j < 7; j++) {
					if (Bates[j][i] == 0) {
						currenti = i;
						currentj = (j + 1);
						if (j == 6) {
							currentj = 21;
						}
						return new String(j + " "+ i);
					}
				}
			} else {
				for (int j = 21; j < 28; j++) {
					if (Bates[j][i] == 0) {
						currenti = i;
						currentj = j;
						if(j==27)
						{
							currentj=7;
							currenti= (i+1);
						}
						return new String(j + " "+ i);
					}
				}
			}
		}
		soldout = true;
		return "-1 -1";
	}

}



