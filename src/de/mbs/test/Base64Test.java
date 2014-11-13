package de.mbs.test;

import java.io.File;

import de.mbs.db.elasticsearch.utils.ElasticsearchHelper;

public class Base64Test {

	public static void main(String[] args) {
		System.out.println(ElasticsearchHelper.encodeFileBase64(new File("1.pdf")));
	}

}
