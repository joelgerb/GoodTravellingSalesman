import java.util.*;

public class Recursive implements Solver {
	private ArrayList<Node> nodes;
	private ArrayList<Node> bestPath = null;
	static Double bestLength;
	
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


		        Generate ( permutation, nodes, positions );
		        
		        
		   }



		
		private ArrayList<Node> Generate ( ArrayList<Node> permutation, ArrayList<Node> elements, Boolean[] positions ) {
			ArrayList<Node> path = new ArrayList<Node>();
		   
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
        return bestPath;
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



