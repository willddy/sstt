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
 * SIP_MIME.java 
 * Identifies the encryption capabilities of the target host ¨C MIME body.
 */
public class SIP_MIME 
{
	//Not Pass - false; Pass - true
    public boolean mime_result_udp = false; 
    public boolean mime_result_tcp = false; 
    
    public SIP_MIME()
    {
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
         	mess.register("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
         	HelpClass.UA2URL, HelpClass.UA2URL, "1928301774", null, "j8dxkzc9oy11Fx2FTI003", "70",
         	"3600", "10100.12", "application/pkcs7-mime; smime-type=enveloped-data; name=smime.p7m", 
         	"0", HelpClass.mime_udp_test_file);
        	//register VUA2 ddu2 on TCP - No.SIPI004
        	mess.register("2.0","TCP",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI004", "70",
        	"3600", "10100.12", "application/pkcs7-mime; smime-type=enveloped-data; name=smime.p7m",
        	"0", HelpClass.mime_tcp_test_file);
    	}
    	if(HelpClass.test_udp_flag) this.mimeUdp();
        if(HelpClass.test_tcp_flag) this.mimeTcp();
    }
    
    /**
     * UDP MIME body test
     */
    public void mimeUdp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV051: Using MIME body on UDP Test
    	System.out.println("No.SIPV051-Using MIME Body Test on UDP Begin ...");
    	
    	UdpBase udpbase = new UdpBase();
    	/*
         * send SIP message: INVITE to proxy/registrar with MIME attachment
         */
    	String receive = udpbase.sendReceiveMessage(HelpClass.mime_udp_test_file);
      
    	//Actually receive != null already
    	if (receive == null || receive.indexOf("mime")==-1)
        {
        	this.mime_result_udp = true;
        	System.out.println("No.SIPV051 Test: Fail-No Support for MIME on UDP");
            LogWriter.log("No.SIPV051 Test: Fail-No Support for MIME on UDP");
        }
    	
    	if ((receive != null)&& receive.indexOf("mime")>=0) 
        {
    	    this.mime_result_udp = false;
    	    System.out.println("No.SIPV051 Test: Pass-Support for MIME on UDP");
            LogWriter.log("No.SIPV051 Test: Pass-Support for MIME on UDP");
        }
    }
    
    /**
     * TCP MIME body test
     */
    public void mimeTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV052: Using MIME body on TCP Test
    	System.out.println("No.SIPV051-Using MIME Body Test on TCP Begin ...");
    	
    	TcpBase tcpbase = new TcpBase();
    	/*
         * send SIP message: INVITE to proxy/registrar with MIME attachment
         */
    	String receive = tcpbase.sendReceiveMessage(HelpClass.mime_tcp_test_file);
      
    	//Actually receive != null already
    	if (receive == null || receive.indexOf("mime")==-1)
        {
        	this.mime_result_tcp = true;
        	System.out.println("No.SIPV052 Test: Fail-No Support for MIME on TCP");
            LogWriter.log("No.SIPV052 Test: Fail-No Support for MIME on TCP");
        }
    	
    	if ((receive != null)&& receive.indexOf("mime")>=0) 
        {
    	    this.mime_result_tcp = false;
    	    System.out.println("No.SIPV052 Test: Pass-Support for MIME on TCP");
            LogWriter.log("No.SIPV052 Test: Pass-Support for MIME on TCP");
        }
    	
    	tcpbase.clearSocket();
    }
    
    
}
