package org.salman.map;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import tests.FileToResourceReader;

public class GraphGenerator {

	Graph graph;
	String listSeparator,
	nodeSeparator;
	final String DEFAULT_LIST_SEPARATOR="-",
			DEFAULT_NODE_SEPARATOR=",";
        
        ServletContext context;
	
	public GraphGenerator(ServletContext _context){
		graph=null;
		listSeparator = DEFAULT_LIST_SEPARATOR;
		nodeSeparator = DEFAULT_NODE_SEPARATOR;
                context = _context;
	}
	
	
	public Graph createGraph( String fileName, Graph _graph){
		
		this.graph = _graph;
		return createGraph(fileName, _graph.getName() );
	}
	
	public Graph createGraph( String fileName, Graph _graph, String _listSeparator, 
		String _nodeSeparator){
		
		this.graph = _graph;
		this.listSeparator = _listSeparator;
		this.nodeSeparator = _nodeSeparator;
		return createGraph(fileName, _graph.getName());
	}
	
	public Graph createGraph( String fileName, String graphName ){
		
		if( graph == null ){
			graph = new Graph(graphName);
		}
		FileReader fileReader;
		BufferedReader bufferedReader;
		try {
			//fileReader = new FileReader(fileName);
			//bufferedReader = new BufferedReader(fileReader);
                        
                        InputStream in = context.getResourceAsStream("/WEB-INF/resources/"+fileName);
                        bufferedReader = new BufferedReader(new InputStreamReader(in));
                        
                        
                        
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
