import java.util.ArrayList;

public class Random implements Solver {
	private ArrayList<Node> nodes;
	static ArrayList<Node> bestPath = null;
	
	public void solve() {
		
		double totalDistance = 0;
		Node firstNode;
		Node currentNode;
		Node nextNode;
		
		this.nodes = (ArrayList<Node>) Main.nodes.clone();
		
		ArrayList<Node> unvisitedNodes = new ArrayList<Node>(nodes);
		
		Utilities.startTime();
		
		firstNode = nodes.get(0);
		currentNode = firstNode;
		
		Utilities.clearBestPaths();
        
        this.bestPath = new ArrayList<Node>();
		
		bestPath.add(nodes.get(0));
		unvisitedNodes.remove(0);
		
		 
		while (unvisitedNodes.size()  != 0) {
			
			int randomIndex = (int)(Math.random() * unvisitedNodes.size());
			nextNode = unvisitedNodes.get(randomIndex);
			totalDistance += Utilities.distance(currentNode, nextNode);
			
			bestPath.add(nextNode);
			currentNode = nextNode;
			unvisitedNodes.remove(currentNode);
			
			Utilities.updateTime();
		}
		bestPath.add(firstNode);
		totalDistance += Utilities.distance(currentNode, firstNode);
		
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
