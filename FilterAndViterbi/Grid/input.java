package Grid;

import java.util.ArrayList;

/*
 * This will create an object containing the input 
 * information and the preferred output style
 */
public class input {
	// Number of rows or columns, graph creation
	public static int size = 3;
	public static String[] attributes = { "H", "H", "T", "N", "N", "N", "N", "B", "H" };
	public static int noBlocked = 1;
	// Actions and Observations Performed
	private static String[] manualActions = { "R", "R", "D", "D" };
	public static ArrayList<String> actions = null;
	private static String[] manualObserves = { "N", "N", "H", "H" };
	public static ArrayList<String> observes = null;
	
	// For print out to file
	public static Integer[] initialPoint = null;
	public static ArrayList<Integer[]> consecPoints= null;
	public static boolean displayOnlyThreeGrids = false;
	public static boolean heatMap = false;
	public static gridSquare highestProbSquare;
	public static boolean generateTruth = false;
	public static int noActions = 100;
	public static boolean activateTenMostLikely = false;
	public static boolean saveSequences = true;
	//Constructor 
	public input() {
		actions = new ArrayList<String>();
		observes = new ArrayList<String>();
		for (int i = 0; i < manualActions.length; i++) {
			actions.add(manualActions[i]);
		}
		for (int i = 0; i < manualObserves.length; i++) {
			observes.add(manualObserves[i]);
		}
		mapGenerator map = new mapGenerator();
	}

	public static Integer[] actionConverter(int i) {
		Integer[] actionPair = new Integer[] { 0, 0 };
		if (actions.get(i).equals("U")) {
			actionPair[0] = -1;

		} else if (actions.get(i).equals("D")) {
			actionPair[0] = 1;

		} else if (actions.get(i).equals("L")) {
			actionPair[1] = -1;
		} else if (actions.get(i).equals("R")) {
			actionPair[1] = 1;
		}
		return actionPair;
	}
}
