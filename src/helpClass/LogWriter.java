/* 
 * Created on 2005-09-12
 */
package helpClass;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * @author Dayong
 * @version 2.9
 * 
 * LogWriter.java
 * LogWriter is used to loggling any operations in the current SYS
 * If for example, coordinator falure happens, we will create a new log
 * 
 * Usage:
 * LogWriter.log("write string to log file");
 * LogWriter.setOn(false); //shut down LogWriter
 * LogWriter.setOn(true);  //turn on LogWriter
 * LogWriter.logEnd(); 	   //add end to the report
 * LogWriter.log("No.SIPV011 Test: Fail-Need authentiaction for INVITE");
 */
public class LogWriter 
{

	/** On/off flag. */
	private static boolean on = true;
	
	/** Log name */
	private static String logName = "report//log.txt";

	/** Date format. */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM.dd.yyyy HH:mm");

	/**
	 * Return true if logging is on.
	 */
	public static boolean isOn() 
	{
		return on;
	}

	/**
	 * Set logging on/off.
	 */
	public static void setOn(boolean isOn) 
	{
		on = isOn;
	}
    /**
     * 
     * write into a another log file
     */
	public static void logInit() 
	{
		String logNum = dateFormat.format(new Date()); //This is date
		String htmlHeader = "";
		logNum = logNum.replaceAll(":", "-");
		if (HelpClass.single_log__flag)
			logName = "report//latestReport.html";
		else
			logName = "report//log_" + logNum +".html";
		/*
		 * Creat HTML Report
		 */
		htmlHeader = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + 
		"<title>SIP Security Test Tool Report</title></head>" + 
		"<p style=\"text-align: center;\">" +
		"<font face=\"Arial, Helvetica, sans-serif\" size=\"4\">" +
		"<span style=\"font-weight: bold; color: rgb(51, 0, 150);\">" +
		"SIP Security Test Tool Report</span><br></p><p></p>" +
		"<font face=\"Arial, Helvetica, sans-serif\" size=\"2\">" +
		"<p style=\"text-align: center;\">" +
		//logNum + "<br>" + "</p><p>" + 
		"" + "<br>" + "</p><p>" + 
		"<strong>Test Details</strong></p>" +
		"<table border=\"0\" width=\"100%\">";

		if (on) 
		{
			try 
			{   
				PrintStream logFile;
				if (HelpClass.single_log__flag)
					logFile = new PrintStream(new FileOutputStream(logName, false));
				else
					logFile = new PrintStream(new FileOutputStream(logName, true));
				try 
				{
					logFile.println(dateFormat.format(new Date()) + " " + htmlHeader);
					 
				} finally {
					logFile.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Log the given message.
	 */

	public static void log(String msg) 
	{
		String testSuggestion = "";
		Solution so = new Solution (msg);
		testSuggestion = so.Result();
		String title1 = "<table border=\"0\" width=\"100%\">" +
		"<tbody><tr bgcolor=#FF0000><td valign=\"top\" width=\"20%\">" +
		"<strong><font face=\"Arial, Helvetica, sans-serif\" size=\"2\" color=\"#ffffff\">" +
		"<a name=\"TargetHost\"></a> Proxy/Registrar: " + HelpClass.ProxyAddress +
		"</font></strong></td><td width=\"80%\"><strong>" + 
		"<font face=\"Arial, Helvetica, sans-serif\" size=\"2\" color=\"#ffffff\">" +
		"Explanation" + "</font></strong></td></tr>" +
		"<tr valign=\"top\" bgcolor=\"#f0f0f0\">" +
		"<td width=\"20%\"><font face=\"Arial, Helvetica, sans-serif\" size=\"2\">" +
		"Description </font></td><td width=\"80%\"><p align=\"justify\">" +
		"<font face=\"Arial, Helvetica, sans-serif\" size=\"2\">" +
		msg + "</font></p></td></tr>" +
		"<tr valign=\"top\" bgcolor=\"#f0f0f0\">" +
		"<td width=\"20%\"><font face=\"Arial, Helvetica, sans-serif\" size=\"2\">" +
		"Suggestion" +	"</font></td><td width=\"80%\"><p align=\"justify\">" +
		"<font face=\"Arial, Helvetica, sans-serif\" size=\"2\">" +
		testSuggestion + 
		"</font></p></td></tr></tbody></table>";

		
		if (on) 
		{
			try 
			{   
				PrintStream logFile = new PrintStream(new FileOutputStream(
						logName, true));
				try 
				{
					logFile.println(title1);
					//System.out.println("Writing log: " +msg);
				} finally {
					logFile.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Add the ending to SSTT report
	 */
	public static void logEnd() 
	{
		String htmlTailer = "<br><hr style=\"width: 100%; height: 2px;\">" +
		"<div style=\"text-align: center;\">" + 
		"<span style=\"font-weight: bold; color: rgb(51, 0, 153);\">" +
		"The SIP Security Test Tool, 2005 (c) All Rights Reserved</span><br>" +
		"<a href=\"http://www.cs.dal.ca/~ddu/voip\">WiSe VoIP </a><br>" +
		"</div><br><br><table border=\"0\" width=\"100%\"></table></body></html>";
		if (on) 
		{
			try 
			{   
				PrintStream logFile = new PrintStream(new FileOutputStream(
						logName, true));
				try 
				{
					logFile.println(htmlTailer);
				} finally {
					logFile.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			LogWriter.setOn(false);
		}
	}
	/**
	 * Test the Logger.
	 */
	public static void main(String[] args) 
	{   
		/*
		try
		{
			FileWriter out = new FileWriter(new File("outagain.txt"));
			out.write("sdasda");
			out.close();

		}
		catch(IOException e)
		{
			 
		}

		
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"EEE MMM d HH:mm:ss z yyyy");
		String logNum = dateFormat.format(new Date());
		System.out.print(logNum);
		*/
		LogWriter.logInit();
		LogWriter.log("Log started.");
		LogWriter.log("Logging disabled after this.");
		//LogWriter.setOn(false);
		//LogWriter.log("This message should not be in the log.");
		//LogWriter.setOn(true);
		LogWriter.log("Logging enabled.");
		LogWriter.log("Test completed.");
		LogWriter.logEnd();

	}
}

