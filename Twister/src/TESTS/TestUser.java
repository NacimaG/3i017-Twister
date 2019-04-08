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
		String p="phone";
		
		
		Date date= new Date();
		System.out.println(date);
		/**
		 * TEST CREATION D'UN UTILISATEUR
		 */
		//JSONObject  o=User.createUser("test4","test4",c,d,e);
		System.out.println(User.createUser("tata","mdptata",c,d,e,p).toString());
		

		//System.out.println(User.createUser(log,psw,c,d,e).toString());
		/**
		 * TEST CONNEXION
		 */
		//System.out.println(User.connexion("test4", "test4"));
		/**
		 * TEST DECONNEXION
		 */	
		
		System.out.println(User.deconnexion("-t7t99wmbyc71-1b7d2gxwdztj4"));
		//System.out.println(ConnexionTools.getKey(28));
	
	}

}
