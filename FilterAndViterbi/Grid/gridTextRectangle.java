package Grid;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class gridTextRectangle extends TextField {
	
	public gridSquare square;
	public int index;
	public TextArea infoText;
	
	public gridTextRectangle(gridSquare sq, int index, TextArea infoText) {
		super();
		this.square = sq;
		this.index = index;
		this.infoText = infoText;
	}

}
