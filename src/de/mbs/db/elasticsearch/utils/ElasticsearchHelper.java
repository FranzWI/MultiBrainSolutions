package de.mbs.db.elasticsearch.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.function.Consumer;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.Base64;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.cursors.ObjectCursor;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class ElasticsearchHelper {

	public final static Settings CONNECTIONSETTINGS = ImmutableSettings
			.settingsBuilder()
			.put("cluster.name", "MBS Management Cockpit Cluster").build();

	public final static DateFormat DATETIME_NO_MILLIS_FORMATER = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

	public final static InetSocketTransportAddress CONNECTIONADRESS = new InetSocketTransportAddress(
			"127.0.0.1", 9300);

	/**
	 * 
	 * @param f
	 *            - DAtei welche Base 64 encodiert werden soll
	 * @return null --> falls datei ungültig / nicht lesbar
	 */
	public static String encodeFileBase64(File f) {
		try {
			return Base64.encodeFromFile(f.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static JSONArray vectorToJSONArray(Vector<String> data) {
		if(data == null || data.size() == 0)
			return null;
		JSONArray array = new JSONArray();
		for (String string : data)
			array.add(string);
		return array;
	}

	public static Vector<String> jsonArrayToVector(String jsonArrayAsString)
			throws ParseException {
		JSONParser parser = new JSONParser();
		JSONArray data = (JSONArray) parser.parse(jsonArrayAsString);
		final Vector<String> vector = new Vector<String>();
		data.forEach(new Consumer() {

			@Override
			public void accept(Object t) {
				vector.add(t.toString());
			}

		});
		return vector;
	}

	public static String add(ElasticsearchClientHandler view, String index,
			String type, String json) {
		return ElasticsearchHelper.add(view, index, type, null, json);
	}

	public static String add(ElasticsearchClientHandler view, String index,
			String type, String id, String json) {
		IndexResponse response = null;
		if (id == null) {
			response = view.getESClient().prepareIndex(index, type)
					.setSource(json).execute().actionGet();
		} else {
			response = view.getESClient().prepareIndex(index, type).setId(id)
					.setSource(json).execute().actionGet();
		}

		if (response.isCreated()) {
			view.getESClient().admin().indices()
					.flush(new FlushRequest(index).force(true).full(true))
					.actionGet();
			return response.getId();
		} else {
			return null;
		}
	}

	public static <A extends DatabaseObject> A edit(
			ElasticsearchClientHandler view, String index, String type,
			String json, A data) {
		BulkResponse response = view
				.getESClient()
				.prepareBulk()
				.add(view.getESClient()
						.prepareUpdate(index, type, data.getId()).setDoc(json))
				.execute().actionGet();

		if (!response.hasFailures()) {
			view.getESClient().admin().indices()
					.flush(new FlushRequest(index).force(true).full(true))
					.actionGet();
			return data;
		} else {
			System.err.println("ES: Data: " + json);
			System.err.println("ES: Fehler beim ändern eines Datensatzes :"
					+ response.buildFailureMessage());
			return null;
		}
	}

	public static boolean remove(ElasticsearchClientHandler view, String index,
			String type, String id) {
		DeleteResponse response = view.getESClient()
				.prepareDelete(index, type, id).execute().actionGet();
		view.getESClient().admin().indices()
				.flush(new FlushRequest(index).force(true).full(true))
				.actionGet();
		return response.isFound();
	}

	public static SearchHit[] getAll(ElasticsearchClientHandler view,
			String index, String type, String[] fieldList) {
		SearchResponse scrollResp = view.getESClient().prepareSearch(index)
				.setTypes(type).setScroll(new TimeValue(60000))
				.setQuery(QueryBuilders.matchAllQuery()).addFields(fieldList)
				.setSize(100).execute().actionGet();
		Vector<SearchHit> hits = new Vector<SearchHit>();
		while (true) {

			for (SearchHit hit : scrollResp.getHits().getHits()) {
				hits.add(hit);
			}
			scrollResp = view.getESClient()
					.prepareSearchScroll(scrollResp.getScrollId())
					.setScroll(new TimeValue(600000)).execute().actionGet();
			// Break condition: No hits are returned
			if (scrollResp.getHits().getHits().length == 0) {
				break;
			}
		}

		return hits.toArray(new SearchHit[hits.size()]);
	}

	public static Vector<String> getDatabases(ElasticsearchClientHandler view) {
		Vector<String> indices = new Vector<String>();
		ClusterStateResponse clusterStateResponse = view.getESClient().admin()
				.cluster().prepareState().execute().actionGet();
		ImmutableOpenMap<String, IndexMetaData> indexMappings = clusterStateResponse
				.getState().getMetaData().indices();
		for (ObjectCursor<String> key : indexMappings.keys()) {

			indices.add(key.value);
		}
		return indices;
	}

	public static boolean install(ElasticsearchClientHandler view,
			String indexName, Class path) {
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		Map<String, JSONObject> map = ElasticsearchHelper.getMapping(indexName,
				path);

		for (String key : map.keySet()) {
			try {
				request.mapping(key, map.get(key).toJSONString());

			} catch (Exception MapperParsingException) {
				System.err
						.println("Fehler beim Parsen des Mappings für ES von "
								+ key);
			}
		}
		try {
			view.getESClient().admin().indices().create(request).actionGet();
			view.getESClient().admin().cluster()
					.health(new ClusterHealthRequest().waitForYellowStatus())
					.actionGet();
			return true;
		} catch (IndexAlreadyExistsException ex) {
			System.err
					.println("ES: Index " + indexName + " existiert bereits.");
		} catch (Exception ex) {
			System.err
					.println("ES: unbekannte Ausnahme bei der Installation Exception:");
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param dir
	 *            - Ordner indem die *.json Datein gesucht werden sollen
	 * @return
	 */
	private static Map<String, JSONObject> getMapping(String dir, Class path) {
		Map<String, JSONObject> maps = new TreeMap<String, JSONObject>();
		URL url = path.getResource("initscripts/" + dir);
		File folder = new File(url.getFile());
		JSONParser parser = new JSONParser();
		// ordner existiert und ist Ordner
		if (folder.exists() && folder.isDirectory()) {
			// alle Dateien daraus holen
			for (File file : folder.listFiles()) {
				// Datie endet mit .json
				if (file.getName().matches(".*json")) {
					try {
						System.out.println("Installiere Mapping "
								+ file.getPath());
						JSONObject obj = (JSONObject) parser
								.parse(new FileReader(file));
						maps.put(file.getName().replace(".json", ""), obj);
						parser.reset();
					} catch (IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			System.err.println("Ordner existiert nicht: " + folder.getPath());
		}
		return maps;
	}

}
