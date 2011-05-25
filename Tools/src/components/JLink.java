/**
 * Domaine : Composant graphique pour la gestion des butons en forme de liens web
 * 
 * Auteur  : Damien
 * Date    : 26/05/2010
 * ----------------------------------------
 * Modifications :
 **/

package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JLink extends JLabel {
	
	private boolean selected;
	private ActionListener action;
	
	@Deprecated
	public JLink() {
		this(null);
	}
	@Deprecated
	public JLink(String texte) {
		setText(texte);
		setForeground(new Color(0,51,255));
		MouseListener action = new Event_JLink(this);
		this.addMouseListener(action);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		action=null;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
		Font f1 = new Font(Font.SANS_SERIF,Font.BOLD,12);
		Font f2 = new Font(Font.SANS_SERIF,0,12);
		if(selected) {
			setForeground(Color.BLACK);
			setFont(f1);
			
			if (this.getText().startsWith("<html><u>") && this.getText().endsWith("</u></html>"))
				this.setText("<html>"+this.getText().substring(9,this.getText().length()-11)+"</html>");
			else
				this.setText("<html>"+this.getText()+"</html>");
			
		} else {
			setForeground(new Color(0,51,255));
			setFont(f2);
		}
	}
	public boolean isSelected() {
		return selected;
	}
	public void addActionListener(ActionListener action) {
		this.action = action;
	}
	public ActionListener getActionListener() {
		return action;
	}
}
class Event_JLink implements MouseListener {
	JLink ihm;
	public Event_JLink(JLink ihm) {
		this.ihm=ihm;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (!ihm.isSelected()) {
			if (ihm.getText().startsWith("<html>") && ihm.getText().endsWith("</html>"))
				ihm.setText("<html><u>"+ihm.getText().substring(6,ihm.getText().length()-7)+"</u></html>");
			else
				ihm.setText("<html><u>"+ihm.getText()+"</u></html>");
		}
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		if (!ihm.isSelected()) {
			if (ihm.getText().startsWith("<html><u>") && ihm.getText().endsWith("</u></html>"))
				ihm.setText("<html>"+ihm.getText().substring(9,ihm.getText().length()-11)+"</html>");
			else
				ihm.setText("<html>"+ihm.getText()+"</html>");
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		ActionEvent e = new ActionEvent(ihm,1001,ihm.getText());
		ihm.getActionListener().actionPerformed(e);
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}