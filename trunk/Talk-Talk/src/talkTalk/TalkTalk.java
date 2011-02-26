package talkTalk;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
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
	public final static int portRegistry = 1099;
	public final static int portObject = 3000;
	public static Affichage aff;
	public static SaisieConsole saisieconsole;
	public static final String PAGE_IP="http://monip.org";
	public static final boolean NAT = true;
	
	public static void main(String[] args) throws UnknownHostException {
		if (args.length==1) pseudo=args[0]; else pseudo="namelessTalk";

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
		String publicIP;
		if (NAT) {
			publicIP = getPublicIP();
			p.setProperty("java.rmi.server.hostname",publicIP);
			System.out.println("IP Publique : "+publicIP);
			System.out.println("Ouvrez les ports "+portRegistry+" et "+portObject+" vers "+addr.getHostAddress());
		} else {
			p.setProperty("java.rmi.server.hostname",addr.getHostAddress());
		}
		
		
		image=null;
		statut="dispo";
		friends = Contact.parseContact();
		//Creation de l'affichage
		aff = new AffichageConsole();
		
		//Lancement du registry
		try {
			LocateRegistry.createRegistry(portRegistry);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		// Lancement du serveur
		try {
			DistantImpl objLocal = new DistantImpl();
			Remote stub = UnicastRemoteObject.exportObject(objLocal,portObject);
			Naming.rebind("rmi://"+hostname+":"+portRegistry+"/TalkTalk", stub);
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
	
private static String getPublicIP() {
		String addr = null;
		try {

			HttpURLConnection httpcon = (HttpURLConnection) new URL(PAGE_IP).openConnection();
			httpcon.connect();
			if(httpcon.getResponseCode()!=HttpURLConnection.HTTP_OK) {//?chec de la connection
				System.out.println("Recuperation de l'adresse publique : erreur\n"+httpcon.getResponseMessage());
				System.exit(-1);
			} else {//Lecture de la réponse
				InputStream is;
				is = httpcon.getInputStream();

				int len = httpcon.getContentLength();
				int i;
				String s = "";
				for (i=0;i<len;i++){
					s+=((char)is.read());
				}
				addr = s.substring(s.indexOf("<BR>IP : ")+9,s.indexOf("<br></font>"));
			}
		} catch (IOException e) {
			return null;
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