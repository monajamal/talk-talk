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
import java.io.IOException;
import java.io.InputStream;

import javax.swing.FocusManager;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import commun.Contact;
import commun.Personne;

import talkTalk.TalkTalk;
import utils.Resources;
import utils.Sound;
import utils.Wizz;

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
				//FIXME !!
				System.out.println("Action !!");
				Component c = FocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
				
				System.out.println(c.toString());
				
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
							
							System.out.println("Copier : "+mot);
							
						} else if (jmi == ihm.jmi_couper) {
							StringSelection ss = new StringSelection(mot);
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
							jtc.getDocument().remove(start, end-(start));
							
							System.out.println("Couper : "+mot);
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
								
								
								System.out.println("Coller : "+mot);
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
						Sound player = new Sound(Resources.getFile("son/wizz.wav", TalkTalk.class));
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
				boolean exist=false;int select=0;
				for (int i=1;i<ihm.jtabp_onglet.getTabCount();i++) {
					JConversation jc = (JConversation)ihm.jtabp_onglet.getComponentAt(i);
					if (jc.getName().equals(ihm.lstContactAmis[jlst.getSelectedIndex()].getName())) {
						exist=true;
						select=i;
					}
				}
				
				// Si NON, ouvrir l'onglet
				if (!exist) {
					ihm.jtabp_onglet.addTab(ihm.lstContactAmis[jlst.getSelectedIndex()].getName(),
							Resources.getImageIcon(ihm.lstContactAmis[jlst.getSelectedIndex()].getImg(),TalkTalk.class),
							new JConversation(ihm.lstContactAmis[jlst.getSelectedIndex()]));
					/*ihm.jtabp_onglet.add(
							new JConversation(TalkTalk.friends.get(ihm.lstTabPersonnes[jlst.getSelectedIndex()].getName()).getName(),null,
									Resources.getImageIcon("images/tux.png",TalkTalk .class),
									Resources.getImageIcon("images/profil.png",TalkTalk.class)));//jlst.getSelectedIndex(),
					*/
					
					
					//ihm.actuTab(this); TODO
					select=ihm.jtabp_onglet.getTabCount()-1;//ihm.jc_fenetre.size();
					((JConversation)(ihm.jtabp_onglet.getComponentAt(select))).jb_wizz.addActionListener(this);
					ihm.jtabp_onglet.setTabComponentAt(select,new ButtonTabComponent(ihm.jtabp_onglet));
				}
				
				ihm.jtabp_onglet.setSelectedIndex(select);
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