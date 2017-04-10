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
	private static String[] manualActions = { "Right", "Right", "Down", "Down" };
	public static ArrayList<String> actions = new ArrayList<String>();
	private static String[] manualObserves = { "N", "N", "H", "H" };
	public static ArrayList<String> observes = new ArrayList<String>();

	public input() {
		for (int i = 0; i < manualActions.length; i++) {
			actions.add(manualActions[i]);
		}
		for (int i = 0; i < manualObserves.length; i++) {
			observes.add(manualObserves[i]);
		}
	}

	public static Integer[] actionConverter(int i) {
		Integer[] actionPair = new Integer[] { 0, 0 };
		if (actions.get(i).equals("Up")) {
			actionPair[0] = 1;

		} else if (actions.get(i).equals("Down")) {
			actionPair[0] = -1;

		} else if (actions.get(i).equals("Left")) {
			actionPair[1] = -1;
		} else if (actions.get(i).equals("Right")) {
			actionPair[1] = 1;
		}
		return actionPair;
	}
}
