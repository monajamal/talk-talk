package talkTalk;

import java.util.Hashtable;
import java.util.List;

import utils.SaisieControle;

public class SaisieConsole extends Thread {
	
	public void run() {
		System.out.println("Je suis "+TalkTalk.pseudo+" et je suis connecté sur ["+TalkTalk.hostname+"] !");
		System.out.println("Mes amis sont "+print(TalkTalk.friends));
		String saisie = "";
		while (saisie!=null) {
			saisie = SaisieControle.saisieString("Commande ?");
			if (saisie.startsWith("/quit")) {
				saisie=null;		
				
				TalkTalk.exit();
			} else if (saisie.startsWith("/add")) {
				saisie=saisie.replaceAll("/add ", "");
				if (saisie.indexOf(' ')!=-1) {
					String pseudo = saisie.substring(0, saisie.indexOf(' ')).replace(" ", "");
					String addr = saisie.substring(saisie.indexOf(' ')+1).replace(" ", "");
					TalkTalk.ajouterContact(pseudo, addr);
				}
				System.out.println("Mes amis sont "+print(TalkTalk.friends));
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
	
	public static String print(Hashtable<String,Contact> friend) {
		String res="{ ";
		int i=0;
		for (String nom : friend.keySet())
		{
			if (i!=0) res+=", ";
			res += nom;
			Contact c = friend.get(nom);
			if (c.getType()==Contact.CONTACT_GROUP) {
				res += " : ";
				List<String> list  = c.getMembres();
				for (String membre : list) {
					res += membre+" ";
				}
			}
			i++;
		}
		res+=" }";
		return res;
	}
	
	public void exit() {
		System.out.println("SERVEUR ["+TalkTalk.hostname+"] : Server down !");
	}
}
