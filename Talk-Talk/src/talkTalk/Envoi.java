/**
 * Thread permettant l'envoi d'un message.
 */
package talkTalk;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import commun.Contact;
import commun.Groupe;
import commun.Personne;

public class Envoi extends Thread {

	private final static int NB_TENTATIVES = 2;
	private String pseudo_dest;
	private Personne destinataire;
	private String msg; //Si null c'est un wizz
	private Groupe grp = null; // Si non null, c'est un envoi de groupe
	//private List<Adresse> addr_grp;
	//private boolean wizz; //Wizz ou message ?
	
	/**
	 * Creation d'un thread d'envoi avec le nom du contact
	 * @param dest le destinataire
	 * @param msg le message
	 */
	/*public Envoi(String dest, String msg){
		super();
		this.pseudo_dest = dest;
		this.msg = msg;
	}*/

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
	 * Envoie d'un wizz (sans message)
	 * @param dest le pseudo du destinataire
	 */
	/*public Envoi(String dest) {
		this.pseudo_dest = dest;
		this.msg = null;
	}*/
	/**
	 * Envoie d'un wizz (sans message)
	 * @param dest le destinataire
	 */
	public Envoi(Personne dest) {
		this.destinataire = dest;
		this.msg = null;
	}

	public Envoi(Personne p, Groupe grp) {
		this.destinataire = p;
		this.grp = grp;
		this.msg = null;
	}

	public Envoi(Personne p, Groupe grp, String message) {
		this.destinataire = p;
		this.grp = grp;
		this.msg = message;
	}

	/**
	 * Envoie le message
	 */
	public void run(){
		/*if (destinataire == null) { //On doit rechercher le contact
			synchronized(TalkTalk.friends) {
				for (Personne p :TalkTalk.friends){
					if (p.getPseudo().equals(pseudo_dest)) {
						destinataire = p;
						break;
					}
				} 
			}
		}*/
		if (destinataire==null || (destinataire.getType()==Contact.FRIEND && ((Personne)destinataire).getAddress() == null)){
			//TODO Recherche du destinataire
			TalkTalk.ihm.afficherDestinataireInconnu(pseudo_dest);
		} else {
			TalkTalk.ihm.afficherMessageEnvoye(destinataire, msg);
			int i=0;
			boolean res;
			do {
				res = envoiMsg();
				i++;
			} while (i<NB_TENTATIVES && !res);
			if (!res){
				TalkTalk.ihm.afficherErreurEnvoi(pseudo_dest, msg);
			}
		}
	}

	/**
	 * Essaie un envoi de message
	 * @param destinataire le destinataire
	 * @param msg le message 
	 * @return true si l'envoi c'est bien passé, false sinon
	 */
	public boolean envoiMsg() {
		Distant d = this.destinataire.getDistant();
		String addr = this.destinataire.getAddress().toString();
		if (d == null ) {//On a pas encore cherché l'interface distante
			try {
				d = (Distant)Naming.lookup("rmi://"+addr+"/TalkTalk");
				if (this.msg == null && this.grp ==null) {
					d.sendWizz(TalkTalk.pseudo, TalkTalk.adressePerso);
				} else if (this.msg != null && this.grp ==null)  {
					d.sendMsg(TalkTalk.pseudo,TalkTalk.adressePerso,msg);
				} else if (this.msg == null && this.grp !=null)  {//TODO comment construire la liste d'adresse ?
					//d.sendMsgGrp(TalkTalk.pseudo,TalkTalk.adressePerso,msg);
				} else if (this.msg != null && this.grp ==null)  {
					//d.sendMsgGrp(TalkTalk.pseudo,TalkTalk.adressePerso,msg);
				}
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
