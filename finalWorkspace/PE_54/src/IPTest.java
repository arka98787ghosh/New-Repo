 import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Simple Java program to find IP Address of localhost. This program uses
 * InetAddress from java.net package to find IP address.
 *
 * @author Javin Paul
 */
public class IPTest {
 
 
    public static String getIp() throws UnknownHostException, SocketException {
    	/*
        InetAddress addr = InetAddress.getLocalHost();
     
        //Getting IPAddress of localhost - getHostAddress return IP Address
        // in textual format
        String ipAddress = addr.getHostAddress();
        byte[] arr = addr.getAddress();
        StringBuilder ip = new StringBuilder();
        for(byte b : arr) {
            // The & here makes b convert like an unsigned byte - so, 255 instead of -1.
            ip.append(b & 0xFF).append('.');
        }
        ip.setLength(ip.length() - 1); // To remove the last '.'
        System.out.println(ip.toString());
        //Hostname
        String hostname = addr.getHostName();
        System.out.println("Name of hostname : " + hostname);
     	*/
    	//String Ip = null;
    	String Ip = "";
    	Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    	while (interfaces.hasMoreElements()){
    	    NetworkInterface current = interfaces.nextElement();
    	    //System.out.println(current);
    	    if (!current.isUp() || current.isLoopback() || current.isVirtual()) continue;
    	    Enumeration<InetAddress> addresses = current.getInetAddresses();
    	    while (addresses.hasMoreElements()){
    	        InetAddress current_addr = addresses.nextElement();
    	        /*
    	        if (current_addr.isLoopbackAddress()) continue;
    	        System.out.println(current_addr.getHostAddress());
    	        */
    	        if (current_addr instanceof Inet4Address){
    	        	// System.out.println(.substring(0));
    	        	Ip = current_addr.getHostAddress().toString();
    	        }
    	        	 
    	    }
    	}
    	return Ip;
    }
    /*
    public static void main(String args[]) throws UnknownHostException, SocketException{
    	System.out.println(IPTest.getIp());
    }
 	*/
}