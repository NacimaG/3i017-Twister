package TESTS;

import java.sql.Timestamp;

public class teste {

	public static void main(String[] args) {
		 Timestamp timestamp = new Timestamp(System.currentTimeMillis()+(((3600) + 59)* 1000));
	        System.out.println(timestamp);
	        
	   	 Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
	        
	        if(timestamp.after(timestamp1))
	        	System.out.println("coucouc");
	        
	        
	        System.out.println(Integer.parseInt( "24"));
	}

}
