package com.example.hellomap;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.location.Criteria;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;
import android.provider.Settings;


import com.carouseldemo.main.CarousalActivity;
import com.carouseldemo.main.GetInput;
import com.carouseldemo.main.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.campusnav.salman.*;
import org.campusnav.test.WifiToGPSMapper;
import org.campusnav.mapcontroller.*;
public class MainActivity extends FragmentActivity implements LocationListener{
  
	public static Context context;
	public GoogleMap mMap;
    double latitude=31.470947;
    double longitude=74.409896;
    
    private LocationManager locationManager;
    private String provider;
    
    private CameraUpdate center;
    private CameraUpdate zoom;
    
    private Marker currentPosMarker;
    private Marker otherPosMarker;
    private MarkerOptions currentPosMarkerOps ;
    private MarkerOptions otherPosMarkerOps ;
    
    private GroundOverlayOptions gOverlayOps;
    
    private String src;
    private String dest;
    
    LatLng source;
    LatLng destination;
    
    Bitmap imageOfAcdBlock;
    
    Polyline routeLine;
    

    
    GMapV2Direction md;

    int bearing;
    GroundOverlay groundOverlay;
    GroundOverlay gOverLay;
    
	
	long GPSupdateInterval;         // In milliseconds
	float GPSmoveInterval;          // In meters
	
	public static String locations[];
	
	BitmapDescriptor image ;//an image.
	LatLng bounds;
	MapController mapController;
	ServerCommunication serverCom;
	InputStream iStreamFile1;
	WifiData wifiController;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.main);
        setUpMapIfNeeded();
        
        // initializing map controller
        mapController = new MapController();
        mapController.setMap(mMap);
        
        // initiallizing server communicator
        serverCom = new ServerCommunication();
        
        // initializing wificontroller
        wifiController = new WifiData();
        
        MainActivity.context = getApplicationContext();	
        iStreamFile1 = getApplicationContext().getResources().openRawResource(R.raw.locationdata);
        
        // get location suggestions from server at start of activity
        // run in a separate thread
   	 	Runnable runnable = new Runnable() {
	        public void run() {
	        	locations = serverCom.getSuggestionData(iStreamFile1);
	        }
	        
   	 	};  		
   	 	Thread getSuggestionsFromServerThread = new Thread(runnable);   	 	
   	 	getSuggestionsFromServerThread.start();
        
        double latitudex=   31.470807; 
        double longitudex=  74.409480;
        
        //
        int x = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        
        if ( x == ConnectionResult.SERVICE_MISSING || x== ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED||x==ConnectionResult.SERVICE_DISABLED){
        	Dialog dialog =(GooglePlayServicesUtil.getErrorDialog(x,this, 1));
        	dialog.show(); 
        	System.out.println("Status code = "+ x);
        }
        
        imageOfAcdBlock = BitmapFactory.decodeResource(getResources() ,R.drawable.groundfloor);    
        image =  BitmapDescriptorFactory.fromBitmap(imageOfAcdBlock) ;//an image.
   	 	bounds = new LatLng(latitudex, longitudex); // get a bounds
        
        
        
        GPSupdateInterval = 5000;
        GPSmoveInterval = 1;
        
        md = new GMapV2Direction();
        currentPosMarkerOps = new MarkerOptions();
        otherPosMarkerOps   = new MarkerOptions();
        
        
        
        // initially set map to center of LUMS acedemic block
        center= CameraUpdateFactory.newLatLng(new LatLng(31.470802,74.409446));
        zoom=CameraUpdateFactory.zoomTo(17);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean enabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledWiFi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to 
        // go to the settings
        if (!enabledGPS) {
            Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        
        locationManager.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);

        // add a marker at center
        otherPosMarkerOps.position(new LatLng(latitude,longitude)).title(latitude +" , "+longitude);
        
        // 
        otherPosMarkerOps
        .title("Ground Floor")
        .snippet("Academic Block")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        
        otherPosMarker =  mMap.addMarker(otherPosMarkerOps);
        otherPosMarker.showInfoWindow();
        //otherPosMarker.setDraggable(true);
        
        //otherPosMarkerOps.title(otherPosMarker.getPosition().latitude +" , "+otherPosMarker.getPosition().longitude);

        // adding campus map image
        addOverLayImage(image);
        
    

    }

    protected void addOverLayImage(BitmapDescriptor imagex) {
    	
    	 // Adds a ground overlay 
    	gOverlayOps = new GroundOverlayOptions()
		     .image(imagex)
		     .bearing(143)
		     .position(bounds,110,110)
		     .transparency((float) 0.1);
    	
    	 gOverLay = mMap.addGroundOverlay(gOverlayOps);

	}
    
    public void changeMapOverlayImage(int imgResourceID){
    	
    	//R.drawable.groundfloor
    	imageOfAcdBlock.recycle();
        imageOfAcdBlock = BitmapFactory.decodeResource(getResources() ,imgResourceID);    
        image =  BitmapDescriptorFactory.fromBitmap(imageOfAcdBlock) ;//an image.
        
        gOverLay.remove();
        
        gOverlayOps.image(image);
        
        gOverLay = mMap.addGroundOverlay(gOverlayOps);
            	    	
    }
    //////
    // at bottom unused functions
    //////
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.mainmenu, menu);
      return true;
    } 
    
    
    
    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		imageOfAcdBlock.recycle();
		
