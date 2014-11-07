package de.mbs.abstracts.db.views.definition;

import java.util.Vector;

import groovy.xml.MarkupBuilder;

public interface SearchableView {

	
	public String searchId();
	
	/**
	 * Diese Methode führt die eigentliche Suche aus
	 * 
	 * @param search
	 * @return
	 */
	public Vector<MarkupBuilder> search(String search);
}
