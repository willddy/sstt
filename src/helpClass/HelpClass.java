/* 
 * Created on 2005-09-12
 */
package helpClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Dayong
 * @version 2.9
 * 
 * HelpClass.java
 * Provide data accessed by all classes
 */
public class HelpClass  
{
	//Flag for opening dubug output
	public static boolean debug_flag = false;
	//Generate single log
	public static boolean single_log__flag = true;
	//true: use message automatically generated; false: use custumize message
	//When there is authentication, we will use customize message capture 
	//authentication info. from etheral for example
	public static boolean messaeg_flag = true;	
	
	//true - UDP/TCP; false - Not UDP/TCP
	public static boolean test_udp_flag = true; 	
	public static boolean test_tcp_flag = true; 	
	//Timeout
	public static int time_out = 1000;
	//SIP proxy listen and send port
	public static int server_listen_port = 5060;
	//SIP UA listen and send port
	public static int ua_listen_port = 5062;
	//SIP elements address
	public static String ProxyAddress = "129.173.67.67";
	public static String ProxyURL = "proxy@cs.dal.ca";
	public static String UA1Address = "192.168.0.2"; //SIP Security Test Tool Address
	public static String UA1URL = "ddu3@cs.dal.ca";  //SSTT URL
	public static String UA2Address = "129.173.66.246";
	public static String UA2URL = "ddu@cs.dal.ca";
	//other VUA4&5
	public static String UA4Address = "192.168.1.4";
	public static String UA4URL = "ddu4@cs.dal.ca";
	public static String UA5Address = "129.173.67.77";
	public static String UA5URL = "ddu5@cs.dal.ca";
	
	//Number counter for out-of-order error
	public static int num_register = 0;
	public static int num_invite = 0;
	
	//Number of flooding for DoS and Spam attack
	public static int num_flood_invite = 5;
	public static int num_flood_register = 5;
	public static int num_flood_spam = 5;
	
	//Address pool for Spam test
	public static String[] address_pool = 
	{
		"129.173.1.76","129.173.1.7","128.173.67.76","128.17.67.76",
		"192.168.67.26"
	};
	/*
	 * Test Case File path for SIPI001:TCP(SIPI001.txt) or SIPI002:UDP(SIPI002.txt)
	 * Test Case File path for SIPV001:TCP auth Registration (SIPI001.txt) 
	 * or SIPI002:UDP auth Registration (SIPI002.txt) 
	 */
	//register virtual UA1
	public static String udp_test_file = "TestFile//SIPI001.rtf";
	public static String tcp_test_file = "TestFile//SIPI002.rtf";
	//register virtual UA2
	public static String udp_test_file2 = "TestFile//SIPI003.rtf";
	public static String tcp_test_file2 = "TestFile//SIPI004.rtf";
	
	//Test Case File path - for SIPI010 series test - Proxy State
	public static String pstate_udp_test_file = "TestFile//SIPI011.rtf";
	public static String pstate_tcp_test_file = "TestFile//SIPI012.rtf";
	
	//Test Case File path - for SIPV010 series test - Authentication INVITE
	public static String aut_inv_udp_test_file = "TestFile//SIPV011.rtf";
	public static String aut_inv_tcp_test_file = "TestFile//SIPV012.rtf";
	
	//Test Case File path - for SIPV020 series test - Authentication CANCEL
	public static String aut_can_udp_test_file = "TestFile//SIPV021.rtf";
	public static String aut_can_tcp_test_file = "TestFile//SIPV022.rtf";
	
	//Test Case File path - for SIPV030 series test - Authentication BYE
	public static String aut_bye_udp_test_file = "TestFile//SIPV031.rtf";
	public static String aut_bye_tcp_test_file = "TestFile//SIPV032.rtf";
	
	//Test Case File path - for SIPV040 series test - TLS test on TCP only
	public static String tls_tcp_test_file = "TestFile//SIPV042.rtf";	
	
	//Test Case File path - for SIPV050 series test - MIME test
	public static String mime_udp_test_file = "TestFile//SIPV051.rtf";
	public static String mime_tcp_test_file = "TestFile//SIPV052.rtf";	
	
	//Test Case File path - for SIPV060 series test - Header campatiblity test
	public static String header_space_udp_test_file = "TestFile//SIPV061.rtf";
	public static String header_newline_udp_test_file = "TestFile//SIPV063.rtf";
	public static String header_space_tcp_test_file = "TestFile//SIPV062.rtf";
	public static String header_newline_tcp_test_file = "TestFile//SIPV064.rtf";	
	
