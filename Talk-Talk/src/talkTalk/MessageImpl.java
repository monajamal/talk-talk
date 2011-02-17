package talkTalk;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

@SuppressWarnings("serial")
public class MessageImpl extends UnicastRemoteObject implements Message {
	public MessageImpl() throws RemoteException {
		super();
	}

	@Override
	public void sendMsg(String m) throws RemoteException {
		InetAddress addr;
		String hostName = null;
		try {
			addr = InetAddress.getLocalHost();
			hostName = new String(addr.getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println(hostName+":"+m);
	}
	@Override
	public Vector<String> searchContact(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}