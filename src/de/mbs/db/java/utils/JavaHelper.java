package de.mbs.db.java.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class JavaHelper {

	public static DateFormat DATETIME_NO_MILLIS_FORMATER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	public static <A extends DatabaseObject> A edit(A type, List<A> data) {
		for (int i = 0; i < data.size(); i++) {
			A u = data.get(i);
			if (u.getId().equals(type.getId())) {
				data.set(i, type);
				return type;
			}
		}
		return null;
	}

	public static <A extends DatabaseObject> A get(String id, List<A> data) {
		for (A u : data) {
			if (u.getId().equals(id)) {
				return u.makeClone();
			}
		}
		return null;
	}

	public static <A extends DatabaseObject> boolean remove(String id,
			List<A> data) {
		for (A u : data) {
			if (u.getId().equals(id)) {
				data.remove(u);
				return true;
			}
		}
		return false;
	}

}
