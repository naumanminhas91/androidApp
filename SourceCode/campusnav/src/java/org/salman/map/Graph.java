package org.salman.map;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;

public class Graph {
	
	private ArrayList<Node> nodeList;
	
	private String name;
	Graph( String _name ){
		nodeList = new ArrayList<Node>();
		this.name = _name;
	}
	
	Graph( ArrayList<Node> _nodeList , String _name){
		this.nodeList = _nodeList;
		this.name = _name;
	}
	
	public void addNode( Node node ){
		
		assert( node != null );
		if( node != null ){
			nodeList.add(node);
		}
	}
	
	//between node1 and node2
	public void addEdge( Node node1, Node node2 , double weight){
		
		assert( ( node1 != null && node2 != null ) );
		
		if( node1 != null && node2 != null ){
			
			if( !contains( nodeList, node1) ){
				nodeList.add(node1);
			}
			if( !contains( nodeList, node2)){
				nodeList.add(node2);
			}
			Node tmpNode1 = getNode( node1);
			Node tmpNode2 = getNode( node2 );
			tmpNode1.addNodeToAdjacencyList(tmpNode2, weight);
			tmpNode2.addNodeToAdjacencyList(tmpNode1, weight);
		}
		
	}
	
	public boolean contains( Node node ){
		assert( node != null);
		
		if( node != null && contains( nodeList, node) ){
			return true;
		}
		return false;
		
	}
	
	public boolean contains(String _id ){
		
		assert( _id != null );
		if( _id != null ){
			
			for( Node node : nodeList ){
				if( node.getID().equals(_id) ){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean contains( ArrayList<Node> list, Node node){
		
		for( Node _node : list){
			if( _node.equals( node)){
				return true;
			}
		}
		return false;
	}
	
	public Node getNode( Node node ){
		
		assert( node != null );
		
		if( node != null ){
			
			for( Node _node : nodeList ){
				
				if( _node.equals( node)){
					return _node;
				}
			}
		}
		return null;
	}
	
	public Node getNode( String _id ){
		
		assert( _id != null );
		
		if( _id != null ){
			
			for( Node _node : nodeList ){
				
				if( _node.getID().equals(_id) ){
					return _node;
				}
			}
		}
		return null;
	}
	
//	public Node getNode( int _id ){
//		
//		return getNode( Integer.valueOf(_id ));
//	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		
		String graphStr = "";
		for( Node _node: nodeList ){
			graphStr += _node.toString()+"\n";
		}
		return graphStr;
	}

	public ArrayList<String> findRouteStr( String id1, String id2 ){
		
		ArrayList<Node> route = findRoute(id1, id2);
		if( route == null || route.size() <= 1){
			return null;
		}
		ArrayList<String> routeStr = new ArrayList<String>() ;
		for( Node node: route ){
			routeStr.add(node.getID());
		}
		return routeStr;
	}
	public ArrayList<Node> findRoute( String id1, String id2 ){
		
		
		ArrayList<Node> route = new ArrayList<Node>();
                
                if (id1 == null || id2 == null || id1 == "" || id2 == "" || getNode(id1)== null || getNode(id2) == null ){
                    return null;
                }else if(id1.equals(id2)){
                    Node n = getNode(id1);
                    if (n!= null){
                        route.add(n);
                    }else{
                        route = null;
                        
                    }
                    
                    return route;
                }
                
		ArrayList<Node> queue = new ArrayList<Node>();
		for( Node node : nodeList ){
			node.marked = false;
			node.visited = false;
			if( node.getID().equals(id1)){
				node.value =0;
				queue.add(node);
				node.marked = true;
				node.prevNode=null;
			}else{
				node.value = Double.MAX_VALUE;
			}
			
		}
		
		
		Node node1;
		int index = 0;
		while( queue.size() != 0 ){
			node1 = queue.remove(0);
			index=0;
			for( Node node2 : node1.getAdjacencyList() ){
				
				if( node2.getID().equals(id2) ){
					//System.out.println("test");
					node2.prevNode=node1;
					makeRoute(route, node2);
					return route;
				}
				
				if( !node2.marked ){
					node2.marked = true;
					queue.add(node2);
					node2.prevNode=node1;
				}
				
				index++;
			}
			
		}
		return null;
	}
	
	private void makeRoute( ArrayList<Node> _route, Node node ){
		
		Node tmpNode = node;
		while( tmpNode.prevNode != null ){
			_route.add(tmpNode);
			tmpNode = tmpNode.prevNode;
		}
		_route.add(tmpNode);
	}

}
