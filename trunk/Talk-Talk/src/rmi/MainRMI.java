package rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;
import java.util.TimerTask;

public class MainRMI {
	
	public static void main(String[] args) throws UnknownHostException {
		final InetAddress addr = InetAddress.getLocalHost();
		final String hostName = new String(addr.getHostName());
		
		System.out.println("*** START sur ["+hostName+"] vers ["+args[0]+"] ***");
		// équivalent du processus rmiregistry
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// Lancement du serveur
		try {
			MessageImpl objLocal = new MessageImpl();
			Naming.rebind("rmi://"+hostName+":1099/Message", objLocal);
			System.out.println("SERVEUR ["+hostName+"] : Server ready !");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// 5 secondes après je stop le serveur
		TimerTask stopServeur = new TimerTask() {
			@Override
			public void run() {
				System.out.println("SERVEUR ["+hostName+"] : Server down !");
				System.exit(0);
			}
		};
		
		final String plop = args[0];
		TimerTask client = new TimerTask() {
			@Override
			public void run() {
				Message b;
				try {
					b = (Message)Naming.lookup("rmi://"+plop+"/Message");
					System.out.println("CLIENT ["+plop+"] : "+b.messageDistant());
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
		};
		
		
		Timer t = new Timer();
		t.schedule(client, 2000);
		t.schedule(stopServeur, 5000);
		
	}
}