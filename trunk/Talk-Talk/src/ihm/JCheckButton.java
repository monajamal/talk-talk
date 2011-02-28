package ihm;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class JCheckButton extends JButton {
	
	private boolean enfonce;
	private ActionListener actionListener;
	
	public JCheckButton(String text, Icon icon) {
		super(text,icon);//SwingContants.CENTER
		
		/** Événements **/
		MouseListener mouse = new Event_JTouche(this);
		this.addMouseListener(mouse);
		
		/** Paramètres **/
		this.setEnfonce(false);
		//this.setMargin(new Insets(25,25,25,25));
		this.setPreferredSize(new Dimension(25,25));
		
		this.addActionListener(null);
	}
	
	public void setEnfonce(boolean enfonce) {
		if (enfonce)
			this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		else
			this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.enfonce = enfonce;
	}
	public boolean isEnfonce() {
		return enfonce;
	}
	public void addActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	public ActionListener getActionListener() {
		return actionListener;
	}
}
class Event_JTouche implements MouseListener {
	JCheckButton jp;
	boolean lock;
	public Event_JTouche(JCheckButton jp) {
		this.jp=jp;
		this.lock=false;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof JCheckButton) {
			JCheckButton jt = (JCheckButton)obj;
			jt.setEnfonce(true);
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof JCheckButton) {
			JCheckButton jt = (JCheckButton)obj;
			if (lock) {
				jt.setEnfonce(false);
				lock=false;
			} else {
				lock=true;
			}
			ActionEvent e = new ActionEvent(jp,1,"JCheckButton_stateChanged");
			jp.getActionListener().actionPerformed(e);
		}
	}
}