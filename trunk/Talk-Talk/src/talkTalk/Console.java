/**
 * Classe permettant la saisie de commandes sur la console.
 */
package talkTalk;

import java.util.Map;

import utils.SaisieControle;

import commun.Personne;

public class Console implements Affichage {
	@Override
	public void afficherMessageRecu(Personne expediteur, String message) {
		String res=afficherMessageRecu;
		res=res.replace("%expediteur",expediteur.getPseudo());
		res=res.replace("%message",message);
		System.out.println(res);
	}
	@Override
	public void afficherWizzRecu(Personne expediteur) {
		String res=afficherWizzRecu;
		res=res.replace("%expediteur",expediteur.getPseudo());
		System.out.println(res);
	}
	@Override
	public void afficherMessageEnvoye(Personne destinataire, String message) {
		String res=afficherMessageEnvoye;
		res=res.replace("%destinataire",destinataire.getPseudo());
		res=res.replace("%message",message);
		System.out.println(res);
	}
	@Override
	public void afficherErreurEnvoi(String destinataire, String message) {
		String res=afficherErreurEnvoi;
		res=res.replace("%destinataire",destinataire);
		res=res.replace("%message",message);
		System.out.println(res);
	}
	@Override
	public void afficherDestinataireInconnu(String destinataire) {
		String res=afficherDestinataireInconnu;
		res=res.replace("%destinataire",destinataire);
		System.out.println(res);
	}
	
	
	
	@Override
	public void start() {
		System.out.println("Je suis "+TalkTalk.pseudo+" et je suis connecté sur ["+TalkTalk.adressePerso+"] !");
		System.out.println("Mes amis sont "+print(TalkTalk.friends));
		String saisie = "";
		while (saisie!=null) {
			saisie = SaisieControle.saisieString("Commande ?");
			if (saisie.startsWith("/quit")) {
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
				System.out.println("recherche du contact....echec !");
			} else if (saisie.startsWith("/image")) {
				System.out.println("pas implémenté !");
			} else if (saisie.startsWith("/file")) {
				System.out.println("pas implémenté !");
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
				
			} else {
				System.out.println("Commande inconnue");
			}
			//yield();
		}
	}
	@Override
	public void exit() {
		System.out.println("SERVEUR ["+TalkTalk.adressePerso+"] : Server down !");
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
}