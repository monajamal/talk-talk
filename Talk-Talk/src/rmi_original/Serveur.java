package rmi_original;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Serveur {
	
	public static void main(String[] args) {
		try {
			MessageImpl objLocal = new MessageImpl();
			Naming.rebind("rmi://localhost:1099/Message", objLocal);
			System.out.println("Server ready !");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
