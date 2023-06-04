import java.util.*;

public class Recursive implements Solver {
	private ArrayList<Node> nodes;
	private ArrayList<Node> bestPath = null;
	static Double bestLength;
	
	private long started_time;
	private long current_time;
	private long actual_delta_time;
	private long last_refresh_time;
	private long elapsed_time = 0;
	
	
//		public Recursive(ArrayList<Node> nodes) {
//			this.nodes = nodes;
//			this.bestLength = Double.POSITIVE_INFINITY;
//		}
		
		public void solve(){

	        ArrayList<Node> permutation = new ArrayList<Node>();
	        Boolean [] positions = new Boolean [Main.nodes.size()];
	        
	        this.nodes = (ArrayList<Node>) Main.nodes.clone();
	        this.bestLength = Double.POSITIVE_INFINITY;
	        
	        Utilities.clearBestPaths();
	        
	        this.bestPath = new ArrayList<Node>();

	        
	        Arrays.fill(positions, Boolean.FALSE);
	        
	        startTime();
	        
	        Generate ( permutation, nodes, positions );
		        
		   }



		
		private ArrayList<Node> Generate ( ArrayList<Node> permutation, ArrayList<Node> elements, Boolean[] positions ) {
			ArrayList<Node> path = new ArrayList<Node>();
			
			updateTime();
		   
	        if ( permutation.size() == elements.size() ) {
	        	permutation.add(permutation.get(0));

	            for ( int i = 0; i < permutation.size(); i++ ) {
	            	path.add(permutation.get(i));
	            }
	                
            	Double pathLength = Utilities.pathLength(path);
	            if(pathLength < bestLength) {
	            	
	            	System.out.println("New best path");
	            	bestPath = path;
	            	bestLength = pathLength;
	            	Utilities.printSolution(bestPath, pathLength);
	            }
	            permutation.remove(permutation.size()-1);
	            


        } else {
            for (int i = 0; i < elements.size(); i++ ) {
                if (! positions[i] ) {
                	positions[i] = Boolean.TRUE;
                    permutation.add(elements.get(i));

                    Generate( permutation, elements, positions );

                    permutation.remove(permutation.size()-1);
//                    permutation.remove(permutation.size()-1);
                    positions[i] = Boolean.FALSE;
                }
                
            }
        }
//	        updateTime();
        return bestPath;
    }
		
		private void startTime() {
			elapsed_time = 0;
			started_time = System.currentTimeMillis();
			last_refresh_time = started_time;
		}
		
		private void updateTime() {

			current_time = System.currentTimeMillis();
			actual_delta_time = (current_time - last_refresh_time);
			last_refresh_time = current_time;
			elapsed_time += actual_delta_time;
			
			AnimationFrame.lblBottom.setText(String.format("%9.3f", elapsed_time / 1000.0));

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



