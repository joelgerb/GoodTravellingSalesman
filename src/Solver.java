import java.util.ArrayList;

public interface Solver {
	
	
	public void solve();
	public ArrayList<Node> getBestPath();
	public void clearBestPath();
	
}
