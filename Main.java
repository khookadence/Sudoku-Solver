package code;

import java.io.File;
import java.util.Stack;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception { 
		
		Board puzzle = new Board();

		int menuChoice= 0;
		Scanner input = new Scanner(System.in);
		
		do{
			System.out.println("0. Exit Program");
			System.out.println("Choose a difficulty");
			System.out.println("1. Easy");
			System.out.println("2. Medium");
			System.out.println("3. Hard");
			System.out.println("4. Oni");
			menuChoice = input.nextInt();
		}while(menuChoice < 0 || menuChoice > 4);
		
		if(menuChoice == 1) {
			System.out.println("Easy:");
			puzzle.loadPuzzle("easy");
			System.out.println("start");
			puzzle.display();
			puzzle.logicCycles();
			
			System.out.println("end");
			System.out.println();
			puzzle.display();
			System.out.println(puzzle.errorFound());
			System.out.println(puzzle.isSolved());
			
		}
		
		else if(menuChoice == 2) {
			System.out.println("Medium:");
			puzzle.loadPuzzle("medium");
			System.out.println("start");
			puzzle.display();
			puzzle.logicCycles();
			
			System.out.println("end");
			System.out.println();
			puzzle.display();
			System.out.println(puzzle.errorFound());
			System.out.println(puzzle.isSolved());
		}
		
		else if(menuChoice == 3) {
			System.out.println("Hard:");
			puzzle.loadPuzzle("hard");
			System.out.println("start");
			puzzle.display();
			puzzle.logicCycles();
			
			System.out.println("end");
			System.out.println();
			puzzle.display();
			System.out.println(puzzle.errorFound());
			System.out.println(puzzle.isSolved());
		}
		
		else if(menuChoice == 4) {
			System.out.println("Oni:");
			System.out.println("start");
			puzzle.display();
			puzzle.logicCycles();
			
			System.out.println("end");
			System.out.println();
			puzzle.display();
			System.out.println(puzzle.errorFound());
			System.out.println(puzzle.isSolved());
		}
	
//			puzzle.loadPuzzle("oni");
//			System.out.println("start");
//			puzzle.display();
//			puzzle.logicCycles();
//			
//			System.out.println("end");
//			puzzle.display();
//			System.out.println(puzzle.errorFound());
//			System.out.println(puzzle.isSolved());
			
		
	}

}
