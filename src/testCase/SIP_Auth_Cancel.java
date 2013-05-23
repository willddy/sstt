/*
 * Created on 2005-9-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
 * SIP_Auth_Cancel.java
 * Test Authentication for CANCEL or not
 */
public class SIP_Auth_Cancel 
{
	//true - has authentication for CANCEL; false - no authentication
	//Not Pass - false; Pass - true
    public boolean auth_result_udp = false; 
    public boolean auth_result_tcp = false; 
    
    public SIP_Auth_Cancel()
    {
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
    		mess.cancel("2.0", "UDP", HelpClass.UA1Address, "z9hG4bK0553f059c", HelpClass.UA2URL, 
    			HelpClass.UA1URL, "ff7577f256d0df6", "1011", "timer", 
				"7547f946d5a9ca8", "70", "SCSC/v1.2.1 MxSF/v3.2.6.26", HelpClass.aut_can_udp_test_file);
    		mess.cancel("2.0", "TCP", HelpClass.UA1Address, "z9hG4bK4058649c0", HelpClass.UA2URL, 
    			HelpClass.UA1URL, "ff7577f256d0df7", "1012", "timer", 
    			"7547f946d5a9ca9", "70", "SCSC/v1.2.1 MxSF/v3.2.6.26", HelpClass.aut_can_tcp_test_file);
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
    	//SIP Vulnerability Test - SIPV021: CANCEL Authentication on UDP Test
    	System.out.println("No.SIPV021-CANCEL Authentication Test on UDP Begin ...");
    	
    	UdpBase udpbase = new UdpBase();
    	/*
         * send SIP message: CANCEL to proxy/registrar on UDP
         */
    	String receive = udpbase.sendReceiveMessage(HelpClass.aut_can_udp_test_file);
        
    	/*
         * "401"-Unauthorized; "407"-Proxy Authentication Required
         */
 
    	//Actually receive != null already
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.auth_result_udp = true;
        	System.out.println("No.SIPV021 Test: Pass-Authentication required for CANCEL on UDP");
            LogWriter.log("No.SIPV021 Test: Pass-Need authentiaction for CANCEL on UDP");
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 ) 
        {
    	    this.auth_result_udp = false;
    	    System.out.println("No.SIPV021 Test: Fail-No authentication for CANCEL on UDP");
            LogWriter.log("No.SIPV021 Test: Fail-No authentication for CANCEL on UDP");
        }
    }
    
    /**
     * TCP authentication test
     */
    public void authRegTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV022: CANCEL Authentication on TCP Test
    	System.out.println("No.SIPV022-CANCEL Authentication Test on TCP Begin ...");
        
    	TcpBase tcpbase = new TcpBase();
    	/*
	     * send SIP message: CANCEL to proxy/registrar on UDP
	     */
	    String receive = tcpbase.sendReceiveMessage(HelpClass.aut_can_tcp_test_file);
	        
	    /*
	     * "401"-Unauthorized; "407"-Proxy Authentication Required
	     */
	    //Actually receive != null already
	    if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
	    	receive.indexOf("407 Proxy Authentication")>=0)
	    {
	        this.auth_result_tcp = true;
	        System.out.println("No.SIPV022 Test: Pass-Authentication required for CANCEL on TCP");
	        LogWriter.log("No.SIPV022 Test: Pass-Need authentiaction for CANCEL on TCP");
	    }
	    	
	    if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
	    	 receive.indexOf("407 Proxy Authentication")==-1 ) 
	    {
	    	this.auth_result_tcp = false;
	    	System.out.println("No.SIPV022 Test: Fail-No authentication for CANCEL on TCP");
	        LogWriter.log("No.SIPV022 Test: Fail-No authentication for CANCEL on TCP");
	    }
	    	
	    //!!May need or not?
	    tcpbase.clearSocket();
    }
    
    /**
     * CANCEL with authentication
     */
    public void authentication()
    {
    	
    }
}
