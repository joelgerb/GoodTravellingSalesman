import java.util.ArrayList;

public class Random implements Solver {
	
	private ArrayList<Node> nodes;
	static ArrayList<Node> bestPath = null;
	
	public Random(ArrayList<Node> nodes) {
		this.nodes = nodes;
		
	}
	public void solve() {
		
		double totalDistance = 0;
		Node firstNode = nodes.get(0);
		Node currentNode = nodes.get(0);
		Node nextNode;
		bestPath = new ArrayList<Node>();
		
		ArrayList<Node> unvisitedNodes = new ArrayList<Node>(nodes);
		
		Recursive.bestPath = null;
		NearestNeighbour.bestPath = null;
		
		bestPath.add(nodes.get(0));
		unvisitedNodes.remove(0);
		
		 
		while (unvisitedNodes.size()  != 0) {
			
			int randomIndex = (int)(Math.random() * unvisitedNodes.size());
			nextNode = unvisitedNodes.get(randomIndex);
			totalDistance += Utilities.distance(currentNode, nextNode);
			
			bestPath.add(nextNode);
			currentNode = nextNode;
			unvisitedNodes.remove(currentNode);
		}
		bestPath.add(firstNode);
		totalDistance += Utilities.distance(currentNode, firstNode);
		
		Utilities.printSolution(bestPath, totalDistance);
		
	}
}
