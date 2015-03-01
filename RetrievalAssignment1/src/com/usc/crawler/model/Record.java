package com.usc.crawler.model;

/**
 * Bean Class which holds
 * individual records
 * 
 */
public class Record {

	int recordId;
	String url;
	String parseText;
	String title;
	String contentMetadata;
	String parseMetadata;
	String digestMD5;
	long clength;
    boolean exactMatch;
    
	public Record(int recordId, String url, String parseText, String title,
			String contentMetadata, String digestMD5, String parseMetadata,
			long clength, boolean exactMatch) {
		this.recordId = recordId;
		this.url = url;
		this.parseText = parseText;
		this.title = title;
		this.contentMetadata = contentMetadata;
		this.digestMD5 = digestMD5;
		this.parseMetadata = parseMetadata;
		this.clength = clength;
		this.exactMatch=exactMatch;
	}

	public boolean isExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(boolean exactMatch) {
		this.exactMatch = exactMatch;
	}

	public void setClength(int clength) {
		this.clength = clength;
	}

	public long getClength() {
		return clength;
	}

	public String getDigestMD5() {
		return digestMD5;
	}

	public void setDigestMD5(String digestMD5) {
		this.digestMD5 = digestMD5;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParseText() {
		return parseText;
	}

	public void setParseText(String parseText) {
		this.parseText = parseText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentMetadata() {
		return contentMetadata;
	}

	public void setContentMetadata(String contentMetadata) {
		this.contentMetadata = contentMetadata;
	}

	public String getParseMetadata() {
		return parseMetadata;
	}

	public void setParseMetadata(String parseMetadata) {
		this.parseMetadata = parseMetadata;
	}

}
