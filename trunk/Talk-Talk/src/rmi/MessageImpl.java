package rmi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class MessageImpl extends UnicastRemoteObject implements Message {
	public MessageImpl() throws RemoteException {
		super();
	}

	@Override
	public String messageDistant() throws RemoteException {
		InetAddress addr;
		String hostName = null;
		try {
			addr = InetAddress.getLocalHost();
			hostName = new String(addr.getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "Message : Hello World from ["+hostName+"] !";
	}
}