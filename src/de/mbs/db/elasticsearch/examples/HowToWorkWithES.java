package de.mbs.db.elasticsearch.examples;

import groovy.xml.MarkupBuilder;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;
import de.mbs.abstracts.db.views.definition.SearchableView;
import de.mbs.abstracts.db.views.definition.Vector;

/**
 * @author MeinelF1
 * HOWTO use Elasticsearch if one of the following views is implemented
 * AddableView<A>, EditableView<A>, FindableView<A>, RemoveableView<A>, SearchableView 
 * Each method represents one way as an example or guidline for following usages 
 */
public class HowToWorkWithES 
{
	private String getIndex()
	{
		return "MyIndex";
	}
	
	private String getType()
	{
		return "MyType";
	}
	
	private int getID()
	{
		return 1;
	}
	
	/**
	 * this method returns the Elasticsearchclient
	 */
	private Object getESClient()
	{
		Object myEsClient = null;
		return myEsClient;
	}
	
	/**
	 * Here you need the Index where you want to put the JSON object into, the type and the ID:
	 * the newDataSet should be a JSON object in the propper form
	 */
	private void addSomething_withID(Object newDataset)
	{
		IndexResponse response = getESClient().prepareIndex(getIndex(),getType(),getID()).setSource(newDataSet).execute().actionGet();
	}
	private void addSomething_withoutID(Object newDataset)
	{
		IndexResponse response = getESClient().prepareIndex(getIndex(),getType()).setSource(newDataSet).execute().actionGet();
	}
	
	/**
	 * Edit a JSON object in a specific Index
	 */
	private void editSomething(Object newDataset)
	{
		/*
		 * At this point we can choose between several ways to edit the JSON documents. In my opinion we should provide the user a 
		 * history with all information about changes. To provide this it is necessary to contain all old JSON objects in an archiev.
		 * So I have decided to implement a 3 staged way:
		 * 1. create a new JSON object
		 * 2. take the old JSON object and move it to the archiev
		 * 3. save the new JSON object into the appendant index
		 */
		
		//1.
		createJSONObject(ObjectNewDataset);
		//2.
		moveSomething(getDataset(),"archiev","IndexArchiev");
		//3.
		addSomething(JSONObject newDataset);
		
	}
	
	/**
	 * TODO: Should this method contain a GET-Request or should it used to search in an index? 
	 */
	private void findSomething()
	{
		//Way to get an JSON Object with ID from a given Type in a given Index.
		IndexRequest indexRequest = new IndexRequest(getIndex(),getType(),getID());
		IndexRequest response = getESClient().index(indexRequest).actionGet();
		
		//Way to find in an specific index
		
		
		//Way to find over all indexes
	}

	/**
	 * To use this method you need a couple of information:
	 * Which index should I take.
	 * Type of the object I would like to delete.
	 * Object ID
	 */
	private void removeSomething()
	{
		DeleteResponse deleteResponse = getClient().prepareDelete(getIndex(),getType(),getID()).execute().actionGet();
		/* 
		 * If you want you can get now further response information like deleteResponse.getIndex()...
		 */
	}
	
	private void searchSomething()
	{
		
	}
}
