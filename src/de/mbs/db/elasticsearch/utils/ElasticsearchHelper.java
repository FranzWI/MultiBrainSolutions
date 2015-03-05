package de.mbs.db.elasticsearch.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.function.Consumer;

import org.apache.commons.codec.binary.Base64;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;
import de.mbs.db.elasticsearch.ElasticsearchView;


public class ElasticsearchHelper {

	public final static Settings CONNECTIONSETTINGS = ImmutableSettings.settingsBuilder()
			.put("cluster.name", "MBS Management Cockpit Cluster").build();
	
	public final static DateFormat DATETIME_NO_MILLIS_FORMATER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	public final static InetSocketTransportAddress CONNECTIONADRESS = new InetSocketTransportAddress("127.0.0.1", 9300);
	
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
	
	public static String add(ElasticsearchView view, String index, String type, String json){
		IndexResponse response = view.getESClient()
				.prepareIndex(index, type)
				.setSource(json)
				.execute()
				.actionGet();
		
		if (response.isCreated()) 
		{
			view.getESClient().admin().indices().flush(new FlushRequest(index)).actionGet();
			return response.getId();
		} 
		else 
		{
			return null;
		}
	}
	
	public static <A extends DatabaseObject> A edit(ElasticsearchView view, String index, String type, String json, A data)
	{
		System.out.println("DEBUG: Huhhu Tobi "+data.getId());
		BulkResponse response = view
				.getESClient()
				.prepareBulk()
				.add(view.getESClient().prepareUpdate(index, type, data.getId()).setDoc(json))
				.execute()
				.actionGet();
				
		if (!response.hasFailures()) 
		{
			view.getESClient().admin().indices().flush(new FlushRequest(index)).actionGet();
			return data;
		} 
		else 
		{
			System.err.println("ES: Data: "+json);
			System.err.println("ES: Fehler beim ändern eines Datensatzes :"+response.buildFailureMessage());
			return null;
		}
	}
	
	public static boolean remove(ElasticsearchView view, String index, String type, String id){
		DeleteResponse response = view.getESClient()
				.prepareDelete(index, type, id).execute().actionGet();
		view.getESClient().admin().indices()
				.flush(new FlushRequest(index)).actionGet();
		return response.isFound();
	}
	
	public static SearchHit[] getAll(ElasticsearchView view, String index, String type, String[] fieldList){
		return view.getESClient()
				.prepareSearch(index).setTypes(type).addFields(fieldList)
				.setQuery(new MatchAllQueryBuilder()).execute().actionGet().getHits().getHits();
	}
	
}
