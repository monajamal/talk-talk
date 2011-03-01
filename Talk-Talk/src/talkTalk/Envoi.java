/**
 * Thread permettant l'envoi d'un message.
 */
package talkTalk;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Envoi extends Thread {

	private String dest;
	private String msg;
	
	public Envoi(String dest, String msg){
		super();
		this.dest = dest;
		this.msg = msg;
	}
	
	public void run(){
		Contact destinataire = null;
		for (Contact c :TalkTalk.friends){
			if (c.getPseudo().equals(dest)) {
				destinataire = c;
				break;
			}
		}
		if (destinataire==null || destinataire.getType() == Contact.CONTACT_UNKNOW){
			//TODO Recherche du destinataire
			TalkTalk.aff.afficherDestinataireInconnu(dest);
		} else if (destinataire.getType() == Contact.CONTACT_NORMAL){
			TalkTalk.aff.afficherMessageEnvoye(destinataire, msg);
			int i=0;
			boolean res;
			do {
				res = envoiMsg(destinataire,msg);
				i++;
			} while (i<2 && !res);
			if (!res){
				TalkTalk.aff.afficherErreurEnvoi(dest, msg);
			}
			
			
		}/* else if (c.getType() == Contact.CONTACT_GROUP){
			List<String> list  = c.getMembres();
			Envoi env;
			for (String nom : list) {
				env = new Envoi(nom,msg);
				env.start();
			}
		}*/
	}
	
	public boolean envoiMsg(Contact c, String msg) {
		Distant d = c.getDistant();
		String addr = c.getAddr().toString();
		if (d == null ) {//On a pas encore cherché l'interface distante
			try {
				d = (Distant)Naming.lookup("rmi://"+addr+"/TalkTalk");
				d.sendMsg(TalkTalk.pseudo,TalkTalk.adressePerso,msg);
			} catch (MalformedURLException e) {
				c.setDistant(null); //On enleve l'interface distante, c'est pas la bonne
				return false;
			} catch (NotBoundException e) {
				c.setDistant(null); //On enleve l'interface distante, c'est pas la bonne
				return false;
			} catch (RemoteException e) {
				c.setDistant(null); //On enleve l'interface distante, c'est pas la bonne
				return false;
			}
			c.setDistant(d);
		} 
		return true;
	}
}
