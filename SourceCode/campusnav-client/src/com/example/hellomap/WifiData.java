package com.example.hellomap;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.TextView;

public class WifiData {
	
	public String getWifiData(Context context, int LocNo)
	{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	    HashMap<String,Integer> map = new HashMap<String, Integer>();
	    String sentence="";
	    for (int j=0; j < 20; j++){
		    wifiManager.startScan();
		    
		    List<ScanResult> results= wifiManager.getScanResults ();
		    
		    
		    String textOut="", key;
		    for (int i=0; i<results.size();i++)
		    {
		    	key = results.get(i).BSSID+"#"+results.get(i).SSID;
		    	if( map.get( key ) != null ){
		    		int level = (int)(map.get( key )+results.get(i).level)/2;
		    		map.remove(key);
		    		map.put(key, level);
		    	}else{
		    		map.put(key, results.get(i).level);
		    		
		    	}
		    	/*sentence+=LocNo +","+ results.get(i).SSID+",";
		    	sentence+=results.get(i).BSSID+",";
		    	sentence+=results.get(i).level+"";
		    	sentence+="\n";
		    	
		    	textOut+="SSID : "+results.get(i).SSID+"\n";
		    	textOut+="BSSID : "+results.get(i).BSSID+"\n";
		    	textOut+="Level : "+results.get(i).level+"\n";
		    	textOut+="____________________________________\n";*/
		    	
		    }

	    //tv.setText(textOut);
	    }
	    for( String key: map.keySet()){
	    	String bssid = key.split("#")[0];
	    	String ssid = key.split("#")[1];
//		    sentence+=LocNo +","+ ssid+",";
//	    	sentence+=bssid+",";
//	    	sentence+=map.get(key)+"";
//	    	sentence+="\n";
	    	
	    	sentence += bssid+","+map.get(key)+"#";
	    }
	    
	    sentence = sentence.substring(0, sentence.length()-1);
	    return sentence;
	}

}
