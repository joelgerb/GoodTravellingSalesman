import java.util.ArrayList;

public class WeightedNearestNeighbour implements Solver {
	private ArrayList<Node> nodes;
	private ArrayList<Node> currentPath;
	private ArrayList<Node> bestPath;

	public void solve() {
		
		for(int i = 0; i < 10000; i++) {
		
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
        
		Utilities.startTime();
		
        this.currentPath = new ArrayList<Node>();
		
		currentPath.add(nodes.get(0));
		unvisitedNodes.remove(0);
		
		 
		while (unvisitedNodes.size()  != 0) {
			
			nextNode = Utilities.weightedSelection(currentNode, unvisitedNodes);
			totalDistance += Utilities.distance(currentNode, nextNode);
			
			currentPath.add(nextNode);
			currentNode = nextNode;
			unvisitedNodes.remove(currentNode);
			
			Utilities.updateTime();
		}
		currentPath.add(firstNode);
		totalDistance += Utilities.distance(currentNode, firstNode);
		
		if(Utilities.pathLength(bestPath) > totalDistance) {
			bestPath = currentPath;
		}
		
		}
		Utilities.finalUpdateTime(Utilities.pathLength(bestPath));
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
