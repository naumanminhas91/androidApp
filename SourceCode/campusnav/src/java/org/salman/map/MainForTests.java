package org.salman.map;

import java.util.ArrayList;
import java.util.HashMap;

import tests.FileToResourceReader;

public class MainForTests {

	public static void main( String[] args ){
		/*GraphGenerator  gg = new GraphGenerator();
		Graph g = gg.createGraph("accademicBlockGraph.txt", "LUMS-Graph");
		System.out.println(g.toString());
		
		ArrayList<Node> n = g.findRoute("j1", "j19");
		for(Node _n: n){
			System.out.print( _n.getID()+" ");
		}
		System.out.println("success");*/
		MapController mc = new MapController();
		HashMap<String, double[]> m1 =  new HashMap<String, double[]>();
		double a[]= new double[2];
		a[0]= 31.470548;
		a[1]=74.409075;
		//m1.put("1", a);
		a= new double[2];
		a[0]=31.47048;
		a[1]=74.409757;
		m1.put("2", a);
		HashMap<String, double[]> m2 =  new HashMap<String, double[]>();
		a= new double[2];
		a[0]=31.470054;
		a[1]=74.409545;
		m2.put("3", a);
		a= new double[2];
		a[0]=31.470157;
		a[1]=74.409193;
		//m2.put("4", a);
		
		String[] strA = mc.findIdsForOptimizedPath(m1, m2);
		System.out.println("src: "+strA[0]+", dest: "+strA[1]);
		//FileToResourceReader resourceReader = new FileToResourceReader();
		
		//HashMap<String,HashMap<String,double[]>>GPSOfLocation = resourceReader.readNodeCoOrdinatesMap("resources\\IdWithStdNameAndGPS.csv");
		
		System.out.println(mc.getDirections("A-1", "SSE"));
		
		
		/*Node n1 = new Node("1", null, null);
		Node n2 = new Node("2", null, null);
		Node n3= new Node("1", null, null);
		if( n1.equals(n3)){
			System.out.println("first condition satisfied");
		}else{
			System.out.println("first condition failed");
		}
		ArrayList<Node> a =  new ArrayList<Node>();
		a.add(n1);a.add(n2);
		
		if( a.contains(n3)){
			System.out.println( "second condition satisfied" );
		}else{
			System.out.println("second condition failed");
		}*/
	}
}
