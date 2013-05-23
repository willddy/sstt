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
 * SIP_SPAM.java
 * Spam INVITE from different UAs, callee keep ringing.
 * Test the ability of UA to prevent SIP spam attack with 
 * access list implemented or not
 */

public class SIP_SPAM
{
	//Not Pass - false; Pass - true
    boolean spam_result_udp = true; 
    boolean spam_result_tcp = true;  
    
    /*
     * SIPA05X test case
     * In this case, UA1 (Test tool) plays as a hack
     * Target is UA2/ddu@cs.dal.ca
     */
    public SIP_SPAM()
    {
    	if(HelpClass.test_udp_flag) 
    	{
    		this.spamUDP();
    	}
        if(HelpClass.test_tcp_flag) 
        {
        	this.spamTCP();
        }
    }
    
    public void spamUDP()
    {
    	int counter = 0;
    	int index = 0;
    	String receive = "";
    	String FromURL = "";
    	
    	HelpClass.debug("");
    	System.out.println("No.SIPA051-DoS:SIP Spam Test on UDP Begin ...");
    	UdpBase udpbase = new UdpBase();
    	
    	//De-register flooding begin
    	HelpClass.debug("----->1.INVITE spam from other UAs.");
    	while(counter < HelpClass.num_flood_spam)
    	{
    		if(HelpClass.messaeg_flag)
        	{
    			index = counter % HelpClass.address_pool.length;
    			//create random FromURL "Advertisement-09809@test.net"
    			FromURL = "Advertisement-" + counter * index + "@test.net";
    				
    			Message mess = new Message();
        		//Create flooding message with CSeq increasing
        		//invite from SSTT/Hack/VUA1 ddu3 to VUA2 ddu on UDP, 
    			//but "From" field is VUA2; CSeq use automatic increase number here
            	mess.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.address_pool[index],
            	"z9hG4bK0553f059c",HelpClass.UA2URL, FromURL, "ff7577f256d0df6", 
            	null, "timer", "3600", "7547f946d5a9ca8",
            	"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", 
    			"70", null, "application/sdp", "0", FromURL, HelpClass.address_pool[index],
    			HelpClass.UA2URL, "sdp", HelpClass.spam_udp_test_file);
        	}
        	
        	receive = udpbase.sendReceiveMessage(HelpClass.spam_udp_test_file);
        	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
            {
            	this.spam_result_udp = true;
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
    		this.spam_result_udp = false;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA051 Test: Fail-SIP Spam Test on UDP " +
			"CAN Be Launched");
        	LogWriter.log("No.SIPA051 Test: Fail-SIP Spam Test on UDP " +
			"CAN Be Launched");
        }
    	else
    	{
    		this.spam_result_udp = true;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA051 Test: Pass-SIP Spam Test on UDP " +
			"CANNOT Be Launched");
        	LogWriter.log("No.SIPA051 Test: Pass-SIP Spam Test on UDP " +
			"CANNOT Be Launched");
    	}
    }
    
    public void spamTCP()
    {
    	int counter = 0;
    	int index = 0;
    	String receive = "";
    	String FromURL = "";
    	
    	HelpClass.debug("");
    	System.out.println("No.SIPA052-DoS:SIP Spam Test on TCP Begin ...");
    	TcpBase tcpbase = new TcpBase();
    	
    	//De-register flooding begin
    	HelpClass.debug("----->1.INVITE spam from other UAs.");
    	while(counter < HelpClass.num_flood_spam)
    	{
    		if(HelpClass.messaeg_flag)
        	{
    			index = counter % HelpClass.address_pool.length;
    			//create random FromURL "Advertisement-09809@test.net"
    			FromURL = "Advertisement-" + counter * index + "@test.net";
    				
    			Message mess = new Message();
        		//Create flooding message with CSeq increasing
        		//invite from SSTT/Hack/VUA1 ddu3 to VUA2 ddu on TCP, 
    			//but "From" field is VUA2; CSeq use automatic increase number here
            	mess.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.address_pool[index],
            	"z9hG4bK0553f059c",HelpClass.UA2URL, FromURL, "ff7577f256d0df6", 
            	null, "timer", "3600", "7547f946d5a9ca8",
            	"INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", 
    			"70", null, "application/sdp", "0", FromURL, HelpClass.address_pool[index],
    			HelpClass.UA2URL, "sdp", HelpClass.spam_tcp_test_file);
        	}
        	
        	receive = tcpbase.sendReceiveMessage(HelpClass.spam_tcp_test_file);
        	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
        		receive.indexOf("407 Proxy Authentication")>=0)
            {
            	this.spam_result_udp = true;
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
    		this.spam_result_udp = false;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA052 Test: Fail-SIP Spam Test on TCP " +
			"CAN Be Launched");
        	LogWriter.log("No.SIPA052 Test: Fail-SIP Spam Test on TCP " +
			"CAN Be Launched");
        }
    	else
    	{
    		this.spam_result_udp = true;
        	HelpClass.debug("----->Info:INVITE Flooding is stopped here.");
        	System.out.println("No.SIPA052 Test: Pass-SIP Spam Test on TCP " +
			"CANNOT Be Launched");
        	LogWriter.log("No.SIPA052 Test: Pass-SIP Spam Test on TCP " +
			"CANNOT Be Launched");
    	}
    	
    }
    
    public void authentication()
    {
    	System.out.println("NOTE:");
    	System.out.println("For futher test, authentication information is needed " +
    			"following the steps below.");
    	System.out.println("1. Check SIP REGISTER message in Ethereal.");
    	System.out.println("2. Find the field 'Authorization' and copy.");
    	System.out.println("3. Paste to the REGISTER messages in all files in TestFile\\SIPA051" +
    			"TestFile\\SIPA052 folder.");
    	System.out.println("4. Restart test.");
    }
}
