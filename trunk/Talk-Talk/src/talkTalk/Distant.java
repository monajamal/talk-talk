/**
 * Interface recuperé à distance
 */
package talkTalk;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

public interface Distant extends Remote {
	/**
	 * Permet d'envoyer un message a cette personne
	 * @param expediteur le pseudo de l'expediteur
	 * @param addr_exp l'adresse de l'expediteur
	 * @param m le message a envoyer
	 * @throws RemoteException
	 */
	public void sendMsg(String expediteur,Adresse addr_exp,String m) throws RemoteException;
	
	public Vector<String> searchContact(String pseudo) throws RemoteException;
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
	public void sendMsgGr(String pseudo,Adresse addr_exp,String m,List<Adresse> grp) throws RemoteException;
	/**
	 * Permet d'envoyer un wizz de groupe
	 * @param pseudo le pseudo de l'expediteur
	 * @param addr_exp l'adresse de l'expediteur
	 * @param grp la liste des personnes appartenant au groupe
	 * @throws RemoteException
	 */
	public void sendWizzGr(String pseudo,Adresse addr_exp,List<Adresse> grp) throws RemoteException;

}
