/*
 * Created on 2005-09-12
 */
package mainApp;

import helpClass.*;
import testCase.*;

//import java.util.HashSet;
import java.io.*;

/**
 * @author Dayong
 * @version 2.9
 * 
 * TestApp.java
 * Main test control, target: SIP porxy/registrar
 * Can creat many virtual client
 */
public class TestApp 
{  
    public TestApp()
	{
    	try
		{
			String str = "";
			System.out.println("----------SIP Security Test Tool------------");
			System.out.println("Please open Ethereal to monitor a SIP session");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Input Proxy/Registrar Server Address [129.173.67.67]:");
			str = input.readLine();
			if(!str.equals("")) HelpClass.ProxyAddress = str;

			System.out.print("Input User Agent1 Address [192.168.0.2]:");
			str = input.readLine();
			if(!str.equals("")) HelpClass.UA1Address = str;
			
			System.out.print("Input User Agent1 URL [ddu3@cs.dal.ca]:");
			str = input.readLine();
			if(!str.equals("")) HelpClass.UA1URL = str;
			
			System.out.print("Input User Agent2 Address [129.173.66.246]:");
			str = input.readLine();
			if(!str.equals("")) HelpClass.UA2Address = str;	
			
			System.out.print("Input User Agent2 URL [ddu@cs.dal.ca]:");
			str = input.readLine();
			if(!str.equals("")) HelpClass.UA2URL = str;
			
			System.out.print("Input Server Send and Receive Port [5060]:");
			str = input.readLine();
			if(!str.equals("")) HelpClass.server_listen_port = Integer.parseInt(str);
			
			System.out.print("Input UA Send and Receive Port [5062]:");
			str = input.readLine();
			if(!str.equals("")) HelpClass.ua_listen_port = Integer.parseInt(str);
			
			System.out.print("Test Mode - Manual/Auto [Auto]:");
			str = input.readLine();
			if(!str.equals("")||str.equalsIgnoreCase("Manual") == true) 
				HelpClass.messaeg_flag = false;
			else
				HelpClass.messaeg_flag = true;

			//--test--@@@@@@@@@
			HelpClass.debug(HelpClass.ProxyAddress);
			HelpClass.debug(HelpClass.UA1Address);
			HelpClass.debug(HelpClass.UA1URL);
			HelpClass.debug(HelpClass.UA2Address);
			HelpClass.debug(HelpClass.UA2URL);
			HelpClass.debug(HelpClass.server_listen_port + "");
			HelpClass.debug(HelpClass.ua_listen_port + "");
			HelpClass.debug(HelpClass.messaeg_flag + "");
			//--test--@@@@@@@@@
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
	    TestApp siptest = new TestApp();
	    //open new log
	    LogWriter.logInit();
	    //turn on log writer
	    LogWriter.setOn(true);
	    System.out.println();
	    /*
	     * SIP Security Test
	     */
	 
		//SIP implemetation UDP and REGISTER authentication test
		SIP_Auth_Reg aut_reg = new SIP_Auth_Reg();
		HelpClass.enter();
		//SIP implemetation UDP and REGISTER authentication test
		SIP_Proxystate pstate = new SIP_Proxystate();
		HelpClass.enter();
		//SIP Vlnerability INVITE authentication test
		SIP_Auth_Invi aut_inv = new SIP_Auth_Invi();
		HelpClass.enter();
		//SIP Vlnerability CANCEL authentication test
		SIP_Auth_Cancel aut_can = new SIP_Auth_Cancel();
		HelpClass.enter();
		//SIP Vlnerability BYE authentication test
		SIP_Auth_Bye aut_bye = new SIP_Auth_Bye();
		HelpClass.enter();
		//SIP Vlnerability TLS (SIPS) Support Test
		SIP_TLS tls = new SIP_TLS();
		HelpClass.enter();
		//SIP Vlnerability MIME Support Test
		SIP_MIME mime = new SIP_MIME();
		HelpClass.enter();
		//SIP Robust Test ability to parce extra space and lines within SIP header
		SIP_Header header = new SIP_Header();
		HelpClass.enter();
		//SIP Robust Test ability to parce syntax error for SIP Implementation
		SIP_Syntax syntax = new SIP_Syntax();
		HelpClass.enter();
		//SIP Attack Test ability to prevent Registration Hijacking
		SIP_Reg_Hijack hiji = new SIP_Reg_Hijack();
		HelpClass.enter();
		//SIP Attack Test ability to prevent Registration Hijacking
		SIP_Replay_Attack rep = new SIP_Replay_Attack();
		HelpClass.enter();
		//SIP Attack Test ability to prevent INVITE Request Spoofing
		SIP_Invite_Spoof inv = new SIP_Invite_Spoof();
		HelpClass.enter();
		//SIP Attack Test ability to prevent Session Tear Down Attack
		SIP_Tear_Down tear = new SIP_Tear_Down
		(aut_can.auth_result_udp, aut_can.auth_result_tcp,
				aut_bye.auth_result_udp, aut_bye.auth_result_tcp);
		HelpClass.enter();
		//SIP Attack Test ability to prevent DoS attack
		SIP_SPAM spam = new SIP_SPAM();
		HelpClass.enter();
		//SIP Attack Test ability to prevent DoS attack
		SIP_DOS dos = new SIP_DOS();

		//End of Log
		LogWriter.logEnd();
		
		//End of Test
		System.out.println();
		System.out.println("SIP Security Test - DONE!");
		System.out.println("Test Report is at \"SIPTest\\report\".");
	}

}
