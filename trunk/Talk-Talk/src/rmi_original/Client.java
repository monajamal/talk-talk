package rmi_original;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
	public static void main(String[] args) {
		Message b;
		try {
			b = (Message)Naming.lookup("rmi://"+args[0]+"/Message");
			System.out.println("Client : "+b.messageDistant());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
