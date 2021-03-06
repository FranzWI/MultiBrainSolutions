package de.mbs.db.elasticsearch;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.cursors.ObjectCursor;
import org.json.simple.JSONObject;

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.Pair;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.GroupView;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.abstracts.db.views.UserPortletView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.abstracts.db.views.definition.SearchableView;
import de.mbs.db.elasticsearch.utils.ElasticsearchClientHandler;
import de.mbs.db.elasticsearch.utils.ElasticsearchHelper;
import de.mbs.db.elasticsearch.views.ElasticsearchGroupview;
import de.mbs.db.elasticsearch.views.ElasticsearchMessageview;
import de.mbs.db.elasticsearch.views.ElasticsearchNotificationview;
import de.mbs.db.elasticsearch.views.ElasticsearchPortletview;
import de.mbs.db.elasticsearch.views.ElasticsearchSettingsview;
import de.mbs.db.elasticsearch.views.ElasticsearchUserPortletview;
import de.mbs.db.elasticsearch.views.ElasticsearchUserview;

public class ElasticsearchView extends DatabaseView implements ElasticsearchClientHandler {

	private Client client;

	private ElasticsearchUserview userview;
	private ElasticsearchGroupview groupview;
	private ElasticsearchPortletview portletview;
	private ElasticsearchSettingsview settingview;
	private ElasticsearchMessageview messageview;
	private ElasticsearchNotificationview notificationview;
	private ElasticsearchUserPortletview userportletview;

	public ElasticsearchView() {
		this.connect();
		this.init();
	}

	public ElasticsearchView(boolean reset) {
		this.connect();
		if (reset) {
			// ACHTUNG alle indexe löschen !
			DeleteIndexResponse response = client.admin().indices()
					.delete(new DeleteIndexRequest("_all")).actionGet();
			if (response.isAcknowledged()) {
				System.out.println("ES: Index gelöscht");
			}
		}
		this.init();
	}

	private void connect() {
		client = new TransportClient(ElasticsearchHelper.CONNECTIONSETTINGS).addTransportAddress(ElasticsearchHelper.CONNECTIONADRESS);
		System.out.println("ES: initialisiert, verbunden");
	}

	private void init() {
		if (!this.isInstalled()) {
			System.out.println("ES: nicht installiert");
			if (this.install()) {
				this.groupview = new ElasticsearchGroupview(this);
				
				System.out.println("DEBUG ID admin Group : "+this.groupview.getAdminGroupId());
				System.out.println("DEBUG ID User Group : "+this.groupview.getUserGroupId());
				
				this.portletview = new ElasticsearchPortletview(this);
				
				Portlet p = new Portlet(null);
				p.setName("Festplattenauslastung");
				p.setPath("admin/hdd.groovy");
				p.setDescription("Test Portlet No. 1");
				p.setSizeXS(12);
				p.setSizeSM(6);
				p.setSizeMD(3);
				p.setSizeLG(2);
				p.addUseableGroup(this.getGroupView().getAdminGroupId());
				portletview.add(p);
				
				Portlet p2 = new Portlet(null);
				p2.setName("RAM-Auslastung");
				p2.setPath("admin/ram.groovy");
				p2.setDescription("Test Portlet No. 2");
				p2.addUseableGroup(this.getGroupView().getAdminGroupId());
				p2.setSizeXS(12);
				p2.setSizeSM(6);
				p2.setSizeMD(3);
				p2.setSizeLG(2);
				portletview.add(p2);
				
				Portlet p4 = new Portlet(null);
				p4.setName("Angemeldete Nutzer");
				p4.setPath("admin/loginuser.groovy");
				p4.setDescription("Test Portlet No. 2");
				p4.addUseableGroup(this.getGroupView().getAdminGroupId());
				p4.setSizeXS(12);
				p4.setSizeSM(6);
				p4.setSizeMD(3);
				p4.setSizeLG(2);
				portletview.add(p4);
				
				this.userview = new ElasticsearchUserview(this);
				this.messageview = new ElasticsearchMessageview(this);
				this.settingview = new ElasticsearchSettingsview(this);
				this.notificationview = new ElasticsearchNotificationview(this);
				this.userportletview = new ElasticsearchUserPortletview(this);
				System.out.println("ES: erfolgreich installiert");

				System.out.println("ES: initialisiere default Data");
				User admin = new User(null);
				admin.setFirstname("Admini");
				admin.setLastname("Strator");
				admin.setUsername("admin");
				admin.setEmail("ich@michaelkuerbis.de");
				admin.setActive(true);
				admin.setPw("admin");
				admin.addMembership(this.groupview.getAdminGroupId());
				admin.addMembership(this.groupview.getUserGroupId());
				if(this.userview.add(admin) == null)
					System.err.println("Admin anlegen fehlgeschlagen");

				// Nutzer anlegen
				User user = new User(null);
				user.setFirstname("Default");
				user.setLastname("User");
				user.setUsername("user");
				user.setEmail("ich@michaelkuerbis.de");
				user.setActive(true);
				user.addMembership(this.groupview.getUserGroupId());
				user.setPw("user");
				this.userview.add(user);
			} else {
				System.out.println("ES: installation fehlgeschlagen");
			}
		} else {
			this.groupview = new ElasticsearchGroupview(this);
			this.portletview = new ElasticsearchPortletview(this);
			this.userview = new ElasticsearchUserview(this);
			this.messageview = new ElasticsearchMessageview(this);
			this.settingview = new ElasticsearchSettingsview(this);
			this.notificationview = new ElasticsearchNotificationview(this);
			this.userportletview = new ElasticsearchUserPortletview(this);
		}
		this.addSearchableView(this.getUserView());
		this.addSearchableView(this.getMessageView());
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

	public String getServiceName() {
		return "elasticsearch";
	}

	/**
	 * 
	 * alle Indexe abfragen
	 * 
	 * @return
	 */
	public Vector<String> getDatabases() {
		return ElasticsearchHelper.getDatabases(this);
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
		return dbs.size() > 0 && dbs.contains("system");
	}

	private JSONObject getProperties(String[][] data) {
		JSONObject grr = new JSONObject();
		for (int i = 0; i < data.length; i++) {
			grr.put(data[i][0], data[i][1]);
		}
		return grr;
	}

	

	public boolean install() {
		return ElasticsearchHelper.install(this, "system", this.getClass());
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

	/*
	@Override
	public Map<String, Vector<Pair<SearchResult, String>>> search(
			String search, User u) {
		Map<String, Vector<Pair<SearchResult, String>>> result = new TreeMap<String, Vector<Pair<SearchResult, String>>>();
		for (SearchableView searchable : this.getSearchable()) {
			// TODO suchen :)
		}
		return result;
	}
	*/

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

	@Override
	public UserPortletView getUserPortletView(){
		return this.userportletview;
	}
}
