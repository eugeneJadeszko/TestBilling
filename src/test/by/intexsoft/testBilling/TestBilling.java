package test.by.intexsoft.testBilling;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.by.intexsoft.testBilling.model.CallRecord;
import main.by.intexsoft.testBilling.service.RecordService;

public class TestBilling {
	private static String pathToMessageDir;
	AnnotationConfigApplicationContext context;
	ObjectMapper mapper = new ObjectMapper();
	RecordService service;
	Set<CallRecord> sourceSet = new HashSet<>();
	Set<CallRecord> rezultSet;
	File[] arrayFiles;

	static {
		Properties properties = new Properties();
		try (FileInputStream fr = new FileInputStream("resources/application.properties")) {
			properties.load(fr);
		} catch (IOException e) {
			System.out.println("file properties not found");
		}
		pathToMessageDir = properties.getProperty("directory.read");
	}

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
		sourceSet = getFileSet();
		rezultSet = service.findAll();
	}

	/**
	 * This method clears the database and directory after the test
	 */
	@AfterMethod
	public void clear() {
		service.deleteAll();
		for (File item : arrayFiles) {
			item.delete();
		}
		context.close();
	}

	/**
	 * This method gets a set of source files from the directory
	 * 
	 * @return File[] - set of source files
	 */
	public File[] getFilesFromDir() {
		File dir = new File(pathToMessageDir);
		arrayFiles = dir.listFiles();
		return arrayFiles;
	}

	/**
	 * This method return a collection of source files from the directory
	 * 
	 * @return Set<CallRecord>
	 */
	public Set<CallRecord> getFileSet() throws IOException, JsonMappingException {
		for (File item : getFilesFromDir()) {
			CallRecord record = mapper.readValue(item, CallRecord.class);
			sourceSet.add(record);
		}
		return sourceSet;
	}
}
