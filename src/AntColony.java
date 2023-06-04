import java.util.ArrayList;

public class AntColony implements Solver {
	private ArrayList<Node> nodes;
	private ArrayList<Node> bestPath = null;
	 
//	public AntColony(ArrayList<Node> nodes) {
//		this.nodes = nodes;
//	}

	public void solve() {
		this.nodes = (ArrayList<Node>) Main.nodes.clone();
		System.out.println("I'm Solving using ant colony optimzation!");
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
