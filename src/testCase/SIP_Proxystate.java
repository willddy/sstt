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
 * SIP_Proxystate.java
 * Identify Re-transmissions
 * SIPI005 Stateless or state proxy server on UDP
 * SIPI006 Stateless or state proxy server on TCP
 */
public class SIP_Proxystate 
{
	//stateful proxy - Pass - true; stateless proxy - Fail - false
    public boolean pstate_result_udp = false; 
    public boolean pstate_result_tcp = false; 
    
    public SIP_Proxystate()
    {
    	//Create Message
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
        	//register VUA1 ddu3 on UDP - No.SIPI011
        	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI001", "70",
    		"3600", "10100.12", "application/sdp", "0", HelpClass.pstate_udp_test_file);
        	
        	//register VUA1 ddu3 on TCP - No.SIPI012
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI002", "70",
        	"3600", "10100.12", "application/sdp", "0", HelpClass.pstate_tcp_test_file);
    	}
    	//begin test
    	if(HelpClass.test_udp_flag) this.proxyStateUdp();
    	if(HelpClass.test_tcp_flag) this.proxyStateTcp();
    }
    
    /**
     * UDP authentication test
     */   
    public void proxyStateUdp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPI011: Proxy State Test
    	System.out.println("No.SIPI011-SIP Proxy State Test on UDP Begin ...");
    	UdpBase udpbase = new UdpBase();
    	HelpClass.debug("<debug>Register VUA1");
    	String receive = udpbase.sendReceiveMessage(HelpClass.pstate_udp_test_file);

    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
	    	receive.indexOf("407 Proxy Authentication")>=0)
    	{
    		System.out.println("Authentication Required !");
    		this.authentication();
    	}
    	else
    	{
    		HelpClass.debug("<debug>Register VUA1 again"); 
    		String receive2 = udpbase.sendReceiveMessage(HelpClass.pstate_udp_test_file);
    
	    	if (receive2.indexOf("200 OK")>=0)
	        {
	        	System.out.println("No.SIPI011 Test: Fail-Stateless proxy which Cannot absorb " +
	        			"re-transmission on UDP");
	            LogWriter.log("No.SIPI011 Test: Fail-Stateless proxy which Cannot absorb " +
	            		"re-transmission on UDP");
	            this.pstate_result_udp = false;
	        }
	    	else
	        {	    
	    	    System.out.println("No.SIPI011 Test: Pass-Stateful proxy which Can absorb " +
	    	    		"re-transmission on UDP");
	            LogWriter.log("No.SIPI011 Test: Pass-Stateful proxy which Can absorb " +
	            		"re-transmission on UDP");
	            this.pstate_result_udp = true;                 
	        }
    	}
    }
    
    /**
     * TCP authentication test
     */
    public void proxyStateTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPI021: Proxy State Test
    	System.out.println("No.SIPI021-SIP Proxy State Test on TCP Begin ...");
    	TcpBase tcpbase = new TcpBase();
    	HelpClass.debug("<debug>Register VUA1");
    	String receive = tcpbase.sendReceiveMessage(HelpClass.pstate_tcp_test_file);

    	if (receive == null || receive.indexOf("401 Unauthorized")>=0 || 
	    	receive.indexOf("407 Proxy Authentication")>=0)
    	{
    		System.out.println("Authentication Required !");
    		this.authentication();
    	}
    	else
    	{
    		HelpClass.debug("<debug>Register VUA1 again"); 
    		String receive2 = tcpbase.sendReceiveMessage(HelpClass.pstate_udp_test_file);
    
	    	if (receive2.indexOf("200 OK")>=0)
	        {
	        	System.out.println("No.SIPI021 Test: Fail-Stateless proxy which Cannot absorb " +
	        			"re-transmission on TCP");
	            LogWriter.log("No.SIPI021 Test: Fail-Stateless proxy which Cannot absorb " +
	            		"re-transmission on TCP");
	            this.pstate_result_udp = false;
	        }
	    	else
	        {	    
	    	    System.out.println("No.SIPI021 Test: Pass-Stateful proxy which Can absorb " +
	    	    		"re-transmission on TCP");
	            LogWriter.log("No.SIPI021 Test: Pass-Stateful proxy which Can absorb " +
	            		"re-transmission on TCP");
	            this.pstate_result_udp = true;                 
	        }
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
    	System.out.println("   SIPV071-8.txt; SIPA001-2");
    	System.out.println("5. Restart test.");
    }
}
