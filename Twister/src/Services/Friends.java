package Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.util.JSON;

import BaseDD.Database;
import ServiceTools.*;

public class Friends {
	/**
	 * ********ADD_FRIEND
	 * ajouter contrainte d ene pas s'ajouter soi meme verifer idfriend!=mon id
	 * @param key
	 * @param id_friend
	 * @return
	 * 
	 * Entree : key + id_friend
	 * Sortie : {}
	 */
	public static JSONObject AddFriend(String key, int id_friend) {
		JSONObject retour = new JSONObject();
		try {
			if(key==null || id_friend==0)
				return ErrorJson.serviceRefused("pas d'argument", -1);
			if(!ConnexionTools.checkSession(key))
				return ErrorJson.serviceRefused("Connexion non existante", 100);
				
			if(!ConnexionTools.checkId(id_friend))
				return ErrorJson.serviceRefused("id friend not exists", 100);
		/*	if(ConnexionTools.hasExceededTimeOut(key)) {
				System.out.println(1);
				User.deconnexion(key);
				return ServiceTools.ErrorJson.serviceRefused("TimeOut exceeded, disconnected automatically", 1);
			}
			*/	
				FriendTools.InsertFriend(key, id_friend);
				retour = ServiceTools.ErrorJson.serviceAccepted();
			
		
			//}catch(SQLException s) {
				//s.printStackTrace();
			}catch(JSONException j) {
				j.printStackTrace();
			}
/*			try {
			ConnexionTools.updateTimeOut(key);
			}catch(SQLException s) {
				s.printStackTrace();
			}
	*/	
		return retour;
	}
	/**
	 * ******REMOVE_FRIEND
	 * Entree : key + id_friend
	 * Sortie : {}
	 *
	 * @param key
	 * @param id_friend
	 * @return
	 */
	public static JSONObject RemoveFriend(String key, int id_friend) {
		/*try {
			if(ConnexionTools.hasExceededTimeOut(key)) {
				User.deconnexion(key);
				return ServiceTools.ErrorJson.serviceRefused("TimeOut exceeded, disconnected automatically", 1);
			}
			}catch(SQLException s) {
				s.printStackTrace();
			}catch(JSONException j) {
				j.printStackTrace();
			}
			try {
			ConnexionTools.updateTimeOut(key);
			}catch(SQLException s) {
				s.printStackTrace();
			}*/
		JSONObject retour= new JSONObject();
		
			try {
				if((key==null)||(!ConnexionTools.checkSession(key)))
					return  ServiceTools.ErrorJson.serviceRefused("Connexion non existante",-1);
				
				if(!FriendTools.checkFriend(id_friend))
					return ServiceTools.ErrorJson.serviceRefused("id friend non existant",-1);
				if(!FriendTools.checkFriend(FriendTools.getId(key), id_friend))
					return ServiceTools.ErrorJson.serviceRefused("id friend non existant",-1);
				Connection connexion = Database.getMySQLConnection();
				Statement statement = connexion.createStatement();
				String query = "DELETE FROM Friends WHERE id_user1='"+FriendTools.getId(key)+"'and id_user2='"+id_friend+"';";
				
				int resultat= statement.executeUpdate(query);
				statement.close();
				connexion.close();
				System.out.println(resultat);
				
				retour=  ServiceTools.ErrorJson.serviceAccepted();
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException s){
			s.printStackTrace();
		}
			return retour;
	}
	
	
	public static JSONObject listFriend(String login){
		/*String clef = ConnexionTools.getKey(id_user1);
		try {
			if(ConnexionTools.hasExceededTimeOut(clef)) {
				User.deconnexion(clef);
				return ServiceTools.ErrorJson.serviceRefused("TimeOut exceeded, disconnected automatically", 1);
			}
			}catch(SQLException s) {
				s.printStackTrace();
			}catch(JSONException j) {
				j.printStackTrace();
			}
			try {
			ConnexionTools.updateTimeOut(clef);
			}catch(SQLException s) {
				s.printStackTrace();
			}*/
		int id_user1= ConnexionTools.getId(login);
		JSONObject jo = new JSONObject();
		Connection connexion;
	
		try {
			if(!ConnexionTools.checkId(id_user1)) 
				return ServiceTools.ErrorJson.serviceRefused("Utilisateur not exists",-1);
				
			connexion = Database.getMySQLConnection();  
			Statement statement = connexion.createStatement();
			String query = "SELECT id_user2 FROM Friends WHERE id_user1 = '"+id_user1+"' ";
			ResultSet resultat=	statement.executeQuery(query);
			while(resultat.next()){
				jo.append("Statue","0K");
				jo.append("code", 200);
				jo.append("id_friend", resultat.getString(1));
				jo.append("login", UserTools.getLogin(Integer.parseInt(resultat.getString(1))));
			}
			resultat.close();
			statement.close();
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(JSONException j){
			j.printStackTrace();
		}	
		System.out.println(jo.toString());
		return jo;
	}

}
