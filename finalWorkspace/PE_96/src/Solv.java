import java.io.FileNotFoundException;

public class Solv {

	public static void main(String[] args) throws FileNotFoundException {
		int start = 0;
		int sum = 0;
		int num = 0;
		Solv s = new Solv();
		ReadFile.read();
		int[][] board = new int[9][9];
		for (int i = 0; i < 50; i++) {
			board = ReadFile.readOne(start);
			s.solver(board);
			start += 9;			
			num = 100 * board[0][0] + 10 * board[0][1] + board[0][2]; // calculating the three digit number
			sum += num; // calculating the sum of the 50 3-digit numbers

		}
		System.out.println(sum);
	}

	public boolean solver(int[][] board) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] == 0) {
					for (int k = 1; k <= 9; k++) {
						board[r][c] = (0 + k);
						if (isValid(board, r, c) && solver(board)) {	// solving the puzzle recursively
							return true;
						} else {
							board[r][c] = 0;
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	public boolean isValid(int[][] board, int r, int c) {
		// check row
		boolean[] row = new boolean[9];
		for (int i = 0; i < 9; i++) {
			if (board[r][i] >= 1 && board[r][i] <= 9) {
				if (row[board[r][i] - 1] == false) {		// row[board[r][i] - 1] = true would mean repeated value and hence inValid
					row[board[r][i] - 1] = true;
				} else {
					return false;
				}
			}
		}

		// check column
		boolean[] col = new boolean[9];
		for (int i = 0; i < 9; i++) {
			if (board[i][c] >= 1 && board[i][c] <= 9) {
				if (col[board[i][c] - 1] == false) {
					col[board[i][c] - 1] = true;
				} else {
					return false;
				}
			}
		}

		// check the 3*3 grid
		boolean[] grid = new boolean[9];
		for (int i = (r / 3) * 3; i < (r / 3) * 3 + 3; i++) {
			for (int j = (c / 3) * 3; j < (c / 3) * 3 + 3; j++) {
				if (board[i][j] >= 1 && board[i][j] <= 9) {
					if (grid[board[i][j] - 1] == false) {
						grid[board[i][j] - 1] = true;
					} else {
						return false;
					}
				}
			}
		}

		return true;
	}
}