/*
 * Created on 2005-09-12
 */
package mainApp;

/**
 * @author Dayong
 * @version 2.9
 *  
 * TcpBase.java
 * used to provide interface for SIP implementation on TCP
 * Usage:
 * TcpBase()-open connection
 * clearSocket()-close connection
 * 
 */
import helpClass.*;

import java.net.*;
import java.io.*;

public class TcpBase 
{
	private Socket m_Socket; 
	private BufferedReader m_in;
	private PrintWriter m_out;

	public TcpBase()
	{
		try
		{
			//In TCP, connection in both side must use same port src port = des port 
			m_Socket=new 
			Socket(InetAddress.getByName(HelpClass.ProxyAddress),HelpClass.server_listen_port);
			m_out = new PrintWriter(m_Socket.getOutputStream(), true /* autoFlush */);
			m_in = new BufferedReader(new InputStreamReader(m_Socket.getInputStream()));                     
			HelpClass.test_tcp_flag = true;
		}
		catch(IOException ioe)
		{
			HelpClass.test_tcp_flag = false;
			if(HelpClass.debug_flag) ioe.printStackTrace();  
			System.out.println("No.SIPI002 Test: Fail-Implementation is not on TCP");
			System.out.println("No.SIPV002 Test: Fail-No Registration Authentication on TCP");
			LogWriter.log("No.SIPI002 Test: Fail-Implementation is not on TCP");
			LogWriter.log("No.SIPV002 Test: Fail-No Registration Authentication on TCP");
			return;
		}						
	}
	
	/**
	 * clear socket and stream reader and writer
	 */
	public void clearSocket()
	{
		try
		{
			m_out.close();
			m_in.close();
			m_Socket.close();
		}
		catch(IOException ioe)
		{
			if(HelpClass.debug_flag) ioe.printStackTrace();  
			return;
		}	
	}
	
	/**
	 * only send require from local file to the server without timeout
	 * @param send_file_name
	 */
	public void sendMessage(String send_file_name)
	{
		try
		{
			//set timeout to infinite
			this.m_Socket.setSoTimeout(0);
			//read from File
			BufferedInputStream buffer = new 
			BufferedInputStream( new FileInputStream(send_file_name) );
			int length = buffer.available();
			byte[] data_a = new byte[length];
			buffer.read(data_a, 0, length);
		    String send_message = new String(data_a);
		    HelpClass.debug("<< Send << " );
		    HelpClass.debug(send_message);
		    //send to server
			this.m_out.println(send_message);
		}		
		catch(IOException ioe)
		{
			if(HelpClass.debug_flag) ioe.printStackTrace(); 
			return;
		}
	}
	
	/**
     * receive reply as string format without timeout
     * @return msgReceiver
     */
    public String receiveMessage()
    {
    	String receive_message = null;
    	String print_message = null;
    	try
		{   
    		//set timeout to infinite
    		this.m_Socket.setSoTimeout(0);
			HelpClass.debug(">> Receive >> ");
			print_message = this.m_in.readLine(); // Read one line
			receive_message = print_message;	  // Keep message received
			
			while(print_message != null)
	        {
				 HelpClass.debug(receive_message);      // Output the line
				 print_message = this.m_in.readLine();  // Read the next line, if any
				 receive_message += print_message;
	        }
	        return receive_message;
		}		
		catch(IOException ioe)
		{
			if(HelpClass.debug_flag) ioe.printStackTrace();  
		    return null;
		}
    }
    
    /**
     * send require and receive reply, having timeout
     * @param send_file_name
     * @return
     */
    public String sendReceiveMessage(String send_file_name)
    {
    	String receive_message = null;
    	String print_message = null;
    	try
		{
    		//send require
			sendMessage(send_file_name);
			//set timeout
    		this.m_Socket.setSoTimeout(HelpClass.time_out);
			HelpClass.debug(">> Receive >> ");
			print_message = this.m_in.readLine();     // Read one line
			receive_message = print_message;	  // Keep message received
			
			while(print_message != null)
	        {
				 HelpClass.debug(receive_message);  // Output the line
				 print_message = this.m_in.readLine();  // Read the next line, if any
				 receive_message += print_message;
	        }
	        return receive_message;
		}	
    	catch (SocketException se)
    	{
    		if(HelpClass.debug_flag) se.printStackTrace();
    		return null;
    	}
		catch(IOException ioe)
		{
			if(HelpClass.debug_flag) ioe.printStackTrace();  
		    return null;
		}
    }
}

