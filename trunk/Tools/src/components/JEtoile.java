/**
 * Domaine : Composant graphique pour la gestion des note avec graphisme en étoile
 * 
 * Auteur  : Damien
 * Date    : 24/05/2010
 * ----------------------------------------
 * Ressources : images/
 *                     white.png et yellow.png
 * ----------------------------------------
 * Modifications :
 **/

package components;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Resources;

@SuppressWarnings("serial")
public class JEtoile extends JPanel {
	
	public final static int ETOILE=5;
	protected JPanel jp;
		protected JLabel[] jl;
	private int value;
	
	public JEtoile() {
		this(-1);
	}
	public JEtoile(int value) {
		if (value<-1 || value>5) value=-1;
		this.value=value;
		
		/** Évènement **/
		MouseListener mouse = new Event_JEtoile(this);
		/** Barre de Menu **/
		creeBarreDeMenu();
		/** Interface **/
		creeInterface(mouse);
		/** Zone de notification **/
		menuContextuel();
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	public void creeBarreDeMenu() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public void creeInterface(MouseListener mouse) {
		/** Création des éléments     **/
		this.jp = new JPanel(new GridLayout(1,5));
			this.jl = new JLabel[ETOILE];
			for (int i=0;i<ETOILE;i++) this.jl[i] = new JLabel(Resources.getImageIcon((i<this.value)?"images/yellow.png":"images/white.png",JEtoile.class));
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		for (int i=0;i<ETOILE;i++) this.jl[i].addMouseListener(mouse);
		this.addMouseListener(mouse);
		/** Montage des éléments      **/
		this.add(this.jp);
		for (int i=0;i<ETOILE;i++) this.jp.add(this.jl[i]);
	}
	public void menuContextuel() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public void setValue(int value) {
		this.value = value;
		refresh();
	}
	public int getValue() {
		return value;
	}
	private void refresh() {
		for (int i=0;i<ETOILE;i++)
			this.jl[i].setIcon(Resources.getImageIcon((i<this.getValue())?"images/yellow.png":"images/white.png",JEtoile.class));
	}
}
class Event_JEtoile implements MouseListener {
	JEtoile ihm;
	public Event_JEtoile(JEtoile ihm) {
		this.ihm=ihm;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Object obj=arg0.getSource();
		if (obj instanceof JLabel) {
			JLabel jl=(JLabel)obj;
			for (int i=0;i<JEtoile.ETOILE;i++) if (jl==ihm.jl[i]) ihm.setValue(i+1);
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		//~OnMouseOver~ : Je met en jaune de 0 à courant et à blanc le reste
		Object obj=arg0.getSource();
		if (obj instanceof JLabel) {
			JLabel jl=(JLabel)obj;
			int j=0;
			for (int i=0;i<JEtoile.ETOILE;i++) if (jl==ihm.jl[i]) j=i;
			
			for (int i=0;i<JEtoile.ETOILE;i++) 
				if (i<=j) ihm.jl[i].setIcon(Resources.getImageIcon("images/yellow.png",JEtoile.class));
				else ihm.jl[i].setIcon(Resources.getImageIcon("images/white.png",JEtoile.class));
		}
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		//~OnMouseOut~ : Je remet la note d'origine
		Object obj=arg0.getSource();		
		if (obj instanceof JLabel) {
			for (int i=0;i<JEtoile.ETOILE;i++)
				ihm.jl[i].setIcon(Resources.getImageIcon((i<ihm.getValue())?"images/yellow.png":"images/white.png",JEtoile.class));
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}