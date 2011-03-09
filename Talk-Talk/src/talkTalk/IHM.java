/**
 * Interface qui représente une sortie du programme
 */

package talkTalk;

import commun.Personne;

public interface IHM {
	
	/**
	 * Fonction appelée lors de la reception d'un message
	 * @param exp l'expéditeur du message
	 * @param msg le message
	 */
	public void afficherMessageRecu(Personne exp, String msg);
	/**
	 * Afficher le wizz recu.
	 * @param exp l'expediteur du wizz
	 */
	public void afficherWizzRecu(Personne exp);
	/**
	 * Fonction appelée lorsqu'un message est envoyé
	 * @param destinataire le destinataire 
	 * @param msg le message
	 */
	public void afficherMessageEnvoye(Personne destinataire, String msg);
	/**
	 * Fonction appelée lors d'une erreur d'envoi d'un message 
	 * @param destinataire le pseudo du destinataire
	 * @param msg le message
	 */
	public void afficherErreurEnvoi(String destinataire, String msg);
	/**
	 * Fonction appelée lorsqu'un destinataire est inconnu
	 * @param destinataire 
	 */
	public void afficherDestinataireInconnu(String destinataire);
	/**
	 * Appelée pour fermer l'ihm
	 */
	public void exit();
	/**
	 * Appelée pour lancer l'ihm
	 */
	public void start();
}
