import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;
	
// used to read the puzzles from the text file

public class ReadFile {
	static int[][] puzzle = new int[450][9]; // stores all the puzzles
	static int count = 0;
	public static void read() throws FileNotFoundException {
		String file = "sudoku.txt";
		FileReader fr = new FileReader(file);
		Scanner in = new Scanner(fr);
		StringTokenizer line;
		for (int i = 0; i < 500; i++) {
			if (i % 10 == 0) {
				line = new StringTokenizer(in.nextLine()); // used to skip the lines Grid x
			} else {
				line = new StringTokenizer(in.nextLine());
				String numLine = line.nextToken();
				// System.out.println(numLine);
				for (int j = 0; j < 9; j++) {						// initialze the total array
					puzzle[count][j] = numLine.charAt(j)- 48;
				}
				count++;
			}
		}
	}

	// send one array at a time
	
	public static int[][] readOne(int start) throws FileNotFoundException {
		int[][] onePuzzle = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				onePuzzle[i][j] = puzzle[start + i][j];
			}
		}
		return onePuzzle;
	}
	/*
	public static void main(String args[]) throws FileNotFoundException {
		ReadFile.read();
		for (int i = 0; i < 450; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(puzzle[i][j] + " ");
			}
			System.out.println();
		}
	}
	*/
}
