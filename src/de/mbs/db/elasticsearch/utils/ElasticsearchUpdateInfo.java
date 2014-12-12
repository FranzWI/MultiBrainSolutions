package de.mbs.db.elasticsearch.utils;

/**
 * Wird von den Observable in abhängigkeit der ausgeführten Aktion weitergegeben<br>
 * <br>
 * Beispiel: eine Gruppe wird entfernt<br>
 * <br>
 * dann würde in der GroupView bei der Methode remove folgendes stehen<br>
 * <br>
 * ...<br>
 * setChanged(); <br>
 * notifyObservers(new
 * ElasticsearchUpdateInfo("system","group",id,ElasticsearchUpdateInfo
 * .ACTION_REMOVE));<br>
 * ...<br>
 * <br>
 * somit könnten alle Views die etwas mit gruppen zutun haben darauf reagieren<br>
 * <br>
 * Beispiele sind<br>
 * <ul>
 * <li>Nutzer</li>
 * <li>Nachrichten</li>
 * <li>Benachrichtigungen</li>
 * <li>Portlets</li>
 * </ul>
 * <br>
 * @author mkürbis
 *
 */
public class ElasticsearchUpdateInfo {

	public final static int ACTION_ADD = 0, ACTION_EDIT = 1, ACTION_REMOVE = 2;

	private String index = "", type = "", id = "";

	private int action = -1;

	public ElasticsearchUpdateInfo(String index, String type, String id,
			int action) {
		this.index = index;
		this.type = type;
		this.id = id;
		this.action = action;
	}

	public String getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public int getAction() {
		return action;
	}

}
