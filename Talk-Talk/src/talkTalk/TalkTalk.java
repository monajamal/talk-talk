/**
 * Classe principale
 */
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
import java.util.Properties;
import java.util.Vector;

public class TalkTalk {
	
	public static Adresse adressePerso; //Mon adresse ou me contacter
	public static String pseudo; //Mon pseudo
	public static String image; //Mon image (non implémenté)
	public static String statut; //Mon statut (non implémenté)
	public static Vector<Contact> friends; //La liste de mes contacts
	public final static int portRegistry = 1099; //Le port du serveur de nom 
	public final static int portObject = 3000; //Le port de l'objet
	public static Affichage aff; //Interface d'affichage
	public static SaisieConsole saisieconsole; //Interface de sortie à la console
	public static final String PAGE_IP="http://monip.org"; //Page permettant de connaitre son ip publique
	public static final boolean NAT = false; //Utilisation d'un nat ?
	
	public static void main(String[] args) throws UnknownHostException {
		if (args.length==1) pseudo=args[0]; else pseudo="namelessTalk";

		//On cherche une interface correcte (plus sûr)
		InetAddress addr = getBestAddr();
		if (addr == null) {
			System.out.println("Etes vous bien connecte quelque part ?");
			System.exit(-1);
		}
		//Nom de la machine
		String hostname = addr.getHostName();
		// Rmi doit ecouter sur la bonne interface
		Properties p = System.getProperties();
		String publicIP;
		if (NAT) {
			publicIP = getPublicIP();
			adressePerso = new Adresse(publicIP, portRegistry);
			p.setProperty("java.rmi.server.hostname",publicIP);
			System.out.println("IP Publique : "+publicIP);
			System.out.println("Ouvrez les ports "+portRegistry+" et "+portObject+" vers "+addr.getHostAddress());
		} else {
			adressePerso = new Adresse(addr.getHostAddress(), portRegistry);
			p.setProperty("java.rmi.server.hostname",addr.getHostAddress());
		}
		
		//Initialisation des paramètres
		image=null;
		statut="dispo";
		friends = Contact.parseContact();
		Contact.saveContact(friends);
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
	

	/****Fonctions pouvant être appeles par les interfaces de saisie ou d'affichage****/
	
	/**
	 * Envoie un message 
	 * @param dest le pseudo du destinataire
	 * @param message le message à envoyer
	 */
	public static void envoyerMessage(String dest, String message) {
		Envoi env = new Envoi(dest,message);
		env.start();
	}
	/**
	 * Ajoute un contact
	 * @param pseudo le pseudo 
	 * @param address l'adresse ip
	 * @param port le port
	 */
	public static void ajouterContact(String pseudo, String address, int port){
		Contact c = new ContactAddr(pseudo,address,1099);
		TalkTalk.friends.add(c);
	}
	/**
	 * Quitte l'application.
	 * Appele la fonction exit() sur les interfaces de saisie.
	 */
	public static void exit() {
		saisieconsole.exit();
		System.exit(0);
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
	/**
	 * Retourne l'ip publique 
	 * @return une chaine de caractère contenant l'ip publique
	 */
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

}



//Debug.start(args);
//Debug.addLog("*** START ***");