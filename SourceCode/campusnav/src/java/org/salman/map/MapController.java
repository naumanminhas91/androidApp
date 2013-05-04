package org.salman.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.ServletContext;

import tests.FileToResourceReader;
import tests.WifiToGPSMapper;

public class MapController {
	
	private HashMap<String,Graph> graphList;	//it contains all the graphs of indoors of the buildings
	private WifiToGPSMapper wifiToGPSMapper;	//this class will give the GPS position based on wifi data
	private FileToResourceReader resourceReader;	//this class has the ability to read files into a data structure in specific formates
	private HashMap<String, String> floorsInfo;
	/*
	 * LocationDetail will contain the standard name of the location as a key and value will be 
	 * an array of the string of length 2 [0]will contain all the possible name of the the given
	 * location and [1] will contain the block level location if location itself is block level
	 * then this [1] will be equal to null.
	 * afterwards this functionality will be replaced by the query to database.
	 */
	private HashMap<String, String[]> locationDetail;
	/*
	 * this structure holds standard location name as a key to the hash map
	 * storing id-coordinate pairs for that location.
	 * For example if the location name is A-1 then the corresponding entry
	 * to this key will be the two door's ids and their coodrdinates.
	 */
	private HashMap<String,HashMap<String, double[]>> GPSOfLocation; 
	private HashMap<String,double[]> idVsGPS;
	
	// set servlet context
        public void setContext(ServletContext contextx){
        
            context = contextx;
        }
        
        private ServletContext context;
        
	public MapController( ){}
        
