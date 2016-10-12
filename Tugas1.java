import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sat4j.specs.TimeoutException;

public class Tugas1 {
	private static Cnf cnf = new Cnf();
	private static GenerateMore generateMore = new GenerateMore();
	private static SATSolver SAT = new SATSolver();

	public static void main(String[] args) throws TimeoutException,
			NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("Masukan pilihan:\n"
					+ "1. Masukan ukuran board\n" + "2. Generate More\n"
					+ "3. Exit\n\nPilihan: ");

			int N = Integer.parseInt(bf.readLine());

			if (N == 1) {
				cnf.getCnf();
				SAT.solve();
				
				// generate GUI
				boolean isSat = ChessGUI.checkSAT();
				if (isSat) {
					ChessGUI chessGUI = new ChessGUI();
					chessGUI.createChess();
				}
			} else if (N == 2) {
				generateMore.generate();
				SAT.solve();
				
				// generate GUI
				boolean isSat = ChessGUI.checkSAT();
				if (isSat) {
					ChessGUI chessGUI = new ChessGUI();
					chessGUI.createChess();
				}
			} else {
				break;
			}
		}
	}
	
}