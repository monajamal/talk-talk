package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import talkTalk.TalkTalk;
import utils.Resources;

@SuppressWarnings("serial")
public class JConversation extends JPanel {
	
	private String name;
	private Document doc;
	private Icon icon_perso;
	private Icon icon_ami;
	private Color couleur;
	
	protected JTextPane jtp_conversation;protected JScrollPane jsp_conversation;
	protected JPanel jp_right;
		protected JLabel jl_image_ami;
		protected JLabel jl_temp;
		protected JLabel jl_image_perso;
	protected JPanel jp_write;
		protected JTextPane jtp_ecrire;protected JScrollPane jsp_ecrire;
		protected JPanel jp_cmd;
			protected JButton jb_wizz;
			protected JCheckButton jchkb_gras;
			protected JCheckButton jchkb_italique;
			protected JCheckButton jchkb_souligne;
			protected JButton jb_police;
			protected JButton jb_couleur;
			protected JSmiley[] js_smiley;
			protected JCoolButton jb_send;
	
	
	public JConversation(String name,Document doc,Icon icon_perso,Icon icon_ami) {
		this.setName(name);
		this.setDoc(doc);
		this.setIcon_perso(icon_perso);
		this.setIcon_ami(icon_ami);
		this.setCouleur(Color.BLACK);
		/** Évènement **/
		ActionListener action = new Event_JConversation(this);
		KeyListener key = new Event_JConversation(this);
		//MouseListener mouse = new Event_JEtoile(this);
		/** Barre de Menu **/
		creeBarreDeMenu();
		/** Interface **/
		creeInterface(action,key);
		/** Zone de notification **/
		menuContextuel();
		
		/*
		Style defaut = this.jtp_ecrire.getStyle("default");
		Style style1 = this.jtp_ecrire.addStyle("style1", defaut);
		StyleConstants.setFontFamily(style1, "Comic sans MS");
		Style style2 = this.jtp_ecrire.addStyle("style2", style1);
		StyleConstants.setForeground(style2, Color.RED);
		StyleConstants.setFontSize(style2, 25);
		*/
		
		//DefaultStyledDocument doc = (DefaultStyledDocument) monComposantTexte.getDocument ();
		//doc.insertString (offset, "texte à ajouter", /** [un style d'écriture] */);
		
		/*HTMLDocument html = new HTMLDocument();
		//html.ge
		try {
			System.out.println(html.getText(0, html.getLength()));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//html.getDefaultRootElement().
	}
	public void creeBarreDeMenu() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public void creeInterface(ActionListener action,KeyListener key) {
		String[] smile = {":)","<3","ø","(","()","!","*"};
		/** Création des éléments     **/
		this.jtp_conversation = new JTextPane();this.jsp_conversation = new JScrollPane(this.jtp_conversation);
		this.jp_right = new JPanel(new BorderLayout());
			this.jl_image_ami = new JLabel(this.icon_ami,SwingConstants.CENTER);
			this.jl_temp = new JLabel("<html><p>Alias : "+name+"</p><p>Pseudo : ?</p><p>IP : ?</p><p>Port : ?</p></html>");
			this.jl_image_perso = new JLabel(this.icon_perso,SwingConstants.CENTER);
		this.jp_write = new JPanel(new BorderLayout());
			this.jtp_ecrire = new JTextPane();this.jsp_ecrire = new JScrollPane(this.jtp_ecrire);
			this.jp_cmd = new JPanel();
				this.jchkb_gras = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_1.png",TalkTalk.class));
				this.jchkb_italique = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_2.png",TalkTalk.class));
				this.jchkb_souligne = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_3.png",TalkTalk.class));
				this.jb_police = new JButton(Resources.getImageIcon("images/cmd/cmd_4.png",TalkTalk.class));
				this.jb_couleur = new JButton(Resources.getImageIcon("images/cmd/cmd_5.png",TalkTalk.class));
				this.jb_wizz = new JButton("wizz");
				this.js_smiley = new JSmiley[smile.length];
				for (int i=0;i<this.js_smiley.length;i++) this.js_smiley[i] = new JSmiley(Resources.getImageIcon("images/smiley/smiley_"+(i+1)+".png",TalkTalk.class),smile[i]);
				this.jb_send = new JCoolButton("Envoyer");
		
		//this.jcc = new JColorChooser(Color.BLACK);
		/** Paramétrage des éléments  **/
		// paramètres du JColorChooser
		/*AbstractColorChooserPanel[] panels = this.jcc.getChooserPanels();
		for (int i=0; i<panels.length; i++) {
		  String tmpString = panels[i].getClass().getName();
		  if (tmpString.equals("javax.swing.colorchooser.DefaultRGBChooserPanel") ||
				  tmpString.equals("javax.swing.colorchooser.DefaultSwatchChooserPanel")) {//DefaultHSBChooserPanel
			  this.jcc.removeChooserPanel(panels[i]);
		  }
		}*/
		
		this.jtp_ecrire.setForeground(couleur);
		
		this.setLayout(new BorderLayout());
		//this.jtp_conversation.setDocument(this.doc);
		
		this.jl_image_ami.setPreferredSize(new Dimension(128,128));
		this.jl_image_perso.setPreferredSize(new Dimension(128,128));
		
		
		this.jtp_conversation.setEditable(false);
		this.jtp_ecrire.setEditable(true);
		/** Action sur les éléments   **/
		this.jchkb_gras.addActionListener(action);
		this.jchkb_italique.addActionListener(action);
		this.jchkb_souligne.addActionListener(action);
		this.jb_police.addActionListener(action);
		this.jb_couleur.addActionListener(action);
		
		for (int i=0;i<this.js_smiley.length;i++) this.js_smiley[i].addActionListener(action);
		
		this.jb_wizz.addActionListener(action);
		this.jtp_ecrire.addKeyListener(key);
		this.jb_send.addActionListener(action);
		/** Montage des éléments      **/
		this.add(this.jsp_conversation,BorderLayout.CENTER);
		this.add(this.jp_right,BorderLayout.EAST);
			this.jp_right.add(this.jl_image_ami,BorderLayout.NORTH);
			this.jp_right.add(this.jl_temp,BorderLayout.CENTER);
			this.jp_right.add(this.jl_image_perso,BorderLayout.SOUTH);
		this.add(this.jp_write,BorderLayout.SOUTH);
			this.jp_write.add(this.jsp_ecrire,BorderLayout.CENTER);
			this.jp_write.add(this.jp_cmd,BorderLayout.SOUTH);
				this.jp_cmd.add(this.jb_wizz);
				this.jp_cmd.add(this.jchkb_gras);
				this.jp_cmd.add(this.jchkb_italique);
				this.jp_cmd.add(this.jchkb_souligne);
				this.jp_cmd.add(this.jb_police);
				this.jp_cmd.add(this.jb_couleur);
				for (int i=0;i<this.js_smiley.length;i++) this.jp_cmd.add(this.js_smiley[i]);
				this.jp_cmd.add(this.jb_send);
	}
	public void menuContextuel() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	public Document getDoc() {
		return doc;
	}
	public void setIcon_perso(Icon icon_perso) {
		this.icon_perso = icon_perso;
	}
	public Icon getIcon_perso() {
		return icon_perso;
	}
	public void setIcon_ami(Icon icon_ami) {
		this.icon_ami = icon_ami;
	}
	public Icon getIcon_ami() {
		return icon_ami;
	}
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	public Color getCouleur() {
		return couleur;
	}
}
class Event_JConversation implements ActionListener, KeyListener {
	JConversation jc;
	public Event_JConversation(JConversation jc) {
		this.jc=jc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof JSmiley) {
			JSmiley js = (JSmiley)obj;
			
			//System.out.println("ici");
			jc.jtp_ecrire.insertIcon(js.getIcon());//FIXME : cette fonction empeche de mettre 2 fois de suite le même smiley
			Component c =new JButton("plop");
			jc.jtp_ecrire.insertComponent(c);
			//jc.jtp_ecrire.getDocument().insertString(jc.jtp_ecrire.getDocument().getLength(), js.getToolTipText(), null);
			
		} else if (obj instanceof JButton) {
			JButton jb = (JButton)obj;
			if (jb==jc.jb_wizz) {
				Style style2 = jc.jtp_ecrire.getStyle("default");
				StyleConstants.setForeground(style2, Color.RED);
				StyleConstants.setFontSize(style2, 10);
				try {
					jc.jtp_conversation.getDocument().insertString(jc.jtp_conversation.getDocument().getLength(),"Vous avez wizz votre partenaire ! Il vous en remercie !\n",style2);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				//Component c =new JButton("Rejouer ?");
				//jc.jtp_conversation.insertComponent(jc.jb_wizz);
			} else if (jb==jc.jb_send) {
				//System.out.println("là");
				//jc.jtp_ecrire.cut();jc.jtp_conversation.paste();
				
				
				Style defaut = jc.jtp_ecrire.getStyle("default");
				Style style1 = jc.jtp_ecrire.addStyle("style1", defaut);
				StyleConstants.setFontFamily(style1, "Comic sans MS");
				StyleConstants.setBold(style1, true);
				/*Style style2 = jc.jtp_ecrire.addStyle("style2", style1);
				StyleConstants.setForeground(style2, Color.RED);
				StyleConstants.setFontSize(style2, 25);*/
				
				
				try {
					jc.jtp_conversation.getDocument().insertString(
							jc.jtp_conversation.getDocument().getLength(),
							"Damien : ",
							style1);
					jc.jtp_conversation.getDocument().insertString(
							jc.jtp_conversation.getDocument().getLength(),
							jc.jtp_ecrire.getText()+"\n",
							defaut);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				/*try {
					jc.jtp_conversation.getDocument().insertString(jc.jtp_conversation.getDocument().getLength(),jc.jtp_ecrire.getText()+"\n",null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}*/
				jc.jtp_ecrire.setText("");
			} else if (jb==jc.jchkb_gras || jb==jc.jchkb_italique) {	// Changement de gras et italique
				Font f=jc.jtp_ecrire.getFont();
				if (jc.jchkb_gras.isEnfonce() && jc.jchkb_italique.isEnfonce()) {
					f=f.deriveFont(Font.BOLD|Font.ITALIC);
				} else if (jc.jchkb_gras.isEnfonce()) {
					f=f.deriveFont(Font.BOLD);
				} else if (jc.jchkb_italique.isEnfonce()) {
					f=f.deriveFont(Font.ITALIC);
				} else {
					f=f.deriveFont(Font.PLAIN);
				}
				jc.jtp_ecrire.setFont(f);
			} else if (jb==jc.jchkb_souligne) {
				
			} else if (jb==jc.jb_police) {
				GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
				/** Liste de toutes les polices : */
				@SuppressWarnings("unused")
				Font[] polices = environment.getAllFonts();
				/** Liste des noms de toutes les polices : */
				String[] nomPolices = environment.getAvailableFontFamilyNames();
				for (int i=0;i<nomPolices.length;i++) {
					System.out.println(nomPolices[i]);
				}
			} else if (jb==jc.jb_couleur) {
				Color newColor = JColorChooser.showDialog(jc,"Choose Background Color",jc.getCouleur());
				if (newColor != null) {
					jc.setCouleur(newColor);
					jc.jtp_ecrire.setForeground(newColor);
				}
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// si on fait ENTER (KeyCode=10) et que Ctrl (Modifiers==1) ou Maj (Modifiers==2) alors on rajoute une ligne
		//if (arg0.getKeyCode()==10 && (arg0.getModifiers()==1 || arg0.getModifiers()==2)) {
		if (arg0.getKeyCode()==10 && (arg0.isControlDown() || arg0.isShiftDown())) {
			try {
				jc.jtp_ecrire.getDocument().insertString(jc.jtp_ecrire.getDocument().getLength(),"\n",null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else if (arg0.getKeyCode()==10) {//si on fait juste un ENTER, on valide
			try {
				jc.jtp_conversation.getDocument().insertString(jc.jtp_conversation.getDocument().getLength(),jc.jtp_ecrire.getText()+"\n",null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			jc.jtp_ecrire.setText("");
			
			arg0.consume();//ne pas faire "ENTER" dans la zone de texte
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}