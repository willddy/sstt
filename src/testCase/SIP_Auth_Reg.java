/*
 * Created on 2005-9-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package testCase;

import helpClass.*;
import helpClass.LogWriter;
import mainApp.UdpBase;
import mainApp.TcpBase;

/**
 * @author Dayong
 * @version 2.9
 * 
 * SIP_Auth_Reg.java
 * Test UDP or TCP or both
 * Test Authentication for REGISTER or not
 */

/**
 * REGISTER sip:ss.under.test.com SIP/2.0
    Via: SIP/2.0/UDP node.under.test.com:5060;branch=z9hG4bKnashds8
    Max-Forwards: 70
    From: UA11 <sip:UA11@under.test.com>;tag=ja743ks76zlflH
    To: UA11 <sip:UA11@under.test.com>
    Call-ID: 1j9FpLxk3uxtm8tn@under.test.com
    CSeq: 2 REGISTER
    Contact: <sip:UA11@node.under.test.com>
    Expires: 3600
    Authorization: Digest username="UA11",
     realm="under.test.com",
     nonce="ea9c8e88df84f1cec4341ae6cbe5a359", opaque="",
     qop=auth, nc=00000002, cnonce="d4e4cec0",
     uri="sip:ss.under.test.com",
     response="b7fd380421adc89263e6774026cfc049"
    Content-Length: 0
 */
public class SIP_Auth_Reg 
{
	//true - has authentication for REGISTER; false - no authentication
	//Not Pass - false; Pass - true
    public boolean auth_result_udp = false; 
    public boolean auth_result_tcp = false; 
    
    public SIP_Auth_Reg()
    {
    	//Create Message
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
        	//register Virtual User Agent (VUA) 1 ddu3 on UDP - No.SIPI001
        	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI001", "70",
    		"360", "10100.12", "application/sdp", "0", HelpClass.udp_test_file);
        	//register VUA1 ddu3 on TCP - No.SIPI002
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI002", "70",
        	"360", "10100.12", "application/sdp", "0", HelpClass.tcp_test_file);
        	//register VUA2 ddu on UDP - No.SIPI003
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI003", "70",
         	"360", "10100.12", "application/sdp", "0", HelpClass.udp_test_file2);
        	//register VUA2 ddu on TCP - No.SIPI004
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI004", "70",
        	"360", "10100.12", "application/sdp", "0", HelpClass.tcp_test_file2);
    	}
    	//begin test
    	this.authRegUdp();
    	this.authRegTcp();
    }
    
    /**
     * UDP authentication test
     */   
    public void authRegUdp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV001: Regestration Authentication Test
    	System.out.println("No.SIPI001-SIP UDP Implementation Test Begin ...");
    	System.out.println("No.SIPV001-Regestration Authentication Test on UDP Begin ...");
    	
    	UdpBase udpbase = new UdpBase();
    	/*
         * send SIP message: REGISTRATION to proxy/registrar on UDP
         */
    	HelpClass.debug("<debug>Register Virtual UA1 (VUA1)");
    	String receive = udpbase.sendReceiveMessage(HelpClass.udp_test_file);
    
    	/*
         * "401"-Unauthorized; "407"-Proxy Authentication Required
         */
    	if (receive == null) 
    	{
    		HelpClass.test_udp_flag = false;
    		System.out.println("No.SIPI001 Test: Fail-Implementation is not based on UDP");
    		System.out.println("No.SIPV001 Test: Pass-Authentication required for registration on UDP");
    		LogWriter.log("No.SIPI001 Test: Fail-Implementation is not based on UDP");
    		LogWriter.log("No.SIPV001 Test: Pass-Authentication required for registration on UDP");
    	}
    	else
    	{
    		System.out.println("No.SIPI001 Test: Pass-Implementation is based on UDP");
    	    LogWriter.log("No.SIPI001 Test: Pass-Implementation is based on UDP");

	    	if (receive.indexOf("401 Unauthorized")>=0 || 
	    		receive.indexOf("407 Proxy Authentication")>=0)
	        {
	        	this.auth_result_udp = true;
	        	System.out.println("No.SIPV001 Test: Pass-Authentication required for registration");
	            LogWriter.log("No.SIPV001 Test: Pass - Need authentiaction for registration");
	            this.authentication();
	        }
	    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
	    	     receive.indexOf("407 Proxy Authentication")==-1 ) 
	        {
	    	    this.auth_result_udp = false;
	    	    System.out.println("No.SIPV001 Test: Fail-No authentication required for registration on UDP");
	            LogWriter.log("No.SIPV001 Test: Fail-No authentication required for registration on UDP");
	            
	            //register Virtual UA2
	            HelpClass.debug("<debug>Register UA2 (UA2)"); 
	            String receive2 = udpbase.sendReceiveMessage(HelpClass.udp_test_file2);                 
	        }
    	}
    }
    
    /**
     * TCP authentication test
     */
    public void authRegTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV001: Regestration Authentication Test
    	System.out.println("No.SIPI002-SIP TCP Implementation Test Begin ...");
    	System.out.println("No.SIPV002-Registration Authentication Test on TCP Begin ...");
        
    	TcpBase tcpbase = new TcpBase();
    	if(HelpClass.test_tcp_flag != false)
    	{
	    	/*
	         * send SIP message: REGISTRATION to proxy/registrar on TCP
	         */
        	HelpClass.debug("<debug>Register Virtual UA1 (VUA1)");
	    	String receive = tcpbase.sendReceiveMessage(HelpClass.tcp_test_file);
	        
	    	/*
	         * "401"-Unauthorized; "407"-Proxy Authentication Required
	         */
	    	if (receive != null)
	    	{
	    	    System.out.println("No.SIPI002 Test: Pass-Implementation is based on TCP");
	    	    LogWriter.log("No.SIPI002 Test: Pass-Implementation is based on TCP");
	    	    HelpClass.test_tcp_flag = true;
	    	}
	    	
	    	//Actually receive != null already
	    	if (receive.indexOf("401 Unauthorized")>=0 || 
	    		receive.indexOf("407 Proxy Authentication")>=0)
	        {
	        	this.auth_result_tcp = true;
	        	System.out.println("No.SIPV002 Test: Pass-Authentication required for registration on TCP");
	            LogWriter.log("No.SIPV002 Test: Pass-Need authentiaction for registration on TCP");
	            this.authentication();
	        }
	    	
	    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
	    	     receive.indexOf("407 Proxy Authentication")==-1 ) 
	        {
	    	    this.auth_result_tcp = false;
	    	    System.out.println("No.SIPV002 Test: Fail-No authentication for registration on TCP");
	            LogWriter.log("No.SIPV002 Test: Fail-No authentication for registration on TCP");
	        	//register UA2
	            HelpClass.debug("<debug>Register UA2 (UA2)");
	        	String receive2 = tcpbase.sendReceiveMessage(HelpClass.tcp_test_file2);
	        }	    	
	    	//!!May need or not?
	    	tcpbase.clearSocket();
    	}
    }
    
    /**
     * Regestration with authentication
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
    	System.out.println("   SIPV071-8.txt;SIPA001-2");
    	System.out.println("5. Restart test.");
    }
    
}
