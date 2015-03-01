package com.usc.crawler.dedup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import com.skjegstad.utils.BloomFilter;
import com.usc.crawler.constatnts.Constants;
import com.usc.crawler.model.Record;

/**
 *Class which implements Near Duplicate algorithms 
 *
 */
public class NearDuplicates {
	
	//Data strucutre to hold all records read from file
	static ArrayList<Record> allRecords = new ArrayList<Record>();
	
	//data structure to hold theExact Duplicate  results. Key is the parent Record and value 
	//is list of Near records found
	static Map<Record, ArrayList<Record>> similarRecordMap = new HashMap<Record, ArrayList<Record>>();

	/**
	 * This method takes the Record bean and populates a ArrayList data structure
	 * accordingly. 
	 * 
	 * @param rc
	 */
	public static void recordsToMap(Record rc) {

		allRecords.add(rc);
	}

	public ArrayList<Record> getAllRecords() {
		return allRecords;
	}

	public void setAllRecords(ArrayList<Record> allRecords) {
		this.allRecords = allRecords;
	}

	/**
	 * This method reads the file which contains the parsed text and metadata
	 * and populates the record beans accordingly. Symbol ##### Acts as record separator
	 * Format Expected of File:
	 * Recno:: 0
	   URL:: http://about.gmu.edu/
	   ParseText::
	   Sample  parsed text
	   Content Metadata: Expires=Wed, 11 Jan 1984 05:00:00 GMT _fst_=33 
	   .........
	   #####
	 * @param file
	 */
	public void fileToBean(String file) {
		String line;
		int recordId = 0;
		String url = "";
		String parseText = "";
		String title = "";
		String contentMetadata = "";
		String parseMetadata = "";
		String digestMD5 = "";
		Boolean exactMatch=false;
		long contentLength=0;
		//reading individual records from file and populating Record Bean

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
			
				if (line.startsWith("Recno")) {
					recordId = Integer.parseInt(line.split("::")[1].trim());
					
				}
				if (line.startsWith("URL")) {
					
					url = line.split("::")[1].trim();
				
				}
				if (line.startsWith("ParseText")) {
					line=br.readLine();
					if(!line.startsWith("#####") && !line.startsWith("Content Metadata"))
					{
					parseText = line.trim();
					
					}
					
				}
				if (line.startsWith("Title")) {
					title = line.split(":")[1].trim();
				}
				
				if (line.startsWith("Content Metadata")) {
					
					contentMetadata = line.split("Content Metadata:")[1];
					
					if(contentMetadata.contains("Content-Length="))
					{   
						contentLength=Long.parseLong(contentMetadata.split("Content-Length=")[1].split(" ")[0].trim());
					   
					}
					
				}
				if (line.startsWith("Parse Metadata")) {
					parseMetadata = line.split(":")[1].trim();
				}
				if (line.startsWith("#####")) {
					
					Record rc = new Record(recordId, url, parseText, title,
							contentMetadata, digestMD5, parseMetadata,contentLength,exactMatch);
					recordId = 0;
					url = "";
					parseText = "";
					title = "";
					contentMetadata = "";
					parseMetadata = "";
					digestMD5 = "";
					
					contentLength=0;
					
					recordsToMap(rc);
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method that takes bloom filter for one document and compares it with all
	 * other documents to calculate similarity
	 * 
	 * @param myBloom
	 * @param id
	 */
	public static void compareToAllRecords(BloomFilter<String> myBloom,
			Record parent) {
		System.out.println("Processed"+parent.getRecordId());
		int containCount = 0;
		int notFoundCount = 0;
		// setting the threshold (98%), incoming document should match atleast 98% of
		// elements in current document
		float threshold = (float)(myBloom.count() * 0.98) ;
		ArrayList<Record> similarRc = new ArrayList<Record>();
		for (Record rc : allRecords) {
	    //Comparing only if the record is not compared till now
		//Comparing only if the length of Text doesn't differ much
		//Not comparing if the Parsed Text is empty
		if((rc.getRecordId() > parent.getRecordId())&& Math.abs(parent.getParseText().length()-rc.getParseText().length()) < 30 && !rc.getParseText().equals("") && !rc.isExactMatch())
		{
			containCount = 0;
			notFoundCount = 0;
			
				String text = rc.getParseText();
				
				StringTokenizer st = new StringTokenizer(text);

				while (st.hasMoreElements()) {
					if (myBloom.contains(st.nextToken())) {
						
						containCount += 1;
					} else {
						notFoundCount += 1;
					}
				}
				// To normalize comparison between uneven size of Documents,
				// check both comparison with threshold
				// and also comparison with notFound elements. notFoundCount > 0
				// checks and removes exact duplicates
				if ((containCount > notFoundCount)
						&& (containCount > threshold) && (notFoundCount > 0) ) {
                     rc.setExactMatch(true);
                	 System.out.println(rc.getUrl());
                	similarRc.add(rc);
				}
				if (similarRc.size() > 0) {

					similarRecordMap.put(parent, similarRc);
				}
			

		}}
	}

	/**
	 * method to get all similar records and populate the hash map holding
	 * parent record and similar records
	 */
	public void getSimilarityMatrix() {
		double falsePositiveProbability = 0.1;
		int expectedNumberOfElements = 40000;
		for (Record rc : allRecords) {
			BloomFilter<String> myBloom = new BloomFilter<String>(
					falsePositiveProbability, expectedNumberOfElements);
			String text = rc.getParseText();
			
			StringTokenizer st = new StringTokenizer(text);
			while (st.hasMoreElements()) {
				
				myBloom.add(st.nextToken());
			}
			compareToAllRecords(myBloom, rc);

		}

	}
	//Main method to test the algorithm .Reads the input file in specified format
	// Generates Near match records and write it to an output file
	public static void main(String args[]) throws FileNotFoundException {
		NearDuplicates ndp = new NearDuplicates();
		ndp.fileToBean(Constants.INPUT_FILE_NAME);
		ndp.getSimilarityMatrix();
		PrintWriter writer = new PrintWriter(Constants.OUTPUT_FILE_NAME_NEAR);
		Iterator it = similarRecordMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			
			writer.println("Parent Url : "+ ((Record) pair.getKey()).getUrl());
			ArrayList<Record> matchedRecords = (ArrayList<Record>) pair.getValue();
			for (Record rc : matchedRecords) {
				
				writer.println("Near Match Url : "+ rc.getUrl());
			}

			it.remove(); 
			//output Record Seperator 
			writer.println("**************");
			
		}
		writer.close();
	}

}
