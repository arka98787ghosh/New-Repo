import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReadHands {

	static String[][] readHand = new String[1000][10];

	public static void hands() throws FileNotFoundException {
		String file = "poker.txt";
		FileReader fr = new FileReader(file);
		Scanner in = new Scanner(fr);
		StringTokenizer line;
		for(int i = 0; i<1000; i++){
			
			line = new StringTokenizer(in.nextLine());
			int j = 0;
			while (line.hasMoreTokens()) {
				// System.out.println(line.nextToken(" "));
				readHand[i][j++] = line.nextToken(" ");
			}
		}
		in.close();
		//return readHand;
	}
	
	public static String[] getOneHand(int i){
		return readHand[i];
	}
	/*
	 * public static void main(String args[]) throws FileNotFoundException{
	 * ReadHands.hands(); }
	 */
}
