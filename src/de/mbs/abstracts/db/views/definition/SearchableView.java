package de.mbs.abstracts.db.views.definition;

import groovy.xml.MarkupBuilder;

public interface SearchableView {

	/**
	 * 
	 * @param index
	 *            - index anhand überprüft werden kann ob das überhaupt
	 *            eintreffer ist
	 * @param type
	 *            - type das gleiche wie bei index
	 * @param id
	 *            - des datensatzes
	 * @return liefert null falls kein Treffer vorliegt, ansonsten den Content
	 *         als Markupbuilder
	 */
	public MarkupBuilder getFormatedContent(String index, String type, String id);

}
