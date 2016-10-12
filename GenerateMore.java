import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class GenerateMore {

	public void generate() throws IOException {
		FileReader fr = new FileReader("output.cnf");
		BufferedReader tr = new BufferedReader(fr);

		String isSat = tr.readLine();
		if (isSat.equals("SAT")) {
			String[] results = tr.readLine().split(" ");

			String newClause = "";
			for (int i = 1; i < results.length; i++) {
				if (results[i - 1].charAt(0) == '-') {
					newClause += i + " ";
				} else {
					newClause += "-" + i + " ";
				}
			}
			newClause += "0\n";

			FileReader fr2 = new FileReader("input.cnf");
			BufferedReader bf = new BufferedReader(fr2);
			String result = "";
			String line = bf.readLine();
			while (line != null && line.length() > 0) {
				if (line.charAt(0) == 'p') {
					int idx = line.lastIndexOf(" ");
					int countClause = Integer.parseInt(line.substring(idx + 1));
					countClause++;
					line = line.substring(0, idx) + " " + countClause;
				}
				result += line + "\n";
				line = bf.readLine();
			}
			result += newClause;
			bf.close();

			Writer bw = new BufferedWriter(new FileWriter("input.cnf"));
			bw.write(result);
			;
			bw.close();

			System.out.println("New Clause added to input.cnf");
		} else {
			System.out.println("UNSAT");
		}

		tr.close();

	}
}
