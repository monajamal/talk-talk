package talkTalk;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Distant extends Remote {
	public boolean sendMsg(String expediteur, String msg) throws RemoteException;
	public Vector<String> searchContact(String pseudo) throws RemoteException;
}
