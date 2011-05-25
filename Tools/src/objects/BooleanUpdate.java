/**
 * @Description : Tout les objets généraux
 * 
 * @Auteur  : Damien
 * @Date    : 22/07/2010
 * ----------------------------------------
 * Modifications :
 **/

package objects;

/** 
 * Cette classe est un booléen transformer en objet pour pouvoir être modifié lors de l'appel d'une classe à l'autre.
 * Exemple : pour que IHM_AProposDe puissent dire à l'application si une mise-à-jour est a installé
 */
public class BooleanUpdate {
	private boolean value;
	public BooleanUpdate() {
		this(false);
	}
	public BooleanUpdate(boolean value) {
		this.setValue(value);
	}
	public boolean getValue() {
		return this.value;
	}
	public void setValue(boolean value) {
		this.value=value;
	}
}