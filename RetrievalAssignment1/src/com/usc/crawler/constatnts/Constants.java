package com.usc.crawler.constatnts;

public class Constants {
	
	//Data File for Exact/Near duplicate finding
	/*Expected File Format(Sample):
	   Recno:: 0
	   URL:: http://about.gmu.edu/
	   ParseText::
	   Sample  parsed text
	   Content Metadata: Expires=Wed, 11 Jan 1984 05:00:00 GMT _fst_=33 nutch.segment.name=20150224224832 Connection=close X-Powered-By=PHP/5.3.3 Server=Apache Cache-Control=no-cache, must-revalidate, max-age=0 Pragma=no-cache nutch.content.digest=d29ee470e34a742fcd18b9b0431b528b Strict-Transport-Security=max-age=500, includeSubDomains Link=<http://about.gmu.edu/>; rel=shortlink Date=Mon, 16 Feb 2015 11:55:55 GMT nutch.crawl.score=2.787068E-8 Content-Type=text/html; charset=UTF-8
	   ##### */
	public static final String INPUT_FILE_NAME = "cleaned_data/final_nsidc_selenium";
	
	//Output File for Exact Duplicates
	/*Format of output produced:
	 Parent Url : https://www.aoncadis.org/dataset/ucar.ncar.eol.dataset.56_217.html
	 Near Match Url : https://www.aoncadis.org/dataset/ucar.ncar.eol.dataset.56_219.html
	 Near Match Url : https://www.aoncadis.org/dataset/ucar.ncar.eol.dataset.56_222.html
	 **************
	 */
	public static final String OUTPUT_FILE_NAME_EXACT = "exact_duplicates/exact_duplicates_nsidc_selenium";
	
	//Output file for Near Duplicates , produces output in same format
	public static final String OUTPUT_FILE_NAME_NEAR = "near_duplicates/near_duplicates_nsidc_selenium";
	
	

}
