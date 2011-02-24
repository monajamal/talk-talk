package talkTalk;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Envoi extends Thread {

	private String dest;
	private String msg;
	
	public Envoi(String dest, String msg){
		super();
		this.dest = dest;
		this.msg = msg;
	}
	
	public void run(){
		Contact c;
		c = TalkTalk.friends.get(dest);
		if (c==null || c.getType() == Contact.CONTACT_UNKNOW){
			//TODO Recherche du destinataire
			TalkTalk.aff.afficherDestinataireInconnu(dest);
			System.out.println("Destinataire inconnu : "+dest);
		} else if (c.getType() == Contact.CONTACT_NORMAL){
			boolean again = true; //Recommencer ou pas l'envoi
			boolean first = true; //Premier essai
			while (again) {
				try {
					TalkTalk.aff.afficherMessageEnvoye(c.getAddr(), msg);
					if (c.getDistant() == null || !first){
						//On va faire le lookup ou c'est la deuxieme fois
						again = false; //On ne recommence pas
					}
					first = false; //Plus la premiere fois
					envoiMsg(c,msg); //On envoie
					again = false; //Envoi réussi on recommence pas
				}catch (RemoteException e) {
					c.setDistant(null); //On enleve l'interface distante, c'est pas la bonne
					if (!again) { //Si on a deja fait toutes nos tentatives
						TalkTalk.aff.afficherErreurRecu(dest, msg);
						e.printStackTrace();
					}
					
				} 
			}
		} else if (c.getType() == Contact.CONTACT_GROUP){
			List<String> list  = c.getMembres();
			Envoi env;
			for (String nom : list) {
				env = new Envoi(nom,msg);
				env.start();
			}
		}
	}
	/**
	 * 
	 * @param c
	 * @param msg
	 * @return true si on a du cherche la classe distante, false sinon
	 * @throws RemoteException
	 */
	public boolean envoiMsg(Contact c, String msg) throws RemoteException{
		Distant d = c.getDistant();
		String addr = c.getAddr();
		boolean res = false;
		if (d == null ) {//On a pas encore cherché l'interface distante
			res = true;
			try {
				d = (Distant)Naming.lookup("rmi://"+addr+"/TalkTalk");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.setDistant(d);
		} 
		d.sendMsg(TalkTalk.pseudo,msg);
		return res;
	}
}
