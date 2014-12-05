package de.mbs.modules;

import java.io.File;

import de.mbs.abstracts.db.DatabaseView;

/**
 * Beinhaltet das JarFile zum installieren und übergibt die Datenbannk verbindung
 * 
 * 
 * @author MK�rbis
 * 
 */
public class DataContainer {

	private File jarfile;
	private DatabaseView databaseview;

	// TODO Datenbank verbindung mit aufnehmen

	/**
	 * 
	 * @param jarfile
	 *            - File in dem die Datenstehen
	 */
	public DataContainer(File jarfile, DatabaseView view) {
		this.jarfile = jarfile;
		this.setDatabaseview(view);
	}

	public File getJarfile() {
		return jarfile;
	}

	public void setJarfile(File jarfile) {
		this.jarfile = jarfile;
	}

	public DatabaseView getDatabaseview() {
		return databaseview;
	}

	public void setDatabaseview(DatabaseView databaseview) {
		this.databaseview = databaseview;
	}

}
