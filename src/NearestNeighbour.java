import java.util.ArrayList;

public class NearestNeighbour implements Solver {
	
	private ArrayList<Node> nodes;
	private ArrayList<Node> bestPath = null;
	
//	public NearestNeighbour(ArrayList<Node> nodes) {
//		this.nodes = nodes;
//		
//	}
	public void solve() {
		
		double totalDistance = 0;
		Node firstNode;
		Node currentNode;
		Node nextNode;
		
		ArrayList<Node> unvisitedNodes;
		
		this.nodes = (ArrayList<Node>) Main.nodes.clone();
		
		firstNode = nodes.get(0);
		currentNode = firstNode;
		
		unvisitedNodes = new ArrayList<Node>(nodes);
		
		Utilities.clearBestPaths();
        
        this.bestPath = new ArrayList<Node>();
		
		bestPath.add(nodes.get(0));
		unvisitedNodes.remove(0);
		
		 
		while (unvisitedNodes.size()  != 0) {
			
			nextNode = currentNode.getClosestNode(unvisitedNodes);
			totalDistance += Utilities.distance(currentNode, nextNode);
			
			bestPath.add(nextNode);
			currentNode = nextNode;
			unvisitedNodes.remove(currentNode);
		}
		bestPath.add(firstNode);
		totalDistance += Utilities.distance(currentNode, firstNode);
		
		Utilities.printSolution(bestPath, totalDistance);
		
	}
	@Override
	public ArrayList<Node> getBestPath() {
		return this.bestPath;
	}
	@Override
	public void clearBestPath() {
		this.bestPath = null;
		
	}
}
