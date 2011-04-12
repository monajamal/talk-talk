/**
 * Interface recuperé à distance
 */
package talkTalk;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Distant extends Remote {
	/**
	 * Permet d'envoyer un message a cette personne
	 * @param expediteur le pseudo de l'expediteur
	 * @param addr_exp l'adresse de l'expediteur
	 * @param m le message a envoyer
	 * @throws RemoteException
	 */
	public void sendMsg(String expediteur,Adresse addr_exp,String m) throws RemoteException;
	/**
	 * Passe le jeton 
	 * @param jeton le jeton
	 * @return le jeton modifié
	 * @throws RemoteException
	 */
	public JetonRecherche searchContact(JetonRecherche jeton) throws RemoteException;
	/**
	 * Permet d'envoyer un wizz a cette personne
	 * @param pseudo le pseudo de l'expediteur
	 * @param addr_exp l'adresse de l'expediteur
	 * @throws RemoteException
	 */
	public void sendWizz(String pseudo,Adresse addr_exp) throws RemoteException;
	/**
	 * Permet d'envoyer un message de groupe
	 * @param pseudo le pseudo de l'expediteur
	 * @param addr_exp l'adresse de l'expediteur
	 * @param m le message a envoyer
	 * @param grp la liste des personnes appartenant au groupe
	 * @throws RemoteException
	 */
	public void sendMsgGr(String pseudo,Adresse addr_exp,String m,String grp_name,List<String> grp) throws RemoteException;
	/**
	 * Permet d'envoyer un wizz de groupe
	 * @param pseudo le pseudo de l'expediteur
	 * @param addr_exp l'adresse de l'expediteur
	 * @param grp la liste des personnes appartenant au groupe
	 * @throws RemoteException
	 */
	public void sendWizzGr(String pseudo,Adresse addr_exp,String grp_name,List<String> grp) throws RemoteException;
	
	public void abonnement(String pseudo, Adresse addr) throws RemoteException;
	
	public void setStatut(String pseudo, int newStatut) throws RemoteException;
	
	public int getStatut() throws RemoteException;
	public void sendFichier(String pseudo, Adresse adressePerso, String fichier, byte[] donnees)throws RemoteException;
	public void setImagePerso(String pseudo, byte[] img)throws RemoteException;

}
