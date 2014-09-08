package de.mbs.modules;

import java.io.File;

/**
 * Beinhaltet die Pfade für
 * 
 * 
 * @author MKürbis
 * 
 */
public class DataContainer {

	private File jarfile;

	// TODO Datenbank verbindung mit aufnehmen

	/**
	 * 
	 * @param jarfile
	 *            - File in dem die Datenstehen
	 */
	public DataContainer(File jarfile) {
		this.jarfile = jarfile;
	}

	public File getJarfile() {
		return jarfile;
	}

	public void setJarfile(File jarfile) {
		this.jarfile = jarfile;
	}

}
