package de.mbs.abstracts.db.objects.definition;

import java.io.Serializable;

public class DatabaseObject implements Serializable {

	private static final long serialVersionUID = -3454418128438341284L;
	
	private String id = null;
	private long version = -1;

	public DatabaseObject(String id) {
		this.id = id;
	}

	public DatabaseObject(String id, long version) {
		this.id = id;
		this.version = version;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public <A extends DatabaseObject> A makeClone(){
		try {
			return (A) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
