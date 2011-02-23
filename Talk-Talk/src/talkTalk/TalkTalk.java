package talkTalk;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import utils.SaisieControle;

public class TalkTalk {
	
	public static String pseudo;
	public static String hostname;
	public static String image;
	public static String statut;
	public static Hashtable<String,Contact> friends;
	
	public static void main(String[] args) throws UnknownHostException {
		if (args.length==1) pseudo=args[0]; else pseudo="namelessTalk";

		//InetAddress addr = InetAddress.getLocalHost();
		//hostname = new String(addr.getHostAddress());
		
		//On cherche une interface correcte
		Enumeration<NetworkInterface> interfaces = null;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {  // carte reseau trouvee
				NetworkInterface interfaceN = (NetworkInterface)interfaces.nextElement(); 
				Enumeration<InetAddress> ienum = interfaceN.getInetAddresses();
				while (ienum.hasMoreElements()) {  // retourne l adresse IPv4 et IPv6
					InetAddress ia = ienum.nextElement();
					String adress = ia.getHostAddress().toString();
					//System.out.println("Adresse : "+adress);
					if( adress.length() < 16){          //On s'assure ainsi que l'adresse IP est bien IPv4
						if(adress.startsWith("127")){  //Ce n'est pas l'adresse IP Local' 
							//System.out.println("127 " +ia.getHostAddress());
						} else {
							hostname = ia.getHostAddress();
						}
					}
					
				} 
			}
			
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

		// Rmi doit ecouter sur la bonne interface
		Properties p = System.getProperties();
		p.setProperty("java.rmi.server.hostname",hostname);
		
		image=null;
		statut="dispo";
		friends = Contact.parseContact();
		
		System.out.println("Je suis "+pseudo+" et je suis connecté sur ["+hostname+"] !");
		System.out.println("Mes amis sont "+print(friends));
		
		
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
				if (saisie.indexOf(' ')!=-1) {
					Contact c = new ContactAddr(saisie.substring(0, saisie.indexOf(' ')),saisie.substring(saisie.indexOf(' ')+1));
					friends.put(saisie.substring(0, saisie.indexOf(' ')),c);
				}
				System.out.println("Mes amis sont "+print(friends));
			} else if (saisie.contains("/contact")) {
				System.out.println("recherche du contact....echec !");
			} else if (saisie.contains("/image")) {
				System.out.println("pas implémenté !");
			} else if (saisie.contains("/file")) {
				System.out.println("pas implémenté !");
			} else if (saisie.contains("/send")) {
				saisie=saisie.replaceAll("/send ", "");
				if (saisie.indexOf(' ')!=-1) {
					Envoi env = new Envoi(saisie.substring(0, saisie.indexOf(' ')),saisie.substring(saisie.indexOf(' ')+1));
					env.start();
				}
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
	public static String print(Hashtable<String,Contact> friend) {
		String res="{ ";
		int i=0;
		for (String nom : friend.keySet())
		{
			if (i!=0) res+=", ";
			res += nom;
			Contact c = friend.get(nom);
			if (c.getType()==Contact.CONTACT_GROUP) {
				res += " : ";
				List<String> list  = c.getMembres();
				for (String membre : list) {
					res += membre+" ";
				}
			}
			i++;
		}
		res+=" }";
		return res;
		/*String res="{ ";
		for (int i=0;i<friend.size();i++) {
			if (i!=0) res+=", ";
			res+=friend.get(i);
		}
		res+=" }";
		return res;*/
	}
}
//Debug.start(args);
//Debug.addLog("*** START ***");