package de.mbs.db.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.cursors.ObjectCursor;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.elasticsearch.node.Node;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.mbs.interfaces.DatabaseView;

public class ElasticsearchView implements DatabaseView{

	private Client client;

	public ElasticsearchView() {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "MBS Management Cockpit Cluster")
				.put("node.data", false).put("network.host", "localhost")
				.build();
		Node node = nodeBuilder().client(true).settings(settings)
				.clusterName("MBS Management Cockpit Cluster").node();
		client = node.client();
		System.out.println("ES: initialisiert verbunden");
		if (!this.isInstalled()) {
			System.out.println("ES: nicht installiert");
			if (this.install()) {
				System.out.println("ES: erfolgreich installiert");
			} else {
				System.out.println("ES: installation fehlgeschlagen");
			}

		}
		this.printESStructure();
	}

	public Client getESClient() {
		return this.client;
	}

	private void printESStructure() {
		System.out.println("ES:Structure");
		for (Entry<String, Vector<String>> structur : this.getESStructure()
				.entrySet()) {
			System.out.println("ES:\tIndex:" + structur.getKey());
			for (String type : structur.getValue()) {
				System.out.println("ES:\t\t->" + type);
			}
		}
	}

	public String getServiceName(){
		return "elasticsearch";
	}
	
	/**
	 * 
	 * alle Indexe abfragen
	 * 
	 * @return
	 */
	public Vector<String> getDatabases(){
		Vector<String> indices = new Vector<String>();
		ClusterStateResponse clusterStateResponse = client.admin().cluster()
				.prepareState().execute().actionGet();
		ImmutableOpenMap<String, IndexMetaData> indexMappings = clusterStateResponse
				.getState().getMetaData().indices();
		for (ObjectCursor<String> key : indexMappings.keys()) {

			indices.add(key.value);
		}
		return indices;
	}

	/**
	 * 
	 * @return die Größe der Indexe in Bytes
	 */
	public Map<String, Long> getDatabaseSize() {
		Map<String, Long> map = new TreeMap<String, Long>();
		for (String index : this.getDatabases()) {
			IndicesStatsResponse stats = client.admin().indices()
					.prepareStats().clear().setIndices(index).setStore(true)
					.execute().actionGet();
			long bytes = stats.getIndex(index).getTotal().getStore().getSize()
					.bytes();
			map.put(index, bytes);
		}
		return map;
	}

	/**
	 * 
	 * @param index
	 *            - bei dem die Anzahl der DOkumente gezählt werden soll
	 * @return die Anzahl der Dokumente die in einen Index sind
	 */
	public long getEntryCount(String index) {
		CountResponse response = client.prepareCount(index).execute()
				.actionGet();
		return response.getCount();
	}

	/**
	 * alle Typen aller Indexe abfragen
	 * 
	 * @return
	 */
	public Map<String, Vector<String>> getESStructure() {
		Map<String, Vector<String>> indexes = new TreeMap<String, Vector<String>>();
		ClusterStateResponse clusterStateResponse = client.admin().cluster()
				.prepareState().execute().actionGet();
		ImmutableOpenMap<String, IndexMetaData> indexMappings = clusterStateResponse
				.getState().getMetaData().indices();
		for (ObjectCursor<String> index : indexMappings.keys()) {
			ImmutableOpenMap<String, MappingMetaData> typeMappings = clusterStateResponse
					.getState().getMetaData().index(index.value).getMappings();
			Vector<String> types = new Vector<String>();
			for (ObjectCursor<String> type : typeMappings.keys()) {
				types.add(type.value);
			}
			indexes.put(index.value, types);
		}
		return indexes;
	}

	public boolean isRunning() {
		return client.admin() != null;
	}

	public boolean isInstalled() {
		Vector<String> dbs = this.getDatabases();
		return dbs.size() > 0
				&& dbs.contains("system");
	}

	private JSONObject getProperties(String[][] data) {
		JSONObject grr = new JSONObject();
		for (int i = 0; i < data.length; i++) {
			grr.put(data[i][0], data[i][1]);
		}
		return grr;
	}

	/**
	 * 
	 * @param dir
	 *            - Ordner indem die *.json Datein gesucht werden sollen
	 * @return
	 */
	private Map<String, JSONObject> getMapping(String dir) {
		Map<String, JSONObject> maps = new TreeMap<String, JSONObject>();
		URL url = this.getClass().getResource("initscripts/" + dir);
		File folder = new File(url.getFile());
		JSONParser parser = new JSONParser();
		// ordner existiert und ist Ordner
		if (folder.exists() && folder.isDirectory()) {
			// alle Dateien daraus holen
			for (File file : folder.listFiles()) {
				// Datie endet mit .json
				if (file.getName().matches(".*json")) {
					try {
						System.out.println("DEBUG "+dir+"/"+file.getName());
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
		}
		return maps;
	}

	public boolean install() {
		// Index System erstellen
		String indexName = "system";
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		Map<String, JSONObject> map = this.getMapping("system");
		for (String key : map.keySet()) {
			request.mapping(key, map.get(key).toJSONString());
		}
		try {
			client.admin().indices().create(request).actionGet();
			client.admin().cluster()
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

	@Override
	public boolean exit() {
		this.getESClient().close();
		return true;
	}
}