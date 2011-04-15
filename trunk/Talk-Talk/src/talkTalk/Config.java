/**
 * Classe qui gère le fichiers de configuration, de contacts et du dictionnaire personnel
 * @author Marie-Hélène Goddet, Damien Finck, Cédric Schaller
 * 
 */
package talkTalk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Scanner;

import utils.OperationsDates;

public class Config {
	
	public final static String DOSSIER_DATA = "./data/";
	public final static String DOSSIER_IMAGES = "./data/images/";
	public final static String DOSSIER_DOWNLOAD = "./data/fichiers/";
	public final static String FICHIER_CONFIG = "./data/config.ini";
	public final static String FICHIER_CONTACTS = "./data/contacts.csv";
	
	/**
	 * Vérifie si le fichiers de config, de contacts et de dictionnaire perso existent
	 * Les créent et les préremplissent si ce n'est pas le cas.
	 */
	public static void checkData() {
		File data = new File(DOSSIER_DATA);
		if (!data.exists())
			data.mkdir();
		File data1 = new File(DOSSIER_IMAGES);
		if (!data1.exists())
			data1.mkdir();
		File data2 = new File(DOSSIER_DOWNLOAD);
		if (!data2.exists())
			data2.mkdir();
		
		File config = new File(FICHIER_CONFIG);
		if (!config.exists()) {
			try {
				config.createNewFile();
				createConfig();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		File contacts = new File(FICHIER_CONTACTS);
		if (!contacts.exists()) {
			try {
				contacts.createNewFile();
				createContacts();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Lis le fichier de configuration.
	 */
	public static void lireConfig() { 
		checkData();
		
		File f = new File(FICHIER_CONFIG);
		TalkTalk.config = new Hashtable<String,Hashtable<String,String>>();
		Scanner sc;
		String noeud = null;
		String key,value;
		try {
			sc = new Scanner(f);
			while(sc.hasNextLine()) {
				String l=sc.nextLine();
				if (l.length()>0 && l.charAt(0)!=';' && l.charAt(0)!=' ') {
					if (l.charAt(0)=='[') {
						noeud=l.substring(1,l.indexOf(']'));
						TalkTalk.config.put(noeud,new Hashtable<String,String>());
					} else {
						key=l.substring(0,l.indexOf("="));
						value=l.substring(l.indexOf("=")+1);
						TalkTalk.config.get(noeud).put(key,value);
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sauvegarde les modifications de configurations dans le fichier
	 * @param isCreation true si on crée la config
	 */
	public static void saveConfig(boolean isCreation) { 
		
		String pseudo,image,interfac;
		String date, os_name, os_version;
		if (isCreation) {
			pseudo="namelessTalk";
			image="null";
			interfac="ihm";
			
			Date today = new Date();
			date = OperationsDates.toString(today);
			os_name = System.getProperty("os.name", "");
			os_version = System.getProperty("os.version", "");
			
		} else {
			pseudo=TalkTalk.config.get("Utilisateur").get("Pseudo").toString();
			image=TalkTalk.config.get("Utilisateur").get("Image").toString();
			interfac=TalkTalk.config.get("Utilisateur").get("Interface").toString();
			
			date=TalkTalk.config.get("Configuration").get("DateCreationCetteConfig").toString();
			os_name=TalkTalk.config.get("Configuration").get("NomOS").toString();
			os_version=TalkTalk.config.get("Configuration").get("VersionOS").toString();
		}
		
		PrintWriter fout;
		try {
			fout = new PrintWriter(new FileWriter(FICHIER_CONFIG,false));
			fout.println(";---------------------------------------------------------------------------");
			fout.println(";      Config.ini      --   TalkTalk (c) 2011                               ");
			fout.println(";---------------------------------------------------------------------------");
			fout.println();
			fout.println(";NE MODIFIEZ CE FICHIER QUE SI VOUS SAVEZ CE QUE VOUS FAITES !!");
			fout.println(";   *** Information *** : true=VRAI ; false=FAUX               ");
			fout.println();
			fout.println(";Paramètres du l'utilisateur");
			fout.println("[Utilisateur]");
			fout.println("Pseudo="+pseudo);
			fout.println("Image="+image);
			fout.println("Interface="+interfac);
			fout.println();
			fout.println("; ***** NE PAS TOUCHER EN-DESSOUS DE CETTE LIGNE *****");
			fout.println(";               (auto-generate bloc)                  ");
			fout.println("[Configuration]");
			fout.println("DateCreationCetteConfig="+date);
			fout.println("NomOS="+os_name);
			fout.println("VersionOS="+os_version);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		lireConfig();
	}
	
	/**
	 * Crée la configuration 
	 */
	public static void createConfig() {
		saveConfig(true);
	}
	
	/**
	 * Crée les contacts
	 */
	public static void createContacts() {
		PrintWriter fout;
		try {
			fout = new PrintWriter(new FileWriter(FICHIER_CONTACTS,false));
			fout.println("#;Pseudo;IP;Port;imgPerso;");
			fout.println("C;TeeTux;194.254.52.154;1099;images/tux.png;");
			fout.println("C;Plop;194.254.52.152;22222;images/schtroumpfette.png;");
			fout.println("C;Jonathan;194.254.52.153;1099;;");
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}