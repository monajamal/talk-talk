package ihm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
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
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionListener;

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
		protected JList jlst_contact;protected JScrollPane jsp_contact;
	protected JSplitPane jslip;
	protected JPanel jp_center;
		protected JTabbedPane jtabp_onglet;
			protected JTextArea jta_log;
			protected Vector<JConversation> jc_fenetre;
	protected JPanel jp_bottom;
		protected JHyperTextLink jl_pub;
		protected JLabel jl_temp;
		protected JCheckBox jchk_fastwriting;
	
	int size_split=200;
	
	protected String[] statut = {"Disponible","Absent","Occupé","Invisible","Se déconnecter"};
	protected String[] images = {"images/statut/dispo.png","images/statut/absent.png","images/statut/occupe.png","images/statut/invisible.png","images/statut/offline.png"};
	
	//STATIC DATA
	//protected String[] contacts = {"Damien","Marie-Hélène","FastWriting","Schtroumpfette","Plop","Groupe 1","Tux"};
	//protected String[] temp = {"images/statut/dispo.png","images/statut/absent.png","images/statut/occupe.png","images/statut/offline.png","images/statut/dispo.png","images/groupe.png","images/statut/dispo.png"};
	
	public IHM(String titre) {
		/** Titre **/
		super(titre);
		/** Look & Feel **/
		//Divers.setLookAndFeel(this);
		/** Événement **/
		ActionListener action = new Event(this);
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
		/** Affichage **/
		new Affich(this);
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
		this.jmi_couper.setEnabled(false);
		this.jmi_copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		this.jmi_coller.setEnabled(false);
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
		Integer[] dataContact = new Integer[MainTalkTalk.ami.size()];
		String[] nomContact = new String[MainTalkTalk.ami.size()];
		String[] imgContact = new String[MainTalkTalk.ami.size()];
		for (int i=0;i<MainTalkTalk.ami.size();i++) {
			dataContact[i]=i;
			nomContact[i]=MainTalkTalk.ami.get(i).getName();
			imgContact[i]=MainTalkTalk.ami.get(i).getImg();
		}
		
		
		/** Création des éléments     **/
		this.jp_left = new JPanel(new BorderLayout());
			this.jcb_statut = new JComboBox(dataStatut);
			this.jlst_contact = new JList(dataContact);this.jsp_contact = new JScrollPane(this.jlst_contact);
		this.jp_center = new JPanel(new BorderLayout());
			this.jtabp_onglet = new JTabbedPane();
				this.jta_log = new JTextArea("Log");
				this.jc_fenetre = new Vector<JConversation>();
		this.jp_bottom = new JPanel(new BorderLayout());
			this.jl_pub = new JHyperTextLink("Publicité de nos partenaires !","http://code.google.com/p/talk-talk/");
			this.jl_temp = new JLabel("    [hostname + ip + port]    ");
			this.jchk_fastwriting = new JCheckBox("Utiliser FastWriting");
		this.jslip = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,this.jp_left,this.jp_center);/* JSplitPane à la fin */
		
		/** Paramétrage des éléments  **/
		this.jslip.setDividerLocation(size_split);
		// Panneau de gauche
		//this.jp_left.setPreferredSize(new Dimension(300,0));
		// Liste de disponibilité
		ComboBoxRenderer renderer = new ComboBoxRenderer(statut,images,TalkTalk.class);
		renderer.setPreferredSize(new Dimension(16,16));
		this.jcb_statut.setRenderer(renderer);
		// Liste de contact
		ComboBoxRenderer renderer2 = new ComboBoxRenderer(nomContact,imgContact,TalkTalk.class);
		renderer2.setPreferredSize(new Dimension(16,16));
		this.jlst_contact.setCellRenderer(renderer2);
		// Panneau d'onglets
		/*Document d = this.jtp_conversation[1].getDocument();
		try {
			d.insertString(d.getLength(), "Damien: Coucou\n", null);
			d.insertString(d.getLength(), "    MH: Hello", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}*/
		this.jtabp_onglet.add("log",this.jta_log);
		//this.jc_fenetre.add(new JConversation("MH",null,Resources.getImageIcon("images/tux.png",TalkTalk.class),Resources.getImageIcon("images/schtroumpfette.png",TalkTalk.class)));
		//this.jc_fenetre.add(new JConversation("Tux",null,Resources.getImageIcon("images/tux.png",TalkTalk .class),Resources.getImageIcon("images/profil.png",TalkTalk.class)));
		//this.jc_fenetre.add(new JConversation("Inconnu",null,Resources.getImageIcon("images/tux.png",TalkTalk .class),Resources.getImageIcon("images/profil.png",TalkTalk.class)));
		actuTab(action);
		
		this.jtabp_onglet.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);//scroll horizontal des onglets
		/** Action sur les éléments   **/
		this.jlst_contact.addMouseListener(mouse);
		//this.jlst_contact.addListSelectionListener(listSelection);
		
		//this.jc_fenetre.get(0).jb_wizz.addActionListener(action);
		/** Montage des éléments      **/
		//this.add(this.jp_left,BorderLayout.WEST);
			this.jp_left.add(this.jcb_statut,BorderLayout.NORTH);
			this.jp_left.add(this.jsp_contact,BorderLayout.CENTER);
		//this.add(this.jp_center,BorderLayout.CENTER);
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
	
	public void actuTab(ActionListener action) {
		for (int i=0;i<this.jc_fenetre.size();i++) {
			//this.jlst_contact
			
			//this.jc_fenetre.get(i).getIndex()
			
			this.jtabp_onglet.addTab(this.jc_fenetre.get(i).getName(),
					Resources.getImageIcon("images/statut/dispo.png",TalkTalk.class),
					this.jc_fenetre.get(i),this.jc_fenetre.get(i).getName());
			
			this.jc_fenetre.get(i).jb_wizz.addActionListener(action);
		}
	}
	
	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		
		private String[] textes;
		private String[] images;
		ImageIcon[] img;
		
		public ComboBoxRenderer(String[] textes,String[] images,Class<?> c) {
			// Vérification qu'il n'y pas de différence de taille entre les tableaux textes et images
			if (textes.length>images.length) {
				int i;
				String[] tmp = new String[textes.length];
				for (i=0;i<images.length;i++) tmp[i]=images[i];
				for (int j=i;j<tmp.length;j++) tmp[i]=null;
				images=tmp;
			} else if (images.length>textes.length) {
				int i;
				String[] tmp = new String[images.length];
				for (i=0;i<textes.length;i++) tmp[i]=textes[i];
				for (int j=i;j<tmp.length;j++) tmp[i]=null;
				textes=tmp;
			}
			// Enregistrement des textes et images pour le composant
			this.textes=textes;
			this.images=images;
			// Génération des ImageIcon a partir de l'adresse
			this.img = new ImageIcon[textes.length];
			for (int i=0;i<textes.length;i++) {
				if (this.images[i]!=null) {
					this.img[i]=Resources.getImageIcon(images[i],c);
				} else this.img[i]=null;
			}
			
			// Paramétrage général du composant
			this.setOpaque(true);
			this.setHorizontalAlignment(LEFT);
			this.setVerticalAlignment(CENTER);
		}
		/*
		 * Cette méthode trouve le texte et l'image correspond à l'élément sélectionner,
		 * et retourne le composant avec le texte et l'image.
		 */
		public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
			// L'index Sélectionner (le paramètre "index" n'est pas toujours valide, il faut donc se fier a "value");
			int selectedIndex = ((Integer)value).intValue();
			// Couleur de l'élément en fonction de s'il est sélectionner ou pas
			if (isSelected) {
				this.setBackground(list.getSelectionBackground());
				this.setForeground(list.getSelectionForeground());
			} else {
				this.setBackground(list.getBackground());
				this.setForeground(list.getForeground());
			}
			// Attribuer l'image et le texte (ne pose pas de problème si l'un ou l'autre est null);
			this.setIcon(this.img[selectedIndex]);
			this.setText(this.textes[selectedIndex]);
			// Renvoi le composant
			return this;
		}
	}
}