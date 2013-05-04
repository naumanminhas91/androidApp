package org.campusnav.salman;

import java.util.ArrayList;
import android.location.Location;

public class Node implements Comparable{
	
	private Location location;				//this will contain the location of the node
	private String id;						//every node will be associated with a unique id
	private ArrayList<String> names;		//will contain the possible name of the location
	private ArrayList<Node> adjacencyList;	//will contain all the node adjacent to the node
	private ArrayList<Double> edgeWeight;	//weight for each edge leading from this node to 
											//the other node the idex of the node in adjacency
											//list will be the same as in the edgeWeight list
	public double value;					//these value will be used for the implementation of
											//algorithms
	public boolean visited;
	public boolean marked;
	public Node prevNode;
	
	
	/*
	 * Constructor 1
	 */
	
	Node( String _id, Location _location, ArrayList<String> _names ){
		
		this.id = _id;
		this.names = _names;
		this.location = _location;
		this.adjacencyList = new ArrayList<Node>();
		this.edgeWeight = new ArrayList<Double>();
		this.prevNode = null;
		
		
	}
	
	Node( String _id, Location _location, ArrayList<String> _names,
			ArrayList<Node> _adjacencyList, ArrayList<Double> _edgeWeight ){
			
			this.id = _id;
			this.names = _names;
			this.location = _location;
			this.adjacencyList = new ArrayList<Node>();
			this.edgeWeight = new ArrayList<Double>();
			this.adjacencyList = _adjacencyList;
			this.edgeWeight = _edgeWeight;
			this.prevNode = null;
			
	}
	
	public void addNodeToAdjacencyList( Node node,  Double weight){
		assert(node != null );
		if( node == null ){
			return;
		}
		
		adjacencyList.add(node);
		edgeWeight.add( weight );
		
	}
	
	public void addNodeToAdjacencyList( Node node,  double weight){
		assert(node != null );
		if( node == null ){
			return;
		}
		if( isNodeInAdjacencyList(node)){
			return;
		}
		adjacencyList.add(node);
		edgeWeight.add( Double.valueOf( weight ) );
	}
	
	public ArrayList<Node> getAdjacencyList(){	
		return adjacencyList;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public ArrayList<String> getNames(){
		return names;
	}
	
	public boolean addName( String name ){
		
		assert(name != null );
		if( name != null && !names.contains( name )){
			names.add( name );
			return true;
		}
		return false;
	}
	
	public void setEdgeWeight( Node node, Double weight){
		
		assert(node != null );
		if( node == null ){
			return;
		}
		edgeWeight.set( adjacencyList.indexOf(node), weight);
		
	}
	
	public String getID(){
		return id;
	}
	
	public void setID( String _id){
		
		assert( _id != null );
		if( _id == null ){
			return;
		}
		id = _id;
	}
	
	/*
	public void setID( String _id ){
		
		id = _id;
	}*/
	
	public boolean equals( Node node ){
		
		assert(node != null );
		if( node != null && this.id.equals( node.getID()) ){
				if( (location ==null && node.getLocation()==null) || 
						this.location.equals( node.getLocation()) ){
					return true;
				}else{
					return false;
				}
		}
		return false;
	}
	
	public boolean isNodeInAdjacencyList( Node node ){
		
		assert(node != null );
		if( node == null ){
			return false;
		}
		return contains( adjacencyList, node );
	}
	
	public boolean contains( ArrayList<Node> list, Node node){
		
		for( Node _node : list){
			if( _node.equals( node)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isNodeInAdjacencyList( Integer _id ){
			
		assert( _id != null );
		if( _id == null ){
			return false;
		}
			for( Node _node : adjacencyList ){
				if( _node.getID().equals(_id) ){
					return true;
				}
			}
			return false;
	}
	
	public boolean isNodeInAdjacencyList( int _id ){
		
		
		return isNodeInAdjacencyList( Integer.valueOf(_id) );	
	}
	
	public Node findNodeInAdjacencyList( Node node ){
		
		assert(node != null );
		if( node == null ){
			return null;
		}
		for( Node _node : adjacencyList ){
			if( _node.equals(_node) ){
				return _node;
			}
		}
		return null;
	}

	//@Override
	public int compareTo(Object node) {
		if( value-((Node)node).value == 0){
			return 0;
		}else if( value-((Node)node).value < 0){
			return -1;
		}
		return 1;
	}
	
	public double getEdgeWeight( int index ){
		return edgeWeight.get(index);
	}
	
	public String toString(){
		String nodeStr="";
		nodeStr = id +", "+names+", "+"\n\t\t";
		
		for( Node _node: adjacencyList ){
			nodeStr += _node.getID()+" ";
		}
		return nodeStr;
	}

}
