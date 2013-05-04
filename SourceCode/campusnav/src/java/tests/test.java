package tests;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "my name is&salman asif";
		if( str.toLowerCase().contains("salman")){
			System.out.println("great discovery!");
			System.out.println(str.split("&")[0]);
		}
	}

}
