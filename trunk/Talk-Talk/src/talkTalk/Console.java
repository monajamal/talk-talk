/**
 * Classe permettant la saisie de commandes sur la console.
 */
package talkTalk;

import java.util.Map;
import java.util.Set;

import utils.SaisieControle;

import commun.Groupe;
import commun.Personne;

public class Console implements Affichage {
	@Override
	public void afficherMessageRecu(Personne expediteur, String message) {
		String res=afficherMessageRecu;
		res=res.replace("%expediteur",expediteur.getPseudo());
		res=res.replace("%message",message);
		System.out.print(res);
	}
	@Override
	public void afficherMessageRecuGrp(Groupe groupe, Personne expediteur,String message) {
		String res=afficherMessageRecuGrp;
		res=res.replace("%expediteur",expediteur.getPseudo());
		res=res.replace("%message",message);
		res=res.replace("%groupe",groupe.getName());
		System.out.print(res);
	}
	@Override
	public void afficherWizzRecu(Personne expediteur) {
		String res=afficherWizzRecu;
		res=res.replace("%expediteur",expediteur.getPseudo());
		System.out.print(res);
	}
	@Override
	public void afficherWizzRecuGrp(Groupe grp, Personne expediteur) {
		String res=afficherWizzRecuGrp;
		res=res.replace("%expediteur",expediteur.getPseudo());
		res=res.replace("%groupe",grp.getName());
		System.out.print(res);
	}
	@Override
	public void afficherMessageEnvoye(Personne destinataire, String message) {
		String res=afficherMessageEnvoye;
		res=res.replace("%destinataire",destinataire.getPseudo());
		res=res.replace("%message",message);
		System.out.print(res);
	}
	@Override
	public void afficherWizzEnvoye(Personne destinataire) {
		String res=afficherMessageEnvoye;
		res=res.replace("%destinataire",destinataire.getPseudo());
		res=res.replace("%message","wizz");
		System.out.print(res);
	}
	@Override
	public void afficherErreurEnvoi(String destinataire, String message) {
		if (message!=null) {
			String res=afficherErreurEnvoi;
			res=res.replace("%destinataire",destinataire);
			res=res.replace("%message",message);
			System.out.print(res);
		} else {
			String res=afficherErreurEnvoi;
			res=res.replace("%destinataire",destinataire);
			res=res.replace("%message","wizz");
			System.out.print(res);
		}
	}
	@Override
	public void afficherDestinataireInconnu(String destinataire) {
		String res=afficherDestinataireInconnu;
		res=res.replace("%destinataire",destinataire);
		System.out.println(res);
	}
	@Override
	public void changerStatut(Personne personne) {
		String res=changerStatut;
		res=res.replace("%pseudo",personne.getPseudo());
		res=res.replace("%statut",personne.getStatutName());
		System.out.print(res);
	}
	@Override
	public void afficherFichierRecu(Personne personne, String fichier) {
		String res=afficherFichierRecu;
		res=res.replace("%personne",personne.getPseudo());
		res=res.replace("%fichier",fichier);
		System.out.print(res);
	}
	@Override
	public void changerImage(Personne p) {
		// Rien a faire
	}
	@Override
	public void start() {
		System.out.println(start);
		System.out.println("Mes amis sont "+print(TalkTalk.friends));
		String saisie = "";
		while (saisie!=null) {
			saisie = SaisieControle.saisieString("Commande ?");
			if (saisie.startsWith("/quit") || saisie.startsWith("/exit")) {
				saisie=null;		
				TalkTalk.exit();
			} else if (saisie.startsWith("/add")) {
				saisie=saisie.replaceAll("/add ", "");
				String tab[] = saisie.split(" ");
				if (tab.length==3) {
					TalkTalk.ajouterContact(tab[0], tab[1], Integer.parseInt(tab[2]));
					System.out.println("Mes amis sont "+print(TalkTalk.friends));
				} else {
					System.out.println("Echec lors de l'ajout");
				}	
			} else if (saisie.startsWith("/contact")) {
				saisie=saisie.replaceAll("/contact ", "");
				Personne p = TalkTalk.friends.get(saisie);
				if (p==null){
					//p = new Personne(saisie,null);
					p = TalkTalk.ajouterContact(saisie);
				}
				TalkTalk.searchAdresse(p);
				System.out.println("L'addresse de "+saisie+ " est : " +p.getAddress());
				//System.out.println("recherche du contact....echec !");
			} else if (saisie.startsWith("/image")) {
				System.out.println("pas implémenté !");
			} else if (saisie.startsWith("/file")) {
				saisie=saisie.replaceAll("/file ", "");
				if (saisie.indexOf(' ')!=-1) {
					String dest = saisie.substring(0, saisie.indexOf(' '));
					String file = saisie.substring(saisie.indexOf(' ')+1);
					TalkTalk.envoyerFichier(dest,file);
				}
			} else if (saisie.startsWith("/send")) {
				saisie=saisie.replaceAll("/send ", "");
				if (saisie.indexOf(' ')!=-1) {
					String dest = saisie.substring(0, saisie.indexOf(' '));
					String msg = saisie.substring(saisie.indexOf(' ')+1);
					TalkTalk.envoyerMessage(dest, msg);
				}
			} else if (saisie.startsWith("/wizz")) {
				saisie=saisie.replaceAll("/wizz ", "");
				String dest = saisie;
				TalkTalk.envoyerWizz(dest);
			} else if (saisie.startsWith("/statut")) {
				saisie=saisie.replaceAll("/statut ", "");
				TalkTalk.setStatut(Integer.parseInt(saisie));
			
			} else {
				System.out.println("Commande inconnue");
			}
		}
	}
	@Override
	public void stop() {
		String res=stop;
		System.out.print(res);
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