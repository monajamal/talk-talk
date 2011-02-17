package ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import talkTalk.TalkTalk;
import utils.Divers;
import utils.Resources;

@SuppressWarnings("serial")
public class IHM extends JFrame {
	
	protected JPanel jp_top;
		protected JTabbedPane jtabp_onglet;
	protected JPanel jp_left;
		protected JLabel jl_image;
	protected JPanel jp_center;
		protected JTextPane jtp_conversation;protected JScrollPane jsp_conversation;
	protected JPanel jp_bottom;
	
	public IHM(String titre) {
		/** Titre **/
		super(titre);
		/** Look & Feel **/
		Divers.setLookAndFeel(this);
		/** Événement **/
		ActionListener action = new Event(this);
		/** Barre de Menu **/
		creeBarreDeMenu();
		/** Interface **/
		creeInterface(action);
		/** Zone de notification **/
		menuContextuel();
		/** Fenêtre **/
		creeFenetre();
	}
	public void creeBarreDeMenu() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public void creeInterface(ActionListener action) {
		/** Création des éléments     **/
		this.jp_top = new JPanel();
			this.jtabp_onglet = new JTabbedPane();
		this.jp_left = new JPanel();
			this.jl_image = new JLabel(Resources.getImageIcon("images/profil.png",TalkTalk.class),SwingConstants.CENTER);
		this.jp_center = new JPanel();
			this.jtp_conversation = new JTextPane();this.jsp_conversation = new JScrollPane(this.jtp_conversation);
		this.jp_bottom = new JPanel();
		/** Paramétrage des éléments  **/
		//this.jtabp_onglet.addTab(title, component)
		
		this.jtp_conversation.setEditable(false);
		Document d = this.jtp_conversation.getDocument();
		try {
			d.insertString(d.getLength(), "plop", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
		this.add(this.jp_top,BorderLayout.NORTH);
			this.jp_top.add(this.jtabp_onglet);
		this.add(this.jp_left,BorderLayout.WEST);
			this.jp_left.add(this.jl_image);
		this.add(this.jp_center,BorderLayout.CENTER);
			this.jp_center.add(this.jsp_conversation);
		this.add(this.jp_bottom,BorderLayout.SOUTH);
	}
	public void menuContextuel() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public void creeFenetre() {
		this.setUndecorated(false);											// Affichage de la barre de titre dans la fenêtre
		//this.setLayout(new FlowLayout());									// Layout par défaut pour JFrame : BorderLayout
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				// Action de fermeture
		this.setResizable(true);											// Fenêtre redimensionnable
		/** Dimension : choisir entre pack() ou setSize(width,height) **/
		//this.pack();														// Auto-dimensionner
		this.setSize(500,375);												// Dimensionner la fenêtre
		//this.setExtendedState(MAXIMIZED_BOTH);							// Mettre en mode taille écran
		/** ----------------------------------------------------- **/
		this.setLocationRelativeTo(null);									// Positionner la fenêtre
		this.setIconImage(Resources.getImage("images/icon.png",TalkTalk.class));// Image dans la barre des tâches
		this.setVisible(true);												// Rendre la fenêtre visible
	}
}