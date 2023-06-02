//import java.util.Scanner;
//
//public class InputParser {
//
//	public void parseInput(String userInput) {
//		
//		Scanner inputReader = new Scanner(System.in);
//		
//		if(userInput.equals("-q")) {
//			System.out.println("Program Terminated");
//			Main.run = false;
//		}
//		
//		
//		else if(userInput.equals("-h")) {
//			System.out.println("-q:   Quit the program");
//			System.out.println("-add: add a new node");
//			System.out.println("-a: Solve using ants");
//			System.out.println("-r: Solve using recursion");
//			System.out.println("-n: solve using nearest neighbour");
//		}
//		
//		else if(userInput.equals("-a")) {
//			AntColony antSolver = new AntColony();
//			antSolver.solve();
//		}
//		
//		else if(userInput.equals("-add")) {
//			System.out.print("X:");
//			Double x = 	Double.parseDouble(inputReader.nextLine());
//			System.out.print("Y:");
//			Double y = 	Double.parseDouble(inputReader.nextLine());
//			System.out.print("Name:");
//			String name = inputReader.nextLine();
//			Node node = new Node(name, x, y);
//			Main.nodes.add(node);
//		}
//		
//		else if(userInput.equals("-n")) {
//			NearestNeighbour neighbourSolver = new NearestNeighbour(Main.nodes);
//			neighbourSolver.solve();
//		}
//		
//		else if(userInput.equals("-r")) {
//			Recursive recursiveSolver = new Recursive();
//			recursiveSolver.solve();
//			recursiveSolver = null;
//		}
//		
//		else {
//			System.out.println("I don't understand... Use -h for a list of commands");
//		}
//		
//	}
//	
//}
