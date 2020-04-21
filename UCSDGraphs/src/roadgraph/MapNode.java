package roadgraph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode>{
	private GeographicPoint loc;
	private Set<MapEdge> edgeSet;
	private double dist;
	private double pdist;
	public MapNode(GeographicPoint loc) {
		this.loc = loc;
		edgeSet = new HashSet<MapEdge>();
	}
	/*
	 * Add a neighbouring edge connecting neighbour node
	 * @param me MapEdge object that begins at current MapNode
	 */
	public void addEdge(MapEdge me) {
		edgeSet.add(me);
	}
	/*
	 * getter method for List of neigbouring edges
	 */
	
	public Set<MapEdge> getEdgeSet(){
		return new HashSet<MapEdge>(edgeSet);
	}
	//getter method for location of MapNode
	public GeographicPoint getGeographicPoint() {
		return loc;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
	public double getDist() {
		return dist;
	}
	public void setPDist(double pdist) {
		this.pdist = pdist;
	}
	public double getPDist() {
		return pdist;
	}
	
	public Set<MapNode> getNeighbours(){
		Set<MapNode> ms = new HashSet<>();
		for(MapEdge e: this.getEdgeSet()) {
			ms.add(e.getOtherNode(this));
		}
		return ms;
	}
	public double getEdgeLengthTo(MapNode m) {
		for(MapEdge e: edgeSet) {
			if(e.getStartNode().equals(this) && e.getEndNode().equals(m))
				return e.getLength();
		}
		return Double.MAX_VALUE;
	}
	public int compareTo(MapNode m) {
	
		if(this.dist + this.pdist < m.getDist()+ m.getPDist())
			return -1;
		else if (this.dist + this.pdist > m.getDist() + m.getPDist())
			return 1;
		else
			return 0;
	}
	public String toString() {
		return (loc.toString() +"\t"+ dist);
	}
}
