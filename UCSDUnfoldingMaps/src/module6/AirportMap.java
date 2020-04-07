package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5996407582380911066L;
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	HashMap<Integer, Location> airports;
	HashMap<Integer, Marker> idmarker;
	CommonMarker lastClicked;
	CommonMarker lastSelected;    
	
	public void setup() {
		// setting up PAppler
		size(1200,800, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 150, 50, 1100, 600, new Microsoft.RoadProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		airports = new HashMap<Integer, Location>();
		idmarker = new HashMap<Integer, Marker>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
			
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			idmarker.put(Integer.parseInt(feature.getId()), (Marker)m);
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		
		
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = (new SimpleLinesMarker(route.getLocations(), route.getProperties()));
		
			
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
			
		}
		hideAll(routeList);//routelist has all routes
		
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		//map.addMarkers(routeList);
		map.addMarkers(routeList);
		map.addMarkers(airportList);
		
	}
	private void hideAll(List<Marker> markers) {
		for(Marker m : markers) {
			m.setHidden(true);
		}
	}
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
		
		//loop();
	}
	
	// If there is a marker selected 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	public void mouseClicked() {
		if(lastClicked!=null) {
			lastClicked = null;
			unhideMarkers();
			hideAll(routeList);
		}
		else {
			for (Marker marker : airportList) {
				if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {//if an airport is clicked display that airport and 
					lastClicked = (CommonMarker)marker;
					// Hide all the other airports
					hideAll(airportList);
					lastClicked.setHidden(false);
					String id = marker.getId();
					for(Marker showRoute : routeList) {
						if(showRoute.getProperty("source").equals(id)) {
							showRoute.setHidden(false);
							idmarker.get(Integer.parseInt(showRoute.getProperty("destination").toString())).setHidden(false);
							
						}
						else
							showRoute.setHidden(true);
					}
					
				}
			}
		}
	}
	private void unhideMarkers() {
		for(Marker marker : airportList) {
			marker.setHidden(false);
		}
			
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	

}
