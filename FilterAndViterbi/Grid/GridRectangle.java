package Grid;

import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;

public class GridRectangle extends Rectangle {
	
	public gridSquare square;
	public int index;
	public TextArea infoText;
	
	public GridRectangle(gridSquare sq, int index, TextArea infoText) {
		super();
		this.square = sq;
		this.index = index;
		this.infoText = infoText;
	}

}
