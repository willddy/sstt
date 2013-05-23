/*
 * Created on 2005-9-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package testCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import helpClass.HelpClass;
import helpClass.LogWriter;
import helpClass.Message;
import mainApp.*;

/**
 * @author Dayong
 * @version 2.9
 * 
 * SIP_Reg_Hijack.java
 * Test the ability of SIP proxy to prevent registration hijacking
 */
public class SIP_Reg_Hijack 
{
	//Not Pass - false; Pass - true
    public boolean hij_result_udp = false; 
    public boolean hij_result_tcp = false; 
    
    public SIP_Reg_Hijack()
    {
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
    		//register Virtual User Agent (VUA) 1 ddu3 on UDP
        	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI001", "70",
    		"600", "10100.12", "application/sdp", "0", HelpClass.reg_hij_udp_test_file_s1);
        	//register VUA1 ddu3 on TCP
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI002", "70",
        	"600", "10100.12", "application/sdp", "0", HelpClass.reg_hij_tcp_test_file_s1);
        	
        	//register VUA2 ddu on UDP
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI001", "70",
         	"600", "10100.12", "application/sdp", "0", HelpClass.reg_hij_udp_test_file_s2);
        	//register VUA2 ddu on TCP
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI002", "70",
        	"600", "10100.12", "application/sdp", "0", HelpClass.reg_hij_tcp_test_file_s2);
    		
    		//invite from VUA1 ddu3 to VUA2 ddu on UDP - expect ring
        	mess.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"z9hG4bK0553f059c",
			HelpClass.UA2URL, HelpClass.UA1URL, "ff7577f256d0df6", "8003", "timer", "3600", "7547f946d5a9ca8",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			"SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address,
			HelpClass.UA2URL, "sdp", HelpClass.reg_hij_udp_test_file_s3);	
        	
        	//invite from VUA1 ddu3 to VUA2 ddu on TCP - expect ring
        	mess.invite("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"z9hG4bK4058649c0",
			HelpClass.UA2URL, HelpClass.UA1URL, "ff7577f256d0df7", "8013", "timer", "3600", "7547f946d5a9ca9",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			"SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address,
			HelpClass.UA2URL, "sdp", HelpClass.reg_hij_tcp_test_file_s3);	
        	
        	//de-register VU2 
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI003", "70",
         	"0", "10100.12", "application/sdp", "0", HelpClass.reg_hij_udp_test_file_s4);
        	
         	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI004", "70",
        	"0", "10100.12", "application/sdp", "0", HelpClass.reg_hij_tcp_test_file_s4);
        	
        	//re-invite from VUA1 ddu3 to VUA2 ddu on UDP - expect no route
        	mess.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"z9hG4bK0553f059c",
			HelpClass.UA2URL, HelpClass.UA1URL, "ff7577f256d0df6", "8005", "timer", "3600", "7547f946d5a9cb8",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			"SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address,
			HelpClass.UA2URL, "sdp", HelpClass.reg_hij_udp_test_file_s3);	
        	//re-invite from VUA1 ddu3 to VUA2 ddu on TCP - expect no route
        	mess.invite("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"z9hG4bK4058649c0",
			HelpClass.UA2URL, HelpClass.UA1URL, "ff7577f256d0df7", "8015", "timer", "3600", "7547f946d5a9cb9",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			"SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address,
			HelpClass.UA2URL, "sdp", HelpClass.reg_hij_tcp_test_file_s3);	
    	}
    	
    	if(HelpClass.test_udp_flag) 
    	{
    		this.simUDPHijack();
    	}
        if(HelpClass.test_tcp_flag) 
        {
        	this.simTCPHijack();
        }
    }
    
    /**
     * Test based on UDP
     */
    public void simUDPHijack()
    {
    	HelpClass.debug("");
    	System.out.println("No.SIPA001-Registration Hijacking Test on UDP Begin ...");
    	UdpBase udpbase = new UdpBase();
    	//Register target user - A
    	HelpClass.debug("----->1.Register Virtual User Agent (VUA) - A");
    	String receive = udpbase.sendReceiveMessage(HelpClass.reg_hij_udp_test_file_s1);
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.hij_result_udp = true;
        	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
            this.authentication();
            return;
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && receive.indexOf("200 OK")>=0 ) 
        {
    		//Register a virtual user/UA - B
    		HelpClass.debug("----->Info:Sucess");
    		HelpClass.debug("----->2.Register the target UA - B");
    		String receive2 = udpbase.sendReceiveMessage(HelpClass.reg_hij_udp_test_file_s2);
    		if (receive2.indexOf("200 OK")==-1 ) 
    		{	
    			HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
    	    
    		//Invite from B to A - expect sucess
    		HelpClass.debug("----->Info:Sucess");
    		HelpClass.debug("----->3.INVITE from A to B");
    	    String receive3 = udpbase.sendReceiveMessage(HelpClass.reg_hij_udp_test_file_s3);
    	    if(receive3 == null)  
    		{
    	    	HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
        	//Hacker De-register A
    	    HelpClass.debug("----->Info:Sucess");
    	    HelpClass.debug("----->4.Hacker De-register B");
    	    String receive4 = udpbase.sendReceiveMessage(HelpClass.reg_hij_udp_test_file_s4);
    	    //udpbase.sendReceiveMessage(HelpClass.reg_hij_udp_test_file_s4);
    	    if (receive4.indexOf("200 OK")==-1 ) 
    		{
    	    	HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
        	//Invite from B to A - expect sucess
    	    HelpClass.debug("----->Info:Sucess");
    	    HelpClass.debug("----->5.INVITE again from A to B");
    	    String receive5 = udpbase.sendReceiveMessage(HelpClass.reg_hij_udp_test_file_s3);
    	    if(receive5 == null || receive5.indexOf("404 Not Found") >= 0) 
    		{
    	    	this.hij_result_udp = false;
    	    	System.out.println("No.SIPA001 Test: Fail-Registration Hijacking Attack on UDP Can Be Launched ...");
    	    	LogWriter.log("No.SIPA001 Test: Fail-Registration Hijacking Attack on UDP Can Be Launched");
    		}
    	    else
    	    {
    	    	this.hij_result_udp = true;
    	    	System.out.println("No.SIPA001 Test: Pass-Registration Hijacking Attack on UDP CANNOT Be Launched ...");
    	    	LogWriter.log("No.SIPA001 Test: Pass-Registration Hijacking Attack on UDP CANNOT Be Launched");
    	    }
        }
    }
    
    /**
     * Test on TCP
     */
    public void simTCPHijack()
    {
    	HelpClass.debug("");
    	System.out.println("No.SIPA002-Registration Hijacking Test on TCP Begin ...");
    	TcpBase tcpbase = new TcpBase();
    	//Register target user - A
    	HelpClass.debug("----->1.Register Virtual User Agent - A");
    	String receive = tcpbase.sendReceiveMessage(HelpClass.reg_hij_tcp_test_file_s1);
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.hij_result_udp = true;
        	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
            this.authentication();
            return;
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && receive.indexOf("200 OK")>=0 ) 
        {
    		//Register a virtual user/UA - B
    		HelpClass.debug("----->Info:Sucess");
    		HelpClass.debug("----->2.Register a UA - B");
    		String receive2 = tcpbase.sendReceiveMessage(HelpClass.reg_hij_tcp_test_file_s2);
    		if (receive2.indexOf("200 OK")==-1 ) 
    		{	
    			HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
    	    
    		//Invite from B to A - expect sucess
    		HelpClass.debug("----->Info:Sucess");
    		HelpClass.debug("----->3.INVITE from B to A");
    	    String receive3 = tcpbase.sendReceiveMessage(HelpClass.reg_hij_tcp_test_file_s3);
    	    if(receive3 == null)  
    		{
    	    	HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
        	//Hacker De-register A
    	    HelpClass.debug("----->Info:Sucess");
    	    HelpClass.debug("----->4.Hacker De-register A");
    	    String receive4 = tcpbase.sendReceiveMessage(HelpClass.reg_hij_tcp_test_file_s4);
    	    if (receive4.indexOf("200 OK")==-1 ) 
    		{
    	    	HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
        	//Invite from B to A - expect sucess
    	    HelpClass.debug("----->Info:Sucess");
    	    HelpClass.debug("----->5.INVITE again from B to A");
    	    String receive5 = tcpbase.sendReceiveMessage(HelpClass.reg_hij_tcp_test_file_s3);
    	    if(receive5 == null || receive5.indexOf("404 Not Found") >= 0) 
    		{
    	    	this.hij_result_tcp = false;
    	    	System.out.println("No.SIPA002 Test: Fail-Registration Hijacking Attack on TCP Can Be Launched ...");
    	    	LogWriter.log("No.SIPA002 Test: Fail-Registration Hijacking Attack on TCP Can Be Launched ...");
    		}
    	    else
    	    {
    	    	this.hij_result_tcp = true;
    	    	System.out.println("No.SIPA002 Test: Pass-Registration Hijacking Attack on TCP CANNOT Be Launched ...");
    	    	LogWriter.log("No.SIPA002 Test: Pass-Registration Hijacking Attack on TCP CANNOT Be Launched ...");
    	    }	    
        }
    	tcpbase.clearSocket();
    }
    
    public void authentication()
    {
    	System.out.println("NOTE:");
    	System.out.println("For futher test, authentication information is needed " +
    			"following the steps below.");
    	System.out.println("1. Check SIP REGISTER message in Ethereal.");
    	System.out.println("2. Find the field 'Authorization' and copy.");
    	System.out.println("3. Paste to the REGISTER messages in all files in TestFile\\SIPA001" +
    			"TestFile\\SIPA002 folder.");
    	System.out.println("4. Restart test.");
    }
}
