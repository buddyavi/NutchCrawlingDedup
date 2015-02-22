package com.usc.crawler.model;

public class Record {

	int recordId;
	String url;
	String parseText;
	String title;
	String contentMetadata;
	String parseMetadata;
	String digestMD5;

	
	public Record(String url)
	{
		this.url=url;
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
