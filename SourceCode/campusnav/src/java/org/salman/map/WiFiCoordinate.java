package org.salman.map;


import java.util.HashMap;
import java.util.Set;

//import android.location.Location;

public class WiFiCoordinate {

	/*
	 * this will store the router's signal strengths and mac addresses for a location
	 */
	private HashMap<String,Integer> routers;	 
	private String routerDelimiter,
			signalDelimiter;
	final private String DEFAULT_ROUTER_DELIMITER = "#",
			DEFAULT_SIGNAL_DELIMITER =","; 
	private Location location;
	public double coordinate[];
	
	public WiFiCoordinate(String _routerDelimiter, String _signalDelimiter) {
		
		this.routerDelimiter = _routerDelimiter;
		this.signalDelimiter = _signalDelimiter;
		routers = new HashMap<String, Integer>();
		double tmp[] =  {0,0};
		coordinate =  tmp;
	}
	
	
	/*
	 * assuming all the data is formated correctly and will not result in null value.
	 * format: MAC Address<signalDelimiter>signal strength<routerDelimiter>* 
	 * All the delimiters are single characters.
	 * signal strength is an integer value that in string form and can be parsed to integer.
	 */
	
	public WiFiCoordinate( String rawRouterData, String _routerDelimiter, String _signalDelimiter ) {
		
		this.routerDelimiter = _routerDelimiter;
		this.signalDelimiter = _signalDelimiter;
		location = null;
		double tmp[] =  {0,0};
		coordinate =  tmp;
		routers = new HashMap<String, Integer>();
		int strength;
		String routersData[] = rawRouterData.split(routerDelimiter);
		
		for( String router: routersData ){
			
			String routerData[] = router.split(signalDelimiter);
			
			strength = Integer.parseInt(routerData[1]);
			routers.put(routerData[0], strength);
			
		}
	}
	
	public WiFiCoordinate( String rawRouterData, String _routerDelimiter, String _signalDelimiter , Location _location) {
		
		this.routerDelimiter = _routerDelimiter;
		this.signalDelimiter = _signalDelimiter;
		this.location = _location;
		double tmp[] =  {0,0};
		coordinate =  tmp;
		routers = new HashMap<String, Integer>();
		int strength;
		String routersData[] = rawRouterData.split(routerDelimiter);
		
		for( String router: routersData ){
			
			String routerData[] = router.split(signalDelimiter);
			
			strength = Integer.parseInt(routerData[1]);
			routers.put(routerData[0], strength);
			
		}
	}
	
	public WiFiCoordinate( ) {
		
		this.routerDelimiter = DEFAULT_ROUTER_DELIMITER;
		this.signalDelimiter = DEFAULT_SIGNAL_DELIMITER;
		location = null;
		double tmp[] =  {0,0};
		coordinate =  tmp;
		routers = new HashMap<String, Integer>();
	}
	
	public WiFiCoordinate( Location _location ) {
		
		this.routerDelimiter = DEFAULT_ROUTER_DELIMITER;
		this.signalDelimiter = DEFAULT_SIGNAL_DELIMITER;
		this.location = _location;
		routers = new HashMap<String, Integer>();
	}

	public void put(String MACAddress, Integer _strength ){
		routers.put(MACAddress, _strength);
	}
	
	public Integer getSignalStrengthForMACAddress( String MACAddress ){
		
		return routers.get(MACAddress);
	}
	
	public boolean contains( String MACAddress ){
		
		return routers.containsKey(MACAddress);
	}
	
	public boolean equlas( WiFiCoordinate wifiCoordinate ){
		
		if( routers.size() == wifiCoordinate.getRouters().size() ){
			
			for( String key:routers.keySet()){
				
				if( !routers.get(key).equals( wifiCoordinate.getRouters().get(key) ) ){
					return false;
				}
			}
		}
		return false;
	}
	
	public HashMap<String,Integer> getRouters(){
		return routers;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public void setLocation( double latitude, double longitude ){
		
		if( location == null ){
			
			location = new Location("provider");//have to lookup what is providere
		}
		location.setLatitude(latitude);
		location.setLongitude(longitude);
	}
	
	public Set<String> getKeySet(){
		return routers.keySet();
		
	}
	
	public int getVectorSize(){
		return routers.size();
	}
	public String toString(){
		return routers.toString()+"&"+coordinate[0]+","+coordinate[1];
	}
	
	public double[] compare( WiFiCoordinate wifiCoordinante ){
		
		int compareValue= 0;
		int numOfCommonDim = 0;
		for( String key : routers.keySet()){
			
			if( wifiCoordinante.getSignalStrengthForMACAddress(key) != null ){
				compareValue += Math.abs(wifiCoordinante.getSignalStrengthForMACAddress(key)-routers.get(key));
				numOfCommonDim++;
				//System.out.println(" commDim: "+numOfCommonDim+"\n");
			}
		}
		
		int diffrenceInDim = routers.size()-wifiCoordinante.getRouters().size();
		if( numOfCommonDim == 0){
			compareValue = Integer.MAX_VALUE;
		}
		double compareResult[] = { compareValue, numOfCommonDim, diffrenceInDim, coordinate[0], coordinate[1]};
		 System.out.println( compareResult[0]+","+compareResult[1]+","+compareResult[2]+","+compareResult[3]+","+compareResult[4]+"\n");
		return compareResult ;
	}
}
