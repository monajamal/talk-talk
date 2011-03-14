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
	String afficherMessageRecu = "> %expediteur : %message";
	public void afficherMessageRecu(Personne expediteur, String message);
	/**
	 * Afficher le wizz reçu.
	 * @param exp l'expediteur du wizz
	 */
	String afficherWizzRecu = "%expediteur vous a envoyé un wizz";
	public void afficherWizzRecu(Personne expediteur);
	/**
	 * Fonction appelée lorsqu'un message est envoyé
	 * @param destinataire le destinataire 
	 * @param msg le message
	 */
	String afficherMessageEnvoye = "Envoi de %message à %destinataire";
	public void afficherMessageEnvoye(Personne destinataire, String message);
	/**
	 * Fonction appelée lors d'une erreur d'envoi d'un message 
	 * @param destinataire le pseudo du destinataire
	 * @param msg le message
	 */
	String afficherErreurEnvoi = "Erreur : Le message suivant n'a pas pu être remis à %destinataire : \n%message";
	public void afficherErreurEnvoi(String destinataire, String message);
	/**
	 * Fonction appelée lorsqu'un destinataire est inconnu
	 * @param destinataire 
	 */
	String afficherDestinataireInconnu = "Destinataire inconnu : %destinataire";
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