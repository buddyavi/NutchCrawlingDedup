package com.usc.crawler.dedup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.usc.crawler.constatnts.Constants;
import com.usc.crawler.model.Record;

/**
 * 
 * 
 *
 */
public class ExactDuplicates {

	public static void recordsToMap(Record rc) {
    System.out.println(rc.getUrl());
	}

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
				if(line.startsWith("URL"))
				{   
					url=line.split("::")[1].trim();
				}
				if(line.startsWith("#####"))
				{
					Record rc = new Record(url);
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