	//Test Case File path - for SIPV070 series test - Sytax Error Recognization Test
		//whitespace in keywords
	public static String sytax_udp_test_file_1 = "TestFile//SIPV071.rtf"; 
	public static String sytax_tcp_test_file_1 = "TestFile//SIPV072.rtf";
		//wrong keywords
	public static String sytax_udp_test_file_2 = "TestFile//SIPV073.rtf";
	public static String sytax_tcp_test_file_2 = "TestFile//SIPV074.rtf";
		//use extra keywords not defined
	public static String sytax_udp_test_file_3 = "TestFile//SIPV075.rtf";
	public static String sytax_tcp_test_file_3 = "TestFile//SIPV076.rtf";
		//wrong content in message such as "sip3.0"
	public static String sytax_tcp_test_file_4 = "TestFile//SIPV077.rtf";
	public static String sytax_udp_test_file_4 = "TestFile//SIPV078.rtf";
	
	//Test Case File path - for SIPA00X series test - Registration Hijacking
	//s1-5 for simulation test
	public static String reg_hij_udp_test_file_s1 = "TestFile//SIPA001//registerA.rtf";
	public static String reg_hij_udp_test_file_s2 = "TestFile//SIPA001//registerB.rtf";
	public static String reg_hij_udp_test_file_s3 = "TestFile//SIPA001//AinviteB.rtf";
	public static String reg_hij_udp_test_file_s4 = "TestFile//SIPA001//deregisterB.rtf";
	public static String reg_hij_tcp_test_file_s1 = "TestFile//SIPA002//registerA.rtf";
	public static String reg_hij_tcp_test_file_s2 = "TestFile//SIPA002//registerB.rtf";
	public static String reg_hij_tcp_test_file_s3 = "TestFile//SIPA002//AinviteB.rtf";
	public static String reg_hij_tcp_test_file_s4 = "TestFile//SIPA002//deregisterB.rtf";
	
	//Test Case File path - for SIPA01X series test - Replay Attack
	public static String replay_udp_test_file_s1 = "TestFile//SIPA011//registerA.rtf";
	public static String replay_udp_test_file_s2 = "TestFile//SIPA011//registerB.rtf";
	public static String replay_udp_test_file_s3 = "TestFile//SIPA011//modifiedInvitetoB.rtf";
	public static String replay_tcp_test_file_s1 = "TestFile//SIPA012//registerA.rtf";
	public static String replay_tcp_test_file_s2 = "TestFile//SIPA012//registerB.rtf";
	public static String replay_tcp_test_file_s3 = "TestFile//SIPA012//modifiedInvitetoB.rtf";
	
	//Test Case File path - for SIPA02X series test - INVITE Spoofing test
	public static String inv_spo_udp_test_file_s1 = "TestFile//SIPA021//registerA.rtf";
	public static String inv_spo_udp_test_file_s2 = "TestFile//SIPA021//registerB.rtf";
	public static String inv_spo_udp_test_file_s3 = "TestFile//SIPA021//modifiedInvitetoB.rtf";
	public static String inv_spo_tcp_test_file_s1 = "TestFile//SIPA022//registerA.rtf";
	public static String inv_spo_tcp_test_file_s2 = "TestFile//SIPA022//registerB.rtf";
	public static String inv_spo_tcp_test_file_s3 = "TestFile//SIPA022//modifiedInvitetoB.rtf";
		
	//Test Case File path - for SIPA03X series test - Tear Down Session attack
	public static String tear_udp_test_file = "TestFile//SIPA031//SIPA031.rtf";
	public static String tear_tcp_test_file = "TestFile//SIPA032//SIPA032.rtf";
	
	//Test Case File path - for SIPA04X series test - Media Encryption Test (Do not support)
	public static String srtp_udp_test_file = "TestFile//SIPA041.rtf";
	public static String srtp_tcp_test_file = "TestFile//SIPA042.rtf";
	
	//Test Case File path - for SIPA05X series test - SIP Spam
	public static String spam_udp_test_file = "TestFile//SIPA051//SIPA051.rtf";
	public static String spam_tcp_test_file = "TestFile//SIPA052//SIPA052.rtf";
	
	//Test Case File path - for SIPA06X series test - DoS attack
	public static String dos_udp_test_file_s1 = "TestFile//SIPA061//inviteFlooding.rtf";
	public static String dos_tcp_test_file_s1 = "TestFile//SIPA062//inviteFlooding.rtf";
	public static String dos_udp_test_file_s2 = "TestFile//SIPA063//registerFlooding.rtf";
	public static String dos_tcp_test_file_s2 = "TestFile//SIPA064//registerFlooding.rtf";
	

	
	/**********************
	 ** Static Functions
	 *********************/
	/*
     * Enter for a new line; pause for application
     */
    public static void enter()
    {
    	if (HelpClass.debug_flag)
    	{
    		System.out.print("Press <Enter> to continue next test");
    		try
        	{
        		BufferedReader enter = new BufferedReader(new InputStreamReader(System.in));
        		enter.readLine();
        	}
        	catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
    	}  	
    }
    /*
     * Debug out put
     */
    public static void debug(String statement)
    {       
    	if(HelpClass.debug_flag)
        {
            System.out.println(statement);
        }      
    }

}
