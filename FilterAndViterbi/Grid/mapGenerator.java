package Grid;

import java.util.Random;

public class mapGenerator {

	public mapGenerator() {
		super();

		int Size = input.size * input.size;
		input.attributes = new String[Size];
		for(int i = 0; i< Size/2; i++){
			input.attributes[i] = "N";
		}
		for(int i = Size/2; i< 7*Size/10; i++){
			input.attributes[i] = "H";
		}
		for(int i = 7*Size/10; i< 9*Size/10; i++){
			input.attributes[i] = "T";
		}
		for(int i = 9*Size/10; i< Size; i++){
			input.attributes[i] = "B";
		}
		for(int i = 0; i< Size*10; i++){
			int switch1 = randomNumberGenerator(0, Size);
			int switch2 = randomNumberGenerator(0, Size);
			String temp = input.attributes[switch1];
			input.attributes[switch1] = input.attributes[switch2];
			input.attributes[switch2] = temp;
			
		}
	}
	private int randomNumberGenerator(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max - min) + min;
	}

}
