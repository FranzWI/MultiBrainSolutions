package de.mbs.abstracts.db.views.definition;

import java.util.Vector;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.Pair;
import de.mbs.abstracts.db.utils.SearchResult;

public interface SearchableView {


	public String getTabName();
	//get Reiter name

	/**
	 * @param search - Suchanfrage
	 * @param u - Nutzer der die Suchanfrage durchführt
	 * @return
	 */
	public Vector<Pair<SearchResult, String>> search(String search, User u);
}
