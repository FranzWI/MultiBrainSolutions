package de.mbs.abstracts.db.views.definition;


public interface AddableView<A> {

	/**
	 * 
	 * @param data
	 * @return liefert die ID des Datensatzes zurück
	 */
	public String add(A data);
	
}
