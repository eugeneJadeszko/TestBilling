package main.by.intexsoft.testBilling.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.by.intexsoft.testBilling.model.CallRecord;

public class Utility {
	private static String pathToMessageDir, pathToLogErr, invalidFilesDir;
	private ObjectMapper mapper = new ObjectMapper();

	static {
		Properties properties = new Properties();
		try (FileInputStream fr = new FileInputStream("resources/application.properties")) {
			properties.load(fr);
		} catch (IOException e) {
			System.out.println("file properties not found");
		}
		pathToMessageDir = properties.getProperty("directory.read");
		pathToLogErr = properties.getProperty("path.to.logErr");
		invalidFilesDir = properties.getProperty("directory.invalid");
	}

	/**
	 * This method return a collection of source files from the directory
	 * 
	 * @return Set<CallRecord>
	 */
	public Set<CallRecord> getFileSet() throws IOException, JsonMappingException {
		Set<CallRecord> recordSet = new HashSet<>();
		for (File item : getReadFiles()) {
			CallRecord record = mapper.readValue(item, CallRecord.class);
			recordSet.add(record);
		}
		return recordSet;
	}

	/**
	 * @return array of invalid files
	 */
	public File[] getInvalidFiles() {
		return getFilesFromDir(new File(invalidFilesDir));
	}

	/**
	 * @return array of files read messages
	 */
	public File[] getReadFiles() {
		return getFilesFromDir(new File(pathToMessageDir));
	}

	/**
	 * The method check status error.log file
	 * 
	 * @return true - if file is empty
	 */
	public boolean getLogErrStat() {
		return ((read(new File(pathToLogErr)).equals("")) ? true : false);
	}

	/**
	 * This method gets a set of source files from the directory
	 * 
	 * @param directory
	 *            - path to directory
	 * 
	 * @return File[] - set of source files
	 */
	public File[] getFilesFromDir(File directory) {
		return directory.listFiles();
	}

	/**
	 * The method reads and returns the contents of the file
	 * 
	 * @param inputFile
	 * @return the contents of a file in a string format
	 */
	public String read(File inputFile) {
		File file = inputFile;
		if (!file.exists())
			return "";
		StringBuilder sb = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s);
				sb.append("\n");
			}
		} catch (IOException e) {
			System.out.println("i/o exception");
		}
		return sb.toString();
	}
}
