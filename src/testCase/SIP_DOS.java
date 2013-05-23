/*
 * Created on 2005-9-12
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
 * SIP_DOS.java
 * There are two sub test case to identify the ability of both UA and proxy
 * to prevent DoS attack
 * INVITE flooding: Attacker will keep sending INVITE to target, so the target
 * callee will keep ringing all the time.
 * REGESTER flooding: Attacker will keep sending REGISTER with Expired field "0"
 * to target, so the target callee will be de-register all the time.
 */

public class SIP_DOS
{
	//Not Pass - false; Pass - true
    boolean dos_invite_udp = true; 
    boolean dos_register_udp = true; 
    boolean dos_invite_tcp = true; 
    boolean dos_register_tcp = true;
    
    /*
     * SIPA05X test case
     * In this case, UA1 (Test tool) plays as a hack
     * Target is UA2/ddu
     */
    public SIP_DOS()
    {
    	if(HelpClass.test_udp_flag) 
    	{
    		this.dosInvUDP();
    		this.dosRegUDP();
    	}
        if(HelpClass.test_tcp_flag) 
        {
        	this.dosInvTCP();
        	this.dosRegTCP();
        }
    }
    
    /**
     * Perform INVITE flooding test on UDP
     */
    public void dosInvUDP()
    {
    	int counter = 0;
    	String receive = "";
    	
    	HelpClass.debug("");
    	System.out.println("No.SIPA061-DoS:INVITE Flooding Test on UDP Begin ...");
    	UdpBase udpbase = new UdpBase();
    	
    	//De-register flooding begin
    	HelpClass.debug("----->1.INVITE flooding begin.");
    	while(counter < HelpClass.num_flood_invite)
    	{
    		if(HelpClass.messaeg_flag)
        	{
    			Message mess = new Message();
        		//Create flooding message with CSeq increasing
        		//invite from SSTT/Hack/VUA1 ddu3 to VUA2 ddu on UDP, 
    			//but "From" field is VUA2; CSeq use automatic increase number here
            	mess.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,
            	"z9hG4bK0553f059c",HelpClass.UA2URL, HelpClass.UA2URL, "ff7577f256d0df6", 
            	null, "timer", "3600", "7547f946d5a9ca8",
            	"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", 
    			"70", null, "application/sdp", "0", HelpClass.UA2URL, HelpClass.UA2Address,
    			HelpClass.UA2URL, "sdp", HelpClass.dos_udp_test_file_s1);
        	}
        	
        	receive = udpbase.sendReceiveMessage(HelpClass.dos_udp_test_file_s1);
        	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
            {
            	this.dos_invite_udp = true;
            	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
                this.authentication();
                break;
            }
        	counter ++;
    	} //end of while
    	
