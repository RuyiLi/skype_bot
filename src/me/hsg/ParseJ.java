package me.hsg;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ParseJ {
	
	public static String parse(String web) throws JsonIOException, JsonSyntaxException, IOException{
	    URL url = new URL(web);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();

	    JsonParser jp = new JsonParser();
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
	    JsonObject rootobj = root.getAsJsonObject(); 
	    String image = rootobj.get("img").getAsString();
	    
	    return image;
	}

}