	public MapController( ServletContext _context ){
		graphList = new HashMap<String,Graph>();
		
		graphList.put("AcademicBlock", new Graph("LUMS-AccademicBlock"));
		//this class creates a graph by reading a file the graph using its method createGraph
		GraphGenerator graphGenerator = new GraphGenerator(_context);

                wifiToGPSMapper = new WifiToGPSMapper(_context);
		
		resourceReader = new FileToResourceReader(_context);

                //graphGenerator.createGraph("resources\\AcademicBlock.txt", graphList.get("AcademicBlock"));//add the right file name
                graphGenerator.createGraph("AcademicBlock.txt", graphList.get("AcademicBlock"));//add the right file name

                //resourceReader.setContext(context);
                
		try {
			GPSOfLocation = resourceReader.readNodeCoordinatesMap("IdWithStdNameAndGPS.csv");//to edit have to give file name
			
			idVsGPS = new HashMap<String,double[]>();
			for( HashMap<String,double[]> m: GPSOfLocation.values()){
				for( String key: m.keySet()){
					idVsGPS.put(key, m.get(key));
				}
			}
			//System.out.println(GPSOfLocation);
			locationDetail = resourceReader.readLocationDetails("idPossibleNamesAndBlockLocation.csv");//to edit have to give file name
			//System.out.println(locationDetail);
			floorsInfo = resourceReader.readFloorNodeMap("floorInfo.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("could not erad any of the required file\n*->some error occured");
			e.printStackTrace();
		}//change to aaproperiate file name
	}
	
	//this method will return the current position of the user
	public double[] getCurrentLocation(String wifiData){
		//get the wifi infor for current position from nomi's program
		double GPSCoordinate[];
		GPSCoordinate = wifiToGPSMapper.mapWifiToGPSUsingDiscretePointData(wifiData);
		return GPSCoordinate;
	}
	
	public ArrayList<String> getDirections( String srcLocation, String destLocation ){
		
		HashMap<String,double[]> coordinatesForSrcBlockLocation = null;
		HashMap<String,double[]> coordinatesForDestBlockLocation = null;
		HashMap<String,double[]> coordinatesForSrcIndoorLocation = null;
		HashMap<String,double[]> coordinatesForDestIndoorLocation = null;

		String srcLocDetail[] = getLocationDetails(srcLocation+"&");
		String destLocDetail[] = getLocationDetails(destLocation+"&");

		if( srcLocDetail != null && srcLocDetail[0] != null ){
			coordinatesForSrcIndoorLocation = getIDVsCoordinateForLocation(srcLocDetail[0]);
		}
		if( srcLocDetail != null && srcLocDetail[1] != null ){
			coordinatesForSrcBlockLocation = getIDVsCoordinateForLocation(srcLocDetail[1]);
		}
		if( destLocDetail != null && destLocDetail[0] != null ){
			coordinatesForDestIndoorLocation = getIDVsCoordinateForLocation(destLocDetail[0]);
		}
		if( destLocDetail != null && destLocDetail[1] != null ){
			coordinatesForDestBlockLocation = getIDVsCoordinateForLocation(destLocDetail[1]);
		}
		if( srcLocDetail[1] == null || destLocDetail[1] == null ){
			//not a valid route as block level locations must exist
			return null;
		}
		ArrayList<String> result = new ArrayList<String>();
		
		//outdoorToOutdoor[0] contains the src id location at block level, 
		//outdoorToOutdoor[1] contains the dest id location at block level
		String outdoorToOutdoor[];
		if( !srcLocDetail[1].equals( destLocDetail[1] ) ){
			outdoorToOutdoor= findIdsForOptimizedPath(coordinatesForSrcBlockLocation, coordinatesForDestBlockLocation);
		}else{
			outdoorToOutdoor=null;
		}
			
		String indoorSrcToOutdoor[]=null, outdoorToindoor[]=null, indoorToIndoor[]=null;
		ArrayList<String> indoorSrcToOutdoorRoute = null, outdoorToIndoorDestRoute = null, indoorToIndoorRoute=null;
		
		if( destLocDetail != null && destLocDetail[0] != null  && outdoorToOutdoor == null ){

			indoorToIndoor = findIdsForOptimizedPath(GPSOfLocation.get(srcLocDetail[0]), GPSOfLocation.get(destLocDetail[0]) );
			indoorToIndoorRoute = graphList.get(srcLocDetail[1]).findRouteStr(indoorToIndoor[0],indoorToIndoor[1]);
			result.add(getCoordinatesFormIds(indoorToIndoorRoute) );
			result.add("<outdoor></outdoor>");
			result.add("<indoor></indoor>");
			return result;
			
		}
		if( srcLocDetail != null && srcLocDetail[0] != null && outdoorToOutdoor != null ){
			//indoor at src side
			HashMap<String,double[]> tmp = new HashMap<String,double[]>();
			tmp.put(outdoorToOutdoor[0], GPSOfLocation.get(srcLocDetail[1]).get(outdoorToOutdoor[0]));
			indoorSrcToOutdoor = findIdsForOptimizedPath(coordinatesForSrcIndoorLocation, tmp );
			indoorSrcToOutdoorRoute =  graphList.get(srcLocDetail[1]).findRouteStr(indoorSrcToOutdoor[0], 
																				indoorSrcToOutdoor[1]);
			
		}
		if( destLocDetail != null && destLocDetail[0] != null  && outdoorToOutdoor != null ){
			//indoor at dest side
			HashMap<String,double[]> tmp = new HashMap<String,double[]>();
			tmp.put(outdoorToOutdoor[1], GPSOfLocation.get(destLocDetail[1]).get(outdoorToOutdoor[1]) );
			outdoorToindoor = findIdsForOptimizedPath(tmp, coordinatesForDestIndoorLocation );
			outdoorToIndoorDestRoute = graphList.get(destLocDetail[1]).findRouteStr(outdoorToindoor[0],
																					outdoorToindoor[1]); 
		}
		
		result.add(getCoordinatesFormIds(indoorSrcToOutdoorRoute));
		String tmp ="<outdoor>"+ new Double(GPSOfLocation.get(srcLocDetail[1]).get(outdoorToOutdoor[0])[0]).toString()+
                			","+new Double(GPSOfLocation.get(srcLocDetail[1]).get(outdoorToOutdoor[0])[1]).toString()+","+
                				new Double(GPSOfLocation.get(destLocDetail[1]).get(outdoorToOutdoor[1])[0])+","+
                				new Double(GPSOfLocation.get(destLocDetail[1]).get(outdoorToOutdoor[1])[1]).toString()+
                	"</outdoor>";
		result.add( tmp);
		result.add( getCoordinatesFormIds(outdoorToIndoorDestRoute));
		return result;
	}
	
	
	public String getCoordinatesFormIds(ArrayList<String> idsList){
		if( idsList == null ){
			return "<indoor></indoor>";
		}
		String coordinates = "<indoor>";
                String lastIdentifier=null;
		double temp[];
		int index=0;
		for(String id: idsList ){
			temp = idVsGPS.get(id);
			/*if( id.toLowerCase().startsWith("j")){
				temp = GPSOfLocation.get("junction").get(id);
			}else{
				temp = GPSOfLocation.get(location).get(id);
			}*/
			if(lastIdentifier == null ){
                            lastIdentifier=floorsInfo.get(id);
                            coordinates +="<"+lastIdentifier+">";
                        }else if( lastIdentifier != null && !lastIdentifier.equals(floorsInfo.get(id)) ){
                            coordinates +="</"+lastIdentifier+">";
                            lastIdentifier = floorsInfo.get(id);
                            coordinates +="<"+lastIdentifier+">";
                        }
			coordinates += new Double(temp[0] ).toString()+",";
			coordinates += new Double( temp[1] ).toString()+",";
		}
                coordinates = coordinates.substring(0, coordinates.length()-1)+"</"+lastIdentifier+"></indoor>";
		return coordinates;
	}
	
	
	public double[] getCoordinatesFormIds( String[] ids, String location){
		if( ids == null || location == null ){
			return null;
		}
		double coordinates[] = new double[ids.length*2];
		double temp[];
		int index=0;
		for(String id: ids ){
			temp = GPSOfLocation.get(location).get(id);
			coordinates[index]   = temp[0];
			coordinates[++index] = temp[1];
			++index;
		}
		return coordinates;
	}
	/*
	 * this method returns the id name of the location the length of the array is
	 * two, fist must contain the id name of the inside location and second is the id name or
	 * block level location. if any of them is null it means that location is not 
	 * available of is invalid.
	 */
	public String[] getLocationDetails( String locatioName){
		
		//{std_indoor,std_block_level}
		//get std_indoor and std_block_level from db like query
		String locationDetailArray[] = {null, null};
		for( String stdName:locationDetail.keySet() ){
			//System.out.println(locationDetail.get(stdName)[0]);
			if( locationDetail.get(stdName)[0].contains(locatioName) ){
				if( locationDetail.get(stdName)[1] == null ){
					locationDetailArray[0] = locationDetail.get(stdName)[1];
					locationDetailArray[1] = stdName;
				}else{
					locationDetailArray[0] = stdName;
					locationDetailArray[1] = locationDetail.get(stdName)[1];
				}
				break;
			}
		}
		return locationDetailArray;
	}
	
	//for given location name this function returns the GPS coordinates of the location( location id)
	public HashMap<String,double[]> getIDVsCoordinateForLocation( String locationName){
		
		return GPSOfLocation.get(locationName);
	}
	
	/*
	 * this method find the coordinates for the source and destination location
	 * so that the path is optimized and minimal.
	 */
	public String[] findIdsForOptimizedPath(HashMap<String,double[]> src, HashMap<String,double[]> dest){
		double srcCoordinate[] = {0,0};
		double destCoordinate[] = {0,0};
		double bestSrcCoordinate[] = {0,0};
		double bestDestCoordinate[] = {0,0};
		double smallerDistance= Double.MAX_VALUE;
		if( src == null || dest == null){
			return null;
		}
		String idOfBestSrcAndDest[]={null,null};
		for( String id1:src.keySet() ){
			for( String id2: dest.keySet() ){
				srcCoordinate = src.get(id1);
				destCoordinate = dest.get(id2);
				if( GPSCalculationHelper.getDistanceBW2GPSPoints(srcCoordinate, destCoordinate)< smallerDistance){
					smallerDistance = GPSCalculationHelper.getDistanceBW2GPSPoints(srcCoordinate, destCoordinate);
					bestSrcCoordinate = srcCoordinate;
					bestDestCoordinate = destCoordinate;
					idOfBestSrcAndDest[0] = id1;
					idOfBestSrcAndDest[1] = id2;
				}
			}
		}
		//double result[] = {bestSrcCoordinate[0],bestSrcCoordinate[1],bestDestCoordinate[0],bestDestCoordinate[1]};
		//bestCoordniates.add(bestSrcCoordinate);
		//bestCoordniates.add(bestDestCoordinate);
		return idOfBestSrcAndDest;
	}
}
