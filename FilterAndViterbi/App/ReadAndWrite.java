package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.gridworld.exceptions.CoordinateException;
import com.gridworld.exceptions.ReadException;
import com.gridworld.grid.CoordinatePair;
import com.gridworld.grid.Coordinates;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;
import com.gridworld.grid.SquareColor;

import Grid.grid;

public class ReadAndWrite {
	
	
	
	public static void writeGridToFile(grid grid, /*CoordinatePair coords,*/ String fileName) {
		
		//System.out.println("Writer called.");
		//System.out.println("Filename is "+fileName);
		
		try{
		    
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		    
		    // Get coordinates of start and goal squares, build strings, and write to file
			
		/*	String start = coords.sStart.toString();
			String end = coords.sGoal.toString();
		    writer.println(start);
		    writer.println(end);
		    for(int i = 0; i < grid.centersOfHardRegions.size(); i++) {
		    	writer.println(grid.centersOfHardRegions.get(i).toString());
		    }
		    */
		    for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					writer.print("0");
					/*if(grid.GridSquares[i][j].color == SquareColor.DARK_GRAY)
						writer.print("0");
					if(grid.GridSquares[i][j].color == SquareColor.WHITE && !grid.GridSquares[i][j].memberOfHorizontalHighway && !grid.GridSquares[i][j].memberOfVerticalHighway)
						writer.print("1");
					if(grid.GridSquares[i][j].color == SquareColor.LIGHT_GRAY && !grid.GridSquares[i][j].memberOfHorizontalHighway && !grid.GridSquares[i][j].memberOfVerticalHighway)
						writer.print("2");
					if(grid.GridSquares[i][j].color == SquareColor.WHITE && (grid.GridSquares[i][j].memberOfHorizontalHighway || grid.GridSquares[i][j].memberOfVerticalHighway))
						writer.print("a");
					if(grid.GridSquares[i][j].color == SquareColor.LIGHT_GRAY && (grid.GridSquares[i][j].memberOfHorizontalHighway || grid.GridSquares[i][j].memberOfVerticalHighway))
						writer.print("b");*/
				}
				writer.println();
			}
		    
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	public static grid readGridFromFile(String fileName) {
		grid grid = new grid(fileName);
		try {
			
			// get Start and End Coordinates
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			line = br.readLine();
			String[] read = line.split(",");
			int startx = Integer.parseInt(read[0]);
			int starty = Integer.parseInt(read[1]);
		//	Coordinates newC = new Coordinates(startx, starty);
			line = br.readLine();
			String[] readNext = line.split(",");
			int endx = Integer.parseInt(readNext[0]);
			int endy = Integer.parseInt(readNext[1]);
		//	Coordinates newCE = new Coordinates(endx, endy);
		//	CoordinatePair newCP = new CoordinatePair(newC, newCE);
		//	newCP.parent = grid;
		//	grid.pathPoints.add(newCP);
			
			// Centers of hard to traverse regions
			for (int i = 0; i < 8; i++) {
				line = br.readLine();
				read = line.split(",");
				int nextx = Integer.parseInt(read[0]);
				int nexty = Integer.parseInt(read[1]);
		//		Coordinates nextpair = new Coordinates(nextx, nexty);
		//		grid.centersOfHardRegions.add(nextpair);
			}
			
			 for (int i = 0; i < 3; i++) {
				
				 line = br.readLine();
				 
			/*	 for (int j = 0; j < 3; j++) {
					 char curr = line.charAt(j);
					 char next;
					 char prev;
					 try{
						 prev = line.charAt(j-1);
					 } catch (StringIndexOutOfBoundsException e) {
						 prev = 'z';
					 }
					 
					 try{
						 next = line.charAt(j+1);
					 } catch (StringIndexOutOfBoundsException e) {
						 next = 'z';
					 }
					 
					 if (curr == '0') {
						 GridSquare square = new GridSquare(i, j, SquareColor.DARK_GRAY, grid);
						 square.memberOfHorizontalHighway = false;
						 square.memberOfVerticalHighway = false;
						 grid.GridSquares[i][j] = square;

					 }
					 else if (curr == '1') {
						 GridSquare square = new GridSquare(i, j, SquareColor.WHITE, grid);
						 square.memberOfHorizontalHighway = false;
						 square.memberOfVerticalHighway = false;
						 grid.GridSquares[i][j] = square;
					 }
					 else if (curr == '2') {
						 GridSquare square = new GridSquare(i, j, SquareColor.LIGHT_GRAY, grid);
						 square.memberOfHorizontalHighway = false;
						 square.memberOfVerticalHighway = false;
						 grid.GridSquares[i][j] = square;
					 }
					 else if (curr == 'a') {
						 GridSquare square = new GridSquare(i, j, SquareColor.WHITE, grid);
						 if (next != 'a' && prev != 'a') {
							 square.memberOfVerticalHighway = true;
						 } else {
							 square.memberOfHorizontalHighway = true;
						 }
						 grid.GridSquares[i][j] = square;
					 }
					 else if (curr == 'b') {
						 GridSquare square = new GridSquare(i, j, SquareColor.LIGHT_GRAY, grid);
						 if (next != 'a' && prev != 'a') {
							 square.memberOfVerticalHighway = true;
						 } else {
							 square.memberOfHorizontalHighway = true;
						 }
						 grid.GridSquares[i][j] = square;
					 }
					 else {
						 throw new ReadException("Read failed, file contains: "+curr);
					 }
					 
				 }
				 
				 
			 
			 }*/
			 br.close();
			
			
		}} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		
		}
		
		return grid;
	
	}
	

}
