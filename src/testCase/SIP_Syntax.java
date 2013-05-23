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
import helpClass.Message;

/**
 * @author Dayong
 * @version 2.9
 * 
 * SIP_Syntax.java
 * Sytax Error Recognization Test
 */
public class SIP_Syntax 
{
	//Not Pass - true; Pass - false
	public boolean syntax_result_udp_1 = false; 
	public boolean syntax_result_udp_2 = false;
	public boolean syntax_result_udp_3 = false;
	public boolean syntax_result_udp_4 = false;
    public boolean syntax_result_tcp_1 = false; 
    public boolean syntax_result_tcp_2 = false; 
    public boolean syntax_result_tcp_3 = false; 
    public boolean syntax_result_tcp_4 = false; 
    
    public SIP_Syntax()
    {
    	//Create Message
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
        	//Using Whitespaces in SIP Keywords
        	mess.register("syntax1","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI001", "70",
    		"360", "10100.12", "application/sdp", "0", HelpClass.sytax_udp_test_file_1);
        	mess.register("syntax1","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI002", "70",
        	"360", "10100.12", "application/sdp", "0", HelpClass.sytax_tcp_test_file_1);
        	
        	//Using Wrongs SIP Keywords
         	mess.register("syntax2","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI003", "70",
         	"360", "10100.12", "application/sdp", "0", HelpClass.sytax_udp_test_file_2);
        	mess.register("syntax2","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI004", "70",
        	"360", "10100.12", "application/sdp", "0", HelpClass.sytax_tcp_test_file_2);
        	
        	//Using Keywords Undefined
        	mess.register("syntax3","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI001", "70",
    		"360", "10100.12", "application/sdp", "0", HelpClass.sytax_udp_test_file_3);
        	mess.register("syntax3","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA1URL, HelpClass.UA1URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI002", "70",
        	"360", "10100.12", "application/sdp", "0", HelpClass.sytax_tcp_test_file_3);
        	
        	//Using Wrong Content in Fields
         	mess.register("syntax4","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI003", "70",
         	"3600", "10100.12", "application/sdp", "0", HelpClass.sytax_udp_test_file_4);
        	mess.register("syntax4","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI004", "70",
        	"3600", "10100.12", "application/sdp", "0", HelpClass.sytax_tcp_test_file_4);
    	}
    	if(HelpClass.test_udp_flag) this.syntaxUdp();
        if(HelpClass.test_tcp_flag) this.syntaxTcp();
    }
    
    /**
     * for each sub-test on UDP/TCP
     * @param num
     * @param str
     * @param file
     */
    private void sender(int num, String str, String file, boolean udp)
    {   
    	//UDP - true
    	if(udp)
    	{
    		HelpClass.debug("");
    		System.out.println("No.SIPV07" + num + "-" + str + " Test on UDP Begin ...");
        	UdpBase udpbase = new UdpBase();
        	String receive = udpbase.sendReceiveMessage(file);
        	if (receive == null || receive.indexOf("200 OK")==-1)
            {
            	switch(num)
            	{
            		case 1: this.syntax_result_udp_1 = false;
            		case 3: this.syntax_result_udp_2 = false;
            		case 5: this.syntax_result_udp_3 = false;
            		case 7: this.syntax_result_udp_4 = false;        	
            	}
            	System.out.println("No.SIPV07" + num + " Test: Fail-No Support for " + str + " on UDP");
                LogWriter.log("No.SIPV07" + num + " Test: Fail-No Support for " + str + " on UDP");
            }
        	else
            {
        		switch(num)
            	{
            		case 1: this.syntax_result_udp_1 = true;
            		case 3: this.syntax_result_udp_2 = true;
            		case 5: this.syntax_result_udp_3 = true;
            		case 7: this.syntax_result_udp_4 = true;        	
            	}
        	    System.out.println("No.SIPV07" + num + " Test: Pass-Support for " + str + " on UDP");
                LogWriter.log("No.SIPV07" + num + " Test: Pass-Support for " + str + " on UDP");
            } 	
    	}
    	//TCP - false
    	if(!udp)
    	{
    		HelpClass.debug("");
    		System.out.println("No.SIPV07" + num + "-" + str + "Test on TCP Begin ...");
        	TcpBase tcpbase = new TcpBase();
        	String receive = tcpbase.sendReceiveMessage(file);
        	if (receive == null || receive.indexOf("200 OK")==-1)
            {
            	switch(num)
            	{
            		case 2: this.syntax_result_tcp_1 = false;
            		case 4: this.syntax_result_tcp_2 = false;
            		case 6: this.syntax_result_tcp_3 = false;
            		case 8: this.syntax_result_tcp_4 = false;        	
            	}
            	System.out.println("No.SIPV07" + num + " Test: Fail-No Support for " + str + " on TCP");
                LogWriter.log("No.SIPV07" + num + " Test: Fail-No Support for " + str + " on TCP");
            }
        	else
            {
        		switch(num)
            	{
            		case 2: this.syntax_result_tcp_1 = true;
            		case 4: this.syntax_result_tcp_2 = true;
            		case 6: this.syntax_result_tcp_3 = true;
            		case 8: this.syntax_result_tcp_4 = true;        	
            	}
        	    System.out.println("No.SIPV07" + num + " Test: Pass-Support for " + str + " on TCP");
                LogWriter.log("No.SIPV07" + num + " Test: Pass-Support for " + str + " on TCP");
            }
        	tcpbase.clearSocket();
    	}
    }

    /**
     * Test on UDP
     */
    public void syntaxUdp()
    {
    	this.sender(1, "Using Whitespaces in SIP Keywords", HelpClass.sytax_udp_test_file_1, true);
    	this.sender(3, "Using Wrongs SIP Keywords", HelpClass.sytax_udp_test_file_2, true);
    	this.sender(5, "Using Keywords Undefined", HelpClass.sytax_udp_test_file_3, true);
    	this.sender(7, "Using Wrong Content in Fields", HelpClass.sytax_udp_test_file_4, true);
    }
    
    /**
     * Test on TCP
     */
    public void syntaxTcp()
    {
    	this.sender(2, "Using Whitespaces in SIP Keywords", HelpClass.sytax_tcp_test_file_1, false);
    	this.sender(4, "Using Wrongs SIP Keywords", HelpClass.sytax_tcp_test_file_2, false);
    	this.sender(6, "Using Keywords Undefined", HelpClass.sytax_tcp_test_file_3, false);
    	this.sender(8, "Using Wrong Content in Fields", HelpClass.sytax_tcp_test_file_4, false);
    }
}
