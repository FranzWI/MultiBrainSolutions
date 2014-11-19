package de.mbs.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.db.java.JavaView;
import de.mbs.test.backend.AllBackendTests;

/**
 * JUnit:
 * http://www.vogella.com/tutorials/JUnit/article.html
 * 
 * @author MKuerbis
 *
 */
public class TestExecuter {

	private static DatabaseView view = null;

	public static void start(DatabaseView view) {
		TestExecuter.view = view;
		System.out.println("Beginne Backend Testfälle");
		Result r = JUnitCore.runClasses(AllBackendTests.class);
		for (Failure failure : r.getFailures()) {
			System.err.println(failure.toString());
		}
		System.out.println("Backend Testdauer: "+r.getRunTime()+"ms");
		System.out.println("Backend Fehlgeschlagene Testfälle: "+r.getFailureCount()+" / "+r.getRunCount());
	}

	public static DatabaseView getView() {
		return TestExecuter.view;
	}

	public static void main(String[] args) {
		// TODO hier die zu testenden Database hinterlegen
		TestExecuter.start(new ElasticsearchView(true));
		TestExecuter.start(new JavaView());
	}

}
