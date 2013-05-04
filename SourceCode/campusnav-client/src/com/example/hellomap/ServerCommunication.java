package com.example.hellomap;


import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.campusnav.salman.FileFormater;
import org.w3c.dom.Document;

import com.carouseldemo.main.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class ServerCommunication {
	
	String suggestions[];
	
	/*
	 * get suggection string
	 */
	public String[] getSuggestionData(InputStream iStreamFile1){
		
		
		if (suggestions != null){
			return suggestions;
		}
		else {
			FileFormater f1 = new FileFormater();		
			
			//InputStream iStreamFile1 = MainActivity.context.getResources().openRawResource(R.raw.graphgroundfloorv2);
			
			try {
				suggestions = f1.getAllLocations("xxx", ",", iStreamFile1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return suggestions;
	}

	
//    private class HttpRequestGetRoute extends AsyncTask<String, Void, String>{
//
//		@Override
//		protected String doInBackground(String... params) {
//			String url = "192.168.253.1:8080/campusnav/Server?src=" + params[0] + "&dest=" + params[1];
//			String response  = routeRequest(url);
//			return response;
//		}
//		
//	    @Override
//	    protected void onPostExecute(String response) {
//	    	
//	    	
//			
//	    }
//    	
//    	
//    	
//    	
//    }
	
	/*
	 * connect to server and return an input stream
	 */

	public InputStream OpenHttpConnection(String urlString)throws IOException
    {
		
		
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(urlString);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            return in;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;     
    }

	/*
	 * request to server on specified url 
	 * and return a string response
	 */
	
	 String routeRequest(String URL){        
	        InputStream in = null;        
	        String response = null;
	        try {
	        	
	            in = OpenHttpConnection(URL);
	            if(in == null){ throw new NullPointerException(); }
	            response = convertStreamToString(in);
	            in.close();
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	            Log.e("HttpConnectivity",URL);
	        }
	        return response;                
	    }
	 
	 /*
	  * request current pos
	  */
	 
		
	 String routePositionInddor(String URL){        
	        InputStream in = null;        
	        String response = null;
	        try {
	        	
	            in = OpenHttpConnection(URL);
	            if(in == null){ throw new NullPointerException(); }
	            response = convertStreamToString(in);
	            in.close();
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	            Log.e("HttpConnectivity",URL);
	        }
	        return response;                
	    }
	 
	 
	 
	 /*
	  *  input stream to string conversion
	  *  source : http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
	  */
	 private String convertStreamToString(java.io.InputStream is) {
		    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		    return s.hasNext() ? s.next() : "";
	 }
	
}

   