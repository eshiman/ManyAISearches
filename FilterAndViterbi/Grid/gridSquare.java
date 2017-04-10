package Grid;

public class gridSquare {
	public int coordX = -1;
	public int coordY = -1;
	public double oldProb = 1;
	public double newProb = 0;
	public String attribute = "N";
	public double probStay = 0;
	public double probArrive = 0;
	public String sequence = "";
	
	public gridSquare(int i, int j, int size, int numberBlocked, String attribute) {
		float s = (float) size;
		this.coordX = i;
		this.coordY = j;
		this.oldProb = 1/(s*s-(float)numberBlocked);
		this.attribute = attribute;
		if (attribute.equals("B")){
			this.oldProb = 0;
		}
	}
	public gridSquare copy(){
		gridSquare newSquare = new gridSquare(coordX,coordY, input.size, input.noBlocked, attribute);
		newSquare.oldProb = this.oldProb;
		return newSquare;
	}

}
