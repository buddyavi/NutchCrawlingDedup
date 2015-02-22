package com.usc.crawler.dedup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.usc.crawler.constatnts.Constants;
import com.usc.crawler.model.Record;

/**
 * This class implements a simple logic currently based on Md5 and score of a webpage
 * to determine exact duplicates
 * 
 *
 */
public class ExactDuplicates {

	/**
	 * This method takes the Record bean and populates a Map data structure
	 * accordingly. Key for the map is recordId/MD5 digest and value is the record object
	 * @param rc
	 */
	public static void recordsToMap(Record rc) {
    System.out.println(rc.getUrl());	
    System.out.println(rc.getParseText());
    System.out.println(rc.getDigestMD5());
	}

	/**
	 * This method reads the file which contains the parsed text and metadata and populates
	 * the record beans accordingly.
	 * @param file
	 */
	public void fileToBean(String file) {
		String line;
		int recordId=0;
		String url="";
		String parseText="";
		String title="";
		String contentMetadata="";
		String parseMetadata="";
		String digestMD5="";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				if(line.startsWith("Recno"))
				{   
					recordId=Integer.parseInt(line.split("::")[1].trim());
				}
				if(line.startsWith("URL"))
				{   
					url=line.split("::")[1].trim();
				}
				if(line.startsWith("ParseText"))
				{   
					parseText=line.split("::")[1].trim();
				}
				if(line.startsWith("Title"))
				{   
					title=line.split(":")[1].trim();
				}
				if(line.startsWith("Content Metadata"))
				{   
					contentMetadata=line.split(":")[1].trim();
					digestMD5=contentMetadata.split(" ")[1].split("=")[1].trim();
				}
				if(line.startsWith("Parse Metadata"))
				{   
					parseMetadata=line.split(":")[1].trim();
				}
				if(line.startsWith("#####"))
				{
					Record rc = new Record(recordId,url,parseText,title,contentMetadata,digestMD5,parseMetadata);
					recordId=0;url="";parseText="";title="";contentMetadata="";parseMetadata="";digestMD5="";
					recordsToMap(rc);
					}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		ExactDuplicates edp = new ExactDuplicates();
		edp.fileToBean(Constants.INPUT_FILE_NAME);

	}
}
