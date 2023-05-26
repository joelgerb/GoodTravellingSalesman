import java.util.Scanner;

public class InputParser {

	public void parseInput(String userInput) {
		
		Scanner inputReader = new Scanner(System.in);
		
		if(userInput.equals("-q")) {
			System.out.println("Program Terminated");
			Main.run = false;
		}
		
		
		else if(userInput.equals("-h")) {
			System.out.println("-q:   Quit the program");
			System.out.println("-add: add a new node");
			System.out.println("-s:   Start a solve");
			System.out.println("	-a: Solve using ants");
			System.out.println("	-r: Solve using recursion");
			System.out.println("	-n: solve using nearest neighbour");
		}
		
		else if(userInput.equals("-a")) {
			AntColony antSolver = new AntColony();
			antSolver.solve();
		}
		
		else if(userInput.equals("-add")) {
			System.out.print("X:");
			Double x = 	Double.parseDouble(inputReader.nextLine());
			System.out.print("Y:");
			Double y = 	Double.parseDouble(inputReader.nextLine());
			System.out.print("Name:");
			String name = inputReader.nextLine();
			Node node = new Node(name, x, y);
			Main.nodes.add(node);
		}
		
		else if(userInput.equals("-n")) {
			NearestNeighbour neighbourSolver = new NearestNeighbour(Main.nodes);
			neighbourSolver.solve();
		}
		
		else if(userInput.equals("-r")) {
			Recursive recursiveSolver = new Recursive();
			recursiveSolver.solve();
		}
		
		else if(userInput.equals("-s")) {
			System.out.println("Select a solving method:");
			System.out.println("-a: Ant Colony Optimization");
			System.out.println("-r: Recursion");
			System.out.println("-n: Nearest Neighbour");
			
			System.out.print("->");
		    String input = inputReader.nextLine();
		    
		    if(input.equals("-a")) {
				AntColony antSolver = new AntColony();
				antSolver.solve();
			}
			
			else if(input.equals("-r")) {
				Recursive recursiveSolver = new Recursive();
//				recursiveSolver.solve();
			}
		    
			else if(input.equals("-n")) {
				NearestNeighbour neighbourSolver = new NearestNeighbour(Main.nodes);
				neighbourSolver.solve();
				
			}
		    
		}
		
		else {
			System.out.println("I don't understand... Use -h for a list of commands");
		}
		
	}
	
}
