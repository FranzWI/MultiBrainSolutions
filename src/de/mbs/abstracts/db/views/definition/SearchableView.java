package de.mbs.abstracts.db.views.definition;

import java.util.Vector;

import de.mbs.abstracts.db.utils.SearchResult;

public interface SearchableView {

	
	public String searchId();
	
	/**
	 * Diese Methode führt die eigentliche Suche aus
	 * 
	 * @param search
	 * @return
	 */
	public Vector<SearchResult> search(String search);
}
