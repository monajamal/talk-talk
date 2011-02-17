package talkTalk;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import utils.SaisieControle;

public class TalkTalk {
	
	public static String pseudo;
	public static String hostname;
	public static String friend;
	
	public static void main(String[] args) throws UnknownHostException {
		if (args.length !=2) {
			System.out.println("java TalkTalk <pseudo> <friend>");
			System.exit(0);
		}
		/*
			pseudo=args[0];
		} else pseudo="namelessTalk";
		*/
		friend=args[1];
		pseudo=args[0];
		
		InetAddress addr = InetAddress.getLocalHost();
		hostname = new String(addr.getHostName());
		
		
		System.out.println("Je suis "+pseudo+" et je suis connecté sur ["+hostname+"] !");
		System.out.println("Mon amis est sur "+friend);
		
		// équivalent du processus rmiregistry
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// Lancement du serveur
		try {
			MessageImpl objLocal = new MessageImpl();
			Naming.rebind("rmi://"+hostname+":1099/TalkTalk", objLocal);
			System.out.println("SERVEUR ["+hostname+"] : Server ready !");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		
		String saisie = "";
		while (saisie!=null) {
			saisie = SaisieControle.saisieString("Commande ?");
			if (saisie.contains("/quit")) {
				saisie=null;
				System.out.println("SERVEUR ["+hostname+"] : Server down !");
				System.exit(0);
			} else if (saisie.contains("/contact")) {
				System.out.println("recherche du contact....echec !");
			} else if (saisie.contains("/image")) {
				System.out.println("pas implémenté !");
			} else if (saisie.contains("/file")) {
				System.out.println("pas implémenté !");
			} else if (saisie.contains("/send")) {
				saisie=saisie.replaceAll("/send ", "");
				Message b;
				try {
					b = (Message)Naming.lookup("rmi://"+friend+"/TalkTalk");
					System.out.println("moi: "+saisie);
					b.sendMsg(saisie);
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			} else {
				
			}
		}
	}
}

//Debug.start(args);
//Debug.addLog("*** START ***");