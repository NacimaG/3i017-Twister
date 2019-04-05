package Services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import ServiceTools.ConnexionTools;
import ServiceTools.FriendTools;
import ServiceTools.UserTools;
import BaseDD.Database;

public class User {
	
	/**
	 * ********CREATE_USER
	 * Creation et insertion d'un utilisateur dans la BD
	 *	Verifir que les arguments sont pas null et que login n'existe pas 
	 *@return Json Object qui est le seviceRefuser en cas d'erreur et serviceAccepted sinon
	 * Entree : prenom + nom + login + password
	 * Sortie : {}
	 */
	public static JSONObject createUser(String login,String password,String nom ,String prenom, String mail) {
		JSONObject retour= new JSONObject();
		try {
		try {
		if((login==null)||(mail==null)||(prenom==null)||(nom == null)) {
			return ServiceTools.ErrorJson.serviceRefused("Pas d'argument", -1);}
			
		if(ServiceTools.ConnexionTools.checkUser(login)) { 
			return ServiceTools.ErrorJson.serviceRefused("Login existe deja", 1);
		}
			
		ServiceTools.UserTools.InsertUser(login,password,nom,prenom, mail);
		
		retour = ServiceTools.ErrorJson.serviceAccepted();
	
		
		}catch (SQLException s) {
			try {
				return ServiceTools.ErrorJson.serviceRefused(s.getMessage(), -1);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}		}
		}catch(JSONException e) {
		}
		
		return retour;
	}
	

	/**
	 ************ CONNEXION
	 * Entree : login + password
	 * Sortie : {id,login,key}
	 * @param login
	 * @param password
	 * @return
	 */
	public static JSONObject connexion(String login, String password) {
		JSONObject retour= new JSONObject();
		try {
			if((login==null)||(password == null)) {
				//retour.append("message", "Pas d'arguments");
				//retour.append("code", -1);
				return ServiceTools.ErrorJson.serviceRefused("Pas d'argument", -1);
			}
			if(!ConnexionTools.checkUser(login)) 
				return ServiceTools.ErrorJson.serviceRefused("User not exists", 1);
			
			if(!ConnexionTools.checkLogPwd(login, password))
				return ServiceTools.ErrorJson.serviceRefused("Wrong password", 2);
			if(ConnexionTools.UserConnected(login))
				return ServiceTools.ErrorJson.serviceRefused("Utilisateur deja connect",3);
		
			String key = UserTools.InsertConnexion(login);
			
			retour = ServiceTools.ErrorJson.serviceAccepted();
			retour.put("id", FriendTools.getId(key));
			retour.put("login", login);
			retour.put("key", key);
			
		}catch (JSONException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			try {
				return ServiceTools.ErrorJson.serviceRefused(e.getMessage(), -1);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return retour;
		
}
	/**
	 ********** DECONNEXION
	 * verifer que la cle n'est pas null et que la connexion existe dans la base de donnee
	 * et alors peut se d�connecter
	 * @return Json Object qui est le seviceRefuser en cas d'erreur et serviceAccepted sinon
	 *
	 *  Entree : key
	 *	Sortie : {}
	 */
	public static JSONObject deconnexion(String key){
		JSONObject retour= new JSONObject();

		try {
		if(key == null) 
			return ServiceTools.ErrorJson.serviceRefused("Cle connexion invalide",1000);			
		if(!ConnexionTools.checkSession(key))
			return ServiceTools.ErrorJson.serviceRefused("Cle connexion inexisatante",1000);
		try {
			Connection connexion = Database.getMySQLConnection();
			Statement statement = connexion.createStatement();
			String query = "DELETE FROM Session WHERE session_key='"+key+"';";

			int resultat= statement.executeUpdate(query);
			statement.close();
			connexion.close();
			System.out.println(resultat);
			}
			catch(SQLException e) {
				
			}
		retour = ServiceTools.ErrorJson.serviceAccepted();
		
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return retour;		
	}
	
	
}
