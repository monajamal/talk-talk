/**
 * Domaine : IHM de démarrage des grandes applications Tux-Team
 * 
 * Auteur  : Damien FINCK
 * Date    : 22/07/2010
 * ----------------------------------------
 * Modifications :
 **/

package ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import utils.Resources;

import components.JPanelBackground;

@SuppressWarnings("serial")
public class IHM_Launch extends JFrame {
	
	static IHM_Launch ihm;
	
	protected JPanelBackground jp_bas;
		protected JLabel jl_chargement;
		protected JProgressBar jpb_chargement;
	Timer minuteur;
	
	public IHM_Launch(String titre,Class<?> c) {
		/** Titre **/
		super(titre);
		/** Accessibilité ihm **/
		IHM_Launch.ihm=this;
		/** Événement **/
		ActionListener action = new Event_Launch(this);
		/** Barre de Menu **/
		creeBarreDeMenu();
		/** Interface **/
		creeInterface(c);
		/** Zone de notification **/
		menuContextuel();
		/** Fen�tre **/
		creeFenetre(c);
		
		/** Minuteur **/
		minuteur = new Timer(300,action);
		minuteur.start();
	}
	public static void creeBarreDeMenu() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public static void creeInterface(Class<?> c) {
		/** Création des éléments     **/
		ihm.jp_bas = new JPanelBackground(null,Resources.getImage("images/accueil.png",c));
			ihm.jl_chargement = new JLabel("Chargement en cours...");
			ihm.jpb_chargement = new JProgressBar();
		/** Paramétrage des éléments  **/
		ihm.jl_chargement.setBounds(330,370,150,20);
		ihm.jpb_chargement.setBounds(470,370,100,20);
		ihm.jl_chargement.setVisible(false);
		ihm.jpb_chargement.setVisible(false);
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
		ihm.add(ihm.jp_bas,BorderLayout.CENTER);
			ihm.jp_bas.add(ihm.jl_chargement);
			ihm.jp_bas.add(ihm.jpb_chargement);
	}
	public static void menuContextuel() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public static void creeFenetre(Class<?> c) {
		ihm.setUndecorated(true);//Affichage de la barre de titre dans la fen�tre
		//ihm.setLayout(new FlowLayout());//Layout par d�faut pour JFrame : BorderLayout
		ihm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Action de fermeture
		ihm.setResizable(false);//Redimensionnement de la fen�tre
		/* Dimension : choisir entre pack() ou setSize(width,height) */
		//ihm.pack();//Auto-dimensionner
		ihm.setSize(600,400);//Dimensionner la fen�tre
		//ihm.setExtendedState(MAXIMIZED_BOTH);//Mettre en mode taille �cran
		/* ----------------------------------------------------- */
		ihm.setLocationRelativeTo(null);//Positionner la fen�tre
		ihm.setIconImage(Resources.getImage("images/icon.png",c));//Images dans la barre des t�ches
		ihm.setVisible(true);//Rendre la fen�tre visible
	}
}
class Event_Launch implements ActionListener {
	IHM_Launch ihm;
	public Event_Launch(IHM_Launch ihm) {
		this.ihm=ihm;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj=arg0.getSource();
		if (obj instanceof Timer) {
			if (ihm.jpb_chargement.getValue()==100) {
				ihm.minuteur.stop();
				//FIXME new IHM_Calendrier("Calendrier");
				ihm.dispose();
			} else if (ihm.jpb_chargement.getValue()==0) {
				ihm.jl_chargement.setVisible(true);
				ihm.jpb_chargement.setVisible(true);
				ihm.jpb_chargement.setValue(1);
				ihm.minuteur.setDelay(10);
			} else
				ihm.jpb_chargement.setValue(ihm.jpb_chargement.getValue()+1);
		}
	}
}