package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	private GeographicPoint start, end;
	private MapNode start_node, end_node;
	private double length;
	private String roadName, roadType;
	/*
	 * Constructor to intantiate a MapEdge using params
	 */
	private MapEdge(GeographicPoint start, GeographicPoint end, String roadName, String roadType,  double length) {
		this.start = start;
		this.end = end;
		this.length = length;
		this.roadName = roadName;
		this.roadType = roadType;
	}
	public MapEdge(MapNode start_node, MapNode end_node, String roadName, String roadType, double length) {
		this(start_node.getGeographicPoint(), end_node.getGeographicPoint(), roadName, roadType, length);
		this.start_node = start_node;
		this.end_node = end_node;
	}
	/*
	 * Getter methods to retrieve private attributes of Edge
	 */
	public GeographicPoint getStart() {
		return start;
	}
	public GeographicPoint getEnd() {
		return end;
	}
	public MapNode getStartNode() {
		return start_node;
	}
	public MapNode getEndNode() {
		return end_node;
	}
	public String roadName() {
		return roadName;
	}
	public String getroadType() {
		return roadType;
	}
	public double getLength() {
		return length;
	}
	
	public MapNode getOtherNode(MapNode m) {
		if(start_node.equals(m))
			return end_node;
		else if(end_node.equals(m))
			return start_node;
		throw new IllegalArgumentException();
	}
	
	
}
