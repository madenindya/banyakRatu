import java.io.IOException;
import java.io.PrintWriter;

public class Cnf {

	private int N;
	public int jumlahClause;

	public void getCnf(int x) throws NumberFormatException, IOException {

		N = x;
		jumlahClause = N + (N * N * (N - 1));

		String result = "";
		result += rowAssignment(N);
		result += columnAssignment(N);
		result += diagonalKanan(N);
		result += diagonalKiri(N);

		PrintWriter pw = new PrintWriter("input.cnf", "UTF-8");
		pw.println("c CNF untuk " + N + "-Queens");
		pw.println("p cnf " + (N * N) + " " + jumlahClause);
		pw.print(result);
		pw.close();
		System.out.println("CNF disimpan dalam file: input.cnf");
	}

	private String diagonalKanan(int n) {
		String result = "";
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				int now = getIndex(i, j);
				int tmp = i > j ? i : j;
				tmp--;
				for (int k = 1; k < n - tmp; k++) {
					int now2 = getIndex(i + k, j + k);
					jumlahClause++;
					result += "-" + now + " -" + now2 + " 0\n";
				}
			}
		}

		return result;
	}

	private String diagonalKiri(int n) {
		String result = "";
		for (int i = 1; i < n; i++) {
			for (int j = n; j > 1; j--) {
				int now = getIndex(i, j);
				int j2 = Math.abs(n - j + 1);
				int tmp = i > j2 ? i : j2;
				for (int k = 1; k <= n - tmp; k++) {
					int now2 = getIndex(i + k, j - k);
					jumlahClause++;
					result += "-" + now + " -" + now2 + " 0\n";
				}
			}
		}

		return result;
	}

	private String rowAssignment(int n) {
		// Row Assignment
		String sub1 = "";
		String sub2 = "";

		// Assignment value
		for (int j = 1; j <= n; j++) {
			for (int i = 1; i <= n; i++) {
				if (i == n) {
					sub1 += getIndex(j, i) + " 0\n";
				} else {
					sub1 += getIndex(j, i) + " ";
				}
			}
		}

		// column restriction
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j < n; j++) {
				for (int k = j + 1; k <= n; k++) {
					sub2 += -getIndex(i, j) + " " + -getIndex(i, k) + " 0\n";
				}
			}
		}

		return sub1 + sub2;
	}

	private String columnAssignment(int n) {
		// combination
		String sub2 = "";
		for (int col = 1; col <= n; col++) {
			for (int pivotRow = 1; pivotRow <= n - 1; pivotRow++) {
				for (int combination = pivotRow + 1; combination <= n; combination++) {
					sub2 += "-" + getIndex(pivotRow, col) + " -"
							+ getIndex(combination, col) + " 0\n";
				}
			}
		}

		return sub2;
	}

	private int getIndex(int row, int column) {
		return (N * (row - 1)) + column;
	}
}
