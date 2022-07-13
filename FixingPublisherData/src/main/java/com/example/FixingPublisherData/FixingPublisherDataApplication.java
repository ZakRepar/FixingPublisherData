package com.example.FixingPublisherData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;

@SpringBootApplication
public class FixingPublisherDataApplication {

	//extracts data needed to update database
	private static void parseLine(String input, List<Publisher> publishers, ObjectMapper mapper, File outFile) throws IOException {

		//split input line
		String[] split1 = input.split(" ");

		//split out code and filepath
		String[] split2 = split1[2].split("-");

		String[] data = new String[2];
		//stores code
		data[0] = split2[0];
		//stores file path
		data[1] = split2[1] + "-" + split2[2] + "-" + split2[3] + "-" + split2[4] + "-" + split2[5];
		//System.out.println(Arrays.toString(data));
		modifyJSON(data, publishers, mapper, outFile);
	}



	//updates database
	public static void modifyJSON(String[] data, List<Publisher> publishers, ObjectMapper mapper, File outFile) throws IOException {

		for (Publisher p : publishers) {
			if (p.code.equalsIgnoreCase(data[0])) {
				p.file = ("s3://some-special-bucket/production/publisher-data/" + p.code + data[1]);
				//System.out.println("s3://some-special-bucket/production/publisher-data/" + p.code + data[1]);
			}
		}
	}



	public static void main(String[] args) throws Exception {

		SpringApplication.run(FixingPublisherDataApplication.class, args);

		//retrieve json publisher data
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		InputStream inputStream = new FileInputStream("A:\\Users\\zakth\\Documents\\Projects\\FixingPublisherData\\FixingPublisherData\\src\\testDatabasePublishers.json");
		TypeReference<List<Publisher>> typeReference = new TypeReference<List<Publisher>>() {};
		List<Publisher> publishers = mapper.readValue(inputStream, typeReference);
		inputStream.close();

		//read in update data
		Scanner scanner = new Scanner(new File("A:\\Users\\zakth\\Documents\\Projects\\FixingPublisherData\\FixingPublisherData\\src\\publishersFileList.txt"));

		//create output file
		File outFile = new File("A:\\Users\\zakth\\Documents\\Projects\\FixingPublisherData\\FixingPublisherData\\src\\updatedPublishers.json");

		while (scanner.hasNextLine()) {
			parseLine(scanner.nextLine(), publishers, mapper, outFile);
		}
		mapper.writerWithDefaultPrettyPrinter().writeValue(outFile, publishers);
	}
}
