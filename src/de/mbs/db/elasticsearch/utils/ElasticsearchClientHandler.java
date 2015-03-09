package de.mbs.db.elasticsearch.utils;

import org.elasticsearch.client.Client;

public interface ElasticsearchClientHandler {

	public Client getESClient();
	
}
