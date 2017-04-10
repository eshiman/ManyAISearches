package App;
import java.util.HashMap;

import FilterAndViterbi.filterAndViterbi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	// searchVal can be 1, 2 or 3
	public static int searchVal = 1;
	// search 1: specifies heuristic type, search 2 or 3: number of heuristics
	public static int heuristicVal = 5;

	public static filterAndViterbi filteredGridList;
	//public static HashMap<String, Double> whiteCosts = new HashMap<String, Double>();
	//public static HashMap<String, Double> lightgrayCosts = new HashMap<String, Double>();

	@Override
	public void start(Stage primaryStage) {

		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource("/App/mainScreen.fxml"));
			AnchorPane root = loader.load();

			Scene scene = new Scene(root, 1240, 624);
			root.prefHeightProperty().bind(scene.heightProperty());
			root.prefWidthProperty().bind(scene.widthProperty());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Map Generator v0.1.1");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		
			//filteredGridList = new filter();
			filteredGridList = new filterAndViterbi("viterbi");
			//filteredGridList.filteredGrid.get(0).printString();

		launch(args);
	}

}