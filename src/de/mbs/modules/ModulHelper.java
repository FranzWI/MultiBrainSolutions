package de.mbs.modules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Sammlung von Methoden und Variablen für die Module
 * 
 * @author MKürbis
 *
 */
public class ModulHelper {

	public final static String MODUL_LIB_PATH = "/WEB-INF/lib/modules/";
	public final static String MODUL_FRONTEND_PATH = "/WEB-INF/includes/modules/";
	
	private static String serlvetContextPath = "";
	
	public static boolean jarEntryToFile(JarFile jar, JarEntry entry, File f) {
		try {
			InputStream is = jar.getInputStream(entry);
			FileOutputStream fos = new FileOutputStream(f);
			while (is.available() > 0) {
				fos.write(is.read());
			}
			fos.close();
			is.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // get the input stream
		return false;
	}

	public static String getSerlvetContextPath() {
		return serlvetContextPath;
	}

	public static void setSerlvetContextPath(String serlvetContextPath) {
		ModulHelper.serlvetContextPath = serlvetContextPath;
	}

}
