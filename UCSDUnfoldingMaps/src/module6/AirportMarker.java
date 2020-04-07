package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */

public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	String uID;
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		uID = (city.getId());
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(128, 128, 128);
		pg.quad(x-3, y, x, y+3, x+3, y, x, y-3);
		
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		String title = (getProperty("name").toString().split("\""))[1]+"\n"+(getProperty("code"))+"\n"+(getProperty("city").toString().split("\""))[1];
		pg.pushStyle();
		
		pg.rectMode(PConstants.CORNER);
		
		pg.stroke(110);
		pg.fill(255,255,255);
		pg.rect(x, y + 15, pg.textWidth(title) +6, 48, 10);
		
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(title, x + 3 , y +18);
		
		
		pg.popStyle();
		// show routes
		
		
	}	
	public String getTitle() {
		return (String) getProperty("title");	
		
	}
	public String getId() {
		return uID;
	}
	
}
