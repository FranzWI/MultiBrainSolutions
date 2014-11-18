package de.mbs.abstracts.db.objects.definition;

public class DatabaseObject {

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

}
