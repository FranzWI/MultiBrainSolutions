package de.mbs.abstracts.db.objects.definition;

public class DatabaseObject {

	private String id = null;
	private int version = -1;
	private long timestamp = -1L;

	public DatabaseObject(String id) {
		this.id = id;
	}

	public DatabaseObject(String id, int version) {
		this.id = id;
		this.version = version;
	}

	public DatabaseObject(String id, long timestamp) {
		this.id = id;
		this.timestamp = timestamp;
	}

	public DatabaseObject(String id, int version, long timestamp) {
		this.id = id;
		this.version = version;
		this.timestamp = timestamp;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

}
