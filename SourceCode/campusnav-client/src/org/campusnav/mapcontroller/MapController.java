package org.campusnav.mapcontroller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.R.bool;
import android.graphics.Color;
import android.os.AsyncTask;

import com.carouseldemo.main.R;
import com.example.hellomap.GMapV2Direction;
import com.example.hellomap.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapController {
	
    GoogleMap mMap;
    public Polyline routeLine1;
    public Polyline routeLine2;
    public Polyline routeLine3;
    

	ArrayList<Double>  latitude;
	ArrayList<Double>  longitude ;
	
	public boolean GroundFloorAB;
	public boolean FirstFloorAB;
	
    public void setMap(GoogleMap map) {
		
    	mMap = map;
	}
	
	/*
     * draw path indoor
     * given the coordinates of nodes in the path 
     * it draws the path on the map
     */
    public void drawRoute(String coOrdinateArray ) {
    	
    	String tokenized[] = coOrdinateArray.split("#");
    	
    	ArrayList<Double>  latitude1 = new ArrayList<Double>();
    	ArrayList<Double>  longitude1 = new ArrayList<Double>();
    	
    	double latLangSrcOut[] = new double[2] ;
    	double latLangDestOut[] = new double[2] ;
    	
    	ArrayList<Double>  latitude2 = new ArrayList<Double>();
    	ArrayList<Double>  longitude2 = new ArrayList<Double>();
    	
    	if ( tokenized.length >= 1 && tokenized[0] != null  ){
    		String tokenizedx[] = tokenized[0].split(",");
    		
    		for(int i=0; i < tokenizedx.length ;i = i+2 ){
    			latitude1.add(Double.parseDouble(tokenizedx[i]));
    			longitude1.add(Double.parseDouble(tokenizedx[i+1]));
    			
    		}	

    		drawRouteIndoor(latitude1, longitude1, routeLine1 );
    	}
    	
    	if ( tokenized.length >= 2 && tokenized[1] != null){
    		String tokenizedx[] = tokenized[1].split(",");
    		
    		latLangSrcOut[0] =  Double.parseDouble(tokenizedx[0]);
    		latLangSrcOut[1] =  Double.parseDouble(tokenizedx[1]);
    		
    		latLangDestOut[0] =  Double.parseDouble(tokenizedx[2]);
    		latLangDestOut[1] =  Double.parseDouble(tokenizedx[3]);
    		
    		LatLng src = new LatLng(latLangSrcOut[0],latLangSrcOut[1]);
    		LatLng dest = new LatLng(latLangDestOut[0],latLangDestOut[1]);
    		
    		LatLng arry[] = {src,dest};
    		
    		HttpRequestOutDoorRoute outDoorRouteThread = new HttpRequestOutDoorRoute();
    		outDoorRouteThread.execute(arry);
    		
    	}
    	
    	if ( tokenized.length == 3 && tokenized[2] != null ){
    		String tokenizedx[] = tokenized[2].split(",");
    		
    		for(int i=0; i < tokenizedx.length ;i = i+2 ){
    			latitude2.add(Double.parseDouble(tokenizedx[i]));
    			longitude2.add(Double.parseDouble(tokenizedx[i+1]));
    			//System.out.println(tokenizedx[i]+" , "+tokenizedx[i+1]);
    		}	
    		
    		drawRouteIndoor(latitude2, longitude2, routeLine3);
    	}
    	
	}
    
    /*
     * Coordiante tokenizer
     *  
     */
    
    public void coordinateTokenizer(String coordinates){
    	
    	latitude = new ArrayList<Double>();
        longitude = new ArrayList<Double>();
    	
		String tokenizedx[] = coordinates.split(",");
		
		for(int i=0; i < tokenizedx.length ;i = i+2 ){
			latitude.add(Double.parseDouble(tokenizedx[i]));
			longitude.add(Double.parseDouble(tokenizedx[i+1]));
			//System.out.println(tokenizedx[i]+" , "+tokenizedx[i+1]);
		}	
    	
    }
    
    /*
     * variables for xml method
     */
    // key : tag name & value : tags data
    HashMap<String, String> coordinatesByBuildingAndFloor = new HashMap<String, String>() ;
    

    
    // key : building name & value : hashMap<string floor, polyline>
    private HashMap<String , HashMap<String, Polyline> > floorsInBuildings = new HashMap<String , HashMap<String, Polyline> >() ;
    
    /*
     * draw route method from xml input
     */
    
    public void parseXML(String xmlInput) throws XmlPullParserException, IOException{
		
		{
	         XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	         factory.setNamespaceAware(true);
	         XmlPullParser xpp = factory.newPullParser();

	         //xpp.setInput(new StringReader ("<indoor><x><fisrtfloor>470503,74.409751</fisrtfloor><fisrtfloor>31.471076,74.4096,31.470575,74.409689#31.470336,74.409934,31.471603,74.41069#31.471178,74.409166,31.470503,74.409751</fisrtfloor></x>"
	         //		 +"</indoor><outdoor>31.471076,74.4096</outdoor>"));
	         
	         xpp.setInput(new StringReader (xmlInput));
	        		 
	         String tagname = null;
	         int eventType = xpp.getEventType();
	        
	         while (eventType != XmlPullParser.END_DOCUMENT) {
	          if(eventType == XmlPullParser.START_TAG ) {
	              //System.out.println("Start tag "+xpp.getName());
	              tagname = xpp.getName();
	          } 
	          else if(eventType == XmlPullParser.END_TAG) {
	              //System.out.println("End tag "+xpp.getName());
	          } 
	          else if(eventType == XmlPullParser.TEXT && tagname.equals("groundfloor0AcademicBlock")) {
	        	  GroundFloorAB = true;
	        	  coordinatesByBuildingAndFloor.put(tagname, xpp.getText());
	              System.out.println(GroundFloorAB + " Data " + tagname +"\n"+xpp.getText());
	          }
	          else if(eventType == XmlPullParser.TEXT && tagname.equals("firstfloor0AcademicBlock")) {
	        	  FirstFloorAB = true;
	        	  coordinatesByBuildingAndFloor.put(tagname, xpp.getText());
	              System.out.println(FirstFloorAB + " Data " + tagname +"\n"+xpp.getText());
	          }
	          else if (eventType == XmlPullParser.TEXT && tagname.equals("outdoor")){
	        	  // handle separately
	        	  routeDrawInvoke( xpp.getText());
	          }
	          eventType = xpp.next();
	         }
	     }
    }
    // for now it will work for one building -- for each building it has to be reinitialized
    HashMap<String, Polyline> polyLinesByFloor = new HashMap<String, Polyline>();    
    
    public void drawRouteXML(){
    	
    	// remaing remove all previous lines
		
		if (polyLinesByFloor.get("firstfloor") != null ){
			polyLinesByFloor.get("firstfloor").remove();
			System.out.println( "removing lines 3" );
			
		}
		if (polyLinesByFloor.get("groundfloor") != null ){
			polyLinesByFloor.get("groundfloor").remove();
			System.out.println( "removing lines 2" );
			
		}

		
		
    	// indoor
    	for ( String key : coordinatesByBuildingAndFloor.keySet() ){

    		// split tag name stored in hash map at 0   index [0] = floor
    		//											index [1] = building
    		String tokenize[] = key.split("0");

    		// removing already drawn lines
    		if( this.getFloorsInBuildings().get(tokenize[1]) != null && this.getFloorsInBuildings().get(tokenize[1]).get(tokenize[0])!= null){
    			this.getFloorsInBuildings().get(tokenize[1]).get(tokenize[0]).remove();
    			System.out.println( "removing lines" );
    		}
    		
			if(routeLine2 != null){
				routeLine2.remove();
			}

			

    		// get the coordinate in longitude / latitude Arraylists
    		coordinateTokenizer(coordinatesByBuildingAndFloor.get(key));
    		
    		// draw the route lines on map and get the pointer in temp to that line
    		Polyline temp = drawRouteIndoor(latitude, longitude);
    		

    		// store that in hash map with floor as key
    		polyLinesByFloor.put(tokenize[0], temp);
    		
    		//getFloorsInBuildings().get(tokenize[1]).put(tokenize[0], temp);
    		
    		//put the floor in building hash map
    		getFloorsInBuildings().put(tokenize[1], polyLinesByFloor);
	    	
    		
    	}
    	coordinatesByBuildingAndFloor.clear();
    	// initially setting first floor route invisible
//    	if (polyLinesByFloor.get("firstfloor") != null ){
//    		polyLinesByFloor.get("firstfloor").setVisible(false);
//    	}
//    	if (polyLinesByFloor.get("firstfloor") != null && polyLinesByFloor.get("groundfloor") == null ){
//    		polyLinesByFloor.get("firstfloor").setVisible(true);
//    	}
//    	else if (polyLinesByFloor.get("groundfloor") != null && polyLinesByFloor.get("firstfloor") == null ){
//    		polyLinesByFloor.get("groundfloor").setVisible(true);
//    	}
    	if ( FirstFloorAB && GroundFloorAB  ){
    		polyLinesByFloor.get("firstfloor").setVisible(false);
    		
    	}
    	if (FirstFloorAB && !GroundFloorAB ){
    		polyLinesByFloor.get("firstfloor").setVisible(true);
//    		polyLinesByFloor.get("groundfloor").setVisible(false);
    	}
    	else if (!FirstFloorAB && GroundFloorAB ){
    		polyLinesByFloor.get("groundfloor").setVisible(true);
//   		polyLinesByFloor.get("firstfloor").setVisible(false);
    	}
    	
    	

    }
    
    /*
     * draw path indoor
     * input: arrays of longitude and lattitude point and handle to ployline object
     * output: draws polyline on the map
     */
    protected void drawRouteIndoor(ArrayList<Double>  latitude, ArrayList<Double>  longitude ,Polyline routeLine ) {
    	// Instantiates a new Platiolyline object and adds points to define a rectangle
    	PolylineOptions routeLineOptions = new PolylineOptions();  	
    	for (int i=0 ;i < latitude.size();i++){
    		
    		routeLineOptions.add(new LatLng( latitude.get(i),longitude.get(i)));
    	}

    	// Get back the mutable Polyline
    	
    	if (routeLine != null){
    		routeLine.remove();
    		
    	}

    	routeLine = mMap.addPolyline(routeLineOptions);
    	
    	routeLine.setWidth(2);
    	routeLine.setColor(Color.CYAN);
        routeLine.setGeodesic(true);
	}
    /*
     * draw path indoor
     * input: arrays of longitude and lattitude point and handle to ployline object
     * output: draws polyline on the map
     */
    protected Polyline drawRouteIndoor(ArrayList<Double>  latitude, ArrayList<Double>  longitude ) {
    	// Instantiates a new Platiolyline object and adds points to define a rectangle
    	PolylineOptions routeLineOptions = new PolylineOptions();  	
    	for (int i=0 ;i < latitude.size();i++){
    		
    		routeLineOptions.add(new LatLng( latitude.get(i),longitude.get(i)));
    	}


    	
    	//GoogleMap mMap =;
    	
    	Polyline routeLine = mMap.addPolyline(routeLineOptions);
    	
    	routeLine.setWidth(2);
    	routeLine.setColor(Color.CYAN);
        routeLine.setGeodesic(true);
        
		return routeLine;
	}
    
    public HashMap<String , HashMap<String, Polyline> > getFloorsInBuildings() {
		return floorsInBuildings;
	}

	public void setFloorsInBuildings(HashMap<String , HashMap<String, Polyline> > floorsInBuildings) {
		this.floorsInBuildings = floorsInBuildings;
	}

	/*
	 * out door route draw invoker
	 * 
	 */
	public void routeDrawInvoke(String input){
		
    	double latLangSrcOut[] = new double[2] ;
    	double latLangDestOut[] = new double[2] ;
		
		String tokenizedx[] = input.split(",");
		
		latLangSrcOut[0] =  Double.parseDouble(tokenizedx[0]);
		latLangSrcOut[1] =  Double.parseDouble(tokenizedx[1]);
		
		latLangDestOut[0] =  Double.parseDouble(tokenizedx[2]);
		latLangDestOut[1] =  Double.parseDouble(tokenizedx[3]);
		
		LatLng src = new LatLng(latLangSrcOut[0],latLangSrcOut[1]);
		LatLng dest = new LatLng(latLangDestOut[0],latLangDestOut[1]);
		
		LatLng arry[] = {src,dest};
		
		HttpRequestOutDoorRoute outDoorRouteThread = new HttpRequestOutDoorRoute();
		outDoorRouteThread.execute(arry);
	}
	
	/*
     * draw route outdoor
     * input : LatLng array containing source and destination LatLng points
     * output: draw outdoor root on map 
     * runs on a separate thread
     */
    private class HttpRequestOutDoorRoute extends AsyncTask<LatLng, Void, Document>{

    	GMapV2Direction md = new GMapV2Direction();	
		@Override
		protected Document doInBackground(LatLng... latLang) {
			Document doc = md.getDocument(latLang[0], latLang[1], GMapV2Direction.MODE_WALKING);

			return doc;
		}
		
	    @Override
	    protected void onPostExecute(Document doc) {
	    	
			int duration = md.getDurationValue(doc);
			String distance = md.getDistanceText(doc);
			String start_address = md.getStartAddress(doc);
			String copy_right = md.getCopyRights(doc);

			ArrayList<LatLng> directionPoint = md.getDirection(doc);
			PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.CYAN);
			
			for(int i = 0 ; i < directionPoint.size() ; i++) {			
				rectLine.add(directionPoint.get(i));
			}
			
			if(routeLine2 != null){
				routeLine2.remove();
			}
			
			//GoogleMap mMap = MainActivity.getMap();
			routeLine2 = mMap.addPolyline(rectLine);
			
	    }
    	
    	
    	
    	
    }
    

}
