package ihm;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.FocusManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import talkTalk.TalkTalk;
import utils.Resources;
import utils.Sound;
import utils.Wizz;

import commun.Personne;

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
				TalkTalk.exit();
			} else if (jmi == ihm.jmi_couper || jmi == ihm.jmi_copier || jmi == ihm.jmi_coller) {
				Component c = FocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
				if (c instanceof JTextComponent) {
					JTextComponent jtc = (JTextComponent)c;
					int start = jtc.getSelectionStart();
					int end = jtc.getSelectionEnd();
					String mot;
					
					try {
						mot = jtc.getDocument().getText(start, end-(start));
						
						if (jmi == ihm.jmi_copier) {
							StringSelection ss = new StringSelection(mot);
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
						} else if (jmi == ihm.jmi_couper) {
							StringSelection ss = new StringSelection(mot);
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
							jtc.getDocument().remove(start, end-(start));
						} else if (jmi == ihm.jmi_coller) {
							/** Lecture du contenu : */
							Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
							/** Vérification que le contenu est de type texte. */
							if (t!=null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
								mot = (String)t.getTransferData(DataFlavor.stringFlavor);
								
								start=jtc.getSelectionStart();
								end=jtc.getSelectionEnd();
								jtc.getDocument().remove(start, end-start);
								jtc.getDocument().insertString(jtc.getCaretPosition(),mot,null);
							}
						}
					} catch (BadLocationException e) {
						e.printStackTrace();
					} catch (UnsupportedFlavorException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else if (jmi == ihm.jmi_rechercher) {
				
			}
			else if (jmi == ihm.jmi_preferences) {
				
			}
			else if (jmi == ihm.jckmi_contact) { // Afficher/masquer de panneau de contact
				if (ihm.jckmi_contact.isSelected()) ihm.jslip.setDividerLocation(ihm.size_split);
				else ihm.size_split=ihm.jslip.getDividerLocation();
				ihm.jp_left.setVisible(ihm.jckmi_contact.isSelected()); // Afficher/masquer de panneau de contact
				ihm.jslip.setEnabled(ihm.jckmi_contact.isSelected()); // Activer/désactiver le spliteur
			}
			else if (jmi == ihm.jckmi_image) { // Afficher/masquer les images perso des contacts
				for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
					JConversation jc = (JConversation)ihm.jtabp_onglet.getComponentAt(i);
					jc.jp_right.setVisible(ihm.jckmi_image.isSelected());
				}
			}
			else System.out.println("inconnu");
			
		} else if (obj instanceof JButton) {
			JButton jb = (JButton)obj;
			if (jb.getText().contains("wizz")) {
				new Thread() {
					public void run() {
						File f = new File("data/son/wizz.wav");
						if (f.exists()) {
							Sound player = new Sound(f);
							InputStream stream = new ByteArrayInputStream(player.getSamples());
							player.play(stream);
						}
					}
				}.start();
				Wizz.creerWizz(ihm,40,3);
			}
			if (jb==ihm.jb_add) {
				String name=JOptionPane.showInputDialog("Nom du contact ?");
				Personne p = new Personne(name,null);
				TalkTalk.friends.put(name, p);
				TalkTalk.searchAdresse(p);
				//ihm.addToList(name);
				ihm.createDataListContact();
			}
		} else if (obj instanceof JComboBox) {
			JComboBox jcb = (JComboBox)obj;
			TalkTalk.setStatut(jcb.getSelectedIndex());
		} else if (obj instanceof JConversation) {
			ihm.createDataListContact();
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof JList) {
			JList jlst = (JList)obj;
			if (arg0.getClickCount() == 2) {
				// Cherche si un onglet est déjà ouvert
				boolean exist=false;int select=0;
				for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
					if (ihm.jtabp_onglet.getTitleAt(i).equals(ihm.lstContactAmis[jlst.getSelectedIndex()].getName())) {
						exist=true;
						select=i;
					}
				}
				// Si NON, ouvrir l'onglet
				if (!exist) {
					JConversation jc = new JConversation(ihm.lstContactAmis[jlst.getSelectedIndex()]);
					ButtonTabComponent btc = new ButtonTabComponent(Resources.getImageIcon(ihm.lstContactAmis[jlst.getSelectedIndex()].getImg(),TalkTalk.class),ihm.jtabp_onglet);
					
					// Ajout d'un onglet
					ihm.jtabp_onglet.addTab(ihm.lstContactAmis[jlst.getSelectedIndex()].getName(),null,jc);
					// indice de l'onglet a selectionner
					select=ihm.jtabp_onglet.getTabCount()-1;
					// action wizz
					jc.jb_wizz.addActionListener(this);
					jc.addActionListener(this);
					//jc.jb_bloque.addActionListener(this);
					// Image onglet + croix pour fermer
					ihm.jtabp_onglet.setTabComponentAt(select,btc);
				}
				// selectionne l'onglet
				ihm.jtabp_onglet.setSelectedIndex(select);
				((JConversation)ihm.jtabp_onglet.getComponentAt(select)).jtp_ecrire.requestFocusInWindow();
			}
		}
		ihm.createDataListContact();
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
		
	}
}