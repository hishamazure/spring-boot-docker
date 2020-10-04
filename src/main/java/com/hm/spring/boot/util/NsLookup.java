package com.hm.spring.boot.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NsLookup {

	public static void resolve(String host)
	  {
	    try
	    {
	      InetAddress inetAddress = InetAddress.getByName(host);

	      System.out.println("Host: " +	 inetAddress.getHostName() + "   IP Address: " +     inetAddress.getHostAddress());
	    }
	    catch (UnknownHostException e)
	    {
	      e.printStackTrace();
	    }
	  }

}
