package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import talkTalk.TalkTalk;
import utils.Resources;
import utils.Sound;

public class Event implements ActionListener, ListSelectionListener, MouseListener {//TODO : extends MouseAdapter plutot que implements MouseListener ??
	IHM ihm;
	public Event(IHM ihm) {
		this.ihm=ihm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof JMenuItem) {
			JMenuItem jmi = (JMenuItem)obj;
			
			if (jmi == ihm.jmi_quitter) {
				System.out.println("quitter");
				System.exit(0);
			}
			else if (jmi == ihm.jmi_couper) {
				System.out.println("couper");
			}
			else if (jmi == ihm.jmi_copier) {
				System.out.println("copier");
			}
			else if (jmi == ihm.jmi_coller) {
				System.out.println("coller");
			}
			else if (jmi == ihm.jmi_rechercher) {
				System.out.println("rechercher");
			}
			else if (jmi == ihm.jmi_preferences) {
				System.out.println("Préférences");
			}
			else if (jmi == ihm.jckmi_contact) { // Afficher/masquer de panneau de contact
				if (ihm.jckmi_contact.isSelected()) ihm.jslip.setDividerLocation(ihm.size_split);
				else ihm.size_split=ihm.jslip.getDividerLocation();
				ihm.jp_left.setVisible(ihm.jckmi_contact.isSelected()); // Afficher/masquer de panneau de contact
				ihm.jslip.setEnabled(ihm.jckmi_contact.isSelected()); // Activer/désactiver le spliteur
			}
			else if (jmi == ihm.jckmi_image) { // Afficher/masquer les images perso des contacts
				for (int i=0;i<ihm.jc_fenetre.size();i++)
					ihm.jc_fenetre.get(i).jp_right.setVisible(ihm.jckmi_image.isSelected());
			}
			else System.out.println("inconnu");
			
		} else if (obj instanceof JButton) {
			JButton jb = (JButton)obj;
			if (jb.getText().contains("wizz")) {
				new Thread() {
					public void run() {
						Sound player = new Sound("src/ihm/son/wizz.wav");
						InputStream stream = new ByteArrayInputStream(player.getSamples());
						player.play(stream);
					}
				}.start();
				Wizz.creerWizz(ihm,40,3);
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof JList) {
			JList jlst = (JList)obj;
			if (arg0.getClickCount() == 2) {
				// Cherche si un onglet est déjà ouvert
				boolean exist=false;
				for (int i=0;i<ihm.jc_fenetre.size();i++) {
					if (ihm.jc_fenetre.get(i).getName().equals(ihm.contacts[jlst.getSelectedIndex()])) {
						exist=true;
					}
				}
				// Si NON, ouvrir l'onglet
				if (!exist) {
					ihm.jc_fenetre.add(new JConversation(ihm.contacts[jlst.getSelectedIndex()],null,Resources.getImageIcon("images/tux.png",TalkTalk .class),Resources.getImageIcon("images/profil.png",TalkTalk.class)));
					ihm.actuTab(this);
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		/*Object obj = arg0.getSource();
		if (obj instanceof JList) {
			JList jlst = (JList)obj;
			ihm.jc_fenetre.add(new JConversation(jsl.getSelectedValue().toString(),null,Resources.getImageIcon("images/tux.png",TalkTalk .class),Resources.getImageIcon("images/profil.png",TalkTalk.class)));
			ihm.actuTab();
		}*/
	}
}