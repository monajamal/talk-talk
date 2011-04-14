package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionListener;

import talkTalk.TalkTalk;
import utils.Resources;

import commun.Contact;
import commun.Groupe;
import commun.Personne;

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
			protected JMenuItem jmi_preferences;
		protected JMenu jm_affichage;
			protected JCheckBoxMenuItem jckmi_contact;
			protected JCheckBoxMenuItem jckmi_image;
		protected JMenu jm_contacts;
			protected JMenuItem jmi_gestionContacts;
		protected JMenu jm_help;
			protected JMenuItem jmi_help;
			protected JMenuItem jmi_aProposDe;
	
	protected JPanel jp_left;
		protected JComboBox jcb_statut;
		protected JList jlst_contacts;protected JScrollPane jsp_contacts;
		protected JButton jb_add;
	protected JSplitPane jslip;
	protected JPanel jp_center;
		protected JTabbedPane jtabp_onglet;
			protected JTextArea jta_log;
	protected JPanel jp_bottom;
		protected JHyperTextLink jl_pub;
		protected JLabel jl_temp;
		protected JCheckBox jchk_fastwriting;
		protected ActionListener action;
	
	int size_split=200;
	Contact[] lstContactAmis;	//Pour afficher la liste des contacts
	
	protected String[] statut = {"Disponible","Absent","Occupé","Se déconnecter"};
	protected String[] images = {"images/statut/dispo.png","images/statut/absent.png","images/statut/occupe.png","images/statut/offline.png"};
	
	public IHM(String titre) {
		/** Titre **/
		super(titre);
		/** Look & Feel **/
		//Divers.setLookAndFeel(this);
		/** Événement **/
		action = new Event(this);
		MouseListener mouse = new Event(this);
		ListSelectionListener listSelection = new Event(this);
		/** Barre de Menu **/
		creeBarreDeMenu(action);
		/** Interface **/
		creeInterface(action,mouse,listSelection);
		/** Zone de notification **/
		menuContextuel();
		/** Fenêtre **/
		creeFenetre();
	}
	public void addToList(String name) {
		Integer[] dataContacts = new Integer[1+TalkTalk.friends.size()+TalkTalk.groupes.size()+TalkTalk.bloques.size()];
		lstContactAmis = new Contact[1+TalkTalk.friends.size()+TalkTalk.groupes.size()+TalkTalk.bloques.size()];
		int i=0;
		for (Personne suivant : TalkTalk.friends.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		for (Groupe suivant : TalkTalk.groupes.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		for (Personne suivant : TalkTalk.bloques.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		dataContacts[i]=i;
		lstContactAmis[i] = new Personne(name,null);
		this.jlst_contacts.setListData(dataContacts);
		// Liste de contact
		ComboBoxRenderer renderer2 = new ComboBoxRenderer(lstContactAmis,TalkTalk.class);
		renderer2.setPreferredSize(new Dimension(16,16));
		this.jlst_contacts.setCellRenderer(renderer2);
	}
	public void createDataListContact() {
		Integer[] dataContacts = new Integer[TalkTalk.friends.size()+TalkTalk.groupes.size()+TalkTalk.bloques.size()];
		lstContactAmis = new Contact[TalkTalk.friends.size()+TalkTalk.groupes.size()+TalkTalk.bloques.size()];
		int i=0;
		for (Personne suivant : TalkTalk.friends.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		for (Groupe suivant : TalkTalk.groupes.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		for (Personne suivant : TalkTalk.bloques.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		// Liste de contact
		ComboBoxRenderer renderer2 = new ComboBoxRenderer(lstContactAmis,TalkTalk.class);
		renderer2.setPreferredSize(new Dimension(16,16));
		this.jlst_contacts.setCellRenderer(renderer2);
		
		for (i=0;i<this.jtabp_onglet.getTabCount();i++) {
			ButtonTabComponent btc=(ButtonTabComponent)this.jtabp_onglet.getTabComponentAt(i);
			String name=this.jtabp_onglet.getTitleAt(i);
			Contact c = null;
			for (Personne suivant : TalkTalk.friends.values()) {
				if (name.equals(suivant.getName())) {
					c=suivant;
				}
			}
			for (Groupe suivant : TalkTalk.groupes.values()) {
				if (name.equals(suivant.getName())) {
					c=suivant;
				}
			}
			for (Personne suivant : TalkTalk.bloques.values()) {
				if (name.equals(suivant.getName())) {
					c=suivant;
				}
			}
			if (c!=null) {
				btc.bidule(Resources.getImageIcon(c.getImg(), TalkTalk.class));
			}
		}
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
				this.jmi_preferences = new JMenuItem("Préférences",KeyEvent.VK_P);
			this.jm_affichage = new JMenu("Affichage");
				this.jckmi_contact = new JCheckBoxMenuItem("Panneau de contacts",true);
				this.jckmi_image = new JCheckBoxMenuItem("Panneau d'images",true);
			this.jm_contacts = new JMenu("Contacts");
				this.jmi_gestionContacts = new JMenuItem("Gestion des contacts");
			this.jm_help = new JMenu("?");
				this.jmi_help = new JMenuItem("Aide",KeyEvent.VK_A);
				this.jmi_aProposDe = new JMenuItem("A propos de ...",KeyEvent.VK_P);
		/** Paramétrage des éléments  **/
		// Fichier
		this.jmi_quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		// Edition
		this.jmi_couper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		//this.jmi_couper.setEnabled(false);
		this.jmi_copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		//this.jmi_coller.setEnabled(false);
		this.jmi_coller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		this.jmi_rechercher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		this.jmi_preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		// Help
		this.jmi_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		/** Action sur les éléments   **/
		// Fichier
		this.jmi_quitter.addActionListener(action);
		// Edition
		this.jmi_couper.addActionListener(action);
		this.jmi_copier.addActionListener(action);
		this.jmi_coller.addActionListener(action);
		this.jmi_rechercher.addActionListener(action);
		this.jmi_preferences.addActionListener(action);
		// Affichage
		this.jckmi_contact.addActionListener(action);
		this.jckmi_image.addActionListener(action);
		// Contacts
		this.jmi_gestionContacts.addActionListener(action);
		// Help
		this.jmi_help.addActionListener(action);
		this.jmi_aProposDe.addActionListener(action);
		/** Montage des éléments      **/
		this.setJMenuBar(this.jmb_menubar);
			this.jmb_menubar.add(this.jm_fichier);
				this.jm_fichier.add(this.jmi_quitter);
			this.jmb_menubar.add(this.jm_edition);
				this.jm_edition.add(this.jmi_couper);
				this.jm_edition.add(this.jmi_copier);
				this.jm_edition.add(this.jmi_coller);
				this.jm_edition.insertSeparator(3);
				this.jm_edition.add(this.jmi_rechercher);
				this.jm_edition.insertSeparator(5);
				this.jm_edition.add(this.jmi_preferences);
			this.jmb_menubar.add(this.jm_affichage);
				this.jm_affichage.add(this.jckmi_contact);
				this.jm_affichage.add(this.jckmi_image);
			this.jmb_menubar.add(this.jm_contacts);
				this.jm_contacts.add(this.jmi_gestionContacts);
			this.jmb_menubar.add(this.jm_help);
				this.jm_help.add(this.jmi_help);
				this.jm_help.insertSeparator(1);
				this.jm_help.add(this.jmi_aProposDe);
	}
	public void creeInterface(ActionListener action,MouseListener mouse,ListSelectionListener listSelection) {
		/*Disponibilité*/
		Integer[] dataStatut = new Integer[statut.length];
		for (int i=0;i<statut.length;i++) dataStatut[i]=i;
		/*liste de contacts*/
		Integer[] dataContacts = new Integer[TalkTalk.friends.size()+TalkTalk.groupes.size()+TalkTalk.bloques.size()];
		lstContactAmis = new Contact[TalkTalk.friends.size()+TalkTalk.groupes.size()+TalkTalk.bloques.size()];
		int i=0;
		for (Personne suivant : TalkTalk.friends.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		for (Groupe suivant : TalkTalk.groupes.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		for (Personne suivant : TalkTalk.bloques.values()) {
			dataContacts[i]=i;
			lstContactAmis[i]=suivant;
			i++;
		}
		
		/** Création des éléments     **/
		this.jp_left = new JPanel(new BorderLayout());
			this.jcb_statut = new JComboBox(dataStatut);
			this.jlst_contacts = new JList(dataContacts);this.jsp_contacts = new JScrollPane(this.jlst_contacts);
			this.jb_add = new JButton("Ajouter un contact");
		this.jp_center = new JPanel(new BorderLayout());
			this.jtabp_onglet = new JTabbedPane();
				this.jta_log = new JTextArea("\t\tFenêtre de log\n\t\t-----------------------\n");
		this.jp_bottom = new JPanel(new BorderLayout());
			this.jl_pub = new JHyperTextLink("Publicité de nos partenaires !","http://code.google.com/p/talk-talk/");
			this.jl_temp = new JLabel("   ["+TalkTalk.pseudo+"-"+TalkTalk.adressePerso+"-"+TalkTalk.portRegistry+"]   ");
			this.jchk_fastwriting = new JCheckBox("Utiliser FastWriting");
		this.jslip = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,this.jp_left,this.jp_center);/* JSplitPane à la fin */
		
		/** Paramétrage des éléments  **/
		this.jslip.setDividerLocation(size_split);
		// Liste de disponibilité
		ComboBoxRenderer renderer = new ComboBoxRenderer(statut,images,TalkTalk.class);
		renderer.setPreferredSize(new Dimension(16,16));
		this.jcb_statut.setRenderer(renderer);
		// Liste de contact
		ComboBoxRenderer renderer2 = new ComboBoxRenderer(lstContactAmis,TalkTalk.class);
		renderer2.setPreferredSize(new Dimension(16,16));
		this.jlst_contacts.setCellRenderer(renderer2);
		// Panneau d'onglets
		this.jtabp_onglet.add("log",this.jta_log);
		this.jtabp_onglet.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);//scroll horizontal des onglets
		
		/** Action sur les éléments   **/
		this.jcb_statut.addActionListener(action);
		this.jlst_contacts.addMouseListener(mouse);
		this.jb_add.addActionListener(action);
		/** Montage des éléments      **/
		//zone splitter
			this.jp_left.add(this.jcb_statut,BorderLayout.NORTH);
			this.jp_left.add(this.jlst_contacts,BorderLayout.CENTER);
			this.jp_left.add(this.jb_add,BorderLayout.SOUTH);
		//zone splitter
			this.jp_center.add(this.jtabp_onglet,BorderLayout.CENTER);
		this.add(this.jp_bottom,BorderLayout.SOUTH);
			this.jp_bottom.add(this.jl_pub,BorderLayout.WEST);
			this.jp_bottom.add(this.jl_temp,BorderLayout.CENTER);
			this.jp_bottom.add(this.jchk_fastwriting,BorderLayout.EAST);
		this.add(this.jslip,BorderLayout.CENTER);
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
		this.setSize(700,550);												// Dimensionner la fenêtre
		//this.setExtendedState(MAXIMIZED_BOTH);							// Mettre en mode taille écran
		/** ----------------------------------------------------- **/
		this.setLocationRelativeTo(null);									// Positionner la fenêtre
		this.setIconImage(Resources.getImage("images/icon.png",TalkTalk.class));// Image dans la barre des tâches
		this.setVisible(true);												// Rendre la fenêtre visible
	}
	public int ouvrirOngletContact(Contact ct)
	{
		JConversation jc = new JConversation(ct);
		ButtonTabComponent btc = new ButtonTabComponent(Resources.getImageIcon(ct.getImg(),TalkTalk.class),jtabp_onglet);
			
		// Ajout d'un onglet
		jtabp_onglet.addTab(ct.getName(),null,jc);
		// indice de l'onglet a selectionner
		int select=jtabp_onglet.getTabCount()-1;
		// action wizz
		jc.jb_wizz.addActionListener(action);
		jc.addActionListener(action);
		//jc.jb_bloque.addActionListener(ihm.getActionListener());
		// Image onglet + croix pour fermer
		jtabp_onglet.setTabComponentAt(select,btc);

		// selectionne l'onglet
		jtabp_onglet.setSelectedIndex(select);
		((JConversation)jtabp_onglet.getComponentAt(select)).jtp_ecrire.requestFocusInWindow();
		return(select);
	}
}