package geneticAlgorithm;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class cnfReader {
	private String title = "";
	private String type;
	private int numVariables;
	private int numClauses;
	private Set<Integer>[] cnf;
	private Set<Integer>[] trueClauseSet;
	private Set<Integer>[] falseClauseSet;

	cnfReader(File cnfFile) throws IOException {

		FileReader in = new FileReader(cnfFile);
		BufferedReader bufferedReader = new BufferedReader(in);

		String[] res;
		String input = bufferedReader.readLine();
		while (input.startsWith("c")) {
			title += input + "\n";
			input = bufferedReader.readLine();
		}
		if (input.matches("^p\\s+\\w+\\s+\\d+\\s+\\d+\\s*$")) {
			res = input.split("\\s+");
			assert res.length == 4;
			type = res[1];
			numVariables = Integer.parseInt(res[2]);
			numClauses = Integer.parseInt(res[3]);
		}

		trueClauseSet = new HashSet[numVariables + 1];
		falseClauseSet = new HashSet[numVariables + 1];
		for (int v = 1; v <= numVariables; v++) {
			trueClauseSet[v] = new HashSet<Integer>();
			falseClauseSet[v] = new HashSet<Integer>();
		}

		cnf = new HashSet[numClauses];
		int clauseIndex = 0;
		while ((input = bufferedReader.readLine()) != null && input.length() != 1) {
			res = input.split("\\s+");
			Set<Integer> clause = new HashSet<Integer>();
			assert res[res.length - 1].equals("0") : clauseIndex;
			for (int i = 0; i < res.length - 1; ++i) {
				int literal = 0;
				try {
					literal = Integer.parseInt(res[i]);
				} catch (NumberFormatException e) {
					i++;
					literal = Integer.parseInt(res[i]);
				}
				clause.add(literal);
				if (literal > 0) {
					trueClauseSet[literal].add(clauseIndex);
				} else {
					falseClauseSet[-literal].add(clauseIndex);
				}
			}
			assert !clause.isEmpty();
			cnf[clauseIndex] = clause;
			// Esther change
			clauseIndex++;
			assert clauseIndex <= numClauses;
		}
		// some SATLIB files state the # of clauses incorreclty
		numClauses = clauseIndex;
		System.gc();

	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public Set<Integer>[] getCNF() {
		return cnf;
	}

	public int getNVariables() {
		return numVariables;
	}

	public int getNClauses() {
		return numClauses;
	}

	public Set<Integer>[] getTrueClauseSet() {
		return trueClauseSet;
	}

	public Set<Integer>[] getFalseClauseSet() {
		return falseClauseSet;
	}

	public String toString() {
		StringBuffer sat = new StringBuffer();
		sat.append(getTitle() + "\n");
		sat.append("p " + getType() + " " + getNVariables() + " " + getNClauses() + "\n");
		int clauseNumber = 0;
		for (int clauseIndex = 0; clauseIndex < numClauses; clauseIndex++) {
			System.out.println("clauseNumber: " + clauseNumber);
			sat.append(clauseNumber + ":");
			for (Integer literal : cnf[clauseIndex]) {
				sat.append(" " + literal);
			}
			sat.append(" 0 \n");
			clauseNumber++;
		}
		return new String(sat);
	}
}
