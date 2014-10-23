package de.mbs.abstracts.db.views.definition;

public interface FindableView<A> {

	/**
	 * 
	 * @param id - ID des Datensatzes
	 * @return falls das Objekt existiert den Datensatz andernfalls null
	 */
	public A get(String id);
	
}
