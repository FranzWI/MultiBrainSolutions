package de.mbs.modules;

import java.util.Vector;

import de.mbs.modules.interfaces.Modul;

public class ModulContainer {

	private Vector<Modul> modules = null;
	private static ModulContainer container;
	
	private ModulContainer(){
		this.modules = new Vector<Modul>();
	}
	
	public static ModulContainer initialise(){
		if(container == null){
			container = new ModulContainer();
		}
		return container;
	}
	
	public void addModul(Modul mod){
		this.modules.add(mod);
	}
	
	public Vector<Modul> getModules(){
		return this.modules;
	}
	
}
