package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import talkTalk.TalkTalk;
import utils.Resources;

@SuppressWarnings("serial")
public class JConversation extends JPanel {
	
	private String name;
	private Document doc;
	private Icon icon_perso;
	private Icon icon_ami;
	
	protected JTextPane jtp_conversation;protected JScrollPane jsp_conversation;
	protected JPanel jp_right;
		protected JLabel jl_image_ami;
		protected JLabel jl_image_perso;
	protected JPanel jp_write;
		protected JTextPane jtp_ecrire;protected JScrollPane jsp_ecrire;
		protected JPanel jp_cmd;
			protected JCheckButton[] jb_cmd;
			protected JSmiley[] js_smiley;
			protected JButton jb_send;
	
	public JConversation(String name,Document doc,Icon icon_perso,Icon icon_ami) {
		this.setName(name);
		this.setDoc(doc);
		this.setIcon_perso(icon_perso);
		this.setIcon_ami(icon_ami);
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
	}
	public void creeBarreDeMenu() {
		/** Création des éléments     **/
		/** Paramétrage des éléments  **/
		/** Action sur les éléments   **/
		/** Montage des éléments      **/
	}
	public void creeInterface(ActionListener action,KeyListener key) {
		//Font f0 = new Font("Default",Font.BOLD,12);
		//jb.setFont(f0);
		
		this.jb_cmd = new JCheckButton[3];
		
		this.jb_cmd[0] = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_1.png",TalkTalk.class));
		this.jb_cmd[1] = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_2.png",TalkTalk.class));
		this.jb_cmd[2] = new JCheckButton(null,Resources.getImageIcon("images/cmd/cmd_3.png",TalkTalk.class));
		
		
		
		
		
		String[] smile = {":)","<3","ø","(","()","!","*"};
		/** Création des éléments     **/
		this.jtp_conversation = new JTextPane();this.jsp_conversation = new JScrollPane(this.jtp_conversation);
		this.jp_right = new JPanel(new BorderLayout());
			this.jl_image_ami = new JLabel(this.icon_ami,SwingConstants.CENTER);
			this.jl_image_perso = new JLabel(this.icon_perso,SwingConstants.CENTER);
		this.jp_write = new JPanel(new BorderLayout());
			this.jtp_ecrire = new JTextPane();this.jsp_ecrire = new JScrollPane(this.jtp_ecrire);
			this.jp_cmd = new JPanel();
				this.js_smiley = new JSmiley[smile.length];
				for (int i=0;i<this.js_smiley.length;i++) this.js_smiley[i] = new JSmiley(Resources.getImageIcon("images/smiley/smiley_"+(i+1)+".png",TalkTalk.class),smile[i]);
				this.jb_send = new JButton("Envoyer");
		/** Paramétrage des éléments  **/
		this.setLayout(new BorderLayout());
		//this.jtp_conversation.setDocument(this.doc);
		
		this.jl_image_ami.setPreferredSize(new Dimension(128,128));
		this.jl_image_perso.setPreferredSize(new Dimension(128,128));
		
		
		this.jtp_conversation.setEditable(false);
		this.jtp_ecrire.setEditable(true);
		/** Action sur les éléments   **/
		for (int i=0;i<this.js_smiley.length;i++) this.js_smiley[i].addActionListener(action);
		
		
		this.jtp_ecrire.addKeyListener(key);
		this.jb_send.addActionListener(action);
		/** Montage des éléments      **/
		this.add(this.jsp_conversation,BorderLayout.CENTER);
		this.add(this.jp_right,BorderLayout.EAST);
			this.jp_right.add(this.jl_image_ami,BorderLayout.NORTH);
			this.jp_right.add(this.jl_image_perso,BorderLayout.SOUTH);
		this.add(this.jp_write,BorderLayout.SOUTH);
			this.jp_write.add(this.jsp_ecrire,BorderLayout.CENTER);
			this.jp_write.add(this.jp_cmd,BorderLayout.SOUTH);
				for (int i=0;i<this.jb_cmd.length;i++) this.jp_cmd.add(this.jb_cmd[i]);
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
			
			System.out.println("ici");
			jc.jtp_ecrire.insertIcon(js.getIcon());//TODO : cette fonction empeche de mettre 2 fois de suite le même smiley
			//jc.jtp_ecrire.getDocument().insertString(jc.jtp_ecrire.getDocument().getLength(), js.getToolTipText(), null);
			
		} else if (obj instanceof JButton) {
			JButton jb = (JButton)obj;
			if (jb==jc.jb_send) {
				System.out.println("là");
				//jc.jtp_ecrire.cut();jc.jtp_conversation.paste();
				try {
					jc.jtp_conversation.getDocument().insertString(jc.jtp_conversation.getDocument().getLength(),jc.jtp_ecrire.getText()+"\n",null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				jc.jtp_ecrire.setText("");
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