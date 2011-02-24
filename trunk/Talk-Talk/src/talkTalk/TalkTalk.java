package talkTalk;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

public class TalkTalk {
	
	public static String pseudo;
	public static String hostname;
	public static String image;
	public static String statut;
	public static Hashtable<String,Contact> friends;
	public final static int portObject = 3000;
	public static Affichage aff;
	public static SaisieConsole saisieconsole;
	
	public static void main(String[] args) throws UnknownHostException {
		if (args.length==1) pseudo=args[0]; else pseudo="namelessTalk";

		//Pour choisir l'interface par defaut
		//InetAddress addr = InetAddress.getLocalHost();
		//hostname = new String(addr.getHostAddress());
		
		//On cherche une interface correcte (plus sûr)
		InetAddress addr = getBestAddr();
		if (addr == null) {
			System.out.println("Etes vous bien connecte quelque part ?");
			System.exit(-1);
		}
		//Nom de la machine
		hostname = addr.getHostName();
		// Rmi doit ecouter sur la bonne interface
		Properties p = System.getProperties();
		p.setProperty("java.rmi.server.hostname",addr.getHostAddress());
		//On remplace par l'adresse ip publique si on veut utiliser du nat
		//p.setProperty("java.rmi.server.hostname","stggw1.homeip.net");
		
		image=null;
		statut="dispo";
		friends = Contact.parseContact();
		//Creation de l'affichage
		aff = new AffichageConsole();
		
		//Contact.saveContact(friends);
		
		//Lancement du registry
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Lancement du serveur
		try {
			DistantImpl objLocal = new DistantImpl();
			Remote stub = UnicastRemoteObject.exportObject(objLocal,portObject);
			Naming.rebind("rmi://"+hostname+"/TalkTalk", stub);
			//Si on veut utiliser du nat, il faut remplacer hostname par l'ip publique
			//Naming.rebind("rmi://stggw1.homeip.net:1099/TalkTalk", objLocal);
			System.out.println("SERVEUR ["+hostname+"] : Server ready !");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//Creation des interfaces de saisie et lancement
		saisieconsole = new SaisieConsole();
		saisieconsole.start();
		try {
			saisieconsole.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Cherche une interface non ipv6 et non localhost
	 * @return l'adresse de l'interface trouve ou null
	 */
	private static InetAddress getBestAddr() {
		Enumeration<NetworkInterface> interfaces = null;
		InetAddress addr = null;
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
						if(adress.startsWith("127")){  //C'est localhost
							//System.out.println("127 " +ia.getHostAddress());
						} else {
							addr = ia;
						}
					}
					
				} 
			}
			
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		return addr;
	}
	
	public static void envoyerMessage(String dest, String message) {
		Envoi env = new Envoi(dest,message);
		env.start();
	}
	
	public static void ajouterContact(String pseudo, String address){
		Contact c = new ContactAddr(pseudo,address);
		TalkTalk.friends.put(pseudo,c);
	}
	
	public static void exit() {
		saisieconsole.exit();
		System.exit(0);
	}
}



//Debug.start(args);
//Debug.addLog("*** START ***");