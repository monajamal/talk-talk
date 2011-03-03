/**
 * Classe permettant la saisie de commandes sur la console.
 */
package talkTalk;

import java.util.Vector;

import utils.SaisieControle;

import commun.Personne;

public class SaisieConsole extends Thread {
	
	public void run() {
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
			} else {
				System.out.println("Commande inconnue");
			}
			yield();
		}
	}
	
	public static String print(Vector<Personne> friends) {
		String res="{ ";
		int i=0;
		for (Personne p : friends)
		{
			if (i!=0) res+=", ";
			res += p.getPseudo();
			i++;
		}
		res+=" }";
		return res;
	}
	
	public void exit() {
		System.out.println("SERVEUR ["+TalkTalk.adressePerso+"] : Server down !");
	}
}
