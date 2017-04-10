package Controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import App.Main;
import App.ReadAndWrite;
import FilterAndViterbi.filterAndViterbi;
import Grid.grid;
import Grid.gridSquare;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewController implements Initializable{
	
	@FXML Button displayButton;
	@FXML Button saveMapButton;
	@FXML Button loadMapButton;
	@FXML Button quitButton;
	@FXML ListView<String> mapList;
	@FXML AnchorPane gridpaneHolder;
	@FXML TextArea infoText;
	@FXML Parent root;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		// There needs to be a list of grids somewhere with start and stop points so I can add them to the list, and when they're clicked so I can add them to the gridpane
		
		//System.out.println("ViewController started");

		//This is the container of the  list of actual grids
		filterAndViterbi list = Main.filteredGridList;

		//This is the internal List of grids
		ArrayList<grid> grids = list.filteredGrid;
		
		// The name of each math is stored here, used to be clist
		ArrayList<String> nameList = new ArrayList<String>();
		for(int i=0; i < input.actions.size(); i++) {
			nameList.add(input.actions.get(i)+ " " + input.observes.get(i));
		}
		ObservableList<String> oblist = FXCollections.observableArrayList(nameList);
		mapList.setItems(oblist);

        displayButton.setOnAction(this::handleButtonAction);
        saveMapButton.setOnAction(this::handleButtonAction);
        loadMapButton.setOnAction(this::handleButtonAction);
        quitButton.setOnAction(this::handleButtonAction);
		
    }
	
	@FXML
    private void handleButtonAction(ActionEvent e) {

        //System.out.println("Button pushed!");
        Button b = (Button) e.getSource();
        if (b == displayButton) {
        	infoText.clear();
        	gridpaneHolder.getChildren().clear();
        	//selected item used to be cord
        	String selectedItem = mapList.getSelectionModel().getSelectedItem();

     	   	int ind = mapList.getSelectionModel().getSelectedIndex() % 10;
        	grid grid = Main.filteredGridList.filteredGrid.get(ind);
     	  // System.out.print("Index is "+ind);
        	
        	GridPane newGridDisplay = genGridPane(grid, infoText, ind);
        	
        	gridpaneHolder.getChildren().addAll(newGridDisplay);
        }
    /*    if (b == saveMapButton) {
        	Integer cord = mapList.getSelectionModel().getSelectedItem();
        	ReadAndWrite.writeGridToFile(cord.parent, cord, cord.toString());
        }*/
        if (b == loadMapButton){
        	Stage stage = new Stage();
        	FileChooser fileChooser = new FileChooser();
        	fileChooser.setTitle("Select Map File");
        	File file = fileChooser.showOpenDialog(stage);
        	grid newGrid = ReadAndWrite.readGridFromFile(file.getAbsolutePath());
        	
        	Main.filteredGridList.filteredGrid.add(newGrid);
        	
        	filterAndViterbi list = Main.filteredGridList;
    		//System.out.println(list);
    		//System.out.println("got gridlist");

    		ArrayList<grid> grids = list.filteredGrid;
    		//System.out.println("got internal list of grids");
    		//System.out.println(grids);
    		
    		/*ArrayList<CoordinatePair> clists = new ArrayList<CoordinatePair>();
    		for(int i=0; i < grids.size(); i++) {
    			clists.addAll(grids.get(i).pathPoints);
    		}
    		
    		
    		
    		ObservableList<CoordinatePair> oblist = FXCollections.observableArrayList(clists);
    		mapList.setItems(oblist);*/
        	
        }
        if (b == quitButton) {
        	
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        	System.exit(1);
        	
        }
	}
	 
	private static GridPane genGridPane(grid Grid, /*CoordinatePair pair,*/ TextArea infoText, int index) {
		
		
    	GridPane gridPane = new GridPane();
    	gridPane.setHgap(10);
    	gridPane.setVgap(10);
    	
        for(int row = 0; row < 3; row++){
               for(int col = 0; col < 3; col++){
                  gridSquare thisSquare = Grid.getGrid()[row][col];
               /*   GridRectangle rec = new GridRectangle(thisSquare, index, infoText);
                   rec.setWidth(50);
                   rec.setHeight(50);
                   rec.setFill(Color.BLUE);
                   rec.setWidth(40);
                   rec.setHeight(40);
                   rec.setFill(Color.RED);
                   rec.setAccessibleText(thisSquare.attribute);
                   */
                   TextField tf = new TextField();
                   tf.setPrefHeight(60);
                   tf.setPrefWidth(60);
                   tf.setAlignment(Pos.CENTER);
                   tf.setEditable(false);
                   tf.setText(decToFrac(thisSquare.oldProb));
                   if(thisSquare.attribute.equals("N")){
                       tf.setStyle("-fx-background-color: white;");
                	   
                   }
                   else if(thisSquare.attribute.equals("H")){
                       tf.setStyle("-fx-background-color: lightblue;");
                	   
                   }
                   else if(thisSquare.attribute.equals("T")){
                       tf.setStyle("-fx-background-color: gray;");
                	   
                   }
                   else if(thisSquare.attribute.equals("B")){
                       tf.setStyle("-fx-background-color: black;");
                	   
                   }
                   // Iterate the Index using the loops
                  // setRowIndex(tf,row);
                   //setColumnIndex(tf,column);    
                   /*if(pair.path.contains(thisSquare)){
                	   rec.setFill(Color.GREEN);
                   }  
                   else if (thisSquare.memberOfHorizontalHighway || thisSquare.memberOfVerticalHighway){
                	   rec.setFill(Color.BLUE);
                   } 
                   else if (thisSquare.color == SquareColor.DARK_GRAY) {
                	   rec.setFill(Color.BLACK);
                   }
                   else if (thisSquare.color == SquareColor.LIGHT_GRAY) {
                	   rec.setFill(Color.GRAY);
                   }
                   else {
                	   
                	   rec.setFill(Color.WHITE);
                   }
                   
                   rec.setOnMouseClicked(new EventHandler<MouseEvent>()
                   {
                       public void handle(MouseEvent t) {
                    	   
                    	   GridSquare sq = rec.square;
                    	   Vertex vert = sq.getSavedVertex(index);
                    	   String info;
                    	   if (vert == null) {
                    		   info = "H: NULL \n G: NULL \n F: NULL";
                    	   } else {
                    		   
                        	   Double gval = vert.getG(0);
                        	   Double hval = vert.getH(0);
                        	   if(Double.toString(gval) == null)
                        		   gval = 0.0;
                        	   if(Double.toString(hval) == null)
                        		   hval = 0.0;
                        	   String gvalText = Double.toString(gval);
                        	   String hvalText = Double.toString(hval);
                        	   String fvalText = Double.toString(gval + hval);
                        	   info = "H: " + hvalText + "\n G: " + gvalText + "\n F: " + fvalText;
                    		   
                    	   }
                    	   infoText.setText(info);
                    	   
                       }
                   });*/
                   
                   GridPane.setRowIndex(tf, row);
                   GridPane.setColumnIndex(tf, col);
                   gridPane.getChildren().addAll(tf);
               }
        }
		
		
		return gridPane;		
	}
	static private String decToFrac(double x){
	    if (x < 0){
	        return "-" + decToFrac(-x);
	    }
	    double tolerance = 1.0E-6;
	    double h1=1; double h2=0;
	    double k1=0; double k2=1;
	    double b = x;
	    do {
	        double a = Math.floor(b);
	        double aux = h1; h1 = a*h1+h2; h2 = aux;
	        aux = k1; k1 = a*k1+k2; k2 = aux;
	        b = 1/(b-a);
	    } while (Math.abs(x-h1/k1) > x*tolerance);

	    return (int)h1+"/"+(int)k1;
	}
}
