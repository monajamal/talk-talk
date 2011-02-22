package ihm;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JSmiley extends JLabel {
	
	private ActionListener actionListener;
	
	public JSmiley(ImageIcon imageIcon,String title) {
		/** Événements **/
		MouseListener mouse = new Event_JSmiley(this);
		this.addMouseListener(mouse);
		/** Paramètres **/
		this.setToolTipText(title);
		this.setIcon(imageIcon);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addActionListener(null);
	}
	public void addActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	public ActionListener getActionListener() {
		return actionListener;
	}
}
class Event_JSmiley implements MouseListener {
	JSmiley js;

	public Event_JSmiley(JSmiley js) {
		this.js=js;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (js.getActionListener()!=null) {
			ActionEvent e = new ActionEvent(js,1,"JSmiley_clicked");
			js.getActionListener().actionPerformed(e);
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
}