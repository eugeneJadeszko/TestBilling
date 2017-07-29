package main.by.intexsoft.testBilling.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.by.intexsoft.testBilling.model.CallRecord;

public class Utility {
	private static String pathToMessageDir;
	private ObjectMapper mapper = new ObjectMapper();

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
	 * This method return a collection of source files from the directory
	 * 
	 * @return Set<CallRecord>
	 */
	public Set<CallRecord> getFileSet() throws IOException, JsonMappingException {
		Set<CallRecord> recordSet = new HashSet<>();
		for (File item : getFilesFromDir()) {
			CallRecord record = mapper.readValue(item, CallRecord.class);
			recordSet.add(record);
		}
		return recordSet;
	}

	/**
	 * This method gets a set of source files from the directory
	 * 
	 * @return File[] - set of source files
	 */
	public File[] getFilesFromDir() {
		File dir = new File(pathToMessageDir);
		File[] arrayFiles = dir.listFiles();
		return arrayFiles;
	}
}
