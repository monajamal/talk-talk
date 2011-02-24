package talkTalk;

import java.rmi.RemoteException;
import java.util.Vector;

// extends UnicastRemoteObject
public class DistantImpl implements Distant {
	
	private static final long serialVersionUID = -4154160384111399024L;
	public DistantImpl() throws RemoteException {
		super();
	}

	@Override
	public boolean sendMsg(String expediteur, String msg) throws RemoteException {
		System.out.println(expediteur+" : "+msg);
		return true;
	}
	@Override
	public Vector<String> searchContact(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}