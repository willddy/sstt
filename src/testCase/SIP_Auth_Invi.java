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
 * SIP_Auth_Invi.java
 * Test Authentication for INVITE or not
 * 
 * INVITE sip:5120@192.168.36.180 SIP/2.0: Next hop URI (contain IP address)
 * Via: SIP/2.0/UDP 192.168.6.21:5060: User agent is at 192.168.6.21:5060
 * From: sip:5121@192.168.6.21: Caller
 * To: <sip:5120@192.168.36.180>: Callee or called party.
 * Call-ID: c2943000-e0563-2a1ce-2e323931@192.168.6.21: Identify a messages belonging to the same call.
 * CSeq: 100 INVITE: Maintain order of request. CSeq header field is included to guard against replay attacks.
 * Contact: sip:5121@192.168.6.21:5060: Contact header fields contains IP address and port on which the sender is awaiting further requests sent by callee.
 */
public class SIP_Auth_Invi 
{
	//true - has authentication for INVIYE; false - no authentication
	//Not Pass - false; Pass - true
    public boolean auth_result_udp = false; 
    public boolean auth_result_tcp = false;  
    
    public SIP_Auth_Invi()
    {
    	//Create Message
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
        	//invite from VUA1 ddu3 to VUA2 ddu on UDP - No.SIPV011
        	mess.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"z9hG4bK0553f059c",
			HelpClass.UA2URL, HelpClass.UA1URL, "ff7577f256d0df6", "1011", "timer", "3600", "7547f946d5a9ca8",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			"SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address,
			HelpClass.UA2URL, "sdp", HelpClass.aut_inv_udp_test_file);	
        	
        	//invite from VUA1 ddu3 to VUA2 ddu on TCP - No.SIPV012
        	mess.invite("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"z9hG4bK4058649c0",
			HelpClass.UA2URL, HelpClass.UA1URL, "ff7577f256d0df7", "1012", "timer", "3600", "7547f946d5a9ca9",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			"SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address, 
			HelpClass.UA2URL, "sdp", HelpClass.aut_inv_tcp_test_file);	
        	
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
    	//SIP Vulnerability Test-SIPV011: INVITE Authentication on UDP Test
    	System.out.println("No.SIPV011-INVITE Authentication Test on UDP Begin ...");
    	
    	UdpBase udpbase = new UdpBase();
    	/*
         * send SIP message: INVITE to proxy/registrar on UDP
         */
    	HelpClass.debug("<debug>VUA1-INVITE->VUA2");
    	String receive = udpbase.sendReceiveMessage(HelpClass.aut_inv_udp_test_file);
        
    	/*
         * "401"-Unauthorized; "407"-Proxy Authentication Required
         */
 
    	//Actually receive != null already
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.auth_result_udp = true;
        	System.out.println("No.SIPV011 Test: Pass-Authentication required for INVITE on UDP");
            LogWriter.log("No.SIPV011 Test: Pass-Need authentiaction for INVITE on UDP");
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 ) 
        {
    	    this.auth_result_udp = false;
    	    System.out.println("No.SIPV011 Test: Fail-No authentication for INVITE on UDP");
            LogWriter.log("No.SIPV011 Test: Fail-No authentication for INVITE on UDP");
        }
    }
    
    /**
     * TCP authentication test
     */
    public void authRegTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV012: INVITE Authentication on TCP Test
    	System.out.println("No.SIPV012-INVITE Authentication Test on TCP Begin ...");
        
    	TcpBase tcpbase = new TcpBase();
    	/*
	     * send SIP message: INVITE to proxy/registrar on UDP
	     */
    	HelpClass.debug("<debug>VUA1-INVITE->VUA2");
	    String receive = tcpbase.sendReceiveMessage(HelpClass.aut_inv_tcp_test_file);
	        
	    /*
	     * "401"-Unauthorized; "407"-Proxy Authentication Required
	     */
	    //Actually receive != null already
	    if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
	    	receive.indexOf("407 Proxy Authentication")>=0)
	    {
	        this.auth_result_tcp = true;
	        System.out.println("No.SIPV012 Test: Pass-Authentication required for INVITE on TCP");
	        LogWriter.log("No.SIPV012 Test: Pass-Need authentiaction for INVITE on TCP");
	    }
	    	
	    if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
	    	 receive.indexOf("407 Proxy Authentication")==-1 ) 
	    {
	    	this.auth_result_tcp = false;
	    	System.out.println("No.SIPV012 Test: Fail-No authentication for INVITE on TCP");
	        LogWriter.log("No.SIPV012 Test: Fail-No authentication for INVITE on TCP");
	    }
	    	
	    //!!May need or not?
	    tcpbase.clearSocket();
    }
    
    /**
     * Regestration with authentication
     */
    public void authentication()
    {
    	
    }
}
