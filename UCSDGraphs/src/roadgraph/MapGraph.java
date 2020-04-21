/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	
	private HashMap<GeographicPoint, MapNode> vertices;
	private HashSet<MapEdge> edges;
	private int numVertices, numEdges;
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		vertices = new HashMap<>();
		edges = new HashSet<MapEdge>();
		numVertices = 0;
		numEdges = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		Set<GeographicPoint> gpv = new HashSet<>();
		for(Map.Entry v: vertices.entrySet())
			gpv.add((GeographicPoint)v.getKey());
		return gpv;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if(vertices.containsKey(location)||location==null)
			return false;
		vertices.put(location, new MapNode(location));
		numVertices ++;
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		
		//TODO: Implement this method in WEEK 3
		if(!vertices.containsKey(from)||!vertices.containsKey(to)||roadName==null||roadType==null||length==0)
			throw new IllegalArgumentException();
		
		MapEdge me = new MapEdge(vertices.get(from), vertices.get(to), roadName, roadType, length);
		edges.add(me);
		vertices.get(from).addEdge(me);//add edge to vertex 'from'
		numEdges++;
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		MapNode start_node = vertices.get(start);
		MapNode goal_node = vertices.get(goal);
		if(start_node==null||goal_node==null)
			return new LinkedList<GeographicPoint>();
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<>();
		
		boolean found = bfsSearch(start_node, goal_node, parentMap, nodeSearched);
		
		if(!found) return null;
		
		List<GeographicPoint> path = constructPath(start_node, goal_node, parentMap);
		

		return path;
	}
	/*helper method for bfsSearch: applies bfs to reach goal_node from start_node
	 * @param start_node MapNode denoting start of search
	 * @param goal_node MapNode denoting destination
	 * @param parentMap HashMap that stores parent node for every node
	 * @param nodeSearched Consumer obj for visualising bfs in front end app
	 */
	private boolean bfsSearch(MapNode start_node, MapNode goal_node, HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched) {
		HashSet<MapNode> visited = new HashSet<>();
		Queue<MapNode> toExplore = new LinkedList<>();
		boolean found = false;
		
		toExplore.add(start_node);
		visited.add(start_node);
		while(!toExplore.isEmpty()) {
			MapNode curr = toExplore.remove();
			// Hook for visualization.  See writeup.
			//next is GeographicPoint obj
			nodeSearched.accept(curr.getGeographicPoint());
			if(curr.equals(goal_node)) {
				found = true;
				break;
			}
			//get neighbours of curr, add to visited and push into toExplore, add curr as parent in parentMap
			Set<MapEdge> e = curr.getEdgeSet();
			for(MapEdge edge: e) {
				MapNode next = edge.getEndNode();
				if(!visited.contains(next)) {
					visited.add(next);
					toExplore.add(next);
					parentMap.put(next, curr);
				}
				
			}
		}
		return found;
	}
	/*
	 * helper method to construct path from start_node to goal_node using parentMap
	 */
	private LinkedList<GeographicPoint> constructPath(MapNode start_node, MapNode goal_node, HashMap<MapNode, MapNode> parentMap){
		MapNode curr = goal_node;
		LinkedList<GeographicPoint> path = new LinkedList<>();
		
		while(curr!=start_node) {
			path.addFirst(curr.getGeographicPoint());
			curr = parentMap.get(curr);
		}
		path.addFirst(start_node.getGeographicPoint());
		return path;
		
		
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		for(MapNode m: vertices.values()) {
			m.setDist(Double.MAX_VALUE);
		}
		int count = 0;
		Queue<MapNode> pq = new PriorityQueue<MapNode>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<>();
		HashSet<MapNode> visited = new HashSet<>();
		
		boolean found = false;
		pq.add(vertices.get(start));
		vertices.get(start).setDist(0.0);
		WHILE: while(!pq.isEmpty()) {
			MapNode curr = pq.remove();
			count ++;
			if(!visited.contains(curr)) {
				nodeSearched.accept(curr.getGeographicPoint());
				visited.add(curr);
				if(curr.equals(vertices.get(goal))) {
					found = true;
					break WHILE;
				}
				for(MapNode m : curr.getNeighbours()) {
					double newD = curr.getDist()+curr.getEdgeLengthTo(m);
					if(!visited.contains(m)&&(newD < m.getDist())) {
						m.setDist(newD);
						parentMap.put(m, curr);
						pq.add(m);
					}
				}
			}
		}
		System.out.println("Dijkstra visited "+ count+"nodes");
		if(!found) return null;
		
		List<GeographicPoint> path = constructPath(vertices.get(start), vertices.get(goal), parentMap);
		
		return path;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		for(MapNode m: vertices.values()) {
			m.setDist(Double.MAX_VALUE);
			m.setPDist(Double.MAX_VALUE);
		}
		int count = 0;
		
		Queue<MapNode> pq = new PriorityQueue<MapNode>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<>();
		HashSet<MapNode> visited = new HashSet<>();
		
		boolean found = false;
		pq.add(vertices.get(start));
		
		vertices.get(start).setDist(0.0);
		vertices.get(start).setPDist(start.distance(goal));
		WHILE: while(!pq.isEmpty()) {
			MapNode curr = pq.remove();
			count++;
			if(!visited.contains(curr)) {
				nodeSearched.accept(curr.getGeographicPoint());
				visited.add(curr);
				if(curr.equals(vertices.get(goal))) {
					found = true;
					break WHILE;
				}
				for(MapNode m : curr.getNeighbours()) {
					double newD = curr.getDist()+curr.getEdgeLengthTo(m);
					if(!visited.contains(m)&&(newD < m.getDist())) {
						m.setDist(newD);
						m.setPDist(m.getGeographicPoint().distance(goal));
						parentMap.put(m, curr);
						pq.add(m);
					}
				}
			}
		}
		System.out.println("Dijkstra visited "+ count+"nodes");
		if(!found) return null;
		
		List<GeographicPoint> path = constructPath(vertices.get(start), vertices.get(goal), parentMap);
		
		return path;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		//System.out.println(firstMap.bfs(new GeographicPoint(1.0, 1.0), new GeographicPoint(8.0, -1.0)));
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		/*
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		System.out.println("D: " + testroute.size() + "A* " + testroute2.size());
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		System.out.println("D: " + testroute.size() + "A* " + testroute2.size());
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		
		
	}
	
}
