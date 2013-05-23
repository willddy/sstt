/*
 * Created on 2005-9-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package testCase;

import mainApp.TcpBase;
import mainApp.UdpBase;
import helpClass.HelpClass;
import helpClass.LogWriter;

/**
 * @author Dayong
 * @version 2.9
 *
 * SIP_Tear_Down.java
 * A attacker tears down a SIP session between two UAs by sending his own CANCEL/BYE
 * request to the other through proxy.
 * Here it only needs to check if there is authentication in previous CANCEL or BYE 
 * test case.
 */
public class SIP_Tear_Down 
{
	//true - has authentication for CANCEL; false - no authentication
	//Not Pass - false; Pass - true
    public boolean tear_result_udp = true; 
    public boolean tear_result_tcp = true; 
    
    public SIP_Tear_Down
    (boolean cancel_udp, boolean cancel_tcp, boolean bye_udp, boolean bye_tcp)
    {
    	this.tear_result_udp = cancel_udp && bye_udp;
    	this.tear_result_tcp = cancel_tcp && bye_tcp;
    	if(HelpClass.test_udp_flag) this.tearUdp();
        if(HelpClass.test_tcp_flag) this.tearTcp();
    }
    
    public void tearUdp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPA031: Tear Down Session Attack Test on UDP
    	System.out.println("No.SIPA031-Tear Down Session Attack Test on UDP Begin ...");

    	//Actually receive != null already
    	if (this.tear_result_udp)
        {
        	System.out.println("No.SIPA031 Test: Pass-Tear Down Session Attack on UDP" +
    	    		" CANNOT Be Launched");
            LogWriter.log("No.SIPA031 Test: Pass-Tear Down Session Attack on UDP" +
    	    		" CANNOT Be Launched");
        }
    	
    	if (!this.tear_result_udp) 
        {
    	    System.out.println("No.SIPA031 Test: Fail-Tear Down Session Attack on UDP" +
    	    		" Can Be Launched");
            LogWriter.log("No.SIPA031 Test: Fail-Tear Down Session Attack on UDP" +
    	    		" Can Be Launched");
        }
    	
    }
    
    public void tearTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPA032: Tear Down Session Attack Test on TCP
    	System.out.println("No.SIPA032-Tear Down Session Attack Test on TCP Begin ...");

    	if (this.tear_result_tcp)
        {
        	System.out.println("No.SIPA032 Test: Pass-Tear Down Session Attack on TCP" +
    	    		" CANNOT Be Launched");
            LogWriter.log("No.SIPA032 Test: Pass-Tear Down Session Attack on TCP" +
    	    		" CANNOT Be Launched");
        }
    	
    	if (!this.tear_result_tcp) 
        {
    	    System.out.println("No.SIPA032 Test: Fail-Tear Down Session Attack on TCP" +
    	    		" Can Be Launched");
            LogWriter.log("No.SIPA032 Test: Fail-Tear Down Session Attack on TCP" +
    	    		" Can Be Launched");
        }	
    } 
}
