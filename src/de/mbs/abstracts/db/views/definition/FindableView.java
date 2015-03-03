package de.mbs.abstracts.db.views.definition;

import java.util.List;

public interface FindableView<A> {

	/**
	 * 
	 * @param id - ID des Datensatzes
	 * @return falls das Objekt existiert den Datensatz andernfalls null
	 */
	public A get(String id);
	
	public List<A> getAll();
	
}
