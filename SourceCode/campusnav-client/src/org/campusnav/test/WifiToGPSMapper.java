package org.campusnav.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;


import org.campusnav.salman.*;
public class WifiToGPSMapper {

//	
//	public static void main( String[] argv) throws IOException{
//		String data;
//		/*data = "c8:f9:f9:a0:c9:01,-86,c8:f9:f9:a0:67:11,-77,c8:f9:f9:a0:97:81,-88"+
//				",c8:f9:f9:89:84:51,-84"+
//				",c8:f9:f9:a0:c9:00,-85"+
//				",00:18:ba:b7:57:91,-89"+
//				",00:18:ba:b7:57:90,-90"+
//				",c8:f9:f9:89:8a:80,-51"+
//				",c8:f9:f9:89:8a:81,-61"+
//				",c8:f9:f9:89:87:41,-92"+
//				",c8:f9:f9:a0:53:40,-91"+
//				",c8:f9:f9:a0:67:10,-72";
//		data = "88:43:e1:fb:6c:41,-77,"+
//				"c8:f9:f9:89:a0:e0,-83,"+
//				"00:21:1c:78:8c:00,-84,"+
//				"88:43:e1:fb:6c:40,-79,"+
//				"c8:f9:f9:a0:4f:70,-83,"+
//				"c8:f9:f9:a0:4f:71,-83";
//		data = "c8:f9:f9:a0:c9:e1,-71,"+
//				"c8:f9:f9:a0:97:81,-89,"+
//				"c8:f9:f9:a0:6a:20,-50,"+
//				"c8:f9:f9:89:84:71,-81,"+
//				"c8:f9:f9:a0:ba:c1,-92,"+
//				"c8:f9:f9:a0:6a:21,-56,"+
//				"c8:f9:f9:a0:c4:40,-81,"+
//				"c8:f9:f9:a0:c9:e0,-75,"+
//				"c8:f9:f9:a0:c4:41,-79,"+
//				"c8:f9:f9:89:84:70,-82";
//		data = 	"c8:f9:f9:a0:c9:e1,-71,"+
//				"c8:f9:f9:a0:97:81,-89,"+
//				"c8:f9:f9:a0:6a:20,-50,"+
//				"c8:f9:f9:89:84:71,-81,"+
//				"c8:f9:f9:a0:ba:c1,-92,"+
//				"c8:f9:f9:a0:6a:21,-56,"+
//				"c8:f9:f9:a0:c4:40,-81,"+
//				"c8:f9:f9:a0:c9:e0,-75,"+
//				"c8:f9:f9:a0:c4:41,-79,"+
//				"c8:f9:f9:89:84:70,-82";*/
//		/*data = "c8:f9:f9:a0:97:81,-85#"+	//4th
//				"c8:f9:f9:a0:6a:20,-89#"+
//				"c8:f9:f9:a0:6a:21,-82#"+
//				"c8:f9:f9:89:8a:80,-71#"+
//				"c8:f9:f9:89:8a:81,-80#"+
//				"c8:f9:f9:89:87:41,-86#"+
//				"c8:f9:f9:a0:c9:e0,-84#"+
//				"c8:f9:f9:89:87:40,-85#"+
//				"c8:f9:f9:a0:4f:90,-86#"+
//				"c8:f9:f9:a0:67:10,-93";*/
//
//		/*data = "c8:f9:f9:a0:c9:e1,-74#"+	//5th
//				"c8:f9:f9:89:8a:81,-83#"+
//				"c8:f9:f9:a0:a5:30,-86#"+
//				"c8:f9:f9:a0:6a:20,-85#"+
//				"c8:f9:f9:a0:6a:21,-74#"+
//				"c8:f9:f9:a0:c9:e0,-74";*/
//	/*	data = "c8:f9:f9:a0:c9:e1,-71,"+		//6th
//				"c8:f9:f9:a0:97:81,-89,"+
//				"c8:f9:f9:a0:6a:20,-50,"+
//				"c8:f9:f9:89:84:71,-81,"+
//				"c8:f9:f9:a0:ba:c1,-92,"+
//				"c8:f9:f9:a0:6a:21,-56,"+
//				"c8:f9:f9:a0:c4:40,-81,"+
//				"c8:f9:f9:a0:c9:e0,-75,"+
//				"c8:f9:f9:a0:c4:41,-79,"+
//				"c8:f9:f9:89:84:70,-82";*/
//		/*
//		 * 31.470656935414176 74.4094771313514 -.5
//			31.470998091941244 74.40962261611232 -1
//			31.470985306803637 74.40963379812375 -1.5
//			31.47098932703125 74.40962417892305  -2
//		 */
//		/*data = "c8:f9:f9:a0:c9:e1,-82#"+		//7th
//				"c8:f9:f9:a0:6a:20,-44#"+
//				"c8:f9:f9:89:84:71,-79#"+
//				"c8:f9:f9:a0:6a:21,-44#"+
//				"c8:f9:f9:a0:c4:40,-70#"+
//				"c8:f9:f9:89:9e:c1,-91#"+
//				"c8:f9:f9:a0:c4:41,-76#"+
//				"c8:f9:f9:89:84:70,-80#"+
//				"c8:f9:f9:a0:c9:e0,-85";*/
//	
//
//
//		data = "c8:f9:f9:a0:c9:e1,-74#"+		//3rd
//				"c8:f9:f9:a0:a5:30,-84#"+
//				"c8:f9:f9:a0:6a:20,-66#"+
//				"c8:f9:f9:a0:a5:31,-88#"+
//				"c8:f9:f9:a0:4f:70,-89#"+
//				"c8:f9:f9:a0:6a:21,-70#"+
//				"c8:f9:f9:89:8a:80,-85#"+
//				"c8:f9:f9:89:8a:81,-88#"+
//				"c8:f9:f9:89:87:41,-91#"+
//				"c8:f9:f9:a0:c9:e0,-75#"+
//				"c8:f9:f9:a0:4f:90,-89";
//
//		/*data = "c8:f9:f9:a0:4f:e1,-82#"+
//				"c8:f9:f9:a0:c9:00,-66#"+
//				"00:18:ba:b7:57:91,-84#"+
//				"00:18:ba:b7:57:90,-84#"+
//				"c8:f9:f9:89:8a:80,-62#"+
//				"c8:f9:f9:89:8a:81,-61#"+
//				"c8:f9:f9:a0:4f:e0,-84#"+
//				"c8:f9:f9:a0:53:41,-79#"+
//				"c8:f9:f9:a0:67:10,-76";*/
//
//
//
//
//		WifiToGPSMapper pf = new WifiToGPSMapper();
//		//System.out.println( pf.locateThePoint(data)[0]+","+pf.locateThePoint(data)[1] );
//		double a[] = pf.mapWifiToGPSUsingDiscretePointData(data);
//		System.out.println(a[0]+","+a[1]);
//	}
	
	
	
