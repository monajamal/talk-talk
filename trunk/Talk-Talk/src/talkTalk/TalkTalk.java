/**
 * Classe principale
 */
package talkTalk;

import ihm.Affich;

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
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import commun.Contact;
import commun.Groupe;
import commun.Personne;

public class TalkTalk {
	
	public static Adresse adressePerso; //Mon adresse ou me contacter
	public static String pseudo; //Mon pseudo
	public static String image; //Mon image (non implémenté)
	public static String statut; //Mon statut (non implémenté)
	public static Map<String,Personne> friends; //Ensemble des amis
	public static Map<String,Groupe> groupes; //Ensembles des groupes
	public final static int portRegistry = 1099; //Le port du serveur de nom 
	public final static int portObject = 3000; //Le port de l'objet
	public static Affichage ihm; //Interface d'affichage
	public static final String PAGE_IP="http://monip.org"; //Page permettant de connaître son IP publique
	public static final boolean NAT = false; //Utilisation d'un nat ?
	
	public static void main(String[] args) throws UnknownHostException {
		/** Pseudo **/
		if (args.length==1) pseudo=args[0]; else pseudo="namelessTalk";
		

		//On cherche une interface correcte (plus sûr)
		InetAddress addr = getBestAddr();
		if (addr == null) {
			System.out.println("Etes vous bien connecté quelque part ?");
			System.exit(-1);
		}
		//Nom de la machine
		String hostname = addr.getHostName();
		// Rmi doit ecouter sur la bonne interface
		Properties p = System.getProperties();
		String publicIP;
		if (NAT) { 
			publicIP = getPublicIP(); //On cherche l'ip public
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
		// Les collections doivent etre synchronisé
		friends = Collections.synchronizedMap(new Hashtable<String,Personne>());
		groupes = Collections.synchronizedMap(new Hashtable<String,Groupe>());
		//On lit le fichier de contact
		Contact.parseContact(friends,groupes);
		//Contact.saveContact(friends,groupes);
		
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
		
		/** Creation de l'interface utilisateur **/
		if (args.length==2 && args[1].equals("ihm"))
			ihm = new Affich("TalkTalk");
		else if (args.length==1 || (args.length==2 && args[1].equals("console")))
			ihm = new Console();
		else
			ihm = new Console();
		ihm.start();
	}
	

	/****Fonctions pouvant être appelées par les interfaces de saisie ou d'affichage****/
	
	/**
	 * Envoie un message 
	 * @param dest le pseudo du destinataire
	 * @param message le message à envoyer
	 */
	public static void envoyerMessage(String dest, String message) {
		Personne p = friends.get(dest);
		Groupe g;
		if (p!=null){
			envoyerMessage(p,message);
		} else {
			g = groupes.get(dest);
			if (g!=null){
				envoyerMessage(g,message);
			} else {
				ihm.afficherDestinataireInconnu(dest);
			}
		}
	}
	/**
	 * Envoie un wizz
	 * @param dest le pseudo du destinataire
	 */
	public static void envoyerWizz(String dest) {
		Personne p = friends.get(dest);
		Groupe g;
		if (p!=null){
			envoyerWizz(p);
		} else {
			g = groupes.get(dest);
			if (g!=null){
				envoyerWizz(g);
			} else {
				ihm.afficherDestinataireInconnu(dest);
			}
		}
	}
	/**
	 * Envoie un message (groupe ou personne)
	 * @param dest la classe de contact du destinataire
	 * @param message le message à envoyer
	 */
	public static void envoyerMessage(Contact dest, String message) {
		if (dest.getType()==Contact.PERSONNE) {
			Envoi env = new Envoi((Personne)dest,message);
			env.start();
		} else if (dest.getType()==Contact.GROUPE) {
			List<Personne> membres = ((Groupe) dest).getMembres();
			Envoi env;
			for (Personne p : membres) {
				List<String> grp_pseudo = ((Groupe)dest).getPseudosMembres();
				env = new Envoi(p,(Groupe)dest,grp_pseudo,message);
				env.start();
			}
		}
		
	}
	/**
	 * Envoie un wizz (groupe ou personne)
	 * @param dest la classe de contact du destinataire
	 */
	public static void envoyerWizz(Contact dest) {
		if (dest.getType()==Contact.PERSONNE) {
			Envoi env = new Envoi((Personne)dest);
			env.start();
		}  else if (dest.getType()==Contact.GROUPE) {
			List<Personne> membres = ((Groupe) dest).getMembres();
			Envoi env;
			for (Personne p : membres) {
				List<String> grp_pseudo = ((Groupe)dest).getPseudosMembres();
				env = new Envoi(p,(Groupe)dest,grp_pseudo);
				env.start();
			}
		}
	}
	/**
	 * Ajoute un contact
	 * @param pseudo le pseudo 
	 * @param address l'adresse ip
	 * @param port le port
	 */
	public static void ajouterContact(String pseudo, String address, int port){
		
		//TODO : verifier que pseudo n'existe pas encore
		if (friends.get(pseudo) == null) {
			Personne p = new Personne(pseudo,new Adresse(address,port));
			TalkTalk.friends.put(pseudo,p);
			Contact.saveContact(friends, groupes);
		}
	}
	/**
	 * Quitte l'application.
	 * Appele la fonction exit() sur les interfaces de saisie.
	 */
	public static void exit() {
		ihm.stop();
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
					if( adress.length() < 16){          //On s'assure ainsi que l'adresse IP est bien IPv4
						if(!adress.startsWith("127")){  //C'est pas localhost
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
	 * @return une chaîne de caractère contenant l'ip publique
	 */
	private static String getPublicIP() {
		String addr = null;
		try {

			HttpURLConnection httpcon = (HttpURLConnection) new URL(PAGE_IP).openConnection();
			httpcon.connect();
			if(httpcon.getResponseCode()!=HttpURLConnection.HTTP_OK) {//Echec de la connection
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