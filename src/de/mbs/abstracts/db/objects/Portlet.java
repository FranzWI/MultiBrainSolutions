package de.mbs.abstracts.db.objects;

import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class Portlet extends DatabaseObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3841872276961844960L;
	private String name, description, path;
	private Vector<String> usedByGroups = new Vector<String>();

	/**
	 * siehe Bootstrap Doku
	 */
	private int sizeXS, sizeSM, sizeMD, sizeLG;
	
	private boolean multiple = false;
	
	public Portlet(String id) {
		super(id);
	}
	
	public Portlet(String id, long version) {
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
		return (int) sizeXS;
	}

	public void setSizeXS(int sizeXS) {
		this.sizeXS = (int) sizeXS;
	}

	public int getSizeSM() {
		return (int) sizeSM;
	}

	public void setSizeSM(int sizeSM) {
		this.sizeSM = (int) sizeSM;
	}

	public int getSizeMD() {
		return (int) sizeMD;
	}

	public void setSizeMD(int sizeMD) {
		this.sizeMD = (int) sizeMD;
	}

	public int getSizeLG() {
		return (int) sizeLG;
	}

	public void setSizeLG(int sizeLG) {
		this.sizeLG = (int) sizeLG;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

}
