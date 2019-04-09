package Services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ServiceTools.ConnexionTools;
import ServiceTools.FriendTools;
import ServiceTools.UserTools;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import BaseDD.DBStatic;
import BaseDD.Database;



//listMessage(id_user,string messageDebut,String message)
public class Message {

	public static JSONObject addMessage(String text, String key) {
		JSONObject retour = new JSONObject();

		
		try {
			if((text==null)||(key==null))
				return ServiceTools.ErrorJson.serviceRefused("Pas d'arguments", -1);
		
			if(!ServiceTools.ConnexionTools.checkSession(key))
				return ServiceTools.ErrorJson.serviceRefused("User not connected", 1);
			
			/*	if(ConnexionTools.hasExceededTimeOut(key)) {
					User.deconnexion(key);
					return ServiceTools.ErrorJson.serviceRefused("TimeOut exceeded, disconnected automatically", 1);
				}*/
			
				MongoDatabase mdb = Database.getMongoCollection();
				MongoCollection<Document> msg = mdb.getCollection("messages");		
				
				//ConnexionTools.updateTimeOut(key);
				Document query = new Document();
				
				query.append("idUser",FriendTools.getId(key));
				query.append("text", text);
		
		msg.insertOne(query);
		
		retour = ServiceTools.ErrorJson.serviceAccepted();
		
		}catch(JSONException e) {
			
		}
		/*catch(SQLException s) {
			s.printStackTrace();
		}
		*/
		return retour;
			
	}
	
	public static JSONObject removeMessage(String key, String idMessage) {
		JSONObject jo = new JSONObject();
		if(ConnexionTools.checkSession(key)){

		try {
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
		}

			//String id= FriendTools.getId(key)+"";
			Document doc = new Document();
			doc.append("key", key);
			doc.append("_id", idMessage);
			MongoDatabase mdb = Database.getMongoCollection();

			MongoCollection<Document> message = mdb.getCollection("message");
			
			MongoCursor<Document> cursor = message.find(doc).iterator();
			if(cursor.hasNext()){
				message.deleteOne(doc);
				try{
					jo.put("suppression", "OK");
				}catch(JSONException e){
					e.printStackTrace();
				}
			}else{
				System.out.println("domage");
			}
		}
		return jo;
	}
	/**
	 * Entree : key + query + friends
	 * @throws JSONException 
	 * 
	 * 
	 */
	public static JSONObject ListMessage(String key) {
		
		int id_user;
		//FRienTools.getId nous permet de récuperer l'Id d'un utilisateur avec sa cle de connexion
		id_user = FriendTools.getId(key);
		try {
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
			}

		MongoDatabase mdb = Database.getMongoCollection();

		MongoCollection<Document> message = mdb.getCollection("messages");
		Document query = new Document();
		query.put("idUser", id_user);
		FindIterable<Document> findIterable = message.find(query).sort(new Document("date",-1));
		MongoCursor<Document> msg = findIterable.iterator();

		JSONObject json = new JSONObject();

		JSONArray messages = new JSONArray();
		try {

		while(msg.hasNext()){
			Document document = msg.next();
			JSONObject msgTmp = new JSONObject();
			
			msgTmp.put("message_id", document.get("_id"));
			msgTmp.put("date", new Date());
			msgTmp.put("text", document.get("text"));
			
			messages.put(msgTmp);
		}
		
		json.put("idUser",id_user);
		json.put("login", UserTools.getLogin(id_user));
		json.put("messages", messages);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
		}
	
	

}
