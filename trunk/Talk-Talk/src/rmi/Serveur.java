package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;
import java.util.TimerTask;

public class Serveur {
	public static void main(String[] args) {
		// équivalent du processus rmiregistry
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// Lancement du serveur
		try {
			MessageImpl objLocal = new MessageImpl();
			Naming.rebind("rmi://localhost:1099/Message", objLocal);
			System.out.println("SERVEUR : Server ready !");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// 5 secondes après je stop le serveur
		TimerTask stopServeur = new TimerTask() {
			@Override
			public void run() {
				System.out.println("SERVEUR : Server down !");
				System.exit(0);
			}
		};
		Timer t = new Timer();
		t.schedule(stopServeur, 5000);
	}
}