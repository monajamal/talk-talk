package ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import talkTalk.TalkTalk;
import utils.Resources;

@SuppressWarnings("serial")
public class IHM extends JFrame {
	
	protected JMenuBar jmb_menubar;
		protected JMenu jm_fichier;
			protected JMenuItem jmi_quitter;
		protected JMenu jm_edition;
			protected JMenuItem jmi_couper;
			protected JMenuItem jmi_copier;
			protected JMenuItem jmi_coller;
			protected JMenuItem jmi_rechercher;
		protected JMenu jm_affichage;
			protected JCheckBoxMenuItem jckmi_contact;
			protected JCheckBoxMenuItem jckmi_image;
		protected JMenu jm_contacts;
			protected JMenuItem jmi_gestionContacts;
		protected JMenu jm_help;
			protected JMenuItem jmi_help;
			protected JMenuItem jmi_aProposDe;
	
	protected JPanel jp_top;
		protected JTabbedPane jtabp_onglet;
	protected JPanel jp_left;
		protected JList jlst_contact;
	protected JPanel jp_center;
		protected JTextPane[] jtp_conversation;protected JScrollPane[] jsp_conversation;
		protected JTextPane jtp_ecrire;protected JScrollPane jsp_ecrire;
	protected JPanel jp_right;
		protected JLabel jl_image;
	protected JPanel jp_bottom;
	
	public IHM(String titre) {
		/** Titre **/
		super(titre);
		/** Look & Feel **/
		//Divers.setLookAndFeel(this);
		/** Événement **/
		ActionListener action = new Event(this);
		/** Barre de Menu **/
		creeBarreDeMenu(action);
		/** Interface **/
		creeInterface(action);
		/** Zone de notification **/
		menuContextuel();
		/** Fenêtre **/
		creeFenetre();
	}
	public void creeBarreDeMenu(ActionListener action) {
		/** Création des éléments     **/
		this.jmb_menubar = new JMenuBar();
			this.jm_fichier = new JMenu("Fichier");
				this.jmi_quitter = new JMenuItem("Quitter",KeyEvent.VK_Q);
			this.jm_edition = new JMenu("Edition");
				this.jmi_couper = new JMenuItem("Couper",KeyEvent.VK_U);
				this.jmi_copier = new JMenuItem("Copier",KeyEvent.VK_C);
				this.jmi_coller = new JMenuItem("Coller",KeyEvent.VK_O);
				this.jmi_rechercher = new JMenuItem("Rechercher",KeyEvent.VK_R);
			this.jm_affichage = new JMenu("Affichage");
				this.jckmi_contact = new JCheckBoxMenuItem("Panneau de contacts");
				this.jckmi_image = new JCheckBoxMenuItem("Panneau d'images");
			this.jm_contacts = new JMenu("Contacts");
				this.jmi_gestionContacts = new JMenuItem("Gestion des contacts");
			this.jm_help = new JMenu("?");
				this.jmi_help = new JMenuItem("Aide",KeyEvent.VK_A);
				this.jmi_aProposDe = new JMenuItem("A propos de ...",KeyEvent.VK_P);
		/** Paramétrage des éléments  **/
		this.jmi_quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		
		this.jmi_couper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		this.jmi_copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		this.jmi_coller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		this.jmi_rechercher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		
		
		this.jmi_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		
		/** Action sur les éléments   **/
		this.jmi_quitter.addActionListener(action);
		this.jmi_help.addActionListener(action);
		this.jmi_aProposDe.addActionListener(action);
		/** Montage des éléments      **/
		this.setJMenuBar(this.jmb_menubar);
			this.jmb_menubar.add(this.jm_fichier);
				this.jm_fichier.add(new JMenuItem("Nouveau",KeyEvent.VK_N));
				this.jm_fichier.insertSeparator(1);
				this.jm_fichier.add(new JMenuItem("Ouvrir"));
				this.jm_fichier.add(new JMenuItem("Enregistrer"));
				this.jm_fichier.add(new JMenuItem("Enregistrer sous..."));
				this.jm_fichier.insertSeparator(5);
				this.jm_fichier.add(this.jmi_quitter);
			this.jmb_menubar.add(this.jm_edition);
				this.jm_edition.add(this.jmi_couper);
				this.jm_edition.add(this.jmi_copier);
				this.jm_edition.add(this.jmi_coller);
				this.jm_edition.insertSeparator(3);
				this.jm_edition.add(this.jmi_rechercher);
			this.jmb_menubar.add(this.jm_affichage);
				this.jm_affichage.add(this.jckmi_contact);
				this.jm_affichage.add(this.jckmi_image);
			this.jmb_menubar.add(this.jm_help);
				this.jm_help.add(this.jmi_help);
				this.jm_help.insertSeparator(1);
				this.jm_help.add(this.jmi_aProposDe);
	}
	public void creeInterface(ActionListener action) {
		String[] contacts = {"Damien","Marie-Hélène","FastWriting","Schtroumpfette","Plop","Tux"};
		/** Création des éléments     **/
		this.jp_top = new JPanel();
			this.jtabp_onglet = new JTabbedPane();
		this.jp_left = new JPanel();
			this.jlst_contact = new JList(contacts);
		this.jp_center = new JPanel(new BorderLayout());
			this.jtp_conversation = new JTextPane[4];this.jsp_conversation = new JScrollPane[4];
			for(int i=0;i<4;i++){this.jtp_conversation[i] = new JTextPane();this.jsp_conversation[i] = new JScrollPane(this.jtp_conversation[i]);}
			this.jtp_ecrire = new JTextPane();this.jsp_ecrire = new JScrollPane(this.jtp_ecrire);
		this.jp_right = new JPanel();
			this.jl_image = new JLabel(Resources.getImageIcon("images/profil.png",TalkTalk.class),SwingConstants.CENTER);
		this.jp_bottom = new JPanel();
		/** Paramétrage des éléments  **/
		this.jtabp_onglet.addTab("Person 1", Resources.getImageIcon("images/dispo.png",TalkTalk.class), this.jtp_conversation[0], "Person 1");
		this.jtabp_onglet.addTab("Person 2", Resources.getImageIcon("images/dispo.png",TalkTalk.class), this.jtp_conversation[1]);
		this.jtabp_onglet.addTab("Person 3", Resources.getImageIcon("images/dispo.png",TalkTalk.class), this.jtp_conversation[2]);
		this.jtabp_onglet.addTab("Person 4", Resources.getImageIcon("images/dispo.png",TalkTalk.class), this.jtp_conversation[3]);
		
		
		
		this.jtp_ecrire.setEditable(true);
		Document d = this.jtp_conversation[1].getDocument();
		try {
			d.insertString(d.getLength(), "Damien: Coucou\n", null);
			d.insertString(d.getLength(), "    MH: Hello", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		
		this.jtabp_onglet.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);//scroll horitonzal des onglets
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
		this.add(this.jp_top,BorderLayout.NORTH);
			this.jp_top.add(this.jtabp_onglet);
		this.add(this.jp_left,BorderLayout.WEST);
			this.jp_left.add(this.jlst_contact);
		this.add(this.jp_center,BorderLayout.CENTER);
			this.jp_center.add(this.jtabp_onglet,BorderLayout.CENTER);
			this.jp_center.add(this.jsp_ecrire,BorderLayout.SOUTH);
		this.add(this.jp_right,BorderLayout.EAST);
			this.jp_right.add(this.jl_image);
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