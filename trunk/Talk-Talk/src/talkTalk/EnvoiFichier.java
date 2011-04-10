/**
 * Thread permettant l'envoi d'un message.
 */
package talkTalk;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import commun.Personne;

public class EnvoiFichier extends Thread {

	private final static int NB_TENTATIVES = 2;
	private Personne destinataire;
	private String fichier;
	private byte[] data;
	private File f;
	
	/**
	 * Création d'un thread d'envoi avec la classe Personne
	 * @param dest le destinataire
	 * @param msg le message
	 */
	public EnvoiFichier(Personne dest, String fichier) {
		super();
		this.destinataire = dest;
		this.fichier = fichier;
	}

	/**
	 * Envoie le message
	 */
	public void run(){
		if (destinataire.getAddress() == null){
			Thread t = TalkTalk.searchAdresse(destinataire);
			boolean interrupted = true;
			while (interrupted) 
			{
				try {
					t.join(); //On attend le thread
					interrupted = false;
				} catch (InterruptedException e) {
					
				}
			}
		}
		if (destinataire.getAddress() == null){ //On a fait une recherche mais c'est sans résultat
			TalkTalk.ihm.afficherDestinataireInconnu(destinataire.getName());
		} else {
			//TODO : affichage envoi du fichier
			
			try {
				f = new File(fichier);
				data = new byte[(int) f.length()];
				DataInputStream d = new DataInputStream(new FileInputStream(f));
				d.readFully(data);
				d.close();
				int i=0;
				boolean res=false;
				do {
					res = envoiMsg();
					i++;
				} while (i<NB_TENTATIVES && !res);
				if (!res){ 
					//TODO : Erreur envoi fichier
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Essaie un envoi de message
	 * @param destinataire le destinataire
	 * @param msg le message 
	 * @return true si l'envoi c'est bien passé, false sinon
	 */
	public boolean envoiMsg() {
		Distant d = this.destinataire.getDistant();
		String addr = this.destinataire.getAddress().toString();

		try {
			if (d == null ) {//On a pas encore cherché l'interface distante
				d = (Distant)Naming.lookup("rmi://"+addr+"/TalkTalk");
			}
		
			d.sendFichier(TalkTalk.pseudo,TalkTalk.adressePerso,f.getName(),data);
		
		} catch (MalformedURLException e) {
			destinataire.setDistant(null); //On enlève l'interface distante, c'est pas la bonne
			return false;
		} catch (NotBoundException e) {
			destinataire.setDistant(null); //On enlève l'interface distante, c'est pas la bonne
			return false;
		} catch (RemoteException e) {
			destinataire.setDistant(null); //On enlève l'interface distante, c'est pas la bonne
			return false;
		} 
		destinataire.setDistant(d);
		return true;
	}
}
