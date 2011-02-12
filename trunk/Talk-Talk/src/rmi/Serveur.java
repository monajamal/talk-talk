package rmi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class Serveur {
	public static void main(String[] args) {
		Thread rmiregistry = new Thread() {
			public void run() {
				System.out.println("EXECUTE : rmiregistry");
				String[] commande = {"rmiregistry"};
				Runtime runtime = Runtime.getRuntime();
				Process process;
				try {
					process = runtime.exec(commande,null,new File("./bin/"));
					String line;
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					while((line = reader.readLine()) != null) {
						System.out.println(line);
					}
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		TimerTask startServeur = new TimerTask() {
			@Override
			public void run() {
				System.out.println("EXECUTE : java rmi.Serveur");
				try {
					MessageImpl objLocal = new MessageImpl();
					Naming.rebind("rmi://localhost:1099/Message", objLocal);
					System.out.println("\tSERVEUR : Server ready !");
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		};
		TimerTask stopServeur = new TimerTask() {
			@Override
			public void run() {
				System.out.println("\tSERVEUR : Server down !");
				System.exit(0);
			}
		};
		Timer t = new Timer();
		
		// Je lance le thread de rmiregistry
		rmiregistry.start();
		// 1 sec après, je lance le serveur
		t.schedule(startServeur, 100);
		// 3 secondes après je stop le serveur
		t.schedule(stopServeur, 5000);
	}
}