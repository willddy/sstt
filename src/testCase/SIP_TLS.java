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

/**
 * @author Dayong
 * @version 2.9
 * 
 * SIP_TLS.java
 * TLS can only run on TCP
 * Identifies the encryption capabilities of the target host ¨C TLS (SIPS).
 */
public class SIP_TLS 
{
	//Not Pass - false; Pass - true
    public boolean tls_result_udp = false; 
    public boolean tls_result_tcp = false; 
    
    public SIP_TLS()
    {
    	if(HelpClass.messaeg_flag)
    	{
    		Message mess = new Message();
        	mess.register("TLS","TLS",HelpClass.ProxyAddress,HelpClass.UA2Address,"E5rSD5sq7Q9sHv7",
        	HelpClass.UA2URL, HelpClass.UA2URL, "1928301775", null, "j8dxkzc9oy11Fx2FTI004", "70",
        	"3600", "10100.12", "application/sdp", "0", HelpClass.tls_tcp_test_file);
    	}
    	if(HelpClass.test_udp_flag) this.tlsUdp();
        if(HelpClass.test_tcp_flag) this.tlsTcp();
    }
    
    /**
     * TLS is only on TCP
     */
    public void tlsUdp()
    {
    	System.out.println("No.SIPV041 Test: Fail-No support for TLS (SIPS) on UDP");
    	LogWriter.log("No.SIPV041 Test: Fail-No Support for TLS (SIPS) on TCP");
    }
    
    /**
     * Identifies the encryption capabilities of the target host ¨C TLS (SIPS)
     */
    public void tlsTcp()
    {
    	HelpClass.debug("");
    	//SIP Vulnerability Test - SIPV040: TLS(SIPS) Implementation Test
    	System.out.println("No.SIPV042-SIP TLS Implementation Test Begin ...");
        
    	TcpBase tcpbase = new TcpBase();
    	if(HelpClass.test_tcp_flag != false)
    	{
	    	/*
	         * send SIP message: REGISTRATION to proxy/registrar on TCP with SIPS URL
	         */
	    	String receive = tcpbase.sendReceiveMessage(HelpClass.tls_tcp_test_file);

	    	//Actually receive != null already
		    if (receive == null || receive.indexOf("sips")==-1 || 
		    	receive.indexOf("TLS")==-1)
		    {
		        this.tls_result_tcp = false;
		        System.out.println("No.SIPV042 Test: Fail-No Support for TLS (SIPS) on TCP");
		        LogWriter.log("No.SIPV042 Test: Fail-No Support for TLS (SIPS) on TCP");
		    }
		    if (receive != null && ( receive.indexOf("sips")>=0 || 
			    	receive.indexOf("TLS")>=0))
		    {
		    	this.tls_result_tcp = true;
		    	System.out.println("No.SIPV042 Test: Pass-Support for TLS (SIPS) on TCP");
		        LogWriter.log("No.SIPV042 Test: Pass-Support for TLS (SIPS) on TCP");
		    } 	
		    //!!May need or not?
		    tcpbase.clearSocket();
    	}
    }  
}
