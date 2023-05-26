import java.util.*;



public class Recursive {
	
		static ArrayList<Node> bestPath = new ArrayList<Node>();
		static Double bestLength = Double.POSITIVE_INFINITY;
	   public static ArrayList<Node> Generate ( ArrayList<Node> permutation, ArrayList<Node> elements, Boolean[] positions ) {
		   ArrayList<Node> path = new ArrayList<Node>();
		   
	        if ( permutation.size() == elements.size() ) {

	            for ( int i = 0; i < elements.size(); i++ )
	                path.add(permutation.get(i));
	            Double pathLength = Utilities.pathLength(path);
	            if(pathLength < bestLength) {
	            	
	            	System.out.println("New best path");
	            	bestPath = path;
	            	bestLength = pathLength;
	            	Utilities.printSolution(bestPath, pathLength);
	            }
	            


	        } else {
	            for (int i = 0; i < elements.size(); i++ ) {
	                if ( positions[i] )
	                    continue;

	                positions[i] = Boolean.TRUE;
	                permutation.add(elements.get(i));

	                Generate( permutation, elements, positions );

	                permutation.remove(permutation.size()-1);
	                positions[i] = Boolean.FALSE;
	            }
	        }
	        return bestPath;
	    }
	   public static void solve(){

	        ArrayList<Node> permutation = new ArrayList<Node>();
	        ArrayList<Node> elements = new ArrayList<Node>(Main.nodes);
	        Boolean [] positions = new Boolean [Main.nodes.size()];

	        Arrays.fill(positions, Boolean.FALSE);

	        Generate ( permutation, elements, positions );
	        
	   }

}



