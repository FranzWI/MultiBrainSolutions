package de.mbs.modules.interfaces;

import java.util.Map;
import java.util.Vector;

import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;

import net.xeoh.plugins.base.Plugin;
import de.mbs.modules.DataContainer;

public interface Modul extends Plugin{

	/**
	 * @return den Namen der im Men� angezeigt wird
	 */
	public String getMenuName();

	/**
	 * Name des Moduls
	 * @return
	 */
	public String getModulName();
	
	/**
	 * @return Beschreibung des Modules
	 */
	public String getDescription();

	/**
	 * Initalisiert das Modul
	 * 
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean install(DataContainer container);
	
	/**
	 * entfernt aller vorher installierten dateien
	 * 
	 * @return 
	 */
	public boolean remove(DataContainer container);

	/**
	 * @return true falls breits initialisiert sind, false falls noch nichts
	 *         initialisiert ist (DB...)
	 */
	public boolean isInstalled();

	/**
	 * @return true falls das Modul funktioniert, false falls nicht
	 */
	public boolean isRunning();
	
	/**
	 * @return Fehlermeldung warum das Modul nicht funktioniert
	 */
	public String getError();
	
	/**
	 * @param path - Ordner des Wurzelverzeichnisses
	 * @return Liefert den Pfad zum Frontend bsp.: 'WEB-INF/includes/modules/modul.groovy', falls kein Frontend null
	 */
	public String getFrontendFile();
	
	/**
	 * 
	 * @return den Pfad f�r den Configoberfl�che des Administrators, falls keine Config null
	 */
	public String getConfigFile();
	
	/**
	 * 
	 * Schl�ssel ist der Name des Portlets, Value ist der Dateipfad
	 * 
	 * @return Pfade f�r die Portlets falls keine dann null
	 */
	public Map<String,String> getPortletFiles();
	
	/**
	 * Methode die beim Starten des Management Cockpits ausgef�hrt wird (Tomcat start)
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean startup();
	
	/**
	 *  Methode die beim Herunterfahren des Management Cockpits ausgef�hrt wird (Tomcat stop, undeploy)
	 * @return true falls erfolgreich, false falls nicht
	 */
	public boolean shutdown();
	
	/**
	 * liefert die aktuelle Version
	 * @return
	 */
	public String getVersion();
	
	/**
	 * liefert den namen der Css-Klasse des Entypo Icons
	 * @return
	 */
	public String getMenuIcon();
	
	/**
	 * gibt an ob nach der Installation des Moduls der Tomcat neugestartet werden muss
	 * @return
	 */
	public boolean requiresRestart();
	
	/**
	 * Wird von der Klasse ModulREST  verwendet um der Methode die Abfrage �ber zuhelfen
	 * 
	 * @param uriInfo - Parameter
	 * @param obj - Das JSONObject welches dem CLient als String zur�ck geliefert werden soll
	 * @return String der an den Aufrufer �bergeben werden muss
	 */
	public void handleRest(UriInfo uriInfo, JSONObject obj);
	
	/**
	 * liste der Javascript dateien die verwendet werden sollen
	 * 
	 * @return
	 */
	public Vector<String> getJavascripts();
	
}
