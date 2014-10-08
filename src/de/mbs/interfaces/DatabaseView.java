package de.mbs.interfaces;

import java.util.Map;
import java.util.Vector;

/**
 * 
 * @author mkuerbis
 * 
 * Sicht auf die Datenbank
 *
 */
public interface DatabaseView {
	
	public String getServiceName();
	
	public Vector<String> getDatabases();
	
	public Map<String, Long> getDatabaseSize();
	
	public long getEntryCount(String database);
	
	public boolean isRunning();
	
	public boolean exit();
	
}
