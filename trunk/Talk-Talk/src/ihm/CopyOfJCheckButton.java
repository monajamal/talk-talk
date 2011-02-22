package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class CopyOfJCheckButton extends JLabel {
	
	private boolean enfonce;
	private boolean lock;
	private ActionListener actionListener;
	
	public CopyOfJCheckButton(String text, Icon icon, int alignment) {
		super(text,icon,alignment);
		
		/** Événements **/
		//MouseListener mouse = new Event_JTouche(this);
		//this.addMouseListener(mouse);
		
		/** Paramètres **/
		this.setEnfonce(false);
		this.setLock(false);
		this.setForeground(Color.BLACK);
		this.setBackground(Color.GRAY);
		
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
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	public boolean isLock() {
		return lock;
	}
	public void addActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	public ActionListener getActionListener() {
		return actionListener;
	}
	
	//Autres méthodes
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		Rectangle r = g.getClipBounds();
		//g.fillRoundRect(r.x, r.y, r.width, r.height,50,50);
		//g.fillOval(r.x, r.y, r.width, r.height);
		g.fillRect(r.x, r.y, r.width, r.height);
		super.paint(g);
	}
}
/*
class Event_JTouche implements MouseListener {
	CopyOfJCheckButton jp;
	public Event_JTouche(CopyOfJCheckButton jp) {
		this.jp=jp;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof CopyOfJCheckButton) {
			CopyOfJCheckButton jt = (CopyOfJCheckButton)obj;
			
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
		Object obj = arg0.getSource();
		if (obj instanceof CopyOfJCheckButton) {
			CopyOfJCheckButton jt = (CopyOfJCheckButton)obj;
			
			jt.setEnfonce(true);
			
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof CopyOfJCheckButton) {
			CopyOfJCheckButton jt = (CopyOfJCheckButton)obj;
			
			if (jt.isLock()) {
				jt.setEnfonce(false);
				jt.setLock(false);
			} else {
				jt.setLock(true);
			}
			
			
		}
	}
}*/