	double[] getMax( HashMap<WiFiCoordinate,double[]> inData ){
		
		double result[] = {0,-1,0,0,0};
		WiFiCoordinate key = null;
		for( WiFiCoordinate w: inData.keySet() ){
			if( inData.get(w)[1] > result[1]){
				result = inData.get(w);
				key = w;
				
			}
		}
		if( key != null ){
			inData.remove(key);
		}
		return result;
	}
	public double[] mapWifiToGPSUsingDiscretePointData( String data){
		
		WiFiCoordinate currCoordinate = new WiFiCoordinate(data, "#", ",");
		
		 HashMap<Integer, WiFiCoordinate> map =new FileToResourceReader().discretePointFileReader("file_discrete.csv");
		 HashMap<WiFiCoordinate,double[]> result = new HashMap<WiFiCoordinate, double[]>();
		 for( Integer key : map.keySet()){
			 double comparedResult[] = map.get(key).compare(currCoordinate);
			 if( comparedResult.length < 2){
				 System.out.println("blah blah");
				 continue;
			 }else if( comparedResult[1] > 1 ){
				 System.out.println(comparedResult);
				 result.put(map.get(key), comparedResult);
			 }
		 }
		 
		 //
		 String keyForMin = "";
		 double value=0;
		 double minResult[]= {0,0};
		 ArrayList<double[]> forMean = new ArrayList<double[]>();
		 for(int i=0; i < 5; i++){
			 
			 double tmp[] = getMax(result);
			 forMean.add(tmp);
		 }
		 
		 for(double d[]: forMean ){
			 minResult[0] += d[3];
			 minResult[1] += d[4];
		 }
		 minResult[0] /= forMean.size();
		 minResult[1] /= forMean.size();

		 return minResult;/*
		 for( WiFiCoordinate key : result.keySet() ){
			 
			 if( value == 0 || result.get(key)[0] < value ){
				 value=result.get(key)[0];
				 minResult = result.get(key);
			 }
		 }
		System.out.println(minResult);
		 double resultToSend[] = { minResult[3], minResult[4]};
		return resultToSend;//to edit must*/
	}
	public double[] locateThePoint(String data) throws IOException{
		
		FileToResourceReader ff = new FileToResourceReader();
		HashMap<String,HashMap<String,String>> map = ff.fileRead("file3.csv");
		HashMap<String,Integer> inDataMap = new HashMap<String,Integer>();
		HashMap<String,Integer> valuesMap = new HashMap<String,Integer>();
		String inData[] = data.split(",");
		int allowedDev=1;
		double percisoonRadius=.5;
		for( int i=0; i< inData.length; i+= 2){
			//System.out.print(inData[i]+","+ inData[i+1]+"#");
			inDataMap.put(inData[i], Integer.parseInt( inData[i+1]) );
			if( map.get(inData[i]) == null ){
				System.out.println("mac address: "+inData[i]+" is not found");
			}else{
				
				System.out.println(map.get(inData[i]).keySet().toString());
				
			}
		}
		String key="";
		int tmpVal=0;
		
		for(String MACA: inDataMap.keySet() ){
			
			for(String values: map.get(MACA).values()){
				
				String valArray[] = values.split(",");
				tmpVal = Integer.parseInt( valArray[1] );
				key = valArray[2]+","+valArray[3];
				if( Math.abs( inDataMap.get(MACA)-tmpVal ) <= allowedDev ){
					if( valuesMap.get(key) != null ){
						int val=valuesMap.get(key) + 1;
						valuesMap.remove(key );
						valuesMap.put(key, val);
					}else{
						valuesMap.put(key,1);
					}
				}
			}
		}
		
		//System.out.println(valuesMap);
		System.out.println(valuesMap.size());
		HashMap<String, Double[]> coordinates =  new HashMap<String, Double[]>();
		for( String key1: valuesMap.keySet() ){
			String coordinatesStr[] = key1.split(",");
			Double tmp[] = {Math.toRadians(Double.parseDouble(coordinatesStr[0])), Math.toRadians(Double.parseDouble(coordinatesStr[1]))};
			coordinates.put(key1, tmp);
		}
		
		HashMap<String, Double[]> currCircle=null, oldCircle=null; 
	
		int pointsInCurrCircle, pointsInOldCircle=-1;
		for( String key1: valuesMap.keySet() ){
			pointsInCurrCircle = 0;
			currCircle =  new HashMap<String, Double[]>();
			currCircle.put(key1, coordinates.get(key1));
			for( String key2: valuesMap.keySet() ){
				
				if(  getDistanceBW2GPSPoints( coordinates.get(key1), coordinates.get(key2) ) <= percisoonRadius ){
					
					pointsInCurrCircle += valuesMap.get(key2);
					currCircle.put(key2, coordinates.get(key2));
				}
			}
			
			if( pointsInOldCircle < 0 || pointsInOldCircle < pointsInCurrCircle ){
				pointsInOldCircle = pointsInCurrCircle;
				oldCircle = currCircle;
			}
		}
		System.out.println(oldCircle.size()+" "+pointsInOldCircle);
		System.out.println(oldCircle);
		double mean[] = getMean(oldCircle);
		System.out.println( Math.toDegrees( mean[0] )+" "+ Math.toDegrees( mean[1] ) );
		double s[]={1.1,2.1};
		
		return s;//to change
	}
	
public double[] locateThePoint2(String data) throws IOException{
		
		FileToResourceReader ff = new FileToResourceReader();
		HashMap<String,HashMap<String,String>> map = ff.fileRead("file3.csv");
		HashMap<String,Integer> inDataMap = new HashMap<String,Integer>();
		HashMap<String,Integer> valuesMap = new HashMap<String,Integer>();
		String inData[] = data.split(",");
		int allowedDev=1;
		double percisoonRadius=.5;
		for( int i=0; i< inData.length; i+= 2){
			//System.out.print(inData[i]+","+ inData[i+1]+"#");
			inDataMap.put(inData[i], Integer.parseInt( inData[i+1]) );
			if( map.get(inData[i]) == null ){
				System.out.println("mac address: "+inData[i]+" is not found");
			}else{
				
				System.out.println(map.get(inData[i]).keySet().toString());
				
			}
		}
		String key="";
		int tmpVal=0;
		
		for(String MACA: inDataMap.keySet() ){
			
			for(String values: map.get(MACA).values()){
				
				String valArray[] = values.split(",");
				tmpVal = Integer.parseInt( valArray[1] );
				key = valArray[2]+","+valArray[3];
				
				if( Math.abs( inDataMap.get(MACA)-tmpVal ) <= allowedDev ){
					if( valuesMap.get(key) != null ){
						int val=valuesMap.get(key) + 1;
						valuesMap.remove(key );
						valuesMap.put(key, val);
					}else{
						valuesMap.put(key,1);
					}
				}
			}
		}
		
		//System.out.println(valuesMap);
		System.out.println(valuesMap.size());
		HashMap<String, Double[]> coordinates =  new HashMap<String, Double[]>();
		for( String key1: valuesMap.keySet() ){
			String coordinatesStr[] = key1.split(",");
			Double tmp[] = {Math.toRadians(Double.parseDouble(coordinatesStr[0])), Math.toRadians(Double.parseDouble(coordinatesStr[1]))};
			coordinates.put(key1, tmp);
		}
		
		HashMap<String, Double[]> currCircle=null, oldCircle=null; 
	
		int pointsInCurrCircle, pointsInOldCircle=-1;
		for( String key1: valuesMap.keySet() ){
			pointsInCurrCircle = 0;
			currCircle =  new HashMap<String, Double[]>();
			currCircle.put(key1, coordinates.get(key1));
			for( String key2: valuesMap.keySet() ){
				
				if(  getDistanceBW2GPSPoints( coordinates.get(key1), coordinates.get(key2) ) <= percisoonRadius ){
					
					pointsInCurrCircle += valuesMap.get(key2);
					currCircle.put(key2, coordinates.get(key2));
				}
			}
			
			if( pointsInOldCircle < 0 || pointsInOldCircle < pointsInCurrCircle ){
				pointsInOldCircle = pointsInCurrCircle;
				oldCircle = currCircle;
			}
		}
		System.out.println(oldCircle.size()+" "+pointsInOldCircle);
		System.out.println(oldCircle);
		double mean[] = getMean(oldCircle);
		System.out.println( Math.toDegrees( mean[0] )+" "+ Math.toDegrees( mean[1] ) );
		double s[]={1.1,2.1};
		
		return s;//to change
	}
	
