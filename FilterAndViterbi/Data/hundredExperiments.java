package Data;

import java.util.ArrayList;

import FilterAndViterbi.filterAndViterbi;
import Grid.grid;
import Grid.input;

/*
 * 
 * author: Esther Shimanovich
 * This class runs either the filter or viterbi algorithm 100 times and then 
 * generates data of average distance error over 100 experiments at
 * each step
 * also generates data of probability of estimation being right
 */
public class hundredExperiments {
	Double[] errorSum = new Double[100];
	Double[] successProbability = new Double[100];

	public hundredExperiments(String searchType) {
		//For each experiment
		java.util.Arrays.fill(errorSum, 0.0);
		java.util.Arrays.fill(successProbability, 0.0);
		filterAndViterbi runProgram;
		for (int n = 0; n < 100; n++) {
			runProgram = new filterAndViterbi(searchType);
			ArrayList<grid> listOfGrids = runProgram.filteredGrid;

			//For each step in the experiment
			for (int i = 0; i < input.actions.size(); i++) {
				// compute distance formula between actual and calculated
				double estimatedX = listOfGrids.get(i).highestProbSquare.coordX;
				double estimatedY = listOfGrids.get(i).highestProbSquare.coordY;
				double actualX = input.consecPoints.get(i)[0];
				double actualY = input.consecPoints.get(i)[1];
				// distance formula
				// sqrt((x-x1)^2 + (y-y1)^2)
				double distance = Math.sqrt(Math.pow((estimatedX - actualX), 2) + Math.pow((estimatedX - actualX), 2));
				errorSum[i] += distance;
				if(distance==0){
					this.successProbability[i]++;
				}
			}
		}
		System.out.println("Error average for each step");
		for (int i = 0; i < input.actions.size(); i++) {
			errorSum[i] = errorSum[i]/100;
			System.out.print(errorSum[i]+", ");
		}
		System.out.println();
		System.out.println("Probability successful prediction at each step (use only for filter)");
		for (int i = 0; i < input.actions.size(); i++) {
			successProbability[i] = successProbability[i]/100;
			System.out.print(successProbability[i]+", ");
		}
		System.out.println();
	}

}
