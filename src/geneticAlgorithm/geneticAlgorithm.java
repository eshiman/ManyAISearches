package geneticAlgorithm;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class geneticAlgorithm {
	private cnfReader cnf;
	private popMember solutionMember;
	private int numberOfFlips = 0;
	private boolean isSuccess = false;

	public cnfReader getCnf() {
		return cnf;
	}

	public popMember getSolutionMember() {
		return solutionMember;
	}

	public int getNumberOfFlips() {
		return numberOfFlips;
	}

	public long getRunTime() {
		return runTime;
	}

	private long runTime = 0;

	// Performs Genetic Algorithm
	public geneticAlgorithm(cnfReader cnf) {
		this.runTime = System.currentTimeMillis();
		this.cnf = cnf;
		// Set up your initial population and label the first two as the top two
		popMember[] Population = createPopulation();
		popMember[] topTwo = new popMember[] { Population[0], Population[1] };
		Population[0].inTopTwo = true;
		Population[1].inTopTwo = true;

		// Calculate Evaluation Functions of initial population
		for (popMember member : Population) {
			member.evalFunct = evalFunction(member.assigns, cnf);
			if (member.evalFunct == -1) {
				this.runTime = System.currentTimeMillis() - this.runTime;
				System.out.println("success!");
				this.isSuccess = true;
				return;
			}
		}
		while (System.currentTimeMillis() - this.runTime < 500) {
			// Find the elite two members of the population
			for (popMember member : Population) {
				topTwoTest(member, topTwo);
			}
			// Fitness Function calculation and random selection and crossover
		
			Population = AssignProbs(Population);

			crossover(Population);

			// Mutation and flipping
			for (int i = 0;i< Population.length;i++) {
				if (!Population[i].inTopTwo) {
					Population[i] = mutation(Population[i]);
					Population[i] = flip(Population[i]);

				}
				if (this.solutionMember != null) {
					this.runTime = System.currentTimeMillis() - this.runTime;
					System.out.println("success!");
					this.isSuccess = true;
					return;
				}
			}
		}
		System.out.println("failed");
		this.runTime = System.currentTimeMillis() - this.runTime;
		return;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	private popMember flip(popMember member) {
		popMember oldMember = new popMember(member);
		Integer[] RandomVals = new Integer[member.assigns.length];
		for (int i = 0; i < RandomVals.length; i++) {
			RandomVals[i] = i;
		}
		member = flipBits(member, RandomVals);
		while (member.evalFunct > oldMember.evalFunct) {
			oldMember = new popMember(member);
			member = flipBits(member, RandomVals);
			// if we find a solution from flipping bits
			if (this.solutionMember != null) {
				return this.solutionMember;
			}
			//System.out.println("oldmember is " + oldMember.evalFunct + " and member is " + member.evalFunct);
			//System.out.println(evalFunction(oldMember.assigns, cnf)  + " " + evalFunction(member.assigns, cnf));
		}
		return oldMember;
	}

	private popMember flipBits(popMember member, Integer[] randomVals) {
		// Shuffling random array
		for (int shuffleVal : randomVals) {
			int temp = shuffleVal;
			int randomInt = randomNumberGenerator(0, randomVals.length);
			randomVals[shuffleVal] = randomInt;
			randomVals[randomInt] = temp;
		}
		// Here, we flip the values in random order
		for (int randomIter : randomVals) {
			this.numberOfFlips++;
			popMember oldMember = new popMember(member);
			member.assigns[randomIter] = !member.assigns[randomIter];
			member.evalFunct = evalFunction(member.assigns, cnf);
			if (member.evalFunct == -1) {
				return this.solutionMember = member;
			}
			if (member.evalFunct < oldMember.evalFunct) {
				member = oldMember;
			}
		}
		return member;
	}

	private int randomNumberGenerator(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max - min) + min;

	}

	private popMember mutation(popMember member) {
		if (randomNumberGenerator(0, 10) < 9) {
			for (int i = 0; i < member.assigns.length; i++) {
				if (randomNumberGenerator(0, 2) == 1) {
					this.numberOfFlips++;
					member.assigns[i] = !member.assigns[i];
				}
			}
			member.evalFunct = evalFunction(member.assigns, cnf);
		}
		return member;
	}

	private popMember[] crossover(popMember[] Population) {
		popMember[] newPopulation = new popMember[10];
		Stack<Integer> Eight = new Stack<Integer>();
		for (int i = 0; i < 8; i++) {
			newPopulation[i] = new popMember(Population[i]);
			if (!Population[i].inTopTwo) {
				Eight.push(i);
			}
			for (int j = 0; j < Population[0].assigns.length; j++) {
				Stack<Integer> CopyEight = (Stack<Integer>) Eight.clone();
				while (!CopyEight.isEmpty()) {
					int k1 = CopyEight.pop();
					int k2 = 0;
					if (CopyEight.size() != 0) {
						k2 = CopyEight.peek();
					} else {
						k2 = Eight.peek();
					}
					if (randomNumberGenerator(0, 2) == 1) {
						newPopulation[k1].assigns[j] = Population[k2].assigns[j];
					}
				}
			}

		}
		return newPopulation;
	}

	// Calculates fitness function and performs random selection
	private popMember[] AssignProbs(popMember[] population) {
		float totalFitness = 0;
		for (popMember member : population) {
			totalFitness = totalFitness + member.evalFunct;
		}
		for (popMember member : population) {
			member.probability = member.evalFunct / totalFitness;
		}
		popMember[] newPopulation = population.clone();
		for (int i = 0; i < 10; i++) {
			newPopulation[i] = chooseMember(population);
		}
		return newPopulation;
	}

	private popMember chooseMember(popMember[] population) {
		int randomNo = randomNumberGenerator(0, 1001);
		float probSum = 0;
		for (int i = 0; i < population.length; i++) {
			probSum = probSum + population[i].probability;
			if (randomNo < probSum * 1000) {
				return population[i];
			}
		}
		System.out.println("Probability error " + probSum);
		return population[population.length-1];
	}

	private void topTwoTest(popMember member, popMember[] topTwo) {
		member.inTopTwo = true;
		if (member.evalFunct > topTwo[0].evalFunct && member.evalFunct <= topTwo[1].evalFunct) {
			topTwo[0].inTopTwo = false;
			topTwo[0] = member;
		} else if (member.evalFunct > topTwo[1].evalFunct && member.evalFunct <= topTwo[0].evalFunct) {
			topTwo[1].inTopTwo = false;
			topTwo[1] = member;
		} else if (member.evalFunct > topTwo[0].evalFunct && member.evalFunct > topTwo[1].evalFunct) {
			if (topTwo[0].evalFunct < topTwo[1].evalFunct) {
				topTwo[0].inTopTwo = false;
				topTwo[0] = member;
			} else {
				topTwo[1].inTopTwo = false;
				topTwo[1] = member;
			}
		} else {
			member.inTopTwo = false;
		}

	}

	private int evalFunction(boolean[] assigns, cnfReader cnf) {
		int eVal = 0;
		int bit = 0;
		for (Set<Integer> clause : cnf.getCNF()) {
			bit = 0;
			for (Integer variable : clause) {
				if (variable > 0 && assigns[Math.abs(variable)] == true) {
					bit = 1;
				}
				if (variable < 0 && assigns[Math.abs(variable)] == false) {
					bit = 1;
				}
			}
			eVal = eVal + bit;
		}

		if (eVal >= cnf.getNClauses()-6) {
			return -1;
		}
		return eVal;
	}

	private popMember[] createPopulation() {
		popMember[] Population = new popMember[10];
		for (int i = 0; i < 10; i++) {
			Population[i] = new popMember(cnf);
			for (int j = 0; j < this.cnf.getNVariables(); j++) {
				Population[i].assigns[j] = randomNumberGenerator(0, 2) == 1;
			}
		}
		return Population;
	}

}

class popMember {
	boolean[] assigns;
	int evalFunct = 0;
	float probability = 0;
	boolean inTopTwo = false;

	public popMember(popMember Copied) {
		this.assigns = Copied.assigns.clone();
		this.evalFunct = Copied.evalFunct;
		this.probability = Copied.probability;
		this.inTopTwo = Copied.inTopTwo;
	}

	public popMember(cnfReader cnf) {
		this.assigns = new boolean[cnf.getNVariables() + 1];
		this.evalFunct = 0;
		this.probability = 0;
		this.inTopTwo = false;
	}

	public void printAssigns() {
		for (int i = 0; i < this.assigns.length; i++) {
			if (this.assigns[i]) {
				System.out.print("1");
			} else {
				System.out.print("0");
			}
		}

		System.out.println("");
	}
}
