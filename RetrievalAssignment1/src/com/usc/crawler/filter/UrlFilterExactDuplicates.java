package com.usc.crawler.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.net.URLFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filters URLs based on a file containing Exact duplicates(discovered from
 * previous knowledge). The file should be present in the current working
 * directory invoking crawl command. for eg: if we invoke crawl by bin/crawl
 * then runtime/local should contain Exact Duplicates file.
 */
public class UrlFilterExactDuplicates implements URLFilter {

	private static final Logger LOG = LoggerFactory
			.getLogger(UrlFilterExactDuplicates.class);

	static Map<String, Integer> duplicateUrls = new HashMap<String, Integer>();

	static {

		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"ExactDuplicates.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				if (!duplicateUrls.containsKey(line)) {
					duplicateUrls.put(line, 1);
				}
			}
			br.close();
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
	}

	public UrlFilterExactDuplicates() throws IOException {

	}

	public String filter(String url) {

		if (duplicateUrls.containsKey(url)) {
			LOG.info("********* EXACT DUPLICATE PLUGIN FILTERED URL: " + url);
			return "";
		} else {
			return url;
		}
	}

	public static void main(String args[]) throws IOException {

		UrlFilterExactDuplicates myFilter = new UrlFilterExactDuplicates();
		// sample url to test the functionality of this plugin
		myFilter.filter("http://www.sample.com/");

	}

	@Override
	public Configuration getConf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConf(Configuration arg0) {
		// TODO Auto-generated method stub

	}

}
