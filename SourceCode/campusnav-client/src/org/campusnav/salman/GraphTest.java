package org.campusnav.salman;

import java.util.ArrayList;

import junit.framework.TestCase;


public class GraphTest extends TestCase {

	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGraphString() {
		//fail("Not yet implemented");
		
		Graph g0=new Graph("Graph00");
		
		assertTrue(g0.getName().equals("Graph00"));
	}

	public void testGraphArrayListOfNodeString() {
		//fail("Not yet implemented");
		
		Node n1= new Node("12345", null, new ArrayList<String>());
		Node n2= new Node("12345", null, new ArrayList<String>());
		
		ArrayList<Node> newArr= new ArrayList<Node>();
		newArr.add(n1);
		newArr.add(n2);
		
		Graph g0=new Graph(newArr, "Graph01");
		
		assertTrue(g0.getName().equals("Graph01") && g0.getNode(n2)!=null && g0.getNode(n1)!=null);
		
	}

	public void testAddNode() {
		//fail("Not yet implemented");
		Node n2= new Node("12345", null, new ArrayList<String>());
		Node n1= new Node("123", null, new ArrayList<String>());
		Graph g0=new Graph("Graph02");
		
		g0.addNode(n2);
		g0.addNode(n1);
		g0.addNode(null);
		assertTrue( g0.getNode(n2)!=null && g0.getNode(n1)!=null );
		
	}

	public void testContainsNode() {
		//fail("Not yet implemented");
		
		Node n2= new Node("12345", null, new ArrayList<String>());
		Node n1= new Node("123", null, new ArrayList<String>());
		Node n3= new Node("1234", null, new ArrayList<String>());
		
		Graph g0=new Graph("Graph02");
		
		g0.addNode(n2);
		g0.addNode(n1);
		
		assertTrue( g0.contains(n1) && g0.contains(n2) );
		assertTrue( !g0.contains(n3));
	}


	public void testContainsArrayListOfNodeNode() {
		//fail("Not yet implemented");

		Node n2= new Node("12345", null, new ArrayList<String>());
		Node n1= new Node("123", null, new ArrayList<String>());
		Graph g0=new Graph("Graph02");
		
		ArrayList<Node> myArray= new ArrayList<Node>();
		myArray.add(n1);
		myArray.add(n2);
		
		assertTrue(g0.contains(myArray, n2));
	
	}

	public void testGetNodeNode() {
		//fail("Not yet implemented");
		
		Node n2= new Node("12345", null, new ArrayList<String>());
		Node n1= new Node("123", null, new ArrayList<String>());
		Graph g0=new Graph("Graph02");
		
		g0.addNode(n2);
		g0.addNode(n1);
		
		assertTrue( g0.getNode(n2)!=null && g0.getNode(n1)!=null );
		
	}

	public void testGetNodeInt() {
	//fail("Not yet implemented");
		
		Node n2= new Node("12345", null, new ArrayList<String>());
		Node n1= new Node("123", null, new ArrayList<String>());
		Graph g0=new Graph("Graph02");
		
		g0.addNode(n2);
		g0.addNode(n1);
		
		assertTrue( g0.getNode(123)!=null);
	}

	public void testGetName() {
		//fail("Not yet implemented");
		Graph g0=new Graph("Graph02");
		
		assertTrue(g0.getName().equals("Graph02"));
	}

	public void testFindRoute() {
		//fail("Not yet implemented");
		
		Node n2= new Node("2", null, new ArrayList<String>());
		Node n1= new Node("1", null, new ArrayList<String>());
		Node n3= new Node("3", null, new ArrayList<String>());
		Node n4= new Node("4", null, new ArrayList<String>());
		Node n5= new Node("5", null, new ArrayList<String>());
		
		Graph g0=new Graph("Graph00");
		
		g0.addNode(n1);
		g0.addNode(n2);
		g0.addNode(n3);
		g0.addNode(n4);
		g0.addNode(n5);
		
		g0.addEdge(n1, n3, 0.5);
		g0.addEdge(n3, n4, 0.7);
		g0.addEdge(n2, n1, 0.5);
		
		ArrayList<Node> route= g0.findRoute("2", "4");
		
		Boolean assertion= route.get(0).getID().equals("4") && route.get(1).getID().equals("3") && route.get(2).getID().equals("1");
		
		assertTrue(route.get(0).getID()+"\n"+route.get(1).getID()+"\n"+route.get(2).getID(), assertion);
		
		ArrayList<Node> route1= g0.findRoute("2", "5");
		assertTrue(route1==null);
	}

}
