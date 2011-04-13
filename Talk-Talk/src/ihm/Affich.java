package ihm;

import java.util.Map;
import java.util.Set;

import javax.swing.text.BadLocationException;

import commun.Groupe;
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
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (expediteur.getPseudo().equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherMessageRecuGrp(Groupe groupe, Personne expediteur,String message) {
		String res=afficherMessageRecuGrp;
		res=res.replace("%expediteur",expediteur.getPseudo());
		res=res.replace("%message",message);
		res=res.replace("%groupe",groupe.getName());
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (expediteur.getPseudo().equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherWizzRecu(Personne expediteur) {
		String res=afficherWizzRecu;
		res=res.replace("%expediteur",expediteur.getPseudo());
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (expediteur.getPseudo().equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherWizzRecuGrp(Groupe groupe, Personne expediteur) {
		String res=afficherWizzRecuGrp;
		res=res.replace("%expediteur",expediteur.getPseudo());
		res=res.replace("%groupe",groupe.getName());
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (expediteur.getPseudo().equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
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
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (destinataire.getPseudo().equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherWizzEnvoye(Personne destinataire) {
		String res=afficherWizzEnvoye;
		res=res.replace("%destinataire",destinataire.getPseudo());
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (destinataire.getPseudo().equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherErreurEnvoi(String destinataire, String message) {
		String res=afficherErreurEnvoi;
		res=res.replace("%destinataire",destinataire);
		if (message!=null) {
			res=res.replace("%message",message);
		} else {
			res=res.replace("%message","wizz");
		}
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (destinataire.equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherDestinataireInconnu(String destinataire) {
		String res=afficherDestinataireInconnu;
		res=res.replace("%destinataire",destinataire);
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (destinataire.equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void changerStatut(Personne personne) {
		String res=changerStatut;
		res=res.replace("%pseudo",personne.getPseudo());
		res=res.replace("%statut",personne.getStatutName());
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (personne.getPseudo().equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
		ihm.createDataListContact();
	}
	@Override
	public void changerMessagePerso(Personne personne) {
		String res=changerMessage;
		res=res.replace("%pseudo",personne.getPseudo());
		res=res.replace("%statut",personne.getMsg_perso());
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (personne.getPseudo().equals(((JConversation)(ihm.jtabp_onglet.getComponentAt(i))).getName())) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void afficherFichierRecu(Personne personne, String fichier) {
		String res=afficherFichierRecu;
		res=res.replace("%personne",personne.getPseudo());
		res=res.replace("%fichier",fichier);
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (personne.getPseudo().equals(ihm.jtabp_onglet.getTitleAt(i))) {
				addLog(res,i);
			}
		}
	}
	@Override
	public void changerImage(Personne p) {
		String res=changerImage;
		res=res.replace("%personne",p.getPseudo());
		addLog(res);
		for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
			if (p.getPseudo().equals(ihm.jtabp_onglet.getTitleAt(i))) {
				addLog(res,i);
			}
		}
		ihm.createDataListContact();
	}
	@Override
	public void start() {
		addLog(start);
		addLog("Mes amis sont "+print(TalkTalk.friends));
		addLog("-----------------------------------------------");
	}
	@Override
	public void stop() {
		addLog(stop);
	}
	/**
	 * Écris dans la console de log de l'interface graphique
	 * @param log : message de log à écrire
	 */
	public void addLog(String log) {
		int atEnd = ihm.jta_log.getDocument().getLength();
		try {
			ihm.jta_log.getDocument().insertString(atEnd,log+"\n",null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Écris dans la fenêtre de conversation avec une personne
	 * @param log : message de log à écrire
	 * @param index : index de la fenêtre de conversation
	 */
	public void addLog(String log,int index) {
		int atEnd = ((JConversation)(ihm.jtabp_onglet.getComponentAt(index))).jtp_conversation.getDocument().getLength();
		try {
			((JConversation)(ihm.jtabp_onglet.getComponentAt(index))).jtp_conversation.getDocument().insertString(atEnd,log,null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public static String print(Map<String,Personne> friends) {
		String res="{ ";
		int i=0;
		Set<String> set = friends.keySet();
		for (String pseudo : set) {
			if (i!=0) res+=", ";
			res += pseudo;
			i++;
		}
		res+=" }";
		return res;
	}
}