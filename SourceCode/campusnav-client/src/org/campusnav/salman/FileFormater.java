package org.campusnav.salman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.carouseldemo.main.R;
import com.example.hellomap.MainActivity;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import android.util.Log;

public class FileFormater{
	
//	public static void main( String[] argv){
//		
//		try {
//			//(new FileFormater()).writeCoordinates();
//			(new FileFormater()).fileWriteFormate();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	double coordinate[][]={
							{0,31.471148,74.409404,31.470975,74.409129},
							{0,31.470933,74.409174,31.471095,74.409454},
							{0,31.471053,74.409488,31.470883,74.409216},
							{0,31.470723,74.409098,31.470547,74.409252},
							{0,31.470569,74.409349,31.470790,74.409145},
							{0,31.470842,74.409215,31.470611,74.409421},
							{0,31.470466,74.409581,31.470637,74.409849},
							{0,31.470706,74.409793,31.470536,74.409515},
							{0,31.470603,74.409458,31.470777,74.409728},
							{0,31.470930,74.409865,31.471109,74.409706},
							{0,31.471086,74.409609,31.470851,74.409806},
							{0,31.470814,31.470926,74.409472,74.409620},
							{0,31.470811,74.409746,31.471042,74.409538},
							{0,31.470787,74.409717,31.470798,74.409608},
							{0,31.471024,74.409511,31.470943,74.409491},
							{0,31.470856,74.409257,31.470846,74.409343},
							{0,31.470619,74.409441,31.470719,74.409453}
						};
	
	String MACAddress[]={
							"c8:f9:f9:a0:c9:01",
							"c8:f9:f9:a0:67:11",
							"c8:f9:f9:a0:97:81",
							"c8:f9:f9:89:84:51",
							"c8:f9:f9:a0:c9:00",
							"00:18:ba:b7:57:91",
							"00:18:ba:b7:57:90",
							"c8:f9:f9:89:8a:80",
							"c8:f9:f9:89:8a:81",
							"c8:f9:f9:89:87:41",
							"c8:f9:f9:a0:53:40",
							"c8:f9:f9:a0:67:10",
							"c8:f9:f9:89:84:50",
							"c8:f9:f9:89:87:40",
							"c8:f9:f9:a0:6a:20",
							"c8:f9:f9:a0:6a:21",
							"c8:f9:f9:a0:4f:e1",
							"c8:f9:f9:a0:4f:e0",
							"c8:f9:f9:a0:53:41",
							"c8:f9:f9:a0:4f:91",
							"c8:f9:f9:a0:4f:90",
							"c8:f9:f9:89:84:70",
							"c8:f9:f9:a0:97:80",
							"c8:f9:f9:89:84:71",
							"c8:f9:f9:89:87:60",
							"c8:f9:f9:a0:c4:40",
							"c8:f9:f9:a0:c4:41",
							"c8:f9:f9:89:87:61",
							"00:24:c4:ab:7e:b1",
							"00:18:ba:b7:5c:11",
							"00:18:ba:b7:5c:10",
							"c8:f9:f9:a0:cc:b0",
							"c8:f9:f9:a0:c9:e1",
							"c8:f9:f9:a0:ba:c1",
							"c8:f9:f9:a0:ba:c0",
							"c8:f9:f9:a0:c9:e0",
							"c8:f9:f9:a0:a5:30",
							"c8:f9:f9:89:9e:c0",
							"c8:f9:f9:89:9e:c1",
							"c8:f9:f9:a0:51:21",
							"c8:f9:f9:a0:51:20",
							"c8:f9:f9:a0:a5:31",
							"00:3a:99:74:92:80",
							"c8:f9:f9:a0:4e:e1",
							"c8:f9:f9:a0:4e:e0",
							"c8:f9:f9:a0:6b:80",
							"c8:f9:f9:a0:6b:81",
							"c8:f9:f9:a0:4f:71",
							"c8:f9:f9:a0:4f:70",
							"c8:f9:f9:89:a0:e0",
							"00:24:c4:ab:7e:b0"
						};
	
	public void writeCoordinates() throws IOException{
		
		int trajectoryIndex = 0;
		FileReader fr = new FileReader("file1.csv");
		BufferedReader br = new BufferedReader(fr);
		FileWriter fw = new FileWriter("file2.csv");
		String newLine;
		
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		boolean bit = false;
		
		newLine = br.readLine();
		ArrayList<String> lineData = new ArrayList<String>();
		lineData.add(newLine);
		data.add(lineData);
		
		while(  true ){
			
			if( trajectoryIndex == 17){
		        break;
		    }
		    /*if( trajectoryIndex == 13){
		            continue;
		    }*/
			newLine = br.readLine();
			//System.out.println(newLine+"\n");
			if( newLine == null ){
				break;
			}
			lineData = data.get(data.size()-1);
			String line[]= newLine.split(",");
			/*lineData = new ArrayList<String>();
			data.add(lineData);
			lineData.add(newLine);*/
			
			if( !( "Location".equals(line[0]) ) ){
				//System.out.println(line[0]+"--"+lineData.get(0).split(",")[0] );
				while( true ){
					
					if( newLine.split(",")[0].equals( (lineData.get(0).split(","))[0]) ){
						//System.out.println(newLine+"--"+lineData.get(0).split(",")[0] );
						lineData.add(newLine);
						newLine = br.readLine();
						if( newLine == null || newLine.equals(",,,")){
							break;
						}
						//System.out.println(newLine);
					}else{
						break;
					}
				}
			}
			
			lineData = new ArrayList<String>();
			lineData.add(newLine);
			data.add(lineData);
			
			//System.out.println(data.get( data.size()-1).get(0).split(",")[0]);
			if( "Location".equals( data.get( data.size()-1).get(0).split(",")[0]) && bit ){ 
				
				//System.out.println("success");
		        int numOfPoints = data.size();
		       //System.out.println("size "+numOfPoints);
		      //System.out.println(coordinate[trajectoryIndex][3]-coordinate[trajectoryIndex][1]);
		        double dn = (coordinate[trajectoryIndex][3]-coordinate[trajectoryIndex][1])/(numOfPoints-1);
		        //System.out.println(dn);
		        double m=(coordinate[trajectoryIndex][2]-coordinate[trajectoryIndex][4])/(coordinate[trajectoryIndex][1]-coordinate[trajectoryIndex][3]);
		        //System.out.println(m*dn);
		        double c= coordinate[trajectoryIndex][2]-m*coordinate[trajectoryIndex][1];
		       // System.out.println(c);
		        fw.write( data.get(0).get(0)+"\n" );
		        for(int index=1; index < numOfPoints-1; index++){
		        	//System.out.println("hello");
		        	//System.out.println("index: "+index);
		            
		            
		        	
		            	double lat = coordinate[trajectoryIndex][1]+dn*(index-1);
		            	//System.out.println(coordinate[trajectoryIndex][1]+" "+dn+" "+dn*(index-1)+" lat: "+lat);
			            double log = m*lat+c;
			            for( int i=0; i < data.get(index).size(); i++){
			            	System.out.println("lat: "+new Double(lat).toString());
			            	fw.write(data.get(index).get(i)+","+new Double(lat).toString()+","+new Double(log).toString()+"\n");
				            
		            	
		            }

		        }
		        trajectoryIndex= trajectoryIndex + 1;
		        data = new ArrayList<ArrayList<String>>();
		       ArrayList<String> list = new ArrayList<String>();
		        list.add(newLine);
		        data.add( list );
			}
		   
		    
		    bit=true;
		}
		br.close();
		fr.close();
		fw.close();
	}
	
	public void fileWriteFormate() throws IOException{
		
		FileReader fr = new FileReader("file2.csv");
		BufferedReader br = new BufferedReader(fr);
		FileWriter fw = new FileWriter("file3.csv");
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		for(int i=0; i < MACAddress.length; i++ ){
			map.put(MACAddress[i], new ArrayList<String>() );
		}
		
		String line;
		while( true ){
			line = br.readLine();
			if(line == null ){
				break;
			}
			
			String tokenz[] = line.split(",");
			//System.out.println(tokenz.length);
			if(   tokenz.length == 6 && map.get(tokenz[2]) != null){
				map.get(tokenz[2]).add(tokenz[0]+","+tokenz[3]+","+tokenz[4]+","+tokenz[5]+"\n");
				//System.out.println(tokenz[0]+","+tokenz[3]+","+tokenz[4]+","+tokenz[5]);
				
			}
		}
		
		for( String key: map.keySet() ){
			fw.write(key+"\n");
			for(String linet:map.get(key) ){
				fw.write(linet);
			}
		}
		fw.close();
		br.close();
		fr.close();
		//System.out.println( map.get("c8:f9:f9:a0:c9:00").get(2));
	}

	public HashMap<String, HashMap<String, String>> fileRead(String path)
			throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
		
		String line;
		String router = null;
		while (true) {
			line = br.readLine();
			// System.out.println(line);
			if (line == null) {
				break;
			}
			String tokenz[] = line.split(",");
			if (tokenz.length == 1) {
				map.put(tokenz[0], new HashMap<String, String>());
				line = br.readLine();
				router = tokenz[0];
				// System.out.println(tokenz[0]);
			}
			tokenz = line.split(",");

			if (tokenz.length == 4) {
				map.get(router).put(
						tokenz[0],
						tokenz[0] + "," + tokenz[1] + "," + tokenz[2] + ","
								+ tokenz[3]);
			}
		}
		return map;
	}
	
	public HashMap<String, Double[]> readNodeCoOrdinates(InputStream is)
			throws IOException {

		//FileReader fr = new FileReader(path);
		
		//BufferedReader br = new BufferedReader(fr);
		BufferedReader br = new BufferedReader((new InputStreamReader(is)));
		HashMap<String,Double[]> map = new HashMap<String, Double[]>();
		
		String line;
		line = br.readLine();
		Double latLong[];// = {new Double(0),new Double(0)} ;
		while (true) {
			line = br.readLine();
			// System.out.println(line);
			if (line == null) {
				break;
			}
			String tokenz[] = line.split(",");
			if (tokenz.length == 5){
				
				latLong = new Double[2];
				latLong[0] = Double.parseDouble(tokenz[3]);
				latLong[1] = Double.parseDouble(tokenz[4]);
				
				map.put(tokenz[0], latLong);
				
				Log.i("file reader",tokenz[0]+","+map.get(tokenz[0])[0]+","+map.get(tokenz[0])[1]);
				
			}
		}
		is.close();
		return map;
	}
	
	public String[] getAllLocations(String file_name, String separator , InputStream iStreamFile1) throws IOException {
        FileReader fr;
        BufferedReader br;
        String result[] = null;
        String allLocations = "";
        
//        if (context == null){
//        	
//        	System.out.println("context is null");
//        }
//		InputStream iStreamFile1 = context.getResources().openRawResource(R.raw.locationsdata);

        try {
                //fr = new FileReader(file_name);
                //br = new BufferedReader(fr);
                br = new BufferedReader(new InputStreamReader(iStreamFile1));
                String line = "";
                while (true) {
                        line = br.readLine();
                        if (line == null) {
                                break;
                        }
                        allLocations += line.substring(0, line.length()-1) + separator;
                }
                // System.out.println(map);
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        result = allLocations.split(separator);
        ArrayList<String> arrayStr = new ArrayList<String>();
        for (String str : result) {
                arrayStr.add(str);
        }
        int i = 0;
        Collections.sort(arrayStr);
        for (String str : arrayStr) {
                
        		// some str contain \" at begining for unknown reasons
        		// this condition trims this \" from start of string
                if (str.length() > 1 && str.charAt(0) == '\"' ){
                	System.out.println("xx : " + str);
                	str = str.substring(1);                	
                }
                result[i] = str;
                i++;
        }
        iStreamFile1.close();
        return result;
	}
}