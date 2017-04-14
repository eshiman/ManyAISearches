package FilterAndViterbi;

import java.util.ArrayList;

import Grid.grid;
import Grid.input;
import GroundTruth.groundTruthGenerator;

/*
 * author: Esther Shimanovich
 * This file calls the methods required to conduct the search and stores the
 * resultant grid for each step
 */
public class filterAndViterbi {
	public ArrayList<grid> filteredGrid = new ArrayList<grid>();
	public static ArrayList<grid> threeGrids = new ArrayList<grid>();
	input inputInfo = new input();

	public filterAndViterbi(String modelType) {
		if (modelType.equals("filter")) {
			this.filterGrid();
		} else if (modelType.equals("viterbi")) {
			this.viterbi();
		}
		if (input.displayOnlyThreeGrids) {
			insertThreeGrids();
		}
	}

	public void filterGrid() {
		this.filteredGrid.add(new grid());
		if (input.generateTruth) {
			groundTruthGenerator generatePath = new groundTruthGenerator(this.filteredGrid.get(0));
		}
		for (int i = 0; i < input.actions.size(); i++) {
			int down = input.actionConverter(i)[0];
			int right = input.actionConverter(i)[1];
			this.filteredGrid.get(i).move(down, right);
			this.filteredGrid.get(i).sense(input.observes.get(i));
			// need to add a copy of the grid to the next one
			this.filteredGrid.add(
					new grid(this.filteredGrid.get(i).getGrid().clone(), input.size, this.filteredGrid.get(i).name));
		}
	}

	public void viterbi() {
		this.filteredGrid.add(new grid());
		if (input.generateTruth) {
			groundTruthGenerator generatePath = new groundTruthGenerator(this.filteredGrid.get(0));
		}
		for (int i = 0; i < input.actions.size(); i++) {
			int down = input.actionConverter(i)[0];
			int right = input.actionConverter(i)[1];
			this.filteredGrid.get(i).move(down, right);

			this.filteredGrid.get(i).viterbi(down, right);

			this.filteredGrid.get(i).sense(input.observes.get(i));
			// need to add a copy of the grid to the next one

			this.filteredGrid.add(
					new grid(this.filteredGrid.get(i).getGrid().clone(), input.size, this.filteredGrid.get(i).name));
		}

	}

	private void insertThreeGrids() {
		threeGrids.add(this.filteredGrid.get(0));
		threeGrids.add(this.filteredGrid.get(49));
		threeGrids.add(this.filteredGrid.get(99));
	}
}
