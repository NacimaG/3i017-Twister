package Services;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;
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
	/**
	 * **********ADD_MESSAGE
	 * @param text
	 * @param key
	 * @return
	 */

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
				query.append("date", new Date());
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
	/**
	 * 
	 * REMOVE MESSAGE
	 * @param idUser
	 * @param idMessage
	 * @return
	 */
	
	public static JSONObject removeMessage(String idUser, String idMessage) {
		JSONObject jo = new JSONObject();
			int id= Integer.parseInt(idUser);
			Document doc = new Document();
			doc.append("idUser", id);
			doc.append("_id",new ObjectId(idMessage));

			MongoDatabase mdb = Database.getMongoCollection();

			MongoCollection<Document> message = mdb.getCollection("messages");
			System.out.println(1);
			MongoCursor<Document> cursor = message.find(doc).iterator();
						
			if(cursor.hasNext()){
				System.out.println(3);
				message.deleteOne(doc);
				try{
					jo.put("suppression", "OK");
				}catch(JSONException e){
					System.out.println(4);
					e.printStackTrace();
				}
			}else{
				System.out.println("msg non trouvé");
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
		//FRienTools.getId nous permet de rÃ©cuperer l'Id d'un utilisateur avec sa cle de connexion
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
			msgTmp.put("date",document.get("date") );
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
	/**
	 * 
	 * GET USER MESSAGE
	 * @param idUser
	 * @return
	 * @throws JSONException 
	 */
	
	public static JSONObject getMessageUser(String idUser) throws JSONException {
		int id=Integer.parseInt(idUser);

		if(! ConnexionTools.checkId(id))
			return ServiceTools.ErrorJson.serviceRefused("user not exists", -1);
		MongoDatabase mdb = Database.getMongoCollection();

		MongoCollection<Document> message = mdb.getCollection("messages");
		
		
		Document query = new Document();
		query.put("idUser", id);
		FindIterable<Document> findIterable = message.find(query).sort(new Document("date",-1));
		MongoCursor<Document> msg = findIterable.iterator();

		JSONObject json = new JSONObject();

		JSONArray messages = new JSONArray();
		try {

		while(msg.hasNext()){
			Document document = msg.next();
			JSONObject msgTmp = new JSONObject();
			
			msgTmp.put("message_id", document.get("_id"));
			msgTmp.put("date",document.get("date") );
			msgTmp.put("text", document.get("text"));
			
			messages.put(msgTmp);
		}
		json.put("idUser",idUser);
		//json.put("login", UserTools.getLogin(id));
		json.put("messages", messages);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
		
		
	}
	
	
/**
 * 
 * GetAllMessages
 */


public static JSONArray getMessages() throws UnknownHostException, SQLException, JSONException {
	MongoDatabase mdb = Database.getMongoCollection();
	MongoCollection<Document> message = mdb.getCollection("messages");

	//MongoCollection<Document> message = Database.getCollection("message");
	FindIterable<Document> findIterable = message.find().sort(new Document("date",-1));
	MongoCursor<Document> msg = findIterable.iterator();

	JSONObject json = new JSONObject();
	JSONArray messages = new JSONArray();

	while(msg.hasNext()){
		JSONObject msgtmp = new JSONObject();
		Document document = msg.next();

		msgtmp.put("login", UserTools.getLogin(Integer.parseInt(document.get("idUser").toString())));
		msgtmp.put("message_id", document.get("_id"));
		msgtmp.put("text", document.get("text"));
		msgtmp.put("date", document.get("date"));	

		messages.put(msgtmp);
		
	}
	json.put("messages", messages);

	return messages;
}


}