//		this.finish();
//		mMap.addGroundOverlay(null);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // location indoor
       /* case R.id.location_indoor:
        {
        	
        	try {
				getCurrentPosIndoor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  		
				
		}
         break;
         */
        // location outdoor 
        case R.id.location:
        {
        	
        	//String wifidata = "c8:f1:f9:a0:c9:e1,-74#c5:f9:f7:89:8a:81,-83#c8:f9:f9:a7:a5:30,-86#c8:f9:f9:a0:pa:20,-78";//"c8:f9:f9:a0:c9:e1,-74#c8:f9:f9:89:8a:81,-83#c8:f9:f9:a0:a5:30,-86#c8:f9:f9:a0:6a:20,-85#c8:f9:f9:a0:6a:21,-74#c8:f9:f9:a0:c9:e0,-74";
        	
        	String wifidata = wifiController.getWifiData(getApplicationContext(), 1);
        	
        	HttpRequestGetCurrentLoc curLoc = new HttpRequestGetCurrentLoc();
        	
        	curLoc.execute(wifidata);
        	
//        	Location location = locationManager.getLastKnownLocation(provider);
//			
//        	//mMap.setMyLocationEnabled(true);
//            //Location location = mMap.getMyLocation();
//          
//	        latitude = location.getLatitude();
//	        longitude = location.getLongitude();
//	        		        
//	        // add marker to map on given lat long for showing
//	        String lat  = (latitude+"").substring(0, 6);
//	        String lon = (longitude+"") .substring(0, 6);
//	        
//	        if (currentPosMarker != null ){
//	        	currentPosMarker.remove();
//	        }
//	        
//	        currentPosMarkerOps.position(new LatLng(latitude,longitude)).title(lat +" , "+lon);
//	       
//	        currentPosMarker =  mMap.addMarker(currentPosMarkerOps);
//	        
//	        center= CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
//	        zoom=CameraUpdateFactory.zoomTo(18);
//	        
//	        mMap.moveCamera(center);
//	        mMap.animateCamera(zoom);
	        
	        
	     }
          break;
          
        case R.id.show_route:
        {
        	
        	Intent i = new Intent(MainActivity.this, GetInput.class );
        	startActivityForResult(i, 100); // 100 is some code to identify the returning result
	
        }
         break;	
         
        case R.id.first_floor:
        {
        	changeMapOverlayImage(R.drawable.firstfloor);
        	
        	HashMap<String, Polyline> polyLinesByFloor = new HashMap<String, Polyline>();
        	polyLinesByFloor = mapController.getFloorsInBuildings().get("AcademicBlock");

        	
        	if( polyLinesByFloor != null &&  polyLinesByFloor.get("firstfloor")!= null ){
        		polyLinesByFloor.get("firstfloor").setVisible(true);
        	}
        	if(  polyLinesByFloor != null && polyLinesByFloor.get("groundfloor")!=null ){
        		polyLinesByFloor.get("groundfloor").setVisible(false);
        	}
        	
//        	otherPosMarker.remove();
//        	otherPosMarkerOps.title("First Floor");        	
//        	otherPosMarker =  mMap.addMarker(otherPosMarkerOps);
        	
    		otherPosMarker.setTitle("First Floor");
        	otherPosMarker.showInfoWindow();
        	
        	Toast.makeText(getApplicationContext(), "First Floor", Toast.LENGTH_LONG).show();
        }
        break;
        
        case R.id.ground_floor:
        {
        	changeMapOverlayImage(R.drawable.groundfloor);
        	
        	HashMap<String, Polyline> polyLinesByFloor = new HashMap<String, Polyline>();
        	polyLinesByFloor = mapController.getFloorsInBuildings().get("AcademicBlock");
        	
        	
        	if( polyLinesByFloor != null && polyLinesByFloor.get("firstfloor")!= null ){
        		polyLinesByFloor.get("firstfloor").setVisible(false);
        	}
        	if(  polyLinesByFloor != null &&  polyLinesByFloor.get("groundfloor")!=null ){
        		polyLinesByFloor.get("groundfloor").setVisible(true);
        	}
        	
//        	otherPosMarker.remove();
//        	otherPosMarkerOps.title("Ground Floor");        	
//        	otherPosMarker =  mMap.addMarker(otherPosMarkerOps);
    		otherPosMarker.setTitle("Ground Floor");
        	otherPosMarker.showInfoWindow();
        	
        	Toast.makeText(getApplicationContext(), "Ground Floor", Toast.LENGTH_LONG).show();
        }
        break;
        	
        default:
          break;
        }

        return true;
    } 
    

    
    
    
    /*
     * getting result from returning activity
     * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
     */
	@Override
    protected void onActivityResult (int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
        	 Log.i("in onReturn result","");
        	 
        	// getiing data from other activity 
             src = (String) data.getExtras().get("Src");
             dest = (String) data.getExtras().get("Dest");
             
            System.out.println(src +" : "+ dest);
	           
	   		HttpRequestGetRoute getRoute = new HttpRequestGetRoute();
	   		String input[] = {src,dest}; 
	   		getRoute.execute(input);
	   			
        }
 
    }
    
    /*
     * get current position indoor
     */
    
    protected void getCurrentPosIndoor() throws IOException {
	
    	String wifiCoOrdinates = (new WifiData()).getWifiData(getApplicationContext(), 1);
 
    	
    	
    	
    	//PointFinder pf = (new PointFinder());
    	
//    	WifiToGPSMapper wifiMapper = new WifiToGPSMapper();    	
    	//double location[] = pf.locateThePoint(wifiCoOrdinates);
//    	double location[] = wifiMapper.mapWifiToGPSUsingDiscretePointData(wifiCoOrdinates);
//    	
//        if (currentPosMarker != null ){
//        	currentPosMarker.remove();
//        }
//    	
//    	currentPosMarkerOps.position(new LatLng(location[0],location[1])).title(location[0]+","+location[1]);
//
//        currentPosMarker =  mMap.addMarker(currentPosMarkerOps);
//    	
    	
//    	System.out.println(location);
    }
    
    @Override
    protected void onResume() {

    	super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        
        System.out.println("onResume called  -- " + mMap.getMapType()); 	
    	
    	
    }
    
    @Override
    protected void onPause() {
      super.onPause();
      System.out.println("onPause called");
      locationManager.removeUpdates(this);
    }
    
    //@Override
    public void onLocationChanged(Location location) {

    	//centerOnLocation();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
      // TODO Auto-generated method stub
    	centerOnLocation();

    }
    
    public void centerOnLocation(){
    	
    	Location locationx = locationManager.getLastKnownLocation(provider);
    	
    	if (locationx != null){
	    	String lat  = (locationx.getLatitude()+"").substring(0, 6);
	        String lon = (locationx.getLongitude()+"") .substring(0, 6);
	        
	        if (currentPosMarker != null ){
	        	currentPosMarker.remove();
	        }

	        
	        currentPosMarkerOps.position(new LatLng(locationx.getLatitude(),locationx.getLongitude())).title(lat +" , "+lon);
	        
	        currentPosMarker =  mMap.addMarker(currentPosMarkerOps);
    	}
    }

    
    public void onProviderEnabled(String provider) {
      //Toast.makeText(this, "Enabled new provider " + provider,
      //Toast.LENGTH_SHORT).show();

    }

    
    public void onProviderDisabled(String provider) {
      //Toast.makeText(this, "Disabled provider " + provider,
      //Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	
    	super.onRestoreInstanceState(savedInstanceState);

    }
    
    
    private class HttpRequestGetCurrentLoc extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... arg0) {
			String url = null;
			try {
				url = "http://campusnav.jelastic.servint.net/campusnav/Server?action=getCurrentPosition&wifiData=" + URLEncoder.encode(arg0[0],"UTF-8") ;

				//url = "http://192.168.253.1:8080/campusnav/Server?action=getCurrentPosition&wifiData=" + URLEncoder.encode(arg0[0],"UTF-8") ;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			String response = serverCom.routePositionInddor(url);
			
			return response;
		}
		
		@Override
	    protected void onPostExecute(String response) {
			
	        if (currentPosMarker != null ){
	        	currentPosMarker.remove();
	        }
			
			if ( !response.equals("null") ){
				
				System.out.println(response);
				
				String tokenize[] = response.split(",");
				
				LatLng latlang = new LatLng(Double.parseDouble(tokenize[0]), Double.parseDouble(tokenize[1]));
				
				currentPosMarkerOps.position(latlang);	
				
				center= CameraUpdateFactory.newLatLng(latlang);
			}
			
			else{
	        	Location location = locationManager.getLastKnownLocation(provider);
				
	        	//mMap.setMyLocationEnabled(true);
	            //Location location = mMap.getMyLocation();
	          
		        latitude = location.getLatitude();
		        longitude = location.getLongitude();

		        currentPosMarkerOps.position(new LatLng(latitude,longitude));
		        center= CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
			}
			
	        currentPosMarker =  mMap.addMarker(currentPosMarkerOps);
	        
	        
	        zoom=CameraUpdateFactory.zoomTo(18);
	        
	        mMap.moveCamera(center);
	        mMap.animateCamera(zoom);
			
		}
    	
    	
    }

    private class HttpRequestGetRoute extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			//String url = "http://192.168.253.1:8080/campusnav/Server?action=getRoute&src=" + params[0] + "&dest=" + params[1];
			//String url = "http://192.168.253.1:8080/campusnav/Server?action=getRoute&src=" + "A-4" + "&dest=" + "SSE";	
			String url = null;
			try {
				url = "http://campusnav.jelastic.servint.net/campusnav/Server?action=getRoute&src=" + URLEncoder.encode( params[0],"UTF-8") + "&dest=" + URLEncoder.encode( params[1],"UTF-8");
				//url = "http://192.168.253.1:8080/campusnav/Server?action=getRoute&src=" + URLEncoder.encode( params[0],"UTF-8") + "&dest=" + URLEncoder.encode( params[1],"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			String response  = serverCom.routeRequest(url);
			return response;
		}
		
	    @Override
	    protected void onPostExecute(String response) {
	    	
	    	Log.i("response : ",response);
	    	
	    	try {
				mapController.parseXML(response);
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	mapController.drawRouteXML();
	    	
//	    	if (mapController.getFloorsInBuildings().get("AcademicBlock").get("firstfloor") != null && mapController.getFloorsInBuildings().get("AcademicBlock").get("groundfloor") == null ){
//	    		changeMapOverlayImage(R.drawable.firstfloor);
//	    		//mapController.getFloorsInBuildings().get("AcademicBlock").get("firstfloor").setVisible(true);
//	    	}
//	    	else if (mapController.getFloorsInBuildings().get("AcademicBlock").get("firstfloor") == null && mapController.getFloorsInBuildings().get("AcademicBlock").get("groundfloor") != null ){
//	    		changeMapOverlayImage(R.drawable.groundfloor);
//	    		//mapController.getFloorsInBuildings().get("AcademicBlock").get("groundfloor").setVisible(true);
//	    	}
	    	
	    	
	    	if ( mapController.FirstFloorAB && !mapController.GroundFloorAB ){
	    		mapController.FirstFloorAB = false;
	    		changeMapOverlayImage(R.drawable.firstfloor);
	    		mapController.FirstFloorAB = false;
	    		mapController.GroundFloorAB = false;
	    		//mapController.getFloorsInBuildings().get("AcademicBlock").get("firstfloor").setVisible(true);
	    		Toast.makeText(getApplicationContext(), "First Floor", Toast.LENGTH_LONG).show();
	    		
//	        	otherPosMarker.remove();
//	        	otherPosMarkerOps.title("First Floor");        	
//	        	otherPosMarker =  mMap.addMarker(otherPosMarkerOps);
	    		otherPosMarker.setTitle("First Floor");
	        	otherPosMarker.showInfoWindow();
	        	
	    	}
	    	else if (!mapController.FirstFloorAB && mapController.GroundFloorAB){
	    		changeMapOverlayImage(R.drawable.groundfloor);
	    		mapController.FirstFloorAB = false;
	    		mapController.GroundFloorAB = false;
	    		//mapController.getFloorsInBuildings().get("AcademicBlock").get("groundfloor").setVisible(true);
	    		Toast.makeText(getApplicationContext(), "Ground Floor", Toast.LENGTH_LONG).show();
	    		
	    		otherPosMarker.setTitle("Ground Floor");
	        	otherPosMarker.showInfoWindow();
	    	}
	    	else if ( mapController.FirstFloorAB && mapController.GroundFloorAB){
	    		changeMapOverlayImage(R.drawable.groundfloor);
	    		mapController.FirstFloorAB = false;
	    		mapController.GroundFloorAB = false;
	    		Toast.makeText(getApplicationContext(), "Continue to first floor", Toast.LENGTH_LONG).show();
	    		
	    	}
	    	
	    	//mapController.drawRoute(response);
			
	    }
	
    }
    
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            System.out.println("onPause called");
        }
        if (mMap == null) {
            return;
        }

    }
    
}



