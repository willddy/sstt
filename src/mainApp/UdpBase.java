/*
 * Created on 2005-09-12
 */
package mainApp;

/**
 * @author Dayong
 * @version 2.9
 * 
 * UdpBase.java
 * Main application for test based on UDP
 */
import helpClass.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.SocketTimeoutException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class UdpBase
{
    public String m_ProxyAddress; //also address of registrar

    public String m_UA1Address;

    public String m_UA2Address;

    public int m_serverport;

    private DatagramSocket SRSocket; //Receive Socket, has timeout
    
    private DatagramSocket m_ROSocket; //Receive Only Socket, no timeout

    private DatagramSocket m_SSocket; //Send Socket
    
    /**
     * 
     * @param proxyAddress
     * @param port
     */
    public UdpBase()
    {
        this.m_ProxyAddress = HelpClass.ProxyAddress;
        this.m_serverport = HelpClass.server_listen_port;
    }
    
    /**
     * only send require from local file to the server without timeout
     * @param send_file_name
     */
    public void sendMessage(String send_file_name)
    {
        try
		{   
            //read from File
			BufferedInputStream buffer = new 
			BufferedInputStream( new FileInputStream(send_file_name) );
			int length = buffer.available();
			byte[] data_a = new byte[length];
			buffer.read(data_a, 0, length);
			//send request
			DatagramPacket output_a = new DatagramPacket(data_a, data_a.length,
            InetAddress.getByName(this.m_ProxyAddress), this.m_serverport);
            String out = new String(output_a.getData());
            //--test-- @@@@@@@@@@@
            HelpClass.debug("<< Send << " );
            HelpClass.debug(out);
            //--test-- @@@@@@@@@@@
            this.m_SSocket = new DatagramSocket();
            this.m_SSocket.send(output_a);
            this.m_SSocket.close();
        } 
        catch(FileNotFoundException fnfe)
		{
        	if(HelpClass.debug_flag) fnfe.printStackTrace();
		}
        catch (IOException ioe)
        {
        	if(HelpClass.debug_flag) ioe.printStackTrace();
        	HelpClass.test_udp_flag = false;
        	System.out.println("SIPI001 Test: Fail-Implementation is not on UDP");
        	System.out.println("SIPV001 Test: Fail-No Registration Authentication UDP");
        }
    }
    
    /**
     * receive reply as string format without timeout
     * @return msgReceiver
     */
    public String receiveMessage()
    {
        byte[] buffer_Receiver = new byte[8192];
        String msgReceiver = null;
        try
        {
            this.m_ROSocket = new DatagramSocket(HelpClass.ua_listen_port);
        }
        catch (SocketException se) 
        {
        	if(HelpClass.debug_flag) se.printStackTrace();
        }
        catch (Exception e)
        {
        	if(HelpClass.debug_flag) e.printStackTrace();
        }
        
        DatagramPacket dp_Receiver = new DatagramPacket(buffer_Receiver, buffer_Receiver.length);
        try 
        {
            this.m_ROSocket.receive(dp_Receiver);
            msgReceiver = new String(dp_Receiver.getData(), 0,dp_Receiver.getLength());                      
            this.m_ROSocket.close();
            //--test--@@@@@@@@
            HelpClass.debug(">> Receive >> "); 
            HelpClass.debug(msgReceiver); 
            //--test--@@@@@@@@
            return msgReceiver;
        }
        catch (SocketException se) 
        {
        	if(HelpClass.debug_flag) se.printStackTrace();
            return null;
        }
        catch (IOException ioe) 
        {
        	if(HelpClass.debug_flag) ioe.printStackTrace();
            return null;
        }
      }

    /**
     * send require and receive reply, having timeout
     * OTHER choice using thread underline
     * @param send_file_name
     * @return
     */

    public String sendReceiveMessage(String send_file_name)
    {
    	DatagramPacket incoming;
		DatagramPacket outgoing;
		//DatagramSocket SRSocket;
		String messagein;
		String messageout;
    	try
        {
    		BufferedInputStream buffer = new 
			BufferedInputStream( new FileInputStream(send_file_name) );
			int length = buffer.available();
			byte[] data_a = new byte[length];
			buffer.read(data_a, 0, length);
			//Prepare to send message out
            outgoing = new DatagramPacket
            (data_a, data_a.length, InetAddress.getByName(HelpClass.ProxyAddress), HelpClass.server_listen_port);
            messageout = new String(outgoing.getData(), 0, outgoing.getLength());
            
            //--test-- @@@@@@@@@@@
            HelpClass.debug(">> Send >> ");
            HelpClass.debug(messageout);
            //--test-- @@@@@@@@@@@
            
            //Send message
            SRSocket = new DatagramSocket(HelpClass.ua_listen_port);
            SRSocket.connect(InetAddress.getByName(HelpClass.ProxyAddress), HelpClass.server_listen_port);
            SRSocket.setSoTimeout(1000);           
            SRSocket.send(outgoing);
            //Receive message
            incoming = new DatagramPacket(new byte[8192], 8192);
            SRSocket.receive(incoming);
            SRSocket.close();
            messagein = new String(incoming.getData(), 0, incoming.getLength());
            
            //--test-- @@@@@@@@@@@
            HelpClass.debug(">> Receive >> ");
            HelpClass.debug(messagein);
            //--test-- @@@@@@@@@@@ 

            return messagein;     
        }  
        catch (SocketException se)
        {
         	if(HelpClass.debug_flag) se.printStackTrace();
         	return null;
        }
        catch (SocketTimeoutException ste) 
        {	
        	 SRSocket.close();
             if(HelpClass.debug_flag) ste.printStackTrace(); 
             return null;
        }
        catch (IOException ioe)
        {	
         	 SRSocket.close();
             if(HelpClass.debug_flag) ioe.printStackTrace();
             return null;
        }
          
    }
}