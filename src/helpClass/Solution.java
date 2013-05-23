/* 
 * Created on 2005-09-28
 */
package helpClass;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author dudu
 * @version 2.9
 * 
 * Solution.java
 * According to the result of each test, giving security solution
 * and suggestion for SIP vulnerability
 */
public class Solution {

	/**
	 * @param args
	 */
	private String m_testResult = "";
	private String m_testCase = "";
	private String m_testSolution = "";
	
	public Solution(String testresult)
	{
		this.m_testResult = testresult;
		//Get test case name such as SIPI001
		//Pay attention 7 - length of test case name !!!!
		this.m_testCase = 
			this.m_testResult.substring(this.m_testResult.indexOf("SIP"),
					this.m_testResult.indexOf("SIP") + 7);
	}
	/**
	 * Result()
	 * @return
	 */
	public String Result()
	{
		//Parcer Test Number such as "No.SIPV011"
		this.resGenerator(this.m_testCase);
		return this.m_testSolution;		
	}
	
	/**
	 * Read file to a string
	 * @param file_path
	 * @return
	 */
	private String fileToStr(String file_path)
	{
		String str = "";
		try
		{
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(file_path));
			int length = buffer.available();
			byte[] data_a = new byte[length];
			buffer.read(data_a, 0, length);
			str = new String(data_a, 0, length);
		}
		catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
			return null;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return null;
		}
		 
		return str;
	}
	/**
	 * resGenerator, ease of result generation, good format of code
	 * @param test_case
	 */
	private void resGenerator(String test_case)
	{
		String passfile = "SuggestionFile\\" + test_case + "P.txt";
		String failfile = "SuggestionFile\\" + test_case + "F.txt";
		if(this.m_testResult.indexOf("Pass") >= 0)
			this.m_testSolution = this.fileToStr(passfile);			
		if(this.m_testResult.indexOf("Fail") >= 0)
			this.m_testSolution = this.fileToStr(failfile);
	}
	
}
