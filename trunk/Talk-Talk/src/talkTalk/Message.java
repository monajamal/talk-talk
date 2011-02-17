package talkTalk;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Message extends Remote {
	public void sendMsg(String m) throws RemoteException;
	public Vector<String> searchContact(String pseudo) throws RemoteException;
}
