package de.mbs.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class ElasticsearchContainer {

	private static ElasticsearchContainer container;
	
	private Client client;
	
	private ElasticsearchContainer(){
		Settings settings = ImmutableSettings.settingsBuilder()
		        .put("cluster.name", "MBS Management Cockpit Cluster").put("node.data",false).build();
		client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		System.out.println("ES initialisiert");
	}
	
	public static ElasticsearchContainer initialise(){
		if(container == null){
			container = new ElasticsearchContainer();
		}
		return container;
	}
	
	public Client getESClient(){
		return this.client;
	}
	
}
