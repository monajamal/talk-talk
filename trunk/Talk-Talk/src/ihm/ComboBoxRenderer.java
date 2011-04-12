package ihm;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import utils.Resources;

import commun.Contact;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = -248565055989104798L;
	
	private String[] textes;
	private String[] images;
	ImageIcon[] img;
	
	public ComboBoxRenderer(Contact[] contact,Class<?> c) {
		this.textes=new String[contact.length];
		this.images=new String[contact.length];
		for (int i=0;i<contact.length;i++) {
			this.textes[i]=contact[i].getName();
			if (contact[i].getImg()!=null) {
				this.images[i]=contact[i].getImg();
			} else this.images[i]="images/profil.png";//default;
		}
		//init
		init(c);
	}
	
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
		//init
		init(c);
	}
	private void init(Class<?> c) {
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