package code;

import java.io.File;
import java.util.Stack;
import java.util.Scanner;

public class Board {
	
	/*The Sudoku Board is made of 9x9 cells for a total of 81 cells.
	 * In this program we will be representing the Board using a 2D Array of cells.
	 * 
	 */

	private Cell[][] board = new Cell[9][9];
	//public Cell[] saved = new Cell[81];
	
	//The variable "level" records the level of the puzzle being solved.
	private String level = "";
	
	
	///TODO: CONSTRUCTOR
	//This must initialize every cell on the board with a generic cell.  It must also assign all of the boxIDs to the cells
	public Board(){
		for(int x = 0; x < 9; x++) {
			for(int y = 0 ; y < 9; y++){
				board[x][y] = new Cell();
				board[x][y].setBoxID( 3*(x/3) + (y)/3+1);
			}
		}
	}
	

	
	///TODO: loadPuzzle
	/*This method will take a single String as a parameter.  The String must be either "easy", "medium" or "hard"
	 * If it is none of these, the method will set the String to "easy".  The method will set each of the 9x9 grid
	 * of cells by accessing either "easyPuzzle.txt", "mediumPuzzle.txt" or "hardPuzzle.txt" and setting the Cell.number to 
	 * the number given in the file.  
	 * 
	 * This must also set the "level" variable
	 * TIP: Remember that setting a cell's number affects the other cells on the board.
	 */
	public void loadPuzzle(String level) throws Exception{
		this.level = level;
		String fileName = "easy.txt";
		if(level.contentEquals("medium"))
			fileName = "medium.txt";
		else if(level.contentEquals("hard"))
			fileName = "hard.txt";
		else if(level.contentEquals("oni"))
			fileName = "oni.txt";
		
		Scanner input = new Scanner (new File(fileName));
		
		for(int x = 0; x < 9; x++)
			for(int y = 0 ; y < 9; y++)
			{
				int number = input.nextInt();
				if(number != 0)
					solve(x, y, number);
			}
						
		input.close();
		
		//board[5][2].showPotential();
		
	}
	
