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

public abstract class Contact {
	
	public static final String FIC_CONTACT = "./data/contact";
	
	public static final int FRIEND = 1;
	public static final int GROUPE = 1;
	
	public abstract String getName();
	public abstract String getImg();
	public abstract int getType();
	
	public static void parseContact(Map<String,Personne> friends, Map<String,Groupe> groupes) {
		File f = new File(FIC_CONTACT);
		Scanner sc;
		Groupe grp;
		Personne membre = null;
		try {
			sc = new Scanner(f);
			sc.useDelimiter("\n|\r\n");
			String l ="";
			String[] tab;
			
			while (sc.hasNext()){
				l = sc.next();
				tab = l.split(";");
				int port;
				String ip;
				String pseudo;
				if (tab[0].equals("C")){
					//Contact avec adresse
					if (tab.length>=3) {
						pseudo = tab[1];
						if (tab[2].equals("?")){
							friends.put(pseudo,new Personne(pseudo,null));
						} else {
							ip = tab[2];

							if (tab.length>=4){
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
					//Groupe
					if (tab.length==3){
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

				}else if (tab[0].equals("B")) {
					//Blocage
				} else {
					System.out.println("Erreur de lecture du fichier de configuration");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public static void saveContact(Map<String,Personne> friends, Map<String,Groupe> groupes) {
		PrintWriter fout;
		try {
			fout = new PrintWriter(new FileWriter(FIC_CONTACT,false));
			Personne p;
			for (String pseudo : friends.keySet()){
				p = friends.get(pseudo);
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
			Groupe g;
			for (String grp_name : groupes.keySet())
			{
				g = groupes.get(grp_name);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}