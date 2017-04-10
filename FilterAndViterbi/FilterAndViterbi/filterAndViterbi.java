package FilterAndViterbi;

import java.util.ArrayList;

import Grid.grid;
import Grid.input;

public class filterAndViterbi {
		public ArrayList<grid> filteredGrid = new ArrayList<grid>();
		input inputInfo = new input();

		public filterAndViterbi(String modelType) {
			if(modelType.equals("filter")){
				this.filterGrid();
			} else if(modelType.equals("viterbi")){
				this.viterbi();
			}
		}
		public void filterGrid() {
			this.filteredGrid.add(new grid());
			for (int i = 0; i < input.actions.size(); i++) {
				this.filteredGrid.get(i).move(input.actionConverter(i)[0], input.actionConverter(i)[1]);
				this.filteredGrid.get(i).sense(input.observes.get(i));
				// need to add a copy of the grid to the next one
				this.filteredGrid.add(new grid(this.filteredGrid.get(i).getGrid().clone(), input.size, this.filteredGrid.get(i).name));
			}

			for (int i = 0; i < input.actions.size(); i++) {
				this.filteredGrid.get(i).printString();
			}
		}
		public void viterbi() {
			this.filteredGrid.add(new grid());
			for (int i = 0; i < input.actions.size(); i++) {
				int up = input.actionConverter(i)[0];
				int right = input.actionConverter(i)[1];
				this.filteredGrid.get(i).move(up,right);
				this.filteredGrid.get(i).viterbi(up, right);
				this.filteredGrid.get(i).sense(input.observes.get(i));
				// need to add a copy of the grid to the next one
				this.filteredGrid.add(new grid(this.filteredGrid.get(i).getGrid().clone(), input.size, this.filteredGrid.get(i).name));
			}

		
		}
}
