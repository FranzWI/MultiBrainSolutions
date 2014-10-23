package de.mbs.abstracts.db.views.definition;

import java.util.Vector;

public interface RemoveableView<A> {

	public boolean remove(A data);
	public boolean remove(Vector<A> data);
	
}
