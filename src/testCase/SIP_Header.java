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
 * SIP_Header.java
 * Verifies the ability of the UA/Proxy to handle extra space  
 * characters and new line within the header of request.
 */
public class SIP_Header 
{
	//Not Pass - true; Pass - false
	public boolean mime_result_udp_1 = false; 
    public boolean mime_result_tcp_1 = false; 
	public boolean mime_result_udp_2 = false; 
    public boolean mime_result_tcp_2 = false; 
    
    public SIP_Header()
    {
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
        	//Extra space within SIP header (NOT in keywords)
        	mess.register("2.0     ","     UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,
        	"E5rSD5sq7Q9sHv7", HelpClass.UA1URL, HelpClass.UA1URL, "1928301774       ", null, 
        	"j8dxkzc9oy11Fx2FTI001", "70", "     360", "10100.12", "application/sdp", "0", 
        	HelpClass.header_space_udp_test_file);
        	
        	mess.register("2.0     ","     TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,
        	"E5rSD5sq7Q9sHv7", HelpClass.UA1URL, HelpClass.UA1URL, "1928301775       ", null, 
        	"j8dxkzc9oy11Fx2FTI002", "70", "     360", "10100.12", "application/sdp", "0", 
        	HelpClass.header_space_tcp_test_file);
        	
        	//Newline within SIP header (NOT in keywords)
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI003", "70",
         	"360\n", "10100.12", "application/sdp\n", "0", HelpClass.header_newline_udp_test_file);
        	
         	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI004", "70",
        	"360\n", "10100.12", "application/sdp\n", "0", HelpClass.header_newline_tcp_test_file);
    	}
    	if(HelpClass.test_udp_flag) this.headerUdp();
        if(HelpClass.test_tcp_flag) this.headerTcp();
    }
    
    /**
     * UDP SIP header test
     */
    public void headerUdp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV061: Extra space within SIP header (NOT in keywords)
    	System.out.println("No.SIPV061-Extra space within SIP header Test on UDP Begin ...");
    	
    	UdpBase udpbase = new UdpBase();
    	/*
         * send SIP message: REGISTER to proxy/registrar with extra space within header
         */
    	String receive1 = udpbase.sendReceiveMessage(HelpClass.header_space_udp_test_file);
    	//Actually receive != null already
    	if (receive1 == null || receive1.indexOf("400 Syntax Error")>=0 ||
    			receive1.indexOf("423 Interval Too Brief")>=0)
        {
        	this.mime_result_udp_1 = true;
        	System.out.println("No.SIPV061 Test: Fail-Cannot Parse Extra Whitespaces in Header on UDP");
            LogWriter.log("No.SIPV061 Test: Fail-Cannot Parse Extra Whitespaces in Header on UDP");
        }
    	else 
        {
    	    this.mime_result_udp_1 = false;
    	    System.out.println("No.SIPV061 Test: Pass-Can Parse Extra Whitespaces in Header on UDP");
            LogWriter.log("No.SIPV061 Test: Pass-Can Parcer Parse Whitespaces in Header on UDP");
        }
    	
    	//SIP Vulnerability Test - SIPV063: Newline within SIP header (NOT in keywords)
    	System.out.println("No.SIPV063-Newline within SIP header Test on UDP Begin ...");
    	/*
         * send SIP message: REGISTER to proxy/registrar with newline within header
         */
    	String receive2 = udpbase.sendReceiveMessage(HelpClass.header_newline_udp_test_file);
    	if (receive2 == null || receive2.indexOf("400 Syntax Error")>=0 || 
    			receive2.indexOf("423 Interval Too Brief")>=0)
        {
        	this.mime_result_udp_2 = true;
        	System.out.println("No.SIPV063 Test: Fail-Cannot Parse Extra Newlines in Header on UDP");
            LogWriter.log("No.SIPV063 Test: Fail-Cannot Parse Extra Newlines in Header on UDP");
        }
    	else 
        {
    	    this.mime_result_udp_2 = false;
    	    System.out.println("No.SIPV063 Test: Pass-Can Parse Extra Newlines in Header on UDP");
            LogWriter.log("No.SIPV063 Test: Pass-Can Parse Extra Newlines in Header on UDP");
        }
    }
    
    /**
     * TCP SIP header test
     */
    public void headerTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV062: Extra space within SIP header (NOT in keywords)
    	System.out.println("No.SIPV062-Extra Whitespaces Within SIP Header Test on TCP Begin ...");
    	
    	TcpBase tcpbase = new TcpBase();
    	/*
         * send SIP message: REGISTER to proxy/registrar with extra space within header
         */
    	String receive1 = tcpbase.sendReceiveMessage(HelpClass.header_space_tcp_test_file);
    	//Actually receive != null already
    	if (receive1 == null || receive1.indexOf("400 Syntax Error")>=0)
        {
        	this.mime_result_tcp_1 = true;
        	System.out.println("No.SIPV062 Test: Fail-Cannot Parse Extra Whitespaces in Header on TCP");
            LogWriter.log("No.SIPV062 Test: Fail-Cannot Parse Extra Whitespaces in Header on TCP");
        }
    	else 
        {
    	    this.mime_result_tcp_1 = false;
    	    System.out.println("No.SIPV062 Test: Pass-Can Parse Extra Whitespaces in Header on TCP");
            LogWriter.log("No.SIPV062 Test: Pass-Can Parse Extra Whitespaces in Header on TCP");
        }
    	
    	//SIP Vulnerability Test - SIPV064: Newlinewithin SIP header (NOT in keywords)
    	System.out.println("No.SIPV064-Newlines within SIP header Test on TCP Begin ...");
    	/*
         * send SIP message: REGISTER to proxy/registrar with newline within header
         */
    	String receive2 = tcpbase.sendReceiveMessage(HelpClass.header_newline_tcp_test_file);
    	if (receive2 == null || receive2.indexOf("400 Syntax Error")>=0)
        {
        	this.mime_result_tcp_2 = true;
        	System.out.println("No.SIPV064 Test: Fail-Cannot Parse Extra Newlines in Header on TCP");
            LogWriter.log("N0.SIPV064 Test: Fail-Cannot Parse Extra Newlines in Header on TCP");
        }
    	else 
        {
    	    this.mime_result_tcp_2 = false;
    	    System.out.println("No.SIPV064 Test: Pass-Can Parse Extra Newlines in Header on TCP");
            LogWriter.log("No.SIPV064 Test: Pass-Can Parse Extra Newlines in Header on TCP");
        }
    	
    	tcpbase.clearSocket();
    	
    }
}
