/*Chan-Young Kim, Seungjun Han
 * EE422C
 * ck23586, sh36992
 * Group # 34
 */

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
	int currenti = 0;
	boolean output = false;
	int currentj = 7;

	boolean purchaseSeat(int x1, int x2, int thread) {		//purchase the seat, but if a certain thread failed to buy a seat, only allow that thread to try to purchase it again
		while (threadrunning && currentthread != thread) {
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

	String bestavailableSeat() {					//find and return the best available seat, if there are no seats left, return -1 -1
		for (int i = currenti; i < 27; i++) {		//start from row A 
			if (6 < currentj && currentj < 21) {	//check the center column
				for (int j = 7; j < 21; j++) {
					if (Bates[j][i] == 0) {
						currenti = i;
						currentj = (j + 1);
						if (j == 20) {
							currentj = 0;
						}
						return new String(j + " " + i);
					}
				}
			} else if (0 <= currentj && currentj <= 6) {//check the left column
				for (int j = 0; j < 7; j++) {
					if (Bates[j][i] == 0) {
						currenti = i;
						currentj = (j + 1);
						if (j == 6) {
							currentj = 21;
						}
						return new String(j + " " + i);
					}
				}
			} else {
				for (int j = 21; j < 28; j++) {			//check the right column
					if (Bates[j][i] == 0) {
						currenti = i;
						currentj = j;
						if (j == 27) {
							currentj = 7;
							currenti = (i + 1);
						}
						return new String(j + " " + i);
					}
				}
			}
		}
		soldout = true;
		return "-1 -1";
	}

}
