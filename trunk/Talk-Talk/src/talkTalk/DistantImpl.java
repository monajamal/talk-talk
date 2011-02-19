package talkTalk;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

@SuppressWarnings("serial")
public class DistantImpl extends UnicastRemoteObject implements Distant {
	public DistantImpl() throws RemoteException {
		super();
	}

	@Override
	public boolean sendMsg(String expediteur, String msg) throws RemoteException {
		//InetAddress addr;
		//String hostName = null;
		/*try {
			addr = InetAddress.getLocalHost();
			hostName = new String(addr.getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} */
		System.out.println(expediteur+" : "+msg);
		return true;
	}
	@Override
	public Vector<String> searchContact(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}