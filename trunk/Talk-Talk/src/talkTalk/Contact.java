package talkTalk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Contact {

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
	
	public int getType(){
		return CONTACT_UNKNOW;
	}
	
	public String getString(){
		return "?";
	}
	
	public String getAddr() {
		return null;
	}
	
	public List<String> getMembres() {
		return null;
	}
	
	public static Hashtable<String,Contact> parseContact(){
		Hashtable<String,Contact> contacts = new Hashtable<String,Contact>();
		File f = new File("./data/contact");
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
							c = new Contact(pseudo);
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
	}
}
    