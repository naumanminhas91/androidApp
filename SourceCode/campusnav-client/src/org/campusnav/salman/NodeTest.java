package org.campusnav.salman;
import java.util.ArrayList;

import android.location.Location;
import junit.framework.TestCase;


public class NodeTest extends TestCase {

	Location L1;
	ArrayList<String> AL;
	Node  n0;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		L1=null;
		AL= new ArrayList<String>();
		AL.add("name00");
		AL.add("name01");
		AL.add("name02");
		n0= new Node( "12345", L1, AL );
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testNodeStringLocationArrayListOfString() {
		Boolean pass=false;
		//fail("Not yet implemented");
		ArrayList<String> arr= new ArrayList<String>();
		Node n1=new Node("2", null,arr);
		if (n1.getLocation()==null && n1.getID().equals("2") && n1.getAdjacencyList().equals(arr) )
		{
			pass=true;
		}
		else
		{
			pass=false;
		}
		
		assertTrue("The Constructor Working properly", pass);
		
	}


	public void testAddNodeToAdjacencyListNodeDouble() {
		//fail("Not yet implemented");
		
		Node n1=new Node("2", null,new ArrayList<String>());
		n0.addNodeToAdjacencyList(n1 ,0.71);
		
		assertTrue("Node should be added", n0.getAdjacencyList().contains(n1));
		
	}

	public void testGetAdjacencyList() {
		//fail("Not yet implemented");
		
		Node n1=new Node("3", null , new ArrayList<String>());
		n1.addNodeToAdjacencyList(n0, 0.65);
		
		assertTrue("GetAdjancency List should be working properly", n1.getAdjacencyList().contains(n0));
	}

	public void testGetLocation() {
		//fail("Not yet implemented");
		
		assertTrue("Location returned correctly", n0.getLocation()==null);
	}

	public void testGetNames() {
		//fail("Not yet implemented");
		ArrayList<String> result=n0.getNames();
		
		assertTrue("getNames return correct data", result.get(0).equals("name00") && result.get(2).equals("name02"));
		
		
	}

	public void testAddName() {
		//fail("Not yet implemented");
		n0.addName("name03");
		
		assertTrue(n0.getNames().contains("name03"));
	}

	public void testSetEdgeWeight() {
		//fail("Not yet implemented");
		
		Node n1= new Node("5", null, new ArrayList<String>());
		n0.addNodeToAdjacencyList( n1, 4.5);
		n0.setEdgeWeight(n1, 2.2);
		int ind= n0.getAdjacencyList().indexOf(n1);
		assertTrue(n0.getEdgeWeight(ind)==2.2);
		
	}

	public void testGetID() {
		//fail("Not yet implemented");
		
		n0.setID("0");
		assertTrue(n0.getID().equals("0"));
		
		
	}

	public void testSetID() {
		//fail("Not yet implemented");
		
		n0.setID("12345");
		assertTrue(n0.getID().equals("12345"));
		
	}

	public void testEqualsNode() {
		//fail("Not yet implemented");
		Node n1= new Node("12345", null, new ArrayList<String>());
		Node n2= new Node("12345", null, new ArrayList<String>());
		
		assertTrue(n1.equals(n2));
	}

	public void testIsNodeInAdjacencyListNode() {
		//fail("Not yet implemented");
		Node n1= new Node("7", null, new ArrayList<String>());
		n0.addNodeToAdjacencyList(n1, 2.3);
		assertTrue(n0.isNodeInAdjacencyList(n1));
		
	}

	public void testContains() {
		//fail("Not yet implemented");
		Node n1= new Node("12345", null, new ArrayList<String>());
		Node n2= new Node("123", null, new ArrayList<String>());
		
		n0.addNodeToAdjacencyList(n1, 1.2);
		n0.addNodeToAdjacencyList(n2, 1.3);
		
		assertTrue(n0.contains(n0.getAdjacencyList(), n2));
		
	}

	public void testIsNodeInAdjacencyListInteger() {
		//fail("Not yet implemented");
		
		Node n1= new Node("12345", null, new ArrayList<String>());
		Node n2= new Node("123", null, new ArrayList<String>());
		
		n0.addNodeToAdjacencyList(n1, 1.2);
		n0.addNodeToAdjacencyList(n2, 1.3);
		
		assertTrue( n0.isNodeInAdjacencyList( Integer.valueOf(123) ));
	}

	public void testIsNodeInAdjacencyListInt() {
		//fail("Not yet implemented");
		
		Node n1= new Node("12345", null, new ArrayList<String>());
		Node n2= new Node("123", null, new ArrayList<String>());
		
		n0.addNodeToAdjacencyList(n1, 1.2);
		n0.addNodeToAdjacencyList(n2, 1.3);
		
		assertTrue( n0.isNodeInAdjacencyList(123));
	}

	public void testFindNodeInAdjacencyList() {
		//fail("Not yet implemented");
		
		Node n1= new Node("12345", null, new ArrayList<String>());
		Node n2= new Node("123", null, new ArrayList<String>());
		
		n0.addNodeToAdjacencyList(n1, 1.2);
		n0.addNodeToAdjacencyList(n2, 1.3);
		
		assertTrue(n0.findNodeInAdjacencyList(n2)!=null);
	}


	public void testGetEdgeWeight() {
		//fail("Not yet implemented");
		
		Node n1= new Node("12345", null, new ArrayList<String>());
		Node n2= new Node("123", null, new ArrayList<String>());
		
		n0.addNodeToAdjacencyList(n1, 1.2);
		n0.addNodeToAdjacencyList(n2, 1.3);
		
		int i=n0.getAdjacencyList().indexOf(n2);
		assertTrue(n0.getEdgeWeight(i)==1.3);
	}

}
