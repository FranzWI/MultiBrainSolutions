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
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
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

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.Pair;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.GroupView;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.abstracts.db.views.definition.SearchableView;
import de.mbs.db.elasticsearch.views.ElasticsearchGroupview;
import de.mbs.db.elasticsearch.views.ElasticsearchMessageview;
import de.mbs.db.elasticsearch.views.ElasticsearchNotificationview;
import de.mbs.db.elasticsearch.views.ElasticsearchPortletview;
import de.mbs.db.elasticsearch.views.ElasticsearchSettingsview;
import de.mbs.db.elasticsearch.views.ElasticsearchUserview;

public class ElasticsearchView extends DatabaseView{

	private Client client;

	private ElasticsearchUserview userview;
	private ElasticsearchGroupview groupview;
	private ElasticsearchPortletview portletview;
	private ElasticsearchSettingsview settingview;
	private ElasticsearchMessageview messageview;
	private ElasticsearchNotificationview notificationview;
	
	public ElasticsearchView() {
		this.connect();
		this.init();
	}
	
	public ElasticsearchView(boolean reset){
		this.connect();
		if(reset){
			// ACHTUNG alle indexe löschen !
			DeleteIndexResponse response = client.admin().indices().delete(new DeleteIndexRequest("_all")).actionGet();
			if(response.isAcknowledged()){
				System.out.println("ES: Index gelöscht");
			}
		}
		this.init();
	}
	
	private void connect(){
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "MBS Management Cockpit Cluster")
				.put("node.data", false).put("network.host", "localhost")
				.build();
		Node node = nodeBuilder().client(true).settings(settings)
				.clusterName("MBS Management Cockpit Cluster").node();
		client = node.client();
		System.out.println("ES: initialisiert, verbunden");
	}
	
	private void init(){
		if (!this.isInstalled()) {
			System.out.println("ES: nicht installiert");
			if (this.install()) {
				this.groupview = new ElasticsearchGroupview(this);
				this.portletview = new ElasticsearchPortletview();
				this.userview = new ElasticsearchUserview(this);
				this.messageview = new ElasticsearchMessageview(this);
				this.settingview = new ElasticsearchSettingsview(this);
				this.notificationview = new ElasticsearchNotificationview(this);
				System.out.println("ES: erfolgreich installiert");
				
				System.out.println("ES: initialisiere default Data");
				User admin = new User(null);
				admin.setFirstname("Admini");
				admin.setLastname("Strator");
				admin.setUsername("admin");
				admin.setEmail("ich@michaelkuerbis.de");
				admin.setPw("admin");
				//TODO kommentare entfernen wenn groupview geht
				//for (Group group : this.getGroupView().getAll()) {
				//	admin.addMembership(group.getId());
				//}
				System.out.println("ES: DEBUG ID Admin "+this.getUserView().add(admin));

				// Nutzer anlegen
				User user = new User(null);
				user.setFirstname("Default");
				user.setLastname("'User");
				user.setUsername("user");
				user.setEmail("ich@michaelkuerbis.de");
				user.setPw("user");
				//TODO kommentare entfernen wenn groupview geht
				//for (Group group : this.getGroupView().getAll()) {
				//	if (group.getName().equals("Nutzer"))
				//		user.addMembership(group.getId());
				//}
				System.out.println("ES: DEBUG ID User "+this.getUserView().add(user));
			} else {
				System.out.println("ES: installation fehlgeschlagen");
			}
		}else{
			this.groupview = new ElasticsearchGroupview(this);
			this.portletview = new ElasticsearchPortletview();
			this.userview = new ElasticsearchUserview(this);
			this.messageview = new ElasticsearchMessageview(this);
			this.settingview = new ElasticsearchSettingsview(this);
			this.notificationview = new ElasticsearchNotificationview(this);
		}
		this.addSearchableView(this.getUserView());
		this.addSearchableView(this.getMessageView());
		//this.printESStructure();
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

	@Override
	public UserView getUserView() {
		return this.userview;
	}

	@Override
	public PortletView getPortletView() {
		return this.portletview;
	}

	@Override
	public GroupView getGroupView() {
		return this.groupview;
	}

	@Override
	public Map<String,Vector<Pair<SearchResult,String>>> search(String search, User u) {
		Map<String,Vector<Pair<SearchResult,String>>> result = new TreeMap<String,Vector<Pair<SearchResult,String>>>();
		for(SearchableView searchable : this.getSearchable()){
			//TODO suchen :)
		}
		return result;
	}

	@Override
	public MessageView getMessageView() {
		return this.messageview;
	}

	@Override
	public SettingsView getSettingsView() {
		return this.settingview;
	}

	@Override
	public NotificationView getNotificationView() {
		return this.notificationview;
	}
}
