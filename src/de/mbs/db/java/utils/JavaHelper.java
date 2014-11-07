package de.mbs.db.java.utils;

import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class JavaHelper {

	public static <A extends DatabaseObject> A edit(A type, Vector<A> data) {
		for (int i = 0; i < data.size(); i++) {
			A u = data.get(i);
			if (u.getId().equals(type.getId())) {
				data.set(i, type);
				return type;
			}
		}
		return null;
	}

	public static <A extends DatabaseObject> A get(String id, Vector<A> data) {
		for (A u : data) {
			if (u.getId().equals(id)) {
				return u;
			}
		}
		return null;
	}

	public static <A extends DatabaseObject> boolean remove(String id,
			Vector<A> data) {
		for (A u : data) {
			if (u.getId().equals(id)) {
				data.remove(u);
				return true;
			}
		}
		return false;
	}

}
