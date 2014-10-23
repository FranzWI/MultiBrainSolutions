package de.mbs.abstracts.db.views.definition;

public interface EditableView<A> {

	/**
	 * 
	 * @param data - Daten die geändert werden sollen
	 * @return bei erfolgreicher änderung den geänderten Datensatz bei nicht erfolgreicher änderung null
	 */
	public A edit(A data);
	
}
