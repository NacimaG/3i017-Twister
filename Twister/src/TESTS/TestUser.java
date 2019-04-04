package TESTS;


import java.util.Date;

import org.json.JSONObject;

import ServiceTools.ConnexionTools;
import Services.User;


public class TestUser {

	public static void main(String[] args) {
		
		String log="twiter";
		String psw="abcde";
		String c="tutu";
		String d="otot";
		String e="atat";
		
		
		Date date= new Date();
		System.out.println(date);
		/**
		 * TEST CREATION D'UN UTILISATEUR
		 */
		JSONObject  o=User.createUser("Coucou","123456",c,d,e);
	//	System.out.println(User.createUser("twiter","mdptutu",c,d,e).toString());

		//System.out.println(User.createUser(log,psw,c,d,e).toString());
		/**
		 * TEST CONNEXION
		 */
		//System.out.println(User.connexion("twiter", "mdptutu"));
		/**
		 * TEST DECONNEXION
		 */
		//System.out.println(User.deconnexion("zi6ehg2o53dg-1n1t7r8tqiiu6"));
		//System.out.println(ConnexionTools.getKey(28));
	
	}

}
