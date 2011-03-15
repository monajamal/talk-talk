package ihm;

import java.util.Map;

import javax.swing.text.BadLocationException;

import commun.Personne;

import talkTalk.Affichage;
import talkTalk.TalkTalk;

public class Affich implements Affichage {
	IHM ihm;
	public Affich(String titre) {
		this.ihm=new IHM(titre);
	}
	@Override
	public void afficherMessageRecu(Personne expediteur, String message) {
		String res=afficherMessageRecu;
		res=res.replace("%expediteur",expediteur.getPseudo());
		res=res.replace("%message",message);
		addLog(res);
		for (int i=0;i<ihm.jc_fenetre.size();i++) {
			if (expediteur.getPseudo().equals(ihm.jc_fenetre.get(i).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherWizzRecu(Personne expediteur) {
		String res=afficherWizzRecu;
		res=res.replace("%expediteur",expediteur.getPseudo());
		addLog(res);
		for (int i=0;i<ihm.jc_fenetre.size();i++) {
			if (expediteur.getPseudo().equals(ihm.jc_fenetre.get(i).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherMessageEnvoye(Personne destinataire, String message) {
		String res=afficherMessageEnvoye;
		res=res.replace("%destinataire",destinataire.getPseudo());
		res=res.replace("%message",message);
		addLog(res);
		for (int i=0;i<ihm.jc_fenetre.size();i++) {
			if (destinataire.getPseudo().equals(ihm.jc_fenetre.get(i).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherErreurEnvoi(String destinataire, String message) {
		String res=afficherErreurEnvoi;
		res=res.replace("%destinataire",destinataire);
		res=res.replace("%message",message);
		addLog(res);
		for (int i=0;i<ihm.jc_fenetre.size();i++) {
			if (destinataire.equals(ihm.jc_fenetre.get(i).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherDestinataireInconnu(String destinataire) {
		String res=afficherDestinataireInconnu;
		res=res.replace("%destinataire",destinataire);
		addLog(res);
		for (int i=0;i<ihm.jc_fenetre.size();i++) {
			if (destinataire.equals(ihm.jc_fenetre.get(i).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void start() {
		addLog(start);
		addLog("Mes amis sont "+print(TalkTalk.friends));
	}
	@Override
	public void stop() {
		addLog(stop);
	}
	public static String print(Map<String,Personne> friends) {
		String res="{ ";
		int i=0;
		for (String pseudo : friends.keySet()) {
			if (i!=0) res+=", ";
			res += pseudo;
			i++;
		}
		res+=" }";
		return res;
	}
	public void addLog(String log) {
		try {
			ihm.jta_log.getDocument().insertString(ihm.jta_log.getDocument().getLength(),log,null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	public void addLog(String log,int i) {
		try {
			ihm.jc_fenetre.get(i).jtp_conversation.getDocument().insertString(ihm.jta_log.getDocument().getLength(),log,null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}