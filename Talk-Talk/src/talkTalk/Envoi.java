/**
 * Thread permettant l'envoi d'un message.
 */
package talkTalk;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import commun.Personne;

public class Envoi extends Thread {

	private String pseudo_dest;
	private Personne destinataire;
	private String msg;

	/**
	 * Creation d'un thread d'envoi avec le nom du contact
	 * @param dest le destinataire
	 * @param msg le message
	 */
	public Envoi(String dest, String msg){
		super();
		this.pseudo_dest = dest;
		this.msg = msg;
	}

	/**
	 * Création d'un thread d'envoi avec la classe Personne
	 * @param dest le destinataire
	 * @param msg le message
	 */
	public Envoi(Personne dest, String msg) {
		super();
		this.destinataire = dest;
		this.msg = msg;
	}
	/**
	 * Envoie le message
	 */
	public void run(){
		if (destinataire == null) { //On doit rechercher le contact
			synchronized(TalkTalk.friends) {
				for (Personne p :TalkTalk.friends){
					if (p.getPseudo().equals(pseudo_dest)) {
						destinataire = p;
						break;
					}
				} 
			}
		}
		if (destinataire==null || destinataire.getAddress() == null){
			//TODO Recherche du destinataire
			TalkTalk.aff.afficherDestinataireInconnu(pseudo_dest);
		} else {
			TalkTalk.aff.afficherMessageEnvoye(destinataire, msg);
			int i=0;
			boolean res;
			do {
				res = envoiMsg(destinataire,msg);
				i++;
			} while (i<2 && !res);
			if (!res){
				TalkTalk.aff.afficherErreurEnvoi(pseudo_dest, msg);
			}
		}
	}

	/**
	 * Essaie un envoi de message
	 * @param destinataire le destinataire
	 * @param msg le message 
	 * @return true si l'envoi c'est bien passé, false sinon
	 */
	public boolean envoiMsg(Personne destinataire, String msg) {
		Distant d = destinataire.getDistant();
		String addr = destinataire.getAddress().toString();
		if (d == null ) {//On a pas encore cherché l'interface distante
			try {
				d = (Distant)Naming.lookup("rmi://"+addr+"/TalkTalk");
				d.sendMsg(TalkTalk.pseudo,TalkTalk.adressePerso,msg);
			} catch (MalformedURLException e) {
				destinataire.setDistant(null); //On enlève l'interface distante, c'est pas la bonne
				return false;
			} catch (NotBoundException e) {
				destinataire.setDistant(null); //On enlève l'interface distante, c'est pas la bonne
				return false;
			} catch (RemoteException e) {
				destinataire.setDistant(null); //On enlève l'interface distante, c'est pas la bonne
				return false;
			}
			destinataire.setDistant(d);
		} 
		return true;
	}
}
