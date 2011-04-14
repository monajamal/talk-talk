package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import talkTalk.TalkTalk;
import utils.Resources;

import commun.Contact;
import commun.Groupe;
import commun.Personne;

@SuppressWarnings("serial")
public class JConversation extends JPanel {
	
	Contact ami;
	private Color couleur;
	
	protected JTextPane jtp_conversation;protected JScrollPane jsp_conversation;
	protected JPanel jp_right;
		protected JLabel jl_image_ami;
		protected JPanel jp_dfsdfgdsg;
			protected JButton jb_bloque;
			protected JLabel jl_infos;
		protected JLabel jl_image_perso;
	protected JPanel jp_write;
		protected JTextPane jtp_ecrire;protected JScrollPane jsp_ecrire;
		protected JPanel jp_cmd;
			protected JButton jb_wizz;
			protected JCheckButton jchkb_gras;
			protected JCheckButton jchkb_italique;
			protected JCheckButton jchkb_souligne;
			protected JButton jb_couleur;
			protected JSmiley[] js_smiley;
			protected JCoolButton jb_send;
	
	private ActionListener actionListener;
			
	public JConversation(Contact c) {
		this.setContact(c);
		this.setCouleur(Color.BLACK);
		this.addActionListener(null);
		/** Évènement **/
		ActionListener action = new Event_JConversation(this);
		KeyListener key = new Event_JConversation(this);
		/** Barre de Menu **/
		creeBarreDeMenu();
		/** Interface **/
		creeInterface(action,key);
		/** Zone de notification **/
		menuContextuel();
	}
	public void creeBarreDeMenu() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public void creeInterface(ActionListener action,KeyListener key) {
		String[] smile = {":)","<3","ø","(","()","!","*"};
		ImageIcon ii,iami;
		if (TalkTalk.image.equals("null")) ii = Resources.getImageIcon("images/profil.png", TalkTalk.class);
		else ii = new ImageIcon(TalkTalk.image);
		if (this.ami.getType()==Contact.PERSONNE && ((Personne)this.ami).getImage_perso()!=null) {
			 iami = new ImageIcon(((Personne)this.ami).getImage_perso());
		} else iami = Resources.getImageIcon("images/profil.png", TalkTalk.class);
		String infos;
		String bloque = "";
		if (ami.getType()==Contact.PERSONNE) {
			Personne p =  (Personne)ami;
			infos="<html>";
			infos+="<p>Pseudo : "+p.getName()+"</p>";
			if (p.getStatut()!=Personne.BLOQUE) {
				bloque="Bloquer";
				infos+="<p>Alias : "+p.getAlias()+"</p>";
				infos+="<p>IP : "+p.getAddress().getAddr()+"</p>";
				infos+="<p>Port : "+p.getAddress().getPort()+"</p>";
			} else bloque="Débloquer";
			infos+="</html>";
		} else {
			Groupe g = (Groupe)ami;
			infos="<html>";
			infos+="<p>Nom : "+ami.getName()+"</p>";
			infos+="<p>Membres :<br/>";
			for (Personne p : g.getMembres()) {
				infos+=p.getName()+"<br/>";
			}
			infos+="</p>";
			infos+="</html>";
		}
		/** Création des éléments     **/
		this.jtp_conversation = new JTextPane();this.jsp_conversation = new JScrollPane(this.jtp_conversation);
		this.jp_right = new JPanel(new BorderLayout());
			this.jl_image_ami = new JLabel(iami,SwingConstants.CENTER);
			this.jp_dfsdfgdsg = new JPanel(new BorderLayout());
				this.jb_bloque = new JButton(bloque);
				this.jl_infos = new JLabel(infos);
			this.jl_image_perso = new JLabel(ii,SwingConstants.CENTER);
		this.jp_write = new JPanel(new BorderLayout());
			this.jtp_ecrire = new JTextPane();this.jsp_ecrire = new JScrollPane(this.jtp_ecrire);
			this.jp_cmd = new JPanel();
				this.jchkb_gras = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_1.png",TalkTalk.class));
				this.jchkb_italique = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_2.png",TalkTalk.class));
				this.jchkb_souligne = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_3.png",TalkTalk.class));
				this.jb_couleur = new JButton(Resources.getImageIcon("images/cmd/cmd_5.png",TalkTalk.class));
				this.jb_wizz = new JButton("wizz");
				this.js_smiley = new JSmiley[smile.length];
				for (int i=0;i<this.js_smiley.length;i++) this.js_smiley[i] = new JSmiley(Resources.getImageIcon("images/smiley/smiley_"+(i+1)+".png",TalkTalk.class),smile[i]);
				this.jb_send = new JCoolButton("Envoyer");
		
		/** Paramétrage des éléments  **/
		this.setLayout(new BorderLayout());
		//this.jtp_conversation.setDocument(this.doc);
		this.jtp_conversation.setEditable(false);
		this.jl_image_ami.setPreferredSize(new Dimension(128,128));
		this.jl_image_perso.setPreferredSize(new Dimension(128,128));
		this.jtp_ecrire.setForeground(couleur);
		this.jtp_ecrire.setEditable(true);
		
		/** Action sur les éléments   **/
		this.jtp_ecrire.addKeyListener(key);
		this.jb_wizz.addActionListener(action);
		this.jchkb_gras.addActionListener(action);
		this.jchkb_italique.addActionListener(action);
		this.jchkb_souligne.addActionListener(action);
		this.jb_couleur.addActionListener(action);
		for (int i=0;i<this.js_smiley.length;i++) this.js_smiley[i].addActionListener(action);
		this.jb_send.addActionListener(action);
		this.jb_bloque.addActionListener(action);
		/** Montage des éléments      **/
		this.add(this.jsp_conversation,BorderLayout.CENTER);
		this.add(this.jp_right,BorderLayout.EAST);
			this.jp_right.add(this.jl_image_ami,BorderLayout.NORTH);
			this.jp_right.add(this.jp_dfsdfgdsg,BorderLayout.CENTER);
				if (ami.getType()==Contact.PERSONNE) this.jp_dfsdfgdsg.add(this.jb_bloque,BorderLayout.NORTH);
				this.jp_dfsdfgdsg.add(this.jl_infos,BorderLayout.CENTER);
			this.jp_right.add(this.jl_image_perso,BorderLayout.SOUTH);
		this.add(this.jp_write,BorderLayout.SOUTH);
			this.jp_write.add(this.jsp_ecrire,BorderLayout.CENTER);
			this.jp_write.add(this.jp_cmd,BorderLayout.SOUTH);
				this.jp_cmd.add(this.jb_wizz);
				this.jp_cmd.add(this.jchkb_gras);
				this.jp_cmd.add(this.jchkb_italique);
				this.jp_cmd.add(this.jchkb_souligne);
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
	public void setContact(Contact c) {
		this.ami = c;
	}
	public Contact getContact() {
		return ami;
	}
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	public Color getCouleur() {
		return couleur;
	}
	public void addActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	public ActionListener getActionListener() {
		return actionListener;
	}
	public void changerImageAmi(){
		ImageIcon ii = new ImageIcon(((Personne)this.ami).getImage_perso());
		this.jl_image_ami.setIcon(ii);
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
		if (obj instanceof JCheckButton) {
			JCheckButton jchkb = (JCheckButton)obj;
			if (jchkb==jc.jchkb_gras || jchkb==jc.jchkb_italique) {	// Changement de gras et italique
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
			} else if (jchkb==jc.jchkb_souligne) {					// Changement de souligné
				
			}
		} else if (obj instanceof JSmiley) {	// Si on insere un smiley
			JSmiley js = (JSmiley)obj;
			jc.jtp_ecrire.insertIcon(js.getIcon());
		} else if (obj instanceof JButton) {
			JButton jb = (JButton)obj;
			if (jb==jc.jb_wizz) {
				TalkTalk.envoyerWizz(jc.getContact().getName());
				
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
				int atEnd = jc.jtp_conversation.getDocument().getLength();
				String message = jc.jtp_ecrire.getText();
				Style defaut = jc.jtp_ecrire.getStyle("default");
				Style style1 = jc.jtp_ecrire.addStyle("style1", defaut);
				StyleConstants.setBold(style1, true);
				try {
					jc.jtp_conversation.getDocument().insertString(atEnd,TalkTalk.pseudo+" : ",style1);
					jc.jtp_conversation.getDocument().insertString(atEnd,message+"\n",defaut);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				
				TalkTalk.envoyerMessage(TalkTalk.friends.get(jc.getContact().getName()), message);
				jc.jtp_ecrire.setText("");
				
			} else if (jb==jc.jb_couleur) {
				Color newColor = JColorChooser.showDialog(jc,"Choose Background Color",jc.getCouleur());
				if (newColor != null) {
					jc.setCouleur(newColor);
					jc.jtp_ecrire.setForeground(newColor);
				}
			} else if (jb==jc.jb_bloque) {
				if (((Personne)(this.jc.getContact())).getStatut()==Personne.BLOQUE) {
					jc.jb_bloque.setText("Bloquer");
					((Personne)(this.jc.getContact())).setStatut(Personne.OFFLINE);
					TalkTalk.bloques.remove(this.jc.getContact().getName());
				} else {
					jc.jb_bloque.setText("Débloquer");
					TalkTalk.bloques.put(this.jc.getContact().getName(),((Personne)(this.jc.getContact())));
					((Personne)(this.jc.getContact())).setStatut(Personne.BLOQUE);
				}
				// je declenche un évènement de plus high level
				ActionEvent e = new ActionEvent(jc,1,"JCOnversation_Bloquer");
				jc.getActionListener().actionPerformed(e);
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// si on fait ENTER (KeyCode=10) et que Ctrl (Modifiers==1) ou Maj (Modifiers==2) alors on rajoute une ligne
		if (arg0.getKeyCode()==10 && (arg0.isControlDown() || arg0.isShiftDown())) {
			try {
				jc.jtp_ecrire.getDocument().insertString(jc.jtp_ecrire.getDocument().getLength(),"\n",null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else if (arg0.getKeyCode()==10) {//si on fait juste un ENTER, on valide
			String message = jc.jtp_ecrire.getText();
			try {
				TalkTalk.envoyerMessage(jc.getContact(), message);
				jc.jtp_conversation.getDocument().insertString(
						jc.jtp_conversation.getDocument().getLength(),
						TalkTalk.pseudo+" : "+message+"\n",
						null);
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