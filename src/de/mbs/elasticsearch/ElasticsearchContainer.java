package de.mbs.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
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

public class ElasticsearchContainer {

	private static ElasticsearchContainer container;

	private Client client;

	private ElasticsearchContainer() {
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

	public static ElasticsearchContainer initialise() {
		if (container == null) {
			container = new ElasticsearchContainer();
		}
		return container;
	}

	public Client getESClient() {
		return this.client;
	}

	private void printESStructure(){
		System.out.println("ES:Structure");
		for(Entry<String, Vector<String>> structur: this.getESStructure().entrySet()){
			System.out.println("ES:\tIndex:"+structur.getKey());
			for(String type: structur.getValue()){
				System.out.println("ES:\t\t->"+type);
			}
		}
	}
	
	/**
	 * 
	 * alle Indexe abfragen
	 * @return
	 */
	public Vector<String> getESIndices() {
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
	 * alle Typen aller Indexe abfragen
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
		return this.getESIndices().size() > 0;
	}

	private JSONObject getProperties(String[][] data) {
		JSONObject grr = new JSONObject();
		for (int i = 0; i < data.length; i++) {
			grr.put(data[i][0], data[i][1]);
		}
		return grr;
	}

	public boolean install() {
		// Index System erstellen
		String indexName = "system";
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		//Type user erstellen
		//TODO vereinfachen ....
		String strType = "user";
		JSONObject obj = new JSONObject();
		JSONObject propList = new JSONObject();
		propList.put("name",
				this.getProperties(new String[][] { { "type", "string" } }));
		propList.put("surname",
				this.getProperties(new String[][] { { "type", "string" } }));

		JSONObject prop = new JSONObject();
		prop.put("properties", propList);
		obj.put("user", prop);

		request.mapping(strType, obj.toJSONString());
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
}
