/**
 * Domaine : Composant graphique pour la gestion des liens HyperText
 * 
 * Auteur  : Damien
 * Date    : 23/05/2010
 * ----------------------------------------
 * Modifications :
 **/

package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JHyperTextLink extends JLabel {

	private String URL;
	private boolean visited;
	private Color clickedColor;
	private Color unclickedColor;
	
	public JHyperTextLink() {
		this("","");
	}
	public JHyperTextLink(String texte,String url) {
		this.setText(texte);
		this.setURL(url);
		this.setVisited(false);
		this.setClickedColor(new Color(153,0,153));
		this.setUnclickedColor(new Color(0,51,255));
		MouseListener action = new Event_JHyperTextLink(this);
		this.addMouseListener(action);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	public void setURL(String url) {
		this.URL = url;
	}
	public String getURL() {
		return URL;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
		setForeground(isVisited() ? getClickedColor() : getUnclickedColor());
	}
	public boolean isVisited() {
		return visited;
	}
	public void setClickedColor(Color color) {
		this.clickedColor = color;
		if(isVisited()) setForeground(getClickedColor());
	}
	public Color getClickedColor() {
		return clickedColor;
	}
	public void setUnclickedColor(Color color) {
		this.unclickedColor = color;
		if(!isVisited()) setForeground(getUnclickedColor());
	}
	public Color getUnclickedColor() {
		return unclickedColor;
	}
}
class Event_JHyperTextLink implements MouseListener {
	JHyperTextLink ihm;
	public Event_JHyperTextLink(JHyperTextLink ihm) {
		this.ihm=ihm;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		ihm.setVisited(true);
		try {
			Runtime.getRuntime().exec("cmd.exe /c start "+ihm.getURL());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (ihm.getText().startsWith("<html>") && ihm.getText().endsWith("</html>"))
			ihm.setText("<html><u>"+ihm.getText().substring(6,ihm.getText().length()-7)+"</u></html>");
		else
			ihm.setText("<html><u>"+ihm.getText()+"</u></html>");
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		if (ihm.getText().startsWith("<html><u>") && ihm.getText().endsWith("</u></html>"))
			ihm.setText("<html>"+ihm.getText().substring(9,ihm.getText().length()-11)+"</html>");
		else
			ihm.setText("<html>"+ihm.getText()+"</html>");
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}