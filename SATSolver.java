import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

public class SATSolver {

	@SuppressWarnings("deprecation")
	public void solve() throws org.sat4j.specs.TimeoutException,
			FileNotFoundException, UnsupportedEncodingException {
		ISolver solver = SolverFactory.newDefault();
		solver.setTimeout(3600); // 1 hour timeout
		Reader reader = new DimacsReader(solver);

		// printer
		PrintWriter pw = new PrintWriter("output.cnf", "UTF-8");

		try {
			IProblem problem = reader.parseInstance("input.cnf");
			if (problem.isSatisfiable()) {
				System.out.println("Satisfiable !");
				String output = reader.decode(problem.model());
				System.out.println(output);
				pw.write("SAT\n" + output + "\n");

			} else {
				System.out.println("Unsatisfiable !");
				pw.write("UNSAT\n");

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found!");
		} catch (ParseFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Parse error !");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO Exception !");
		} catch (ContradictionException e) {
			System.out.println("Unsatisfiable (trivial)!");
		}

		pw.close();
	}
}