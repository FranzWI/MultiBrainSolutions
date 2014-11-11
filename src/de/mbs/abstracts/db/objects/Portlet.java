package de.mbs.abstracts.db.objects;

import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class Portlet extends DatabaseObject {

	private String name, description, path;
	private Vector<String> usedByGroups = new Vector<String>();

	/**
	 * siehe Bootstrap Doku
	 */
	private int sizeXS, sizeSM, sizeMD, sizeLG;
	
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
	
	public void addUseableGroup(String id){
		this.usedByGroups.add(id);
	}

	public int getSizeXS() {
		return sizeXS;
	}

	public void setSizeXS(int sizeXS) {
		this.sizeXS = sizeXS;
	}

	public int getSizeSM() {
		return sizeSM;
	}

	public void setSizeSM(int sizeSM) {
		this.sizeSM = sizeSM;
	}

	public int getSizeMD() {
		return sizeMD;
	}

	public void setSizeMD(int sizeMD) {
		this.sizeMD = sizeMD;
	}

	public int getSizeLG() {
		return sizeLG;
	}

	public void setSizeLG(int sizeLG) {
		this.sizeLG = sizeLG;
	}

}
