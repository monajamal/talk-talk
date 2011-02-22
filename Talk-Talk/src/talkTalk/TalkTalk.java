package talkTalk;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Hashtable;
import java.util.Vector;

import utils.SaisieControle;

public class TalkTalk {
	
	public static String pseudo;
	public static String hostname;
	public static String image;
	public static String statut;
	public static Vector<String> friend;
	public static Hashtable<String,Contact> friends;
	
	public static void main(String[] args) throws UnknownHostException {
		if (args.length==1) pseudo=args[0]; else pseudo="namelessTalk";

		
		/*Enumeration<NetworkInterface> interfaces = null;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {  // carte reseau trouvee
				NetworkInterface interfaceN = (NetworkInterface)interfaces.nextElement(); 
				Enumeration<InetAddress> ienum = interfaceN.getInetAddresses();
				while (ienum.hasMoreElements()) {  // retourne l adresse IPv4 et IPv6
					InetAddress ia = ienum.nextElement();
					String adress = ia.getHostAddress().toString();
					System.out.println("Adresse : "+adress);
					if( adress.length() < 16){          //On s'assure ainsi que l'adresse IP est bien IPv4
						if(adress.startsWith("127")){  //Ce n'est pas l'adresse IP Local' 
							System.out.println("127 " +ia.getHostAddress());
						} else {
							hostname = ia.getHostAddress();
						}
					}
					
				} 
			}
			
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		

		InetAddress addr = InetAddress.getLocalHost();
		hostname = new String(addr.getHostAddress());
		
		image=null;
		statut="dispo";
		friend = new Vector<String>();
		
		System.out.println("Je suis "+pseudo+" et je suis connecté sur ["+hostname+"] !");
		System.out.println("Mes amis sont "+print(friend));
		
		friends = Contact.parseContact();
		//Contact.saveContact(friends);
		
		// équivalent du processus rmiregistry
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// Lancement du serveur
		try {
			DistantImpl objLocal = new DistantImpl();
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
			} else if (saisie.contains("/add")) {
				saisie=saisie.replaceAll("/add ", "");
				friend.add(saisie);
				System.out.println("Mes amis sont "+print(friend));
			} else if (saisie.contains("/contact")) {
				System.out.println("recherche du contact....echec !");
			} else if (saisie.contains("/image")) {
				System.out.println("pas implémenté !");
			} else if (saisie.contains("/file")) {
				System.out.println("pas implémenté !");
			} else if (saisie.contains("/send")) {
				saisie=saisie.replaceAll("/send ", "");
				Envoi env = new Envoi(saisie.substring(0, saisie.indexOf(' ')),saisie.substring(saisie.indexOf(' ')));
				env.start();
				/*Distant b;
				try {
					System.out.println("moi: "+saisie);
					for (int i=0;i<friend.size();i++) {
						b = (Distant)Naming.lookup("rmi://"+friend.get(i)+"/TalkTalk");
						b.sendMsg(pseudo,saisie);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}*/
			} else {
				
			}
		}
	}
	public static String print(Vector<String> friend) {
		String res="{ ";
		for (int i=0;i<friend.size();i++) {
			if (i!=0) res+=", ";
			res+=friend.get(i);
		}
		res+=" }";
		return res;
	}
}
//Debug.start(args);
//Debug.addLog("*** START ***");