/**
 * Représente un contact local
 */
package talkTalk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public abstract class  Contact {

	public static final String FIC_CONTACT = "./data/contact";
	public static final int CONTACT_UNKNOW = 0;
	public static final int CONTACT_GROUP = 1;
	public static final int CONTACT_NORMAL = 2;
	private String pseudo;

	public Contact(String pseudo)
	{
		this.pseudo = pseudo;
	}

	public String getPseudo() {
		return pseudo;
	}
	public void setPort(int port) {
		
	}

	public int getPort() {
		return -1;
	}
	
	public abstract void setDistant(Distant d);
	
	public abstract int getType();
	
	public abstract Adresse getAddr();
	
	public abstract List<String> getMembres();
	
	public abstract Distant getDistant() ;

	/**
	 * Lit le fichier de contact
	 * @return la liste des contacts lu
	 */
	public static Vector<Contact> parseContact(){
		Vector<Contact> contacts = new Vector<Contact>();
		File f = new File(FIC_CONTACT);
		Scanner sc;
		try {
			sc = new Scanner(f);
			sc.useDelimiter("\n|\r\n");
			String l ="";
			String[] tab;
			Contact c;
			while (sc.hasNext()){
				l = sc.next();
				tab = l.split(";");
				int port;
				String ip;
				String pseudo;
				c = null;
				if (tab[0].equals("C")){
					//Contact avec adresse
					if (tab.length>=3) {
						pseudo = tab[1];
						if (tab[2].equals("?")){
							c = new ContactAddr(pseudo);
						} else {
							ip = tab[2];

							if (tab.length>=4){
								port = Integer.parseInt(tab[3]);
							} else {
								port = 1099;
							}
							c = new ContactAddr(pseudo,ip,port);
						}
					} else {
						System.out.println("Erreur de lecture du fichier de configuration");
					}
				} else if (tab[0].equals("G")) {
					//Groupe
					if (tab.length==3){
						c = new ContactGroup(tab[1]);
						String tabMembre[] = tab[2].split(",");
						for (int i=0;i<tabMembre.length;i++) {
							((ContactGroup)c).addMembres(tabMembre[i]);
						}
					} else {
						System.out.println("Erreur de lecture du fichier de configuration");
					}

				}else if (tab[0].equals("B")) {
					//Blocage
				} else {
					System.out.println("Erreur de lecture du fichier de configuration");
				}
				if (c!=null){
					//On a lu un contact 
					contacts.add(c);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return contacts;
	}

	/**
	 * Sauvegarde la liste de contacts dans le fichier
	 * @param contacts la liste de contacts actuelle
	 */
	public static void saveContact(Vector<Contact> contacts) {
		PrintWriter fout;
		try {
			fout = new PrintWriter(new FileWriter(FIC_CONTACT,false));
			for (Contact c : contacts) {
				if (c.getType() == CONTACT_GROUP) {
					fout.print("G;");
					fout.print(c.getPseudo()+";");
					List<String> list = c.getMembres();
					for (int i = 0;i<list.size();i++) {
						if (i == list.size()-1) {
							fout.print(list.get(i));
						} else {
							fout.print(list.get(i)+",");
						}
					}
				} else if (c.getType() == CONTACT_NORMAL) {
					fout.print("C;");
					fout.print(c.getPseudo()+";");
					fout.print(c.getAddr().getAddr()+";");
					fout.print(c.getAddr().getPort());
				}else if (c.getType() == CONTACT_UNKNOW) {
					fout.print("C;");
					fout.print(c.getPseudo()+";?;?");
				}
				
				fout.println();
			}
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*public static Hashtable<String,Contact> parseContact2(){
		Hashtable<String,Contact> contacts = new Hashtable<String,Contact>();
		File f = new File(FIC_CONTACT);
		try {
			Scanner sc = new Scanner(f);
			sc.useDelimiter("\n|\r\n");
			String l ="";
			String pseudo;
			String addr;
			String[] tabContact;
			Contact c;
			while (sc.hasNext()){
				
				l = sc.next();
				//System.out.println(l);
				try {
					pseudo = l.substring(0,l.indexOf(':')).replace(" ","");
					if (pseudo.matches("Groupe.*")){
						//System.out.println("Groupe");
						pseudo = pseudo.substring(6);
						tabContact = l.substring(l.indexOf(':')+1,l.length()).replace(" ","").split(",");
						c = new ContactGroup(pseudo);
						for (int i=0;i<tabContact.length;i++){
							((ContactGroup) c).addMembres(tabContact[i]);
						}

					} else {
						addr = l.substring(l.indexOf(':')+1,l.length()).replace(" ","");
						//System.out.println(pseudo+ "---"+addr);
						if (addr.equals("?")){
							c = new ContactAddr(pseudo);
						} else {
							c = new ContactAddr(pseudo,addr);
						}
					}
					contacts.put(pseudo,c);
				}catch (java.lang.StringIndexOutOfBoundsException e){
					System.out.println("Probleme de chargement du fichier de contacts");
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contacts;
	}
	
	public static void saveContact(Hashtable<String,Contact> contacts) {
		PrintWriter fout;
		try {
			fout = new PrintWriter(new FileWriter("./data/contact",false));
			for (String nom : contacts.keySet()) {
				if (contacts.get(nom).getType() == CONTACT_GROUP) {
					fout.print("Groupe ");
				}
				fout.print(nom+" : ");
				fout.println(contacts.get(nom).getString());
			}
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	
	
	
	
}
    