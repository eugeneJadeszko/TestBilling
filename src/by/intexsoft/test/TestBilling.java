package by.intexsoft.test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import by.intexsoft.test.model.CallRecord;
import by.intexsoft.test.service.RecordService;

public class TestBilling {
	Set<CallRecord> sourceSet = new HashSet<>();
	ObjectMapper mapper = new ObjectMapper();

	@BeforeMethod
	private void startBilling() throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec("D:/work/CouchBaseRabbit/start.bat");
		p.getInputStream();
		Thread.sleep(15000);
		Runtime.getRuntime().exec("taskkill /F /IM java.exe");
	}

	@Test
	public void check() throws InterruptedException, JsonParseException, IOException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("by.intexsoft.test.config");
		RecordService service = context.getBean(RecordService.class);
		for (File item : getFiles()) {
			CallRecord record = mapper.readValue(item, CallRecord.class);
			sourceSet.add(record);
		}
		Set<CallRecord> rezultSet = service.findAll();
		context.close();
		Assert.assertNotEqualsDeep(rezultSet, sourceSet);
	}

	public File[] getFiles() {
		File dir = new File("D:/work/read_messages");
		File[] arrayFiles = dir.listFiles();
		return arrayFiles;
	}
}
