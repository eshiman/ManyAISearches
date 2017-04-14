package App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import FilterAndViterbi.filterAndViterbi;
import Grid.grid;
import Grid.input;

public class ReadAndWrite {

	public static void writeGridToFile(grid grid /* CoordinatePair coords, */) {

		// System.out.println("Writer called.");
		// System.out.println("Filename is "+fileName);

		try {
			File file = new File("C:/Users/Esther/Documents/GitHub/ManyAISearches/FilterAndViterbi/file1.txt");

			PrintWriter writer = new PrintWriter(file, "UTF-8");

			// Get coordinates of start and goal squares, build strings, and
			// write to file
			Integer[] initialPoint = input.initialPoint;
			String initPointStr = initialPoint[0] + ", " + initialPoint[1] + ", ";
			writer.println(initPointStr);
			for (int i = 0; i < input.consecPoints.size(); i++) {
				Integer[] consecPoint = input.consecPoints.get(i);
				String consecPointStr = "" + consecPoint[0] + ", " + consecPoint[1] + ", ";
				writer.print(consecPointStr);
			}
			writer.println();
			writer.println(input.actions.toString());
			writer.println(input.observes.toString());
			for (int i = 0; i < input.attributes.length; i++) {
				String attribute = input.attributes[i];
				writer.print(attribute + ", ");
			}

			writer.println();

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static grid readGridFromFile(String fileName) {
		grid grid = new grid(fileName);
		try {

			// get Start and End Coordinates
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			line = br.readLine();
			String[] read = line.split(", ");
			int startx = Integer.parseInt(read[0]);
			int starty = Integer.parseInt(read[1]);
			input.initialPoint = new Integer[] { startx, starty };
			// Coordinates newC = new Coordinates(startx, starty);
			// Coordinates newCE = new Coordinates(endx, endy);
			// CoordinatePair newCP = new CoordinatePair(newC, newCE);
			// newCP.parent = grid;
			// grid.pathPoints.add(newCP);
			// Centers of hard to traverse regions
			line = br.readLine();
			read = line.split(", ");
			input.consecPoints = new ArrayList<Integer[]>();
			for (int i = 0; i < read.length; i = i + 2) {
				int nextx = Integer.parseInt(read[i]);
				int nexty = Integer.parseInt(read[i + 1]);
				input.consecPoints.add(new Integer[] { nextx, nexty });
			}
			String actionString = br.readLine();
			String[] Actions = actionString.substring(1, actionString.length() - 1).split(", ");
			for (int i = 0; i < Actions.length; i++) {
				input.actions.add(Actions[i]);
			}
			String observesString = br.readLine();
			String[] Observes = observesString.substring(1, observesString.length() - 1).split(", ");
			for (int i = 0; i < Observes.length; i++) {
				input.observes.add(Observes[i]);
			}

			String attributeString = br.readLine();
			input.attributes = attributeString.split(", ");
			input.generateTruth = false;
			Main.filteredGridList = new filterAndViterbi("viterbi");
			br.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		}

		return grid;

	}

}
