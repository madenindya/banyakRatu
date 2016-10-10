import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.Writer;

public class GenerateMore {

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("output.cnf");
        BufferedReader tr = new BufferedReader(fr);

        String isSat = tr.readLine();
        if (isSat.equals("SAT")) {
            String[] results = tr.readLine().split(" ");

            String newClause = "";
            for (int i = 1; i < results.length; i++) {
                if (results[i-1].charAt(0) == '-') {
                    newClause += i + " ";
                } else {
                    newClause += "-" + i + " ";
                }
            }
            newClause += "0\n";
            Writer bw = new BufferedWriter(new FileWriter("input.cnf", true));
            bw.append(newClause);
            bw.close();
    
            System.out.println("New Clause added to input.cnf");
        } else {
            System.out.println("UNSAT")
        }
    }
}
