
public class DJvertex {
	private boolean visited;
	private Double totalWeight;
	private DJvertex pred;
	private int ID;
	private Location location;
	
	
	public DJvertex(GraphNode<Location, Path> i){
		this.visited = false;
		this.totalWeight = Double.MAX_VALUE;
		this.ID = i.getId();
		this.pred = null;
		this.location = i.getVertexData();
	}
	public Location getLocation(){
		return location;
	}
	public int getid(){
		return ID;
	}
	
	public boolean isVisited(){
		return visited;
	}
	
	public DJvertex getPred(){
		return pred;
	}
	
	public Double getTotalWeight(){
		return totalWeight;
	}

	public void visit(){
		this.visited = true;
	}
	
	public void setPred(DJvertex i){
		this.pred = i;
	}
	
	public void setTotalWeight(Double  i){
		this.totalWeight = i;
	}

	
}
