package de.mbs.abstracts.db.objects;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class Group extends DatabaseObject {

	private String name, description;
	
	public Group(String id) {
		super(id);
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
