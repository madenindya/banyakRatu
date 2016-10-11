import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.sat4j.specs.TimeoutException;

public class Tugas1 {
	private static Cnf cnf = new Cnf();
	private static GenerateMore generateMore = new GenerateMore();
	private static SATSolver	SAT = new SATSolver();
	public static void main(String[] args) throws TimeoutException, NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			System.out.print("\nMasukan pilihan:\n"
					+ "1. Masukan ukuran board\n"
					+ "2. Generate more\n"
					+ "3. Exist\n\nPilihan: ");
			int N = Integer.parseInt(bf.readLine());
			if(N == 1){
				cnf.getCnf();
				SAT.solve();
			}else if(N == 2){
				generateMore.generate();
				SAT.solve();
			}else{
				break;
			}
		}
	}
}
