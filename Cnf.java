import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cnf {

	public static int N;
	public static int N2;

	public static void main(String[] args) throws NumberFormatException,
			IOException {

		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(bf.readLine());
		N2 = N * N;

		System.out.println("Diagonal kanan:");
		diagonalKanan(N);
		System.out.println("Diagonal kiri:");
		diagonalKiri(N);
	}

	public static void diagonalKanan(int n) {
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				int now = getIndex(i, j);
				int tmp = i > j ? i : j;
				tmp--;
				for (int k = 1; k < n - tmp; k++) {
					int now2 = getIndex(i + k, j + k);
					System.out.println("-" + now + " -" + now2);
				}
			}
		}
	}

	public static void diagonalKiri(int n) {
		for (int i = 1; i < n; i++) {
			for (int j = n; j > 1; j--) {
				int now = getIndex(i, j);
				int j2 = Math.abs(n - j + 1);
				int tmp = i > j2 ? i : j2;
				for (int k = 1; k <= n - tmp; k++) {
					int now2 = getIndex(i + k, j - k);
					System.out.println("-" + now + " -" + now2);
				}
			}
		}
	}

	public static int getIndex(int row, int column) {
		return (N * (row - 1)) + column;
	}
}
