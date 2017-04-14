package GroundTruth;

import java.util.ArrayList;
import java.util.Random;

import Grid.grid;
import Grid.gridSquare;
import Grid.input;

/*
 * author: Esther Shimanovich
 * creates a sequence of actions and sensor readings
 */
public class groundTruthGenerator {

	public groundTruthGenerator(grid GridClass) {
		// generates list of 100 actions
		generateActionSequence(input.noActions);
		int visitor = 0;
		gridSquare currentSquare;
		input.observes = new ArrayList<String>();
		int x;
		int y;
		do {
			x = randomNumberGenerator(0, input.size);
			y = randomNumberGenerator(0, input.size);
			currentSquare = GridClass.getGrid()[x][y];
		} while (currentSquare.attribute.equals("B"));
		// we got initial square
		currentSquare.visited++;
		input.initialPoint = new Integer[] { x, y };
		input.consecPoints = new ArrayList<Integer[]>();
		input.consecPoints.add(input.initialPoint);
		// input.actions
		for (int i = 0; i < input.actions.size(); i++) {
			Integer[] downRight = input.actionConverter(i);
			if (percentChance(90)) {

				// check if border or black
				if (!GridClass.boundaryOrBlocked(x, y, downRight[0], downRight[1])) {
					x = x + downRight[0];
					y = y + downRight[1];
					currentSquare = GridClass.getGrid()[x][y];
				}
			}
			if (percentChance(90)) {
				input.observes.add(currentSquare.attribute);
			} else if (percentChance(50)) {
				input.observes.add(otherTwoAttributes(currentSquare.attribute)[0]);
			} else {
				input.observes.add(otherTwoAttributes(currentSquare.attribute)[1]);
			}
			input.consecPoints.add(new Integer[] { currentSquare.coordX, currentSquare.coordY });
			if (currentSquare.visited == -1) {
				currentSquare.visited = visitor;
			}
			visitor++;
		}
		// label the last one visited
		currentSquare.visited = -1;
	}

	private int randomNumberGenerator(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max - min) + min;
	}

	private boolean percentChance(int percent) {
		if (randomNumberGenerator(0, 101) < percent) {
			return true;
		}
		return false;
	}

	private String[] otherTwoAttributes(String thisAttribute) {
		if (thisAttribute.equals("N")) {
			return new String[] { "T", "H" };
		} else if (thisAttribute.equals("T")) {
			return new String[] { "N", "H" };
		}
		return new String[] { "N", "T" };
	}

	private String randomAction() {
		int x = randomNumberGenerator(0, 3);
		if (x == 0) {
			return "U";
		} else if (x == 1) {
			return "D";
		} else if (x == 2) {
			return "L";
		}
		return "R";
	}

	private void generateActionSequence(int x) {
		input.actions = new ArrayList<String>();
		for (int i = 0; i < x; i++) {
			input.actions.add(randomAction());
		}
	}
}
