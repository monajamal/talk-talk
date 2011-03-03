/**
 * Interface qui représente une sortie du programme
 */

package talkTalk;

import commun.Personne;

public interface Affichage {
	
	/**
	 * Fonction appelée lors de la reception d'un message
	 * @param exp l'expéditeur du message
	 * @param msg le message
	 */
	public void afficherMessageRecu(Personne exp, String msg);
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
}
