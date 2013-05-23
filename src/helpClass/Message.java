/* 
 * Created on 2005-09-12
 */
package helpClass;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dayong
 * @version 2.9
 * 
 * Message.java
 * used to generate SIP message 
 * Usage: see main()
 */

public class Message 
{
	
	public Message(){}
	/**
	 * OPTIONIS message
	 * @param Version
	 * @param TCPorUDP
	 * @param URLAddress
	 * @param ViaAddress
	 * @param Branch
	 * @param FromURL
	 * @param Tag
	 * @param CSeq
	 * @param Call_ID
	 * @param Max_Forwards
	 * @param DesFile
	 * @return
	 */
	public String option
	(
		String Version, String TCPorUDP, String URLAddress, String ViaAddress, String Branch,
		String FromURL, String Tag, String CSeq, String Call_ID, String Max_Forwards, 
		String DesFile
	)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
		String date = dateFormat.format(new Date());
		String message = "";
			//Generate By Each Line of Message
			if(URLAddress != null && Version != null) 
				message += "OPTIONS sip:test@" + URLAddress + " SIP/" + Version + "\n";
			else HelpClass.debug("URLAddress = null or Version = null");
			
			if(TCPorUDP != null && ViaAddress != null && Branch != null) 
				message += "Via: SIP/" + Version + "/" + TCPorUDP + " " + ViaAddress + ":" + 
				HelpClass.ua_listen_port + ";branch=" + Branch + "\n";		 
				message += "To: test <sip:test@" + URLAddress + ">" +"\n";
			if(FromURL != null) 
				message += "From: " + FromURL.substring(0,FromURL.indexOf("@")) + 
				" <SIP:" + FromURL + ">" + ";tag=" + Tag + "\n";
			if(CSeq != null)
				message += "CSeq: " + CSeq + " OPTIONS" + "\n";
			if(Call_ID != null)
				message += "Call-ID: " + Call_ID + FromURL.substring(FromURL.indexOf("@")) + "\n";
			if(Max_Forwards != null) 
				message += "Max-Forwards: " + Max_Forwards + "\n"
				+ "Date: " + date + "\n"
				+ "Contact: " + FromURL.substring(0,FromURL.indexOf("@")) + " <sip:" + FromURL.substring(0,FromURL.indexOf("@")) 
			    + "@" + ViaAddress + ":" + HelpClass.ua_listen_port + ">" + "\n";	
				message += "Content-Type: application/sdp\n";
				message += "Content-Length: 0";
		try
		{
			FileWriter out = new FileWriter(new File(DesFile));
			out.write(message);
			out.close();
		}
		catch(IOException ioe)
		{
			 ioe.printStackTrace();
		}
		return message;
	}
	/**
	 * Generate REGISTER Message and write to file
	 * CSeq == null: automatic increase call sequence number
	 * CSeq != null: use specified call sequence number
	 * 
	 * @param Version
	 * @param TCPorUDP
	 * @param URLAddress
	 * @param ViaAddress
	 * @param Branch
	 * @param ToURL
	 * @param FromURL
	 * @param Tag
	 * @param CSeq
	 * @param Call_ID
	 * @param Max_Forwards
	 * @param Expires
	 * @param Subject
	 * @param Content_Type
	 * @param Content_Length
	 * @param DesFile
	 * @return
	 */
	public String register
	(
		String Version, String TCPorUDP, String URLAddress, String ViaAddress, String Branch,
		String ToURL, String FromURL, String Tag, String CSeq, String Call_ID, String Max_Forwards, 
		String Expires, String Subject, String Content_Type, String Content_Length, String DesFile
	)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
		String date = dateFormat.format(new Date());
		String time = date.substring(date.lastIndexOf(":")-3,date.lastIndexOf(":")+3).
		replaceAll(":", "").trim() + HelpClass.num_register;
		HelpClass.num_register ++;
		
		String message = "";
			//Generate By Each Line of Message
			if(Version == "syntax1") 
				message += "REG  ISTER sip:" + URLAddress + " SIP/" + "2.0" + "\n";
			
			if(Version == "syntax2") 
				message += "REGISTERS sip:" + URLAddress + " SIP/" + "2.0" + "\n";
			
			if(Version == "syntax3") 
				message += "REGISTER sip:" + URLAddress + " SIP/" + "2.0" + "\n" + "Undefined" 
				+ "\n";
			
			if(Version == "syntax4") 
				message += "REGISTER sip:" + URLAddress + " SIP/" + "3.0" + "\n";
			
			if(Version == "TLS") 
				message += "REGISTER sips:" + URLAddress + " SIP/" + "2.0" + "\n";
			
			if(URLAddress != null && Version != null && Version != "syntax1" && 
			Version != "syntax2" && Version != "syntax3" && Version != "syntax4"
			&& Version != "TLS") 
				message += "REGISTER sip:" + URLAddress + " SIP/" + Version + "\n";
			else
				Version = "2.0";
			
			if(TCPorUDP != null && ViaAddress != null && Branch != null) 
				message += "Via: SIP/" + Version + "/" + TCPorUDP + " " + ViaAddress + ":" + 
				HelpClass.ua_listen_port + ";branch=" + Branch + "\n";
			
			if(ToURL != null) 
				message += "To: " + ToURL.substring(0,ToURL.indexOf("@")) + "<SIP:" + ToURL + ">" +"\n";
			
			if(FromURL != null) 
				message += "From: " + FromURL.substring(0,FromURL.indexOf("@")) + 
				"<SIP:" + FromURL + ">" + ";tag=" + Tag + "\n";
			
			if(CSeq != null)
				message += "CSeq: " + CSeq + " REGISTER" + "\n";
				//time as CSeq to prevent out-of-order error
			if(CSeq == null)
				message += "CSeq: " + time + " REGISTER" + "\n";
			
			if(Call_ID != null)
				message += "Call-ID: " + Call_ID + FromURL.substring(FromURL.indexOf("@")) + "\n";
			
			if(Max_Forwards != null) 
				message += "Max-Forwards: " + Max_Forwards + "\n"
				+ "Date: " + date + "\n"
				//+ "Contact: " + FromURL.substring(0,FromURL.indexOf("@")) + "<sip:" + FromURL + ":"   
				+ "Contact: " + FromURL.substring(0,FromURL.indexOf("@")) + "<sip:" + 
				FromURL.substring(0,FromURL.indexOf("@")+1) + ViaAddress + ":"   
				
				+ HelpClass.ua_listen_port + ">" + "\n";
			
			if(Expires != null)
				message += "Expires: " + Expires + "\n";
			
			if(Subject != null)
				message += "Subject: " + Subject + "\n";
			
			if(Content_Type != null)
				message += "Content-Type: " + Content_Type + "\n";
			
			if(Content_Length != null)
				message += "Content-Length: " + Content_Length;
		try
		{
			FileWriter out = new FileWriter(new File(DesFile));
			out.write(message);
			out.close();
		}
		catch(IOException ioe)
		{
			 ioe.printStackTrace();
		}
		return message;
	}
	
	/**
	 * Generate INVITE Message and write to file
	 * CSeq == null: automatic increase call sequence number
	 * CSeq != null: use specified call sequence number
	 * 
	 * @param Version
	 * @param TCPorUDP
	 * @param URLAddress
	 * @param ViaAddress
	 * @param Branch
	 * @param ToURL
	 * @param FromURL
	 * @param Tag
	 * @param CSeq
	 * @param Supported
	 * @param Expires
	 * @param Call_ID
	 * @param Allow
	 * @param Max_Forwards
	 * @param UserAgent
	 * @param Content_Type
	 * @param Content_Length
	 * @param Route
	 * @param SDPContent
	 * @param DesFile
	 * @return
	 */
	public String invite
	(
		String Version, String TCPorUDP, String URLAddress, String ViaAddress, String Branch,
		String ToURL, String FromURL, String Tag, String CSeq, String Supported, String Expires, 
		String Call_ID, String Allow, String Max_Forwards, String UserAgent, String Content_Type,
		String Content_Length, String ContactURL, String ContactAddress, String Route, 
		String SDPContent, String DesFile
	)
	{ 
		String message = "";
		String sdp = "";
		sdp = "\n" + "v=0\n"  
		    + "o=" + FromURL.substring(0,FromURL.indexOf("@")) + " 51684286 0 IN IP4 "
		    + ViaAddress + "\n"
		    + "s=" + UserAgent + "\n"
		    + "c=IN IP4 " + ViaAddress + "\n"
		    + "t=0 0\n"
		    + "m=audio 4518 RTP/AVP 4 0 8\n"
		    + "a=rtpmap:4 G723/8000\n"
		    + "a=rtpmap:0 PCMU/8000\n"
		    + "a=rtpmap:8 PCMA/8000\n"
		    + "a=ptime:30\n"
		    + "m=video 4526 RTP/AVP 34\n"
		    + "a=rtpmap:34 H263/90000\n"
		    + "a=recvonly\n"
		    + "m=application 4518\n" ;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
		String date = dateFormat.format(new Date());
		String time = date.substring(date.lastIndexOf(":")-3,date.lastIndexOf(":")+3).
		replaceAll(":", "").trim() + HelpClass.num_invite;
		HelpClass.num_invite ++;
		
		//Generate By Each Line of Message
		if(URLAddress != null && Version != null) 
			message += "INVITE sip:" + URLAddress + " SIP/" + Version + "\n";
		else HelpClass.debug("URLAddress = null or Version = null");	
		
		if(TCPorUDP != null && ViaAddress != null && Branch != null) 
			message += "Via: SIP/" + Version + "/" + TCPorUDP + " " + ViaAddress + ":" + 
			HelpClass.ua_listen_port + ";branch=" + Branch + "\n";
		
		if(Max_Forwards != null) 
			message += "Max-Forwards: " + Max_Forwards + "\n";
		
		if(ToURL != null) 
			message += "To: " + ToURL.substring(0,ToURL.indexOf("@")) + " <sip:" + ToURL + ">" +"\n";
			//message += "To: sip:" + ToURL + "\n";
		if(FromURL != null) 
			message += "From: " + FromURL.substring(0,FromURL.indexOf("@")) + 
			" <sip:" + FromURL + ">" + ";tag=" + Tag + "\n";				
		
		if(Call_ID != null)
			message += "Call-ID: " + Call_ID + FromURL.substring(FromURL.indexOf("@")) + "\n";
		
		if(CSeq != null)
			message += "CSeq: " + CSeq + " INVITE" + "\n";
		if(CSeq == null)
			message += "CSeq: " + time + " INVITE" + "\n";
		
		if(Supported != null)
			message += "Supported: " + Supported + "\n";
		
		if(Expires != null)
			message += "Session-Expires: " + Expires + "\n";
		
		if(Allow != null)
			message += "Allow: " + Allow + "\n";
		
		if(Content_Type != null)
			message += "Content-Type: " + Content_Type + "\n";
		
		if(ContactURL != null)
			message += "Contact: " + ContactURL.substring(0,ContactURL.indexOf("@")) + 
			" <sip:" + ContactURL.substring(0,ContactURL.indexOf("@")) 
		    + "@" + ContactAddress + ":" + HelpClass.ua_listen_port + ">" + "\n";		
		    //+ ":" + HelpClass.ua_listen_port + ">" + "\n";	
		
		if(UserAgent != null)
			message += "User-Agent: " + UserAgent + "\n";
				
		if(Route != null)
			message += "Route: <sip:" + Route + ">" + "\n";
		
		if(Content_Length != null)
			message += "Content-Length: " + sdp.length() + "\n";
		
		if(SDPContent != null)
			message += sdp;

		try
		{
			FileWriter out = new FileWriter(new File(DesFile));
			out.write(message);
			out.close();
		}
		catch(IOException ioe)
		{
			 ioe.printStackTrace();
		}
		return message;
	}
	
	/**
	 * 
	 * @param Version
	 * @param TCPorUDP
	 * @param ViaAddress
	 * @param Branch
	 * @param ToURL
	 * @param FromURL
	 * @param Tag
	 * @param CSeq
	 * @param Supported
	 * @param Call_ID
	 * @param Max_Forwards
	 * @param UserAgent
	 * @param DesFile
	 * @return
	 */
	public String cancel
	(
		String Version, String TCPorUDP, String ViaAddress, String Branch, String ToURL, 
		String FromURL, String Tag, String CSeq, String Supported, String Call_ID, 
		String Max_Forwards, String UserAgent, String DesFile
	)
	{
		//SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
		//String date = dateFormat.format(new Date());
		String message = "";
			//Generate By Each Line of Message
			message += "CANCEL sip:" + ToURL + " SIP/" + Version + "\n";
			
			if(TCPorUDP != null && ViaAddress != null && Branch != null) 
				message += "Via: SIP/" + Version + "/" + TCPorUDP + " " + ViaAddress + ":" + 
				HelpClass.ua_listen_port + ";branch=" + Branch + "\n";
			if(Max_Forwards != null) 
				message += "Max-Forwards: " + Max_Forwards + "\n";
				message += "Content-Length: 0" + "\n";
			
			if(ToURL != null) 
				//message += "To: " + ToURL.substring(0,ToURL.indexOf("@")) + " <sip:" + ToURL + ">" +"\n";
				message += "To: sip:" + ToURL + "\n";
			if(FromURL != null) 
				message += "From: " + FromURL.substring(0,FromURL.indexOf("@")) + 
				" <sip:" + FromURL + ">" + ";tag=" + Tag + "\n";
			
			if(Call_ID != null)
				message += "Call-ID: " + Call_ID + FromURL.substring(FromURL.indexOf("@")) + "\n";
			
			if(CSeq != null)
				message += "CSeq: " + CSeq + " CANCEL" + "\n";
			
			if(Supported != null)
				message += "Supported: " + Supported + "\n";
			
			if(UserAgent != null)
				message += "User-Agent: " + UserAgent;

		try
		{
			FileWriter out = new FileWriter(new File(DesFile));
			out.write(message);
			out.close();
		}
		catch(IOException ioe)
		{
			 ioe.printStackTrace();
		}
		return message;
	}
	
	
	public String bye
	(
		String Version, String TCPorUDP, String URLAddress, String ViaAddress, String Branch,
		String ToURL, String FromURL, String Tag, String CSeq, String Call_ID, String Max_Forwards, 
		String Expires, String Route, String DesFile
	)
	{
		String message = "";
			//Generate By Each Line of Message
			if(URLAddress != null && Version != null) 
				message += "BYE sip:" + URLAddress + " SIP/" + Version + "\n";
			else HelpClass.debug("URLAddress = null or Version = null");
			
			if(TCPorUDP != null && ViaAddress != null && Branch != null) 
				message += "Via: SIP/" + Version + "/" + TCPorUDP + " " + ViaAddress + ":" + 
				HelpClass.ua_listen_port + ";branch=" + Branch + "\n";
			
			if(Max_Forwards != null) 
				message += "Max-Forwards: " + Max_Forwards + "\n";
			
			if(ToURL != null) 
				message += "To: " + ToURL.substring(0,ToURL.indexOf("@")) + "<SIP:" + ToURL + ">" +"\n";
			
			if(FromURL != null) 
				message += "From: " + FromURL.substring(0,FromURL.indexOf("@")) + 
				"<SIP:" + FromURL + ">" + ";tag=" + Tag + "\n";
			
			if(CSeq != null)
				message += "CSeq: " + CSeq + " BYE" + "\n";
			
			if(Call_ID != null)
				message += "Call-ID: " + Call_ID + FromURL.substring(FromURL.indexOf("@")) + "\n"
				+ "Contact: " + FromURL.substring(0,FromURL.indexOf("@")) + "<sip:" + FromURL + ":"   
				+ HelpClass.ua_listen_port + ">" + "\n";
			
			if(Expires != null)
				message += "Session-Expires: " + Expires + "\n";
				message += "Content-Type: application/sdp" + "\n";
			
			if(Route != null)
				message += "Route: <sip:" + Route + ">" + "\n";
				message += "Content-Length: 0";
		try
		{
			FileWriter out = new FileWriter(new File(DesFile));
			out.write(message);
			out.close();
		}
		catch(IOException ioe)
		{
			 ioe.printStackTrace();
		}
		return message;
	}
	
	public static void main(String[] args)
	{
		Message o = new Message();
		String str = "";
		String str2 = "";
		String str3 = "";
		str = o.register("syntax1","TCP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
				   HelpClass.UA2URL, HelpClass.UA1URL, "1928301774", "87198", "j8dxkzc9oy11Fx2FTtFo", "70",
				   "100", "10100.12", "application/sdp", "0", "TestFile//testMessage1.rtf");
		System.out.println(str);
		System.out.println();
		//UA1 invite UA2
		str2 = o.invite("2.0","UDP",HelpClass.ProxyAddress,HelpClass.UA1Address,"E5rSD5sq7Q9sHv7",
				   HelpClass.UA2URL, HelpClass.UA1URL, "1928301774", "87199", null, "3600", "j8dxkzc9oy11Fx2FTtFo",
				   " INVITE,ACK,OPTIONS,BYE,CANCEL,REGISTER,REFER,SUBSCRIBE,NOTIFY,MESSAGE", "70",
				   "SCSC/v1.2.1 MxSF/v3.2.6.26", "application/sdp", "0", HelpClass.UA1URL, HelpClass.UA1Address, 
				   HelpClass.UA2URL, "sdp", "TestFile//testMessage2.rtf");	
		System.out.println(str2);
		//Option
		str3 = o.option("2.0", "UDP", HelpClass.ProxyAddress, HelpClass.UA1Address, "E5rSD5sq7Q9sHv7", HelpClass.UA1URL, 
				"1928301774", "5050", "j8dxkzc9oy11Fx2option", "70", "TestFile//testMessage3.rtf");
		System.out.println(str3);
		System.out.println();
		str = o.cancel("2.0", "UDP", HelpClass.UA1Address, "E5rSD5sq7Q9sHv0", HelpClass.UA2URL, HelpClass.UA1URL, "1928301794", "2311", "timer", 
				"j8dxkzc9oy11Fx2FCancel", "70", "SCSC/v1.2.1 MxSF/v3.2.6.26", "TestFile//testMessage4.rtf");
		System.out.println(str);
		
		str2 = o.bye("2.0", "UDP", HelpClass.ProxyAddress, HelpClass.UA1Address,"E5rSD5sq7Q9sHv7", 
				HelpClass.UA2URL, HelpClass.UA1URL, "1928301774", "31231", "j8dxkzc9oy11Fx2FTtFo", "70",
				"3600", HelpClass.UA2URL, "TestFile//testMessage5.rtf");
		System.out.println(str2);
		System.out.println();
	}
	
}