	public double[] getMin( HashMap<String,Double> in){
		//System.out.println("hello");
		System.out.println(in);
		double oldValue = -1;
		String keyValue="";
		for( String key: in.keySet() ){
			
			if( oldValue < 0 || in.get(key) < oldValue){
				System.out.println(key);
				keyValue = key;
				oldValue = in.get(key);
			}
		}
		System.out.println(keyValue);
		String tmp[] = keyValue.split(",");
		//System.out.println(Double.parseDouble(tmp[2])+" "+Double.parseDouble(tmp[3]));
		double array[] = { Double.parseDouble(tmp[2]),Double.parseDouble(tmp[3])};
		return array;
	}
	
	public static final double EARTH_DIAMETER = 6371.01 * 1000; //meters
	public double getDistanceBW2GPSPoints(Double[] p1, Double[] p2 ){

	        double diffLongitudes = p2[1] - p1[1];

	        //spherical law of cosines
	// double c = Math.acos(
	// Math.sin(Math.toRadians(forePoint.getLatitude())) * Math.sin(Math.toRadians(standPoint.getLatitude()))
	// + Math.cos(Math.toRadians(forePoint.getLatitude())) * Math.cos(Math.toRadians(standPoint.getLatitude())) * Math.cos(diffLongitudes)
	// );

	        double slat = p1[0];
	        double flat = p2[0];
	     
	        //Vincenty formula
	        double c = Math.sqrt(Math.pow(Math.cos(flat) * Math.sin(diffLongitudes), 2d) + Math.pow(Math.cos(slat) * Math.sin(flat) - Math.sin(slat) * Math.cos(flat) * Math.cos(diffLongitudes), 2d));
	        c = c / (Math.sin(slat) * Math.sin(flat) + Math.cos(slat) * Math.cos(flat) * Math.cos(diffLongitudes));
	        c = Math.atan(c);

	        return EARTH_DIAMETER * c;
	}
	
	double[] getMean( HashMap<String,Double[]> in ){
		double mean[] = {0,0};
		for(Double[] d: in.values() ){
			mean[0] += d[0];
			mean[1] += d[1];
		}
		
		mean[0] = mean[0]/in.size();
		mean[1] = mean[1]/in.size();
		return mean;
	}
}