/*
 * draw path indoor
 */
//protected void drawPath(ArrayList<Double>  latitude, ArrayList<Double>  longitude ) {
//	// Instantiates a new Platiolyline object and adds points to define a rectangle
//	PolylineOptions routeLineOptions = new PolylineOptions();  	
//	for (int i=0 ;i < latitude.size();i++){
//		
//		routeLineOptions.add(new LatLng( latitude.get(i),longitude.get(i)));
//	}
//
//	// Get back the mutable Polyline
//	
//	if (routeLine != null){
//		routeLine.remove();
//		
//	}
//	
//	routeLine = mMap.addPolyline(routeLineOptions);
//	
//	routeLine.setWidth(2);
//	routeLine.setColor(Color.CYAN);
//    routeLine.setGeodesic(true);
//}
//
/*
 * get route nodes coordinates
 */
//
//private void getRouteCoordinate(String source , String destination) throws IOException {
//
//	
//	MainActivity.context = getApplicationContext();		
//	InputStream iStreamFile1 = MainActivity.context.getResources().openRawResource(R.raw.graphgroundfloorv2);
//	
//	HashMap<String, Double[]> nodeCoordinates = (new FileFormater()).readNodeCoOrdinates(iStreamFile1);
//	
//	GraphGenerator  gg = new GraphGenerator();
//	//Graph g = gg.createGraph(dir.getAbsolutePath()+"/accademicBlockGraph.txt", "LUMS-Graph");
//	
//	Graph g = gg.createGraph("accademicblockgraph", "LUMS-Graph" ,MainActivity.context);
//
//	//ArrayList<Node> n = g.findRoute("j1", "j19");
//	
//	ArrayList<Node> n = g.findRoute(source , destination);
//	
//	ArrayList<Double> latitude = new ArrayList<Double>() ;
//	ArrayList<Double>  longitude = new ArrayList<Double>() ;
//	
//	System.out.println("hash returne : "+nodeCoordinates);
//	
//	
//	int i=0;
//	for(Node _n: n){
//		System.out.print( _n.getID());
//		
//		if (nodeCoordinates.get(_n.getID()) != null){
//			latitude.add((nodeCoordinates.get(_n.getID()))[0]);
//			longitude.add((nodeCoordinates.get(_n.getID()))[1]);
//			System.out.println((nodeCoordinates.get(_n.getID()))[0]+","+(nodeCoordinates.get(_n.getID()))[1]);
//			//i++;
//		}			
//	}
//	
//	System.out.println( latitude.get(2) );
//	drawPath(latitude,longitude);
//	
//	System.out.println("success");
//	
//	
//	
//}
//