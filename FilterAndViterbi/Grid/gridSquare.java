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
	public String newSequence = "";
	public boolean mostLikelySequence = false;
	public int visited = -1;

	public gridSquare(int i, int j, int size, int numberBlocked, String attribute) {
		float s = (float) size;
		this.coordX = i;
		this.coordY = j;
		this.oldProb = 1 / (s * s - (float) numberBlocked);
		this.attribute = attribute;
		if (attribute.equals("B")) {
			this.oldProb = 0;
		}
		this.sequence = "(" + i + ", " + j + ") ";
	}

	public gridSquare copy() {
		gridSquare newSquare = new gridSquare(coordX, coordY, input.size, input.noBlocked, attribute);
		newSquare.oldProb = this.oldProb;
		newSquare.sequence = this.sequence;
		newSquare.visited = this.visited;
		return newSquare;
	}

	public int compareTo(gridSquare parent) {
		if (this.oldProb > parent.oldProb) {
			return -1;
		}
		return 1;
	}

}
