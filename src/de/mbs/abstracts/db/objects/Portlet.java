package de.mbs.abstracts.db.objects;

import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class Portlet extends DatabaseObject {

	private String name, description, path;
	private Vector<String> usedByGroups;

	public Portlet(String id) {
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Vector<String> getUsedByGroups() {
		return usedByGroups;
	}

	public void setUsedByGroups(Vector<String> usedByGroups) {
		this.usedByGroups = usedByGroups;
	}

}
