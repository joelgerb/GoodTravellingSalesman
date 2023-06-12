import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	public static final int RECURSIVE_INDEX = 0;
	public static final int NEIGHBOUR_INDEX = 1;
	public static final int WEIGHTED_NEIGHBOUR_INDEX = 2;
	public static final int RANDOM_INDEX = 3;
//	public static final int ANTS_INDEX = 4;
	public static boolean run = true;
	public static boolean runTimer = true;
	public static ArrayList<Node> nodes = new ArrayList<Node>();
	public static Solver[] solvers = {
			new Recursive(),
			new NearestNeighbour(),
			new WeightedNearestNeighbour(),
			new Random(),
//			new AntColony()	
	};
	
	public static void main(String args[]) {
		
		RunGUI.run();
	
	}
}
