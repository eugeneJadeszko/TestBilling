package test.by.intexsoft.testBilling;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import main.by.intexsoft.testBilling.model.CallRecord;
import main.by.intexsoft.testBilling.service.RecordService;
import main.by.intexsoft.testBilling.utility.Utility;

public class TestBilling {
	AnnotationConfigApplicationContext context;
	Utility utility = new Utility();
	RecordService service;
	Set<CallRecord> sourceSet;
	Set<CallRecord> rezultSet;

	/**
	 * This method compares objects from CouchBase with source objects from
	 * directory
	 */
	@Test
	public void check() {
		Assert.assertNotEqualsDeep(rezultSet, sourceSet);
	}

	/**
	 * This method starting tests application, configure TestBilling app and gets
	 * set of files from CouchBase
	 */
	@Parameters({ "path-to-testApp" })
	@BeforeClass
	private void startBilling(String pathToApp) throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec(pathToApp);
		p.getInputStream();
		Thread.sleep(10000);
		Runtime.getRuntime().exec("taskkill /F /IM java.exe");
		context = new AnnotationConfigApplicationContext("main.by.intexsoft.testBilling.config");
		service = context.getBean(RecordService.class);
		sourceSet = new HashSet<>();
		sourceSet = utility.getFileSet();
		rezultSet = service.findAll();
	}

	/**
	 * This method clears the database and directory after the test
	 */
	@AfterMethod
	public void clear() {
		service.deleteAll();
		for (File item : utility.getFilesFromDir()) {
			item.delete();
		}
		context.close();
	}
}
