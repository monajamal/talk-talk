/**
 * Domaine : Panel avec image en fond
 * 
 * Auteur  : Damien
 * Date    : 22/07/2010
 * ----------------------------------------
 * Modifications :
 **/

package components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JPanelBackground extends JPanel {
	Image backGround=null;
	public JPanelBackground(LayoutManager layout,Image img) {
		this.backGround=img;
		this.setOpaque(false);
		this.setLayout(layout);
	}
	public void paint(Graphics g) {
		if (backGround!=null)
			g.drawImage(backGround,0,0,getSize().width,getSize().height,this);
		super.paint(g);
	}
}