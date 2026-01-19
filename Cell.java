package code;

public class Cell {
	
	/*A Cell represents a single square on the Sudoku Game Board. 
	 * It knows it's number - 0 means it is not solved.
	 * It knows the potential numbers that it could have from 1-9.
	 * The Sudoku game board is sub-divided into 9 smaller 3x3 sections that I will call a box. 
	 * These boxes will be numbered from left to right, top to bottom, from 1 to 9.  
	 * Each cell will know which box it belongs in.
	 */
	
	private int number; // This is the solved value of the cell.
	private boolean[] potential = {false, true, true, true, true, true, true, true, true, true};
	/*This array represents the potential of the cell to be each of the given index numbers.  Index [0] is not used since
	 * the cell cannot be solved for 0. 
	 */
	private int boxID;//The boxID is the box to which the cell belongs.
	
	//This method returns TRUE or False depending on whether the cell has the potential to be number
	public boolean canBe(int number){
		if(potential[number] == true)
			return true;
		return false;
	}
	
	//This sets the potential array to be false for a given number
	public void cantBe(int number){
		potential[number] = false;
	}
	
	//This method returns a count of the number of potential numbers that the cell could be.
	public int numberOfPotentials(){
		int count = 0;
		
		for(int x = 0; x < 10; x ++) {
			if(potential[x] == true)
				count++;
		}
		return count;
		
	}
	
	//This method will return the first number that a cell can possibly be.
	public int getFirstPotential(){
		for(int x = 0; x < 10; x ++) {
			if(potential[x] == true)
				return x;
		}
		return 0;
		
	}
	
	public void showPotential() {
		for(int x = 1; x < 10; x++)
		{
			System.out.println(x + ": " + potential[x]);
		
		}
	}
	
	//This method will return the second number that a cell can possibly be.
	public int getSecondPotential(){
		int firstIndex = -1;
		int secondIndex = -1;
		
		for(int x = 1; x < 10; x ++) {
			if(potential[x] == true) {
				if(firstIndex == -1)
					firstIndex = x;
				else
					secondIndex = x;
			}
		}
		return secondIndex;
		
	}
	
	
	
	//GETTERS AND SETTERS
	public int getNumber() {
		return number;
	}
	
	// This method sets the number for the cell but also sets all of the potentials for the cell (except for the solved number)
	//		to be false
	public void setNumber(int number) {
		this.number = number;
		
		for(int x = 0; x < 10; x++) {
			if(x != number)
				potential[x] = false;
			else //this extra else statement doesn't seem necessary???
				potential[x] = true;
		}
		
	}
	
	
	public boolean[] getPotential() {
		return potential;
	}
	public void setPotential(boolean[] potential) {
		this.potential = potential;
	}
	public int getBoxID() {
		return boxID;
	}
	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}
	

}

//if a cell has the potential to be only one number, then it must be that number
//if a raw/column/box requires a number and only one cell in that row/column/box can be that number, then it must be that number
//if 2 cells in a row/box/column only have 2 potentials and have the same 2 potentials, then the rest of the 
//cells in that row/box/column can't be those numbers

//have an object called cell
//has an int value and a potential array

