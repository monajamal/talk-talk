package rmi_original;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class MessageImpl extends UnicastRemoteObject implements Message {
	public MessageImpl() throws RemoteException {
		super();
	}

	@Override
	public String messageDistant() throws RemoteException {
		return "Message : Hello World !";
	}
}
