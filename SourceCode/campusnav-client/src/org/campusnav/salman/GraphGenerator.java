package org.campusnav.salman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.carouseldemo.main.R;
import com.example.hellomap.MainActivity;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

public class GraphGenerator {

	Graph graph;
	String listSeparator,
	nodeSeparator;
	final String DEFAULT_LIST_SEPARATOR="-",
			DEFAULT_NODE_SEPARATOR=",";
	
	public GraphGenerator(){
		graph=null;
		listSeparator = DEFAULT_LIST_SEPARATOR;
		nodeSeparator = DEFAULT_NODE_SEPARATOR;
	}
	
	
	public Graph createGraph( String fileName, Graph _graph , Context context){
		
		this.graph = _graph;
		return createGraph(fileName, _graph.getName(),context);
	}
	
	public Graph createGraph( String fileName, Graph _graph, String _listSeparator, 
		String _nodeSeparator,Context context ){
		
		this.graph = _graph;
		this.listSeparator = _listSeparator;
		this.nodeSeparator = _nodeSeparator;
		return createGraph(fileName, _graph.getName(),context);
	}
	
	public Graph createGraph( String fileName, String graphName,Context context ){
		
				
		if( graph == null ){
			graph = new Graph(graphName);
		}
		FileReader fileReader;
		BufferedReader bufferedReader;
		try {
			
			// file to open for reading from resources folder
			InputStream iStreamFile1 = context.getResources().openRawResource(R.raw.accademicblockgraph);
			
			//fileReader = new FileReader(fileName);
			//bufferedReader = new BufferedReader(fileReader);
			
			bufferedReader = new BufferedReader((new InputStreamReader(iStreamFile1)));
			
			String line = "";
			String nodeStr1[], nodeStr2[];
			//for holding data read from file
			ArrayList<String> graphStr = new ArrayList<String>();
			
			//reading data from file line by line
			while( ( line = bufferedReader.readLine() ) != null ){
				graphStr.add(line);
			}
			
			//closing the construct needed for reading file
			bufferedReader.close();
			//fileReader.close();
			iStreamFile1.close();
			
			for( String node: graphStr  ){
				nodeStr1 = node.split(listSeparator);
				graph.addNode( new Node(  nodeStr1[0] , null, null ) );
			}
			
			for(String node: graphStr ){
				nodeStr1 = node.split(listSeparator);
				nodeStr2 = nodeStr1[1].split(nodeSeparator);
				//System.out.print("\n"+nodeStr1[0]+"\n\t");
				//int id1=Integer.parseInt(nodeStr1[0]);
				for( int i=0; i < nodeStr2.length; i++ ){
					//System.out.print(nodeStr2[i]+" ");
					graph.addEdge(new Node( nodeStr1[0],null, null),new Node( nodeStr2[i], null, null), 1);
				}
				
			}
			
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
		return graph;
	}
}
