import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class NavigationGraph implements GraphADT<Location, Path> {
	private ArrayList<GraphNode<Location, Path>> graph;
	private String[] property;
	private static int ID = 0;
	
	public NavigationGraph(String[] edgePropertyNames) {
		property = edgePropertyNames;
		graph = new ArrayList<GraphNode<Location, Path>>();
	}

	
	/**
	 * Returns a Location object given its name
	 * 
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {
		return new Location(name);  
	}


	@Override
	public void addVertex(Location vertex){
		for ( GraphNode<Location, Path> i : graph ){
			if ( vertex.equals(i.getVertexData())){
				throw new IllegalArgumentException();
			}
		}
		
		graph.add(new GraphNode<Location, Path>(vertex, ID));
		ID++;
		
	}


	@Override
	public void addEdge(Location src, Location dest, Path edge) {
		if (get(src)!= null){
			GraphNode<Location, Path> source = get(src); // right way to get src in the graph?
			Path temp = new Path(src, dest, edge.getProperties());
			source.addOutEdge(temp);
		}
	}


	@Override
	public List<Location> getVertices() {
		List <Location> list = new ArrayList<Location>();
		for ( GraphNode<Location, Path> i : graph){
			list.add((Location)i.getVertexData());
		}
		return list;
	}


	@Override
	public Path getEdgeIfExists(Location src, Location dest) {
		if (get(src)!= null){
			GraphNode<Location, Path> source = get(src);
			if (source == null){
				return null;
			}
			for (Path i : source.getOutEdges() ){
				if ( i.getDestination().equals(dest)){
					return i;
				}
			}
		}
		return null;
	}


	@Override
	public List<Path> getOutEdges(Location src) {
		return graph.get(graph.indexOf(src)).getOutEdges();   // right way to get src in the graph?
	}


	@Override
	public List<Location> getNeighbors(Location vertex) {
		List<Location> list = new ArrayList<Location>();
		if (get(vertex)!= null){
			GraphNode<Location, Path> source = get(vertex);
			if (source == null){
				throw new IllegalArgumentException();
			}
			for(Path i: source.getOutEdges()){
			list.add(i.getDestination());
			}
		}
		// TODO
		return list;
	}

	private GraphNode<Location, Path> get(Location vertex){
		for ( GraphNode<Location, Path> i : graph ){
			if (i.getVertexData().equals(vertex)){
				return i;
			}
		}
		return null;
	}
	@Override
	public List<Path> getShortestRoute(Location src, Location dest, String edgePropertyName) {
		GraphNode<Location, Path> source = get(src);
		GraphNode<Location, Path> destination = get(dest);
		
		if ( source == null || destination == null){
			throw new IllegalArgumentException();
		}
		
		List<DJvertex> DjV = new ArrayList<DJvertex>();
		
		for ( GraphNode<Location, Path> i : graph){
			DjV.add(new DJvertex(i));//here! use DjV.get(graphNode.getID()) to retrieve 
			 //which one you want! ID is the order which graphNodes are
									 // inserted!!!!!!!!!
		}
		
		Queue<DJvertex> DjQ = new PriorityQueue<DJvertex>();
		DJvertex curr = DjV.get(source.getId());
		
		DjQ.add(curr);
		curr.setTotalWeight(0.0);
			
		while(!DjV.isEmpty()){
			curr = DjQ.remove();
			curr.visit();
			
			List<Location> list = getNeighbors(curr.getLocation());
			for (Location i : list) {
				DJvertex temp = DjV.get(get(i).getId());
				if (!temp.isVisited()){
				Path edge = getEdgeIfExists(curr.getLocation(), i);
				if( edge == null){
					throw new IllegalArgumentException();
				}
				
				//GraphNode<Location, Path> temp = get(i);
				Double tempWeight = curr.getTotalWeight() + edge.getProperties().get(index)  // double or Double?
					if( tempWeight < temp.getTotalWeight()){        // index depends on property name//check reduced  
						temp.setTotalWeight( tempWeight);
						
						temp.setPred(curr);
//						if(DjQ.contains(temp)){
//							
//						}
						
						boolean ex = false;
						for (DJvertex j : DjQ) {
							if (j.getLocation().equals(i)) {
								j.setTotalWeight(temp.getTotalWeight());
								ex = true;
							}
						}
						if (!ex)
							DjQ.add(temp);
						
					}
					//find a way to add weight up. temp.outedge(?).property(?)
			
				}
			}
		}
		
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getEdgePropertyNames() {
		return property;
	}
	
	

}
