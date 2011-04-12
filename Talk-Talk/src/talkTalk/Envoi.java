/**
 * Thread permettant l'envoi d'un message.
 */
package talkTalk;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import commun.Groupe;
import commun.Personne;

public class Envoi extends Thread {

	private final static int NB_TENTATIVES = 2;
	private Personne destinataire;
	private String msg = null; //Si null c'est un wizz
	private Groupe grp = null; // Si non null, c'est un envoi de groupe
	private List<String> pseudos_grp;
	private int statut = -1;
	private boolean messagePerso = false;
	
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
	 * @param dest le destinataire
	 */
	public Envoi(Personne dest) {
		super();
		this.destinataire = dest;
		this.msg = null;
	}
	/**
	 * Envoi un wizz à une personne d'un groupe
	 * @param p le destinataire
	 * @param grp le groupe concerné
	 */
	public Envoi(Personne p, Groupe grp, List<String> pseudos) {
		super();
		this.destinataire = p;
		this.grp = grp;
		this.pseudos_grp = pseudos;
		this.msg = null;
	}
	/**
	 * Envoi un message à une personne d'un groupe
	 * @param p le destinataire
	 * @param grp le groupe concerné
	 * @param message le message
	 */
	public Envoi(Personne p, Groupe grp, List<String> pseudos, String message) {
		super();
		this.destinataire = p;
		this.grp = grp;
		this.pseudos_grp = pseudos;
		this.msg = message;
	}
	/**
	 * Envoi une mise a jour de status à une personne
	 * @param p la personne a mettre a jour
	 * @param statut le nouveau statut
	 */
	public Envoi(Personne p, int statut){
		super();
		destinataire = p;
		this.statut = statut;
		this.msg = null;
	}
	/**
	 * Envoi une mise a jour de message perso à une personne
	 * @param p la personne a mettre jour
	 * @param mesagePerso le nouveau message perso
	 * @param b doit etre a true
	 */
	public Envoi(Personne p, String messagePerso, boolean b) {
		super();
		destinataire = p;
		this.msg = messagePerso;
		this.messagePerso = b;
	}
	/**
	 * Envoie le message
	 */
	public void run(){
		if (destinataire.getAddress() == null){
			Thread t = TalkTalk.searchAdresse(destinataire);
			boolean interrupted = true;
			while (interrupted) 
			{
				try {
					t.join(); //On attend le thread
					interrupted = false;
				} catch (InterruptedException e) {
					
				}
			}
		}
		if (destinataire.getAddress() == null){ //On a fait une recherche mais c'est sans résultat
			TalkTalk.ihm.afficherDestinataireInconnu(destinataire.getName());
		} else {
			if (grp ==null && statut==-1 && !messagePerso) {
				if (msg!=null) {
					TalkTalk.ihm.afficherMessageEnvoye(destinataire, msg);
				} else {
					TalkTalk.ihm.afficherWizzEnvoye(destinataire);
				}
			}
			int i=0;
			boolean res=false;
			do {
				res = envoiMsg();
				i++;
			} while (i<NB_TENTATIVES && !res);
			if (!res){ 
				TalkTalk.ihm.afficherErreurEnvoi(destinataire.getName(), msg);
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

		try {
			if (d == null ) {//On a pas encore cherché l'interface distante
				d = (Distant)Naming.lookup("rmi://"+addr+"/TalkTalk");
			}
			if (statut!=-1){
				d.setStatut(TalkTalk.pseudo, statut);
			}else if (messagePerso){
				d.setMessagePerso(TalkTalk.pseudo, msg);
			} else if (this.msg == null && this.grp ==null) {
				d.sendWizz(TalkTalk.pseudo, TalkTalk.adressePerso);
			} else if (this.msg != null && this.grp ==null)  {
				d.sendMsg(TalkTalk.pseudo,TalkTalk.adressePerso,msg);
			} else if (this.msg == null && this.grp !=null)  {
				d.sendWizzGr(TalkTalk.pseudo,TalkTalk.adressePerso,this.grp.getName(),this.pseudos_grp);
			} else if (this.msg != null && this.grp !=null)  {
				d.sendMsgGr(TalkTalk.pseudo,TalkTalk.adressePerso,this.msg,this.grp.getName(),this.pseudos_grp);
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
		return true;
	}
}
