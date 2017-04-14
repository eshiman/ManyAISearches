package Controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import App.Main;
import App.ReadAndWrite;
import FilterAndViterbi.filterAndViterbi;
import Grid.GridRectangle;
import Grid.grid;
import Grid.gridSquare;
import Grid.gridTextRectangle;
import Grid.input;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewController implements Initializable {

	@FXML
	Button saveMapButton;
	@FXML
	Button loadMapButton;
	@FXML
	Button quitButton;
	@FXML
	Button toggleMostLikely;
	@FXML
	ListView<String> mapList;
	@FXML
	AnchorPane gridpaneHolder;
	@FXML
	TextArea infoText;
	@FXML
	Parent root;
	static boolean tenMostActivated = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// This is the internal List of grids

		// The name of each math is stored here, used to be clist
		ArrayList<String> nameList = new ArrayList<String>();
		for (int i = 0; i < input.actions.size(); i++) {
			nameList.add(input.actions.get(i) + " " + input.observes.get(i));
		}
		ObservableList<String> oblist = FXCollections.observableArrayList(nameList);

		if (input.displayOnlyThreeGrids) {
			nameList = new ArrayList<String>();
			nameList.add("0");
			nameList.add("50");
			nameList.add("100");
			oblist = FXCollections.observableArrayList(nameList);

		}

		mapList.setItems(oblist);

		saveMapButton.setOnAction(this::handleButtonAction);
		loadMapButton.setOnAction(this::handleButtonAction);
		quitButton.setOnAction(this::handleButtonAction);
		toggleMostLikely.setOnAction(this::handleButtonAction);

		// String selectedItem = mapList.getSelectionModel().getSelectedItem();
		mapList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				infoText.clear();
				gridpaneHolder.getChildren().clear();
				// selected item used to be cord
				int ind = mapList.getSelectionModel().getSelectedIndex() % 10;
				grid grid = Main.filteredGridList.filteredGrid.get(ind);
				if (input.displayOnlyThreeGrids) {
					grid = filterAndViterbi.threeGrids.get(ind);
				}
				GridPane newGridDisplay = genGridPane(grid, infoText, ind);
				gridpaneHolder.getChildren().addAll(newGridDisplay);
			}

		});
	}

	@FXML

	private void handleButtonAction(ActionEvent e) {

		// System.out.println("Button pushed!");
		Button b = (Button) e.getSource();
		if (b == toggleMostLikely) {
			tenMostActivated = !tenMostActivated;
			infoText.clear();
			gridpaneHolder.getChildren().clear();
			// selected item used to be cord
			int ind = mapList.getSelectionModel().getSelectedIndex() % 10;
			grid grid = Main.filteredGridList.filteredGrid.get(ind);
			if (input.displayOnlyThreeGrids) {
				grid = filterAndViterbi.threeGrids.get(ind);
			}
			GridPane newGridDisplay = genGridPane(grid, infoText, ind);
			gridpaneHolder.getChildren().addAll(newGridDisplay);
		}
		if (b == saveMapButton) {
			int ind = Main.filteredGridList.filteredGrid.size() - 1;
			ReadAndWrite.writeGridToFile(Main.filteredGridList.filteredGrid.get(ind));
		}

		if (b == loadMapButton) {
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Map File");
			File file = fileChooser.showOpenDialog(stage);
			grid newGrid = ReadAndWrite.readGridFromFile(file.getAbsolutePath());

			Main.filteredGridList.filteredGrid.add(newGrid);
		}

		if (b == quitButton) {

			Stage stage = (Stage) quitButton.getScene().getWindow();
			stage.close();
			System.exit(1);

		}
	}

	private static GridPane genGridPane(grid Grid, /* CoordinatePair pair, */ TextArea infoText, int index) {

		GridPane gridPane = new GridPane();
		gridPane.setHgap(30 / input.size);
		gridPane.setVgap(30 / input.size);

		for (int row = 0; row < input.size; row++) {
			for (int col = 0; col < input.size; col++) {
				gridSquare thisSquare = Grid.getGrid()[row][col];

				GridRectangle rec = new GridRectangle(thisSquare, index, infoText);
				rec.setWidth(500 / input.size);
				rec.setHeight(500 / input.size);
				Color heatMapColor = Color
						.rgb((int) (255 * (Math.pow((thisSquare.oldProb / Grid.highestProbSquare.oldProb), .1))), 0, 0);
				rec.setFill(heatMapColor);

				// rec.setAccessibleText(thisSquare.attribute);

				gridTextRectangle tf = new gridTextRectangle(thisSquare, index, infoText);

				tf.setPrefHeight(250 / input.size);
				tf.setPrefWidth(250 / input.size);
				tf.setAlignment(Pos.CENTER);
				tf.setEditable(false);
				tf.setText(decToFrac(thisSquare.oldProb));
				if (thisSquare.mostLikelySequence
						|| tenMostActivated && grid.tenMostLikelyTrajectories.contains(thisSquare)) {
					BorderStroke B = new BorderStroke(Color.PURPLE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
							new BorderWidths(1));
					Border border = new Border(B);
					tf.setBorder(border);
					rec.setStroke(Color.PURPLE);
					rec.strokeWidthProperty().set(500 / (4 * input.size));
					rec.setWidth(500 / input.size - 500 / (4 * input.size));
					rec.setHeight(500 / input.size - 500 / (4 * input.size));
				} else if (input.generateTruth) {
					if (input.consecPoints
							.get(index * (input.consecPoints.size() / 2 - 1) + index / 2)[0] == thisSquare.coordX
							&& input.consecPoints.get(
									index * (input.consecPoints.size() / 2 - 1) + index / 2)[1] == thisSquare.coordY) {
						BorderStroke B = new BorderStroke(Color.GREEN, BorderStrokeStyle.DASHED, CornerRadii.EMPTY,
								new BorderWidths(2));
						Border border = new Border(B);

						tf.setBorder(border);
						rec.setStroke(Color.YELLOW);

						rec.strokeWidthProperty().set(500 / (5 * input.size));
						rec.setWidth(500 / input.size - 500 / (5 * input.size));
						rec.setHeight(500 / input.size - 500 / (5 * input.size));
					} else if (thisSquare.visited >= 0 && thisSquare.visited <= index * (input.consecPoints.size() / 2 - 1) + index / 2) {
						BorderStroke B = new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
								new BorderWidths(2));
						Border border = new Border(B);

						tf.setBorder(border);

						rec.setStroke(Color.GREEN);
						rec.strokeWidthProperty().set(500 / (5 * input.size));
						rec.setWidth((500 / input.size) - 500 / (5 * input.size));
						rec.setHeight((500 / input.size) - 500 / (5 * input.size));
					}
				}
				if (thisSquare.attribute.equals("N")) {
					tf.setStyle("-fx-background-color: white;");

				} else if (thisSquare.attribute.equals("H")) {
					tf.setStyle("-fx-background-color: lightblue;");

				} else if (thisSquare.attribute.equals("T")) {
					tf.setStyle("-fx-background-color: gray;");

				} else if (thisSquare.attribute.equals("B")) {
					tf.setStyle("-fx-background-color: black;");

				}
				tf.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent t) {

						gridSquare sq = tf.square;
						infoText.setText(sq.sequence);

					}
				});
				rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent t) {

						gridSquare sq = rec.square;
						infoText.setText(sq.sequence);

					}
				});
				if (!input.heatMap) {
					GridPane.setRowIndex(tf, row);
					GridPane.setColumnIndex(tf, col);
					gridPane.getChildren().addAll(tf);
				} else {
					GridPane.setRowIndex(rec, row);
					GridPane.setColumnIndex(rec, col);
					gridPane.getChildren().addAll(rec);
				}
			}
		}

		return gridPane;
	}

	static private String decToFrac(double x) {
		if (x < 0) {
			return "-" + decToFrac(-x);
		}
		double tolerance = 1.0E-6;
		double h1 = 1;
		double h2 = 0;
		double k1 = 0;
		double k2 = 1;
		double b = x;
		do {
			double a = Math.floor(b);
			double aux = h1;
			h1 = a * h1 + h2;
			h2 = aux;
			aux = k1;
			k1 = a * k1 + k2;
			k2 = aux;
			b = 1 / (b - a);
		} while (Math.abs(x - h1 / k1) > x * tolerance);

		return (int) h1 + "/" + (int) k1;
	}
}
