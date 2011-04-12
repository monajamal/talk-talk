package commun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import talkTalk.Adresse;
import talkTalk.Config;

public abstract class Contact {
	
	public static final int PERSONNE = 1;
	public static final int GROUPE = 2;
	
	public abstract String getName();
	public abstract String getImg();
	public abstract int getType();
	
	/**
	 * Lit le fichier de contact et remplit les ensembles d'amis et de groupes
	 * @param friends les amis 
	 * @param groupes les groupes
	 */
	public static void parseContact(Map<String,Personne> friends, Map<String,Groupe> groupes, List<String> bloques) {
		File f = new File(Config.FICHIER_CONTACTS);
		Scanner sc;
		String l;
		String[] tab;
		
		Groupe grp;
		Personne membre;
		int port;
		String ip;
		String pseudo;
		
		try {
			sc = new Scanner(f);
			
			while (sc.hasNext()) {
				l = sc.nextLine();
				tab = l.split(";");
				
				if (tab[0].equals("C")) {
					// Lecture des personnes
					if (tab.length>=3) {
						pseudo = tab[1];
						if (tab[2].equals("?")) {
							friends.put(pseudo,new Personne(pseudo,null));
						} else {
							ip = tab[2];
							if (tab.length>=4) {
								port = Integer.parseInt(tab[3]);
							} else {
								port = 1099;
							}
							friends.put(pseudo,new Personne(pseudo,new Adresse(ip,port)));
						}
					} else {
						System.out.println("Erreur de lecture du fichier de configuration");
					}
				} else if (tab[0].equals("G")) {
					// Lecture des groupes
					if (tab.length==3) {
						grp = new Groupe(tab[1]);
						String tabMembre[] = tab[2].split(",");
						for (int i=0;i<tabMembre.length;i++) {
							//Recherche du membre
							membre = friends.get(tabMembre[i]);
							if (membre != null) {
								grp.addMembre(membre);
							} else {
								System.out.println("Cette personne n'existe pas : "+tabMembre[i]);
							}
						}
						groupes.put(tab[1],grp);
					} else {
						System.out.println("Erreur de lecture du fichier de configuration");
					}
				} else if (tab[0].equals("B")) {
					// Lecture des personnes bloquées
					bloques.add(tab[1]);
				} else {
					// ligne ignoré (commentaire, ou mal écrite)
					// System.out.println("Erreur de lecture du fichier de configuration");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Écrit dans le fichier les contacts 
	 * @param friends les amis
	 * @param groupes les groupes
	 */
	public static void saveContact(Map<String,Personne> friends, Map<String,Groupe> groupes, List<String> bloques) {
		PrintWriter fout;
		try {
			fout = new PrintWriter(new FileWriter(Config.FICHIER_CONTACTS,false));
			// On sauvegarde les personnes
			fout.println("#;Pseudo;IP;Port");
			synchronized(friends.values()) {
				for (Personne p : friends.values()) {
					fout.print("C;");
					fout.print(p.getPseudo()+";");
					if (p.getAddress()!=null){
						fout.print(p.getAddress().getNomDns()+";");
						fout.print(p.getAddress().getPort());
					} else {
						fout.print("?;?");
					}
					fout.println();
				}
			}
			// On sauvegarde les groupes
			fout.println("#;Nom;Membre A,Membre B,Membre C, ...");
			synchronized(groupes){
				for (Groupe g : groupes.values()) {
					fout.print("G;");
					fout.print(g.getName()+";");
					List<Personne> list = g.getMembres();
					for (int i = 0;i<list.size();i++) {
						if (i == list.size()-1) {
							fout.print(list.get(i).getPseudo());
						} else {
							fout.print(list.get(i).getPseudo()+",");
						}
					}
					fout.println();
				}
				fout.close();
			}
			// On sauvegarde les personnes bloquées
			fout.println("#;Nom");
			synchronized(bloques) {
				for (String b : bloques) {
					fout.print("B;");
					fout.println(b);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}