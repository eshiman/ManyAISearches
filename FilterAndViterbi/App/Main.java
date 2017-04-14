package App;
import java.util.HashMap;

import Data.hundredExperiments;
import FilterAndViterbi.filterAndViterbi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	

	public static filterAndViterbi filteredGridList;

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

		//hundredExperiments generateData = new hundredExperiments("filter");
		filteredGridList = new filterAndViterbi("viterbi");
		launch(args);
	}

}