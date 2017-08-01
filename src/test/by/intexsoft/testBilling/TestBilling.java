package test.by.intexsoft.testBilling;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import main.by.intexsoft.testBilling.model.CallRecord;
import main.by.intexsoft.testBilling.service.RabbitService;
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
	 * directory, checks error.log file status and checks directory with invalid
	 * files
	 */
	@Test
	public void check() {
		Assert.assertNotEqualsDeep(rezultSet, sourceSet);
		assertTrue(utility.getLogErrStat());
		assertTrue(utility.getInvalidFiles().length == 0);
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
		Thread.sleep(820);
		context = new AnnotationConfigApplicationContext("main.by.intexsoft.testBilling.config");
		do {
		} while (context.getBean(RabbitService.class).getMessageCount() != 0);
		Runtime.getRuntime().exec("taskkill /F /IM java.exe");
		sourceSet = utility.getFileSet();
		rezultSet = context.getBean(RecordService.class).findAll();
	}

	/**
	 * This method clears the database and directory after the test
	 */
	@AfterMethod
	public void clear() {
		context.getBean(RecordService.class).deleteAll();
		for (File item : utility.getReadFiles()) {
			item.delete();
		}
		context.close();
	}
}