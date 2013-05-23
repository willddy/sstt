/*
 * Created on 2005-9-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package testCase;

import mainApp.*;
import helpClass.HelpClass;
import helpClass.LogWriter;
import helpClass.Message;

/**
 * @author Dayong
 * @version 2.9
 * 
 * SIP_Replay_Attack.java
 * Replay attack test case
 * An attacker records an authenticated INVITE message; then changes the "Via"
 * and "Contact" field to point to the attack; the attack then can send modified
 * INVITE to victom
 */
public class SIP_Replay_Attack 
{
	//Not Pass - false; Pass - true
    boolean replay_result_udp = true; 
    boolean replay_result_tcp = true;  
    
    /*
     * SIPA011 test case
     * In this case, UA1 (Test tool) plays as a hack
     * Caller - VUA4 ddu4; Calee UA2 ddu
     */
    public SIP_Replay_Attack()
    {
      	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();

    		//register VUA ddu4 on UDP - caller (A) with ip: 192.168.1.4
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA4Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA4URL, HelpClass.UA4URL, "1928301774", null, "j8dxkzc9oy11Fx2FTA011", "70",
         	"600", "10100.12", "application/sdp", "0", HelpClass.replay_udp_test_file_s1);
        	//register VUA ddu4 on TCP - caller (A) with ip: 192.168.1.5
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA4Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA4URL, HelpClass.UA4URL, "1928301775", null, "j8dxkzc9oy11Fx2FTA012", "70",
        	"600", "10100.12", "application/sdp", "0", HelpClass.replay_tcp_test_file_s1);
    		
    		//register UA2 ddu on UDP - callee (B) victm
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTA011", "70",
         	"600", "10100.12", "application/sdp", "0", HelpClass.replay_udp_test_file_s2);
        	//register UA2 ddu on TCP - callee (B) victm
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTA012", "70",
        	"600", "10100.12", "application/sdp", "0", HelpClass.replay_tcp_test_file_s2);
    		
    		//modified invite from VUA ddu4 to VUA2 ddu on UDP, but contact to VUA1
        	//This message is sent from VUA1 ddu3.
        	mess.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA4Address,"z9hG4bK0553f059c",
			HelpClass.UA2URL, HelpClass.UA4URL, "ff7577f256d0df6", "9003", "timer", "3600", "7547f946d5a9ca8",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			"SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address,
			HelpClass.UA2URL, "sdp", HelpClass.replay_udp_test_file_s3);	
        	
        	//modified invite from Hack A ddu to VUA2 ddu on TCP, but contact to VUA1
        	//This message is sent from VUA1 ddu3.  
        	mess.invite("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"z9hG4bK4058649c0",
			HelpClass.UA2URL, HelpClass.UA4URL, "ff7577f256d0df7", "9013", "timer", "3600", "7547f946d5a9ca9",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			"SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address,
			HelpClass.UA2URL, "sdp", HelpClass.replay_tcp_test_file_s3);	
    	}
    	
    	if(HelpClass.test_udp_flag) 
    	{
    		this.replayUDP();
    	}
        if(HelpClass.test_tcp_flag) 
        {
        	this.replayTCP();
        }
    }
    
    public void replayUDP()
    {
    	HelpClass.debug("");
    	System.out.println("No.SIPA011-Replay Attack Test on UDP Begin ...");
    	UdpBase udpbase = new UdpBase();
    	//Register target user - A
    	HelpClass.debug("----->1.Register VUA - Caller");
    	String receive = udpbase.sendReceiveMessage(HelpClass.replay_udp_test_file_s1);
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.replay_result_udp = true;
        	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
            this.authentication();
            return;
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && receive.indexOf("200 OK")>=0 ) 
        {
    		//Register a virtual user/UA - B
    		HelpClass.debug("----->Info:Sucess");
    		HelpClass.debug("----->2.Register UAB - Callee");
    		String receive2 = udpbase.sendReceiveMessage(HelpClass.replay_udp_test_file_s2);
    		if (receive2.indexOf("200 OK")==-1 ) 
    		{	
    			HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
    	    
    	    HelpClass.debug("----->3.Modified INVITE from SSTT to B which seems from A");
    	    String receive3 = udpbase.sendReceiveMessage(HelpClass.replay_udp_test_file_s3);
    	    if(receive3 == null || receive3.indexOf("404 Not Found") >= 0) 
    		{
    	    	this.replay_result_udp = true;
    	    	System.out.println("No.SIPA011 Test: Pass-Replay Attack on UDP CANNOT Be Launched ...");
    	    	LogWriter.log("No.SIPA011 Test: Pass-Replay Attack on UDP CANNOT Be Launched");
    		}
    	    else
    	    {
    	    	this.replay_result_udp = true;
    	    	System.out.println("No.SIPA011 Test: Fail-Replay Attack on UDP Can Be Launched ...");
    	    	LogWriter.log("No.SIPA011 Test: Fail-Replay Attack on UDP Can Be Launched");
    	    }
        }
    	
    }
    
    public void replayTCP()
    {
    	HelpClass.debug("");
    	System.out.println("No.SIPA012-Replay Attack Test on TCP Begin ...");
    	TcpBase tcpbase = new TcpBase();
    	//Register target user - A
    	HelpClass.debug("----->1.Register VUA - Caller");
    	String receive = tcpbase.sendReceiveMessage(HelpClass.replay_tcp_test_file_s1);
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.replay_result_udp = true;
        	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
            this.authentication();
            return;
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && receive.indexOf("200 OK")>=0 ) 
        {
    		//Register a virtual user/UA - B
    		HelpClass.debug("----->Info:Sucess");
    		HelpClass.debug("----->2.Register UAB - Callee");
    		String receive2 = tcpbase.sendReceiveMessage(HelpClass.replay_tcp_test_file_s2);
    		if (receive2.indexOf("200 OK")==-1 ) 
    		{	
    			HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
    	    
    	    HelpClass.debug("----->3.Modified INVITE from SSTT to B which seems from A");
    	    String receive3 = tcpbase.sendReceiveMessage(HelpClass.replay_tcp_test_file_s3);
    	    if(receive3 == null || receive3.indexOf("404 Not Found") >= 0) 
    		{
    	    	this.replay_result_udp = true;
    	    	System.out.println("No.SIPA012 Test: Pass-Replay Attack on TCP CANNOT Be Launched ...");
    	    	LogWriter.log("No.SIPA012 Test: Pass-Replay Attack on TCP CANNOT Be Launched");
    		}
    	    else
    	    {
    	    	this.replay_result_udp = true;
    	    	System.out.println("No.SIPA012 Test: Fail-Replay Attack on TCP Can Be Launched ...");
    	    	LogWriter.log("No.SIPA012 Test: Fail-Replay Attack on TCP Can Be Launched");
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
    	System.out.println("3. Paste to the REGISTER messages in all files in TestFile\\SIPA011" +
    			"TestFile\\SIPA012 folder.");
    	System.out.println("4. Restart test.");
    }
}
