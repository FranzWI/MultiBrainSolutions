package de.mbs.db.elasticsearch.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.function.Consumer;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ElasticsearchHelper {

	/**
	 * 
	 * @param f - DAtei welche Base 64 encodiert werden soll
	 * @return null --> falls datei ungültig / nicht lesbar
	 */
	public static String encodeFileBase64(File f){
		if(f.exists() && !f.isDirectory()){
			Path path = Paths.get(f.toURI());
			try {
				return Base64.encodeBase64String(Files.readAllBytes(path));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public static JSONArray vectorToJSONArray(Vector<String> data){
		JSONArray array = new JSONArray();
		for(String string:data)
			array.add(string);
		return array;
	}
	
	public static Vector<String> jsonArrayToVector(String jsonArrayAsString) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONArray data = (JSONArray) parser.parse(jsonArrayAsString);
		final Vector<String> vector = new Vector<String>();
		data.forEach(new Consumer(){

			@Override
			public void accept(Object t) {
				vector.add(t.toString());
			}
			
		});
		return vector;
	}
	
}
