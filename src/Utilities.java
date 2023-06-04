import java.util.ArrayList;

public class Utilities {
	private static long started_time;
	private static long current_time;
	private static long actual_delta_time;
	private static long last_refresh_time;
	private static long elapsed_time = 0;
	
	public static double distance(double pointOneX, double pointOneY, double pointTwoX, double pointTwoY) {
		double base = pointOneX - pointTwoX;
		double height = pointOneY - pointTwoY;
		return pythagoras(base, height);
	}
	
	public static double distance(Node node1, Node node2) {
		return distance(node1.getXPosition(), node1.getYPosition(), node2.getXPosition(), node2.getYPosition());
	}
	
	public static double pythagoras(double base, double height) {
		return Math.hypot(base, height);
	}
	
	public static void printSolution(ArrayList<Node> solution, Double distance) {
		for(int i = 0; i < solution.size(); i++) {
			if(i == solution.size() - 1) {
				System.out.println(solution.get(i).getName());
			}
			
			else {
			System.out.print(solution.get(i).getName() + " -> ");
			}
		}
		System.out.println("Path length: " + distance );
	}
	
	public static double pathLength(ArrayList<Node> path) {
		double totalDistance = 0;
		
		for(int i = 0; i < path.size() - 1; i ++) {
			totalDistance += distance(path.get(i), path.get(i + 1));
		}
		
		return totalDistance;
	}
	
	public static void clearBestPaths() {
		for (Solver solver : Main.solvers) {
			solver.clearBestPath();
		}
		
	}
	
	public static void startTime() {
		elapsed_time = 0;
		started_time = System.currentTimeMillis();
		last_refresh_time = started_time;
	}
	
	public static void updateTime() {

		current_time = System.currentTimeMillis();
		actual_delta_time = (current_time - last_refresh_time);
		
		if (actual_delta_time > 10) {
			last_refresh_time = current_time;
			elapsed_time += actual_delta_time;
			AnimationFrame.lblBottom.setText(String.format("%9.2f", elapsed_time / 1000.0));
		}
		

	}
	
	public static void finalUpdateTime(Double bestLength) {
		updateTime();
		if (elapsed_time > 1000) {
			AnimationFrame.lblBottom.setText(String.format("Distance: %.1f, Time: %.3f", bestLength, elapsed_time / 1000.0));

		} else {
			AnimationFrame.lblBottom.setText(String.format("Distance: %.1f, Time: %f", bestLength, elapsed_time / 1000.0));

		}
	}
	
	
	
}
