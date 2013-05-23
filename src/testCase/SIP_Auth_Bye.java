/*
 * Created on 2005-09-12
 */
package testCase;

import helpClass.HelpClass;
import helpClass.LogWriter;
import helpClass.Message;
import mainApp.TcpBase;
import mainApp.UdpBase;

/**
 * @author Dayong
 * @version 2.9
 *
 * SIP_Auth_Bye.java
 * Test Authentication for BYE or not
 */
public class SIP_Auth_Bye 
{
	//true - has authentication for BYE; false - no authentication
	//Not Pass - false; Pass - true
    public boolean auth_result_udp = false; 
    public boolean auth_result_tcp = false; 
    
    public SIP_Auth_Bye()
    {
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
    		mess.bye("2.0", "UDP", HelpClass.ProxyAddress, HelpClass.UA1Address,"z9hG4bK0553f059c", 
    				HelpClass.UA2URL, HelpClass.UA1URL, "ff7577f256d0df6", "1011", "7547f946d5a9ca8", "70",
    				"3600", HelpClass.UA2URL, HelpClass.aut_bye_udp_test_file);
    		mess.bye("2.0", "TCP", HelpClass.ProxyAddress, HelpClass.UA1Address,"z9hG4bK4058649c0", 
    				HelpClass.UA2URL, HelpClass.UA1URL, "ff7577f256d0df7", "1012", "7547f946d5a9ca9", "70",
    				"3600", HelpClass.UA2URL, HelpClass.aut_bye_tcp_test_file);
    	}
    	
    	if(HelpClass.test_udp_flag) this.authRegUdp();
        if(HelpClass.test_tcp_flag) this.authRegTcp();
    }
    
    /**
     * UDP authentication test
     */   
    public void authRegUdp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV031: BYE Authentication on UDP Test
    	System.out.println("No.SIPV031-BYE Authentication Test on UDP Begin ...");
    	
    	UdpBase udpbase = new UdpBase();
    	/*
         * send SIP message: BYE to proxy/registrar on UDP
         */
    	String receive = udpbase.sendReceiveMessage(HelpClass.aut_bye_udp_test_file);
        
    	/*
         * "401"-Unauthorized; "407"-Proxy Authentication Required
         */
 
    	//Actually receive != null already
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.auth_result_udp = true;
        	System.out.println("No.SIPV031 Test: Pass-Authentication required for BYE on UDP");
            LogWriter.log("SIPV031 Test: Pass-Need authentiaction for BYE on UDP");
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 ) 
        {
    	    this.auth_result_udp = false;
    	    System.out.println("No.SIPV031 Test: Fail-No authentication for BYE on UDP");
            LogWriter.log("SIPV031 Test: Fail-No authentication for BYE on UDP");
        }
    }
    
    /**
     * TCP authentication test
     */
    public void authRegTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV032: BYE Authentication on TCP Test
    	System.out.println("No.SIPV032-BYE Authentication Test on TCP Begin ...");
        
    	TcpBase tcpbase = new TcpBase();
    	/*
	     * send SIP message: BYE to proxy/registrar on UDP
	     */
	    String receive = tcpbase.sendReceiveMessage(HelpClass.aut_bye_tcp_test_file);
	        
	    /*
	     * "401"-Unauthorized; "407"-Proxy Authentication Required
	     */
	    //Actually receive != null already
	    if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
	    	receive.indexOf("407 Proxy Authentication")>=0)
	    {
	        this.auth_result_tcp = true;
	        System.out.println("No.SIPV032 Test: Pass-Authentication required for BYE on TCP");
	        LogWriter.log("SIPV032 Test: Pass - Need authentiaction for BYE on TCP");
	    }
	    	
	    if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
	    	 receive.indexOf("407 Proxy Authentication")==-1 ) 
	    {
	    	this.auth_result_tcp = false;
	    	System.out.println("No.SIPV032 Test: Fail-No authentication for BYE on TCP");
	        LogWriter.log("SIPV032 Test: Fail - No authentication for BYE on TCP");
	    }
	    	
	    //!!May need or not?
	    tcpbase.clearSocket();
    }
    
    /**
     * BYE with authentication
     */
    public void authentication()
    {
    	System.out.println("NOTE:");
    	System.out.println("For futher test, authentication information is needed " +
    			"following the steps below.");
    	System.out.println("1. Check SIP REGISTER message in Ethereal.");
    	System.out.println("2. Find the field 'Authorization' and copy.");
    	System.out.println("3. Paste to the REGISTER messages in TestFile folder.");
    	System.out.println("4. Files include:SIPV042.txt;SIPV051.txt;SIPV052.txt;SIPV061-4.txt");
    	System.out.println("   SIPV071-8.txt;");
    }
}
