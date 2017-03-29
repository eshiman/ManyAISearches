package geneticAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class manyCNFs {
	ArrayList<plotPoint> Output = new ArrayList<plotPoint>();

	public manyCNFs() {
		File dir = new File("cnfFiles");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				File[] directoryListing2 = child.listFiles();
				if (directoryListing2 != null) {
					int iterator = 0;
					for (File child2 : directoryListing2) {
						if(iterator<100){
							iterator++;
						try {
							geneticAlgorithm g = new geneticAlgorithm(new cnfReader(child2));
							Output.add(new plotPoint(g.getCnf().getNVariables(), g.getRunTime(), g.getNumberOfFlips(),
									g.isSuccess()));
						} catch (IOException e) {
							e.printStackTrace();
						}
						}
					}
				}
			}
		}

	}

	public void printOutput(){
		System.out.println("number of clauses");
		for(plotPoint P : Output){
			System.out.print(P.NClauses + ", ");
		}
		System.out.println("");
		System.out.println("time elapsed");
		for(plotPoint P : Output){
			System.out.print(P.timeElapsed + ", ");
		}
		System.out.println("");
		System.out.println("noFlips");
		for(plotPoint P : Output){
			System.out.print(P.noFlips + ", ");
		}
		System.out.println("");

		System.out.println("isSuccess");
		int noSuccesses = 0;
		for(plotPoint P : Output){
			if(P.isSuccess){
				noSuccesses++;
			}
			System.out.print(noSuccesses + ", ");
		}
		System.out.println("");
	}
}

class plotPoint {
	public plotPoint(int nClauses, long timeElapsed, int noFlips, boolean isSuccess) {
		super();
		NClauses = nClauses;
		this.timeElapsed = timeElapsed;
		this.noFlips = noFlips;
		this.isSuccess = isSuccess;
	}

	int NClauses;
	long timeElapsed;
	int noFlips;
	boolean isSuccess;
}