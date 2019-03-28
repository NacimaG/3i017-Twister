package ServiceTools;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoDatabase;

import BaseDD.Database;

public class MessageTools {

	public static boolean InsertMessage(String key,String text) throws JSONException{
		JSONObject o = new JSONObject();
		Document doc = new Document();
		//int id = ConnexionTools.getId(key);
		
		doc.append("idMEssage",""+key+"");
		doc.append("idUser", 12);
		doc.append("message", text);
		System.out.println(doc);
		o.put("OK", doc);
		
		//MongoDatabase mdb = Database.getMongoConnection();
		return false;
	}
}