	///TODO: isSolved
	/*This method scans the board and returns TRUE if every cell has been solved.  Otherwise it returns FALSE
	 * 
	 */
	public boolean isSolved(){
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 9; y++) {
				if(board[x][y].getNumber() == 0)
					return false;
			}
		}
		return true;
		
	}


	///TODO: DISPLAY
	/*This method displays the board neatly to the screen.  Please see the sample output from the assignment to see the format required.
	 */
	public void display(){
		for(int x = 0; x < 9; x++) {
			if(x % 3 == 0 && x != 0)
				System.out.println("=====================");
			for(int y = 0; y < 9; y++) {
				if(y % 3 == 0&& y != 0)
					System.out.print("| ");
				System.out.print(board[x][y].getNumber() + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
	}
	
	///TODO: solve
	/*This method solves a single cell at x,y for number.  It also must adjust the potentials of the remaining cells in the same row,
	 * column, and box.
	 */
	public void solve(int x, int y, int number){
		board[x][y].setNumber(number);
		
		for(int row = 0; row < 9; row ++) {
			if(row != x)//board[row][y].getNumber() != number
				board[row][y].cantBe(number);	
		}
		for(int col = 0; col < 9; col ++) {
			if(col != y) //board[x][col].getNumber() != number
				board[x][col].cantBe(number);
		}
		
		for(int j = 0; j < 9; j++) {
			for(int k = 0; k < 9; k++) {
				if((j != x && k != y) && board[j][k].getBoxID() == board[x][y].getBoxID())
					board[j][k].cantBe(number);
			}
		}
		//display();
	}
	
	
	//logicCycles() continuously cycles through the different logic algorithms until no more changes are being made.
	public void logicCycles()throws Exception{
		
		Board[] saved = new Board[81];
	    int guessInd = 0;
	    while (!isSolved()) {
	    	int changesMade = 0;
	    	do {
	            changesMade = 0;
	            changesMade += logic1();
	            changesMade += logic2();
	            changesMade += logic3();
	            changesMade += logic4();
	        } while(changesMade > 0 && !errorFound());
	       
	        if(errorFound()) {
	            if (guessInd == 0) {
	                System.out.println("unable to solve the puzzle");
	                return;
	            }
	           
	            Board lastSaved = saved[--guessInd];
	            for(int x = 0; x < 9; x++) {
	                for(int y = 0; y < 9; y++) {
	                    board[x][y] = new Cell();
	                    board[x][y].setBoxID(lastSaved.board[x][y].getBoxID());
	                    int num = lastSaved.board[x][y].getNumber();
	                    if (num != 0) {
	                        solve(x, y, num);
	                    } 
	                    else {
	                        for(int i = 1; i <= 9; i++) {
	                            if (!lastSaved.board[x][y].canBe(i)){
	                                board[x][y].cantBe(i);
	                            }
	                        }
	                    }
	                }
	            }
	           
	            for(int x = 0; x < 9; x++) {
	                for(int y = 0; y < 9; y++) {
	                    if (board[x][y].getNumber() == 0 && board[x][y].numberOfPotentials() > 0) {
	                        board[x][y].cantBe(board[x][y].getFirstPotential());
	                        break;
	                    }
	                   
	                }
	                break;
	            }
	            continue;
	        }
	      
	        if(!isSolved()) {
	           
	            Board copy = new Board();
	            for (int x = 0; x < 9; x++) {
	                for (int y = 0; y < 9; y++) {
	                    copy.board[x][y].setBoxID(board[x][y].getBoxID());
	                    int num = board[x][y].getNumber();
	                    if (num != 0) {
	                        copy.solve(x, y, num);
	                    } 
	                    else {
	                        for (int i = 1; i <= 9; i++) {
	                            if (!board[x][y].canBe(i)) {
	                                copy.board[x][y].cantBe(i);
	                            }
	                        }
	                    }
	                }
	            }
	            saved[guessInd++] = copy;
	           
	           
	            for (int x = 0; x < 9; x++) {
	                for (int y = 0; y < 9; y++) {
	                    if (board[x][y].getNumber() == 0 && board[x][y].numberOfPotentials() > 0) {
	                        solve(x, y, board[x][y].getFirstPotential());
	                        break;
	                    }
	                }
	                 break;
	            }
	        }
	      
	    }


//		while(isSolved() == false)
//		{
//			int changesMade = 0;
//			do
//			{
//				changesMade = 0;
//				changesMade += logic1();
//				changesMade += logic2();
//				changesMade += logic3();
//				changesMade += logic4();
//				if(errorFound()) {
//					System.out.println("error");
//					break;
//					
//				}
//					
//				//System.out.println("=====");
//			}while(changesMade > 0);
//			//display();
//			
//			
////			if(changesMade == 0 && board != solved)
////				guess;
////				save board state
////				find first unsolved cell and solve for first potential
////				then continue with logic cycles
////				if error found (0 potnetials) then reload saved board
////				go to first unsolved cell,m set first potential to be false then go with second potentials
//				
//	
//		}			
		
	}
	
	///TODO: logic1
	/*This method searches each row of the puzzle and looks for cells that only have one potential.  If it finds a cell like this, it solves the cell 
	 * for that number. This also tracks the number of cells that it solved as it traversed the board and returns that number.
	 */
	public int logic1(){
		int changesMade = 0;
		
		for(int x = 0; x < 9; x ++) {
			for(int y = 0; y < 9; y ++) {
				if(board[x][y].numberOfPotentials() == 1 && board[x][y].getNumber() == 0) {
					int number = board[x][y].getFirstPotential();
					solve(x, y, number);
					changesMade ++;
				}
					
			}
		}
		
		return changesMade;
					
	}
	
	///TODO: logic2
	/*This method searches each row for a cell that is the only cell that has the potential to be a given number.  If it finds such a cell and it
	 * is not already solved, it solves the cell.  It then does the same thing for the columns.This also tracks the number of cells that 
	 * it solved as it traversed the board and returns that number.
	 */
	
	public int logic2(){
		int changesMade = 0;
		
		//solving the rows
		for(int row = 0; row < 9; row ++) {
			for(int number = 1; number <= 9; number++) {
				int count = 0;
				int trackCol = -1;
				
				for(int col = 0; col < 9; col ++) {
					if(board[row][col].getNumber() == 0 && board[row][col].canBe(number)) { //if that cell hasn't been solved, it has potential to be a certain number 
						count++;
						trackCol = col;
					}
				}
				if(count == 1) {
					solve(row, trackCol, number);
					changesMade++;
				}
			}
		}
		
		//solving the columns
		for(int col = 0; col < 9; col ++) {
			for(int number = 1; number <= 9; number++) {
				int count = 0;
				int trackRow = -1;
				for(int row = 0; row < 9; row ++) {
					if(board[row][col].getNumber() == 0 && board[row][col].canBe(number)) { //if that cell hasn't been solved, it has potential to be a certain number 
						count++;
						trackRow = row;
					}
				}
				if(count == 1) {
					solve(trackRow, col, number);
					changesMade++;
				}
			}
		}
			
		return changesMade;
	}
	
	///TODO: logic3
	/*This method searches each box for a cell that is the only cell that has the potential to be a given number.  If it finds such a cell and it
	 * is not already solved, it solves the cell. This also tracks the number of cells that it solved as it traversed the board and returns that number.
	 */
	public int logic3(){
	
		//SWAP THE BOXID AND NUMBER LOOP WITH THE ROW AND COL LOOP. THE ROW AND COL LOOP NEEDS TO BE NESTED INSIDE THE BOXID AND NUMBER
		int changesMade = 0;
		for(int boxID = 1; boxID <= 9; boxID ++) {
			for(int number = 1; number <= 9; number ++) {
				int count = 0;
				int x = -1;
				int y = -1;
				
				for(int row = 0; row < 9; row ++) {
					for(int col = 0; col < 9; col ++) {
						if(board[row][col].getBoxID() == boxID && board[row][col].getNumber() == 0 && board[row][col].canBe(number)) {
							count++;
							x = row;
							y = col;
						}
					}
				}
				
				if(count == 1) {
					solve(x, y, number);
					changesMade++;
				}
				
			}
		}
		return changesMade;
	}
	
	
	///TODO: logic4
		/*This method searches each row for the following conditions:
		 * 1. There are two unsolved cells that only have two potential numbers that they can be
		 * 2. These two cells have the same two potentials (They can't be anything else)
		 * 
		 * Once this occurs, all of the other cells in the row cannot have these two potentials.  Write an algorithm to set these two potentials to be false
		 * for all other cells in the row.
		 * 
		 * Repeat this process for columns and rows.
		 * 
		 * This also tracks the number of cells that it solved as it traversed the board and returns that number.
		 */
	public int logic4(){
		int changesMade = 0;
		
		//searching row
		for(int row = 0; row < 9; row++) {
			for(int col1 = 0; col1 < 8; col1 ++) {
				if(board[row][col1].getNumber() != 0 || board[row][col1].numberOfPotentials() != 2)  //if cell already solved or if cell has more than 2 potentials
					continue;
				
				for(int col2 = col1 + 1; col2 < 9; col2++) {
					if(board[row][col2].getNumber() != 0 || board[row][col2].numberOfPotentials() != 2)
						continue;
					
					int potential1a = board[row][col1].getFirstPotential();
					int potential2a = board[row][col2].getFirstPotential();
					int potential1b = board[row][col1].getSecondPotential();
					int potential2b = board[row][col2].getSecondPotential();
					
					if((potential1a == potential2a && potential1b == potential2b) || (potential1a == potential2b && potential1b == potential2a)) { //if both cells have the same 2 potentials
						for(int col3 = 0; col3 < 9; col3 ++) {
							if(col3 == col1 || col3 == col2)
								continue;
							
							if(board[row][col3].getNumber() == 0) { //if the cell is unsolved and its not the cell with the 2 potentials
								if(board[row][col3].canBe(potential1a)) {
									board[row][col3].cantBe(potential1a);
									changesMade++;
								}
								if(board[row][col3].canBe(potential1b)) {
									board[row][col3].cantBe(potential1b);
									changesMade++;
								}
							}
						}
					}
						
				}
			}
		}
		return changesMade;
		
		//
//		for(int row = 0; row < 9; row++) {
//			for(int col1 = 0; col1 < 8; col1 ++) {
//				if(board[row][col1].getNumber() != 0 || board[row][col1].numberOfPotentials() != 2)  //if cell already solved or if cell has more than 2 potentials
//					continue;
//				
//				for(int col2 = col1 + 1; col2 < 9; col2++) {
//					if(board[row][col2].getNumber() != 0 || board[row][col2].numberOfPotentials() != 2)
//						continue;
//					
//					int potential1a = board[row][col1].getFirstPotential();
//					int potential2a = board[row][col1].getSecondPotential();
//					int potential1b = board[row][col2].getFirstPotential();
//					int potential2b = board[row][col2].getSecondPotential();
//					
//					if((potential1a == potential2a && potential1b == potential2b) || (potential1a == potential2b && potential1b == potential2a)) { //if both cells have the same 2 potentials
//						for(int col3 = 0; col3 < 10; col3 ++) {
//							if(col3 == col1 || col3 == col2)
//								continue;
//							
//							if(board[row][col3].getNumber() == 0) { //if the cell is unsolved and its not the cell with the 2 potentials
//								if(board[row][col3].canBe(potential1a)) {
//									board[row][col3].cantBe(potential1a);
//									changesMade++;
//								}
//								if(board[row][col3].canBe(potential1b)) {
//									board[row][col3].cantBe(potential1b);
//									changesMade++;
//								}
//							}
//						}
//					}
//						
//				}
//			}
//		}
		
	}
	
	
	///TODO: errorFound
	/*This method scans the board to see if any logical errors have been made.  It can detect this by looking for a cell that no longer has the potential to be 
	 * any number.
	 */
	public boolean errorFound(){
		for(int x = 0; x < 9; x ++) {
			for(int y = 0; y < 9; y ++) {
				if(board[x][y].numberOfPotentials() == 0 && board[x][y].getNumber() == 0)
					return true;
			}
		}
		return false;
	}
	

}

/*
  Guess:
  board[] saved = new board[81]
  int guess = 0;
  saved[guess] = new board[]
  make method going cell by cell to copy. set data of cell to other cell
  
  1. make copy of board
  2. save that copy into the stack
  3. solve the first unsolved cell for its first potential
  4. keep using logic
  5. IF stuck again, guess again
  6. IF error found, reload the last board from the stack, turn off the first potential of the first unsolved cell

*/
  