    	//After flooding, indentify results.
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
        {
            	return;
        }
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && 
    	     receive.indexOf("ringing")>=0 ) 
        {
    		this.dos_invite_udp = false;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA061 Test: Fail-DoS:INVITE Flooding Test on UDP " +
			"CAN Be Launched");
        	LogWriter.log("No.SIPA061 Test: Fail-DoS:INVITE Flooding Test on UDP " +
			"CAN Be Launched");
        }
    	else
    	{
    		this.dos_invite_udp = true;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA061 Test: Pass-DoS:INVITE Flooding Test on UDP " +
			"CANNOT Be Launched");
        	LogWriter.log("No.SIPA061 Test: Pass-DoS:INVITE Flooding Test on UDP " +
			"CANNOT Be Launched");
    	}
    }
    
    /**
     * Perform INVITE flooding test on TCP
     */
    public void dosInvTCP()
    {
    	int counter = 0;
    	String receive = "";
    	
    	HelpClass.debug("");
    	System.out.println("No.SIPA062-DoS:INVITE Flooding Test on TCP Begin ...");
    	TcpBase tcpbase = new TcpBase();
    	
    	//De-register flooding begin
    	HelpClass.debug("----->1.INVITE flooding begin.");
    	while(counter < HelpClass.num_flood_invite)
    	{
    		if(HelpClass.messaeg_flag)
        	{
    			Message mess = new Message();
        		//Create flooding message with CSeq increasing
        		//invite from SSTT/Hack/VUA1 ddu3 to VUA2 ddu on TCP, 
    			//but "From" field is VUA2; CSeq use automatic increase number here
            	mess.invite("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,
            	"z9hG4bK0553f059c",HelpClass.UA2URL, HelpClass.UA2URL, "ff7577f256d0df6", 
            	null, "timer", "3600", "7547f946d5a9ca8",
            	"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", 
    			"70", null, "application/sdp", "0", HelpClass.UA2URL, HelpClass.UA2Address,
    			HelpClass.UA2URL, "sdp", HelpClass.dos_tcp_test_file_s1);
        	}
        	
        	receive = tcpbase.sendReceiveMessage(HelpClass.dos_tcp_test_file_s1);
        	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
            {
            	this.dos_invite_tcp = true;
            	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
                this.authentication();
                break;
            }
        	counter ++;
    	} //end of while
    	
    	//After flooding, indentify results.
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
        {
            	return;
        }
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && 
    	     receive.indexOf("ringing")>=0 ) 
        {
    		this.dos_invite_tcp = false;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA062 Test: Fail-DoS:INVITE Flooding Test on TCP " +
			"CAN Be Launched");
        	LogWriter.log("No.SIPA062 Test: Fail-DoS:INVITE Flooding Test on TCP " +
			"CAN Be Launched");
        }
    	else
    	{
    		this.dos_invite_tcp = true;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA062 Test: Pass-DoS:INVITE Flooding Test on TCP " +
			"CANNOT Be Launched");
        	LogWriter.log("No.SIPA062 Test: Pass-DoS:INVITE Flooding Test on TCP " +
			"CANNOT Be Launched");
    	}
    	
    	tcpbase.clearSocket();
    }
    
    /**
     * Perform de-REGISTER flooding test on UDP
     */
    public void dosRegUDP()
    {
    	int counter = 0;
    	String receive = "";
    	
    	HelpClass.debug("");
    	System.out.println("No.SIPA063-DoS:REGISTER Flooding Test on UDP Begin ...");
    	UdpBase udpbase = new UdpBase();
    	
    	//De-register flooding begin
    	HelpClass.debug("----->1.de-REGISTER flooding begin.");
    	while(counter < HelpClass.num_flood_invite)
    	{
    		if(HelpClass.messaeg_flag)
        	{
    			Message mess = new Message();
        		//Create flooding message with CSeq increasing
        		//invite from SSTT/Hack/VUA1 ddu3 to VUA2 ddu on UDP, 
    			//but "From" field is VUA2; CSeq use automatic increase number here
             	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,
             	"E5rSD5sq7Q9sHv7", HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, 
             	"j8dxkzc9oy11Fx2FTA021", "70","0", "10100.12", "application/sdp", "0", 
             	HelpClass.dos_udp_test_file_s2);
        	}
        	
        	receive = udpbase.sendReceiveMessage(HelpClass.dos_udp_test_file_s2);
        	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
            {
            	this.dos_register_udp = true;
            	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
                this.authentication();
                break;
            }
        	counter ++;
    	} //end of while
    	
    	//After flooding, indentify results.
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
        {
            	return;
        }
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && 
    	     receive.indexOf("200 OK")>=0 ) 
        {
    		this.dos_register_udp = false;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA063 Test: Fail-DoS:REGISTER Flooding Test on UDP " +
			"CAN Be Launched");
        	LogWriter.log("No.SIPA063 Test: Fail-DoS:REGISTER Flooding Test on UDP " +
			"CAN Be Launched");
        }
    	else
    	{
    		this.dos_register_udp = true;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA063 Test: Pass-DoS:REGISTER Flooding Test on UDP " +
			"CANNOT Be Launched");
        	LogWriter.log("No.SIPA063 Test: Pass-Dos:REGISTER Flooding Test on UDP " +
			"CANNOT Be Launched");
    	}
    	
    }
    
    /**
     * Perform de-REGISTER flooding test on TCP
     */
    public void dosRegTCP()
    {
    	int counter = 0;
    	String receive = "";
    	
    	HelpClass.debug("");
    	System.out.println("No.SIPA064-DoS:REGISTER Flooding Test on TCP Begin ...");
    	TcpBase tcpbase = new TcpBase();
    	
    	//De-register flooding begin
    	HelpClass.debug("----->1.de-REGISTER flooding begin.");
    	while(counter < HelpClass.num_flood_invite)
    	{
    		if(HelpClass.messaeg_flag)
        	{
    			Message mess = new Message();
        		//Create flooding message with CSeq increasing
        		//invite from SSTT/Hack/VUA1 ddu3 to VUA2 ddu on UDP, 
    			//but "From" field is VUA2; CSeq use automatic increase number here
             	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,
             	"E5rSD5sq7Q9sHv7", HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, 
             	"j8dxkzc9oy11Fx2FTA021", "70","0", "10100.12", "application/sdp", "0", 
             	HelpClass.dos_tcp_test_file_s2);
        	}
        	
        	receive = tcpbase.sendReceiveMessage(HelpClass.dos_tcp_test_file_s2);
        	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
            {
            	this.dos_register_tcp = true;
            	HelpClass.debug("----->Info:Unauthorized; Authentication Needed.");
                this.authentication();
                break;
            }
        	counter ++;
    	} //end of while
    	
    	//After flooding, indentify results.
    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
        {
            	return;
        }
    	if ((receive != null)&& receive.indexOf("401 Unauthorized")==-1 &&
    	     receive.indexOf("407 Proxy Authentication")==-1 && 
    	     receive.indexOf("200 OK")>=0 ) 
        {
    		this.dos_register_tcp = false;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA064 Test: Fail-DoS:REGISTER Flooding Test on TCP " +
			"CAN Be Launched");
        	LogWriter.log("No.SIPA064 Test: Fail-DoS:REGISTER Flooding Test on TCP " +
			"CAN Be Launched");
        }
    	else
    	{
    		this.dos_register_tcp = true;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA064 Test: Pass-DoS:REGISTER Flooding Test on TCP " +
			"CANNOT Be Launched");
        	LogWriter.log("No.SIPA064 Test: Pass-DoS:REGISTER Flooding Test on TCP " +
			"CANNOT Be Launched");
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
    	System.out.println("3. Paste to the REGISTER messages in all files in TestFile\\SIPA06X" +
    			"folder.");
    	System.out.println("4. Restart test.");
    }
}

