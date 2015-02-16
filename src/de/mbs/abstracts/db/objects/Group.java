package de.mbs.abstracts.db.objects;

import java.io.Serializable;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class Group extends DatabaseObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2237832191948151549L;

	private String name, description;
	
	public Group(String id) {
		super(id);
	}
	
	public Group(String id, long version)
	{
		super(id, version);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
