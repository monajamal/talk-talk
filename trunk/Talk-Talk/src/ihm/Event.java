package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

public class Event implements ActionListener {
	IHM ihm;
	public Event(IHM ihm) {
		this.ihm=ihm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		if (obj instanceof JButton) {
			JButton jb = (JButton)obj;
		} else if (obj instanceof JMenuItem) {
			JMenuItem jmi = (JMenuItem)obj;
			if (jmi == ihm.jmi_quitter) {
				System.out.println("quitter");
				System.exit(0);
			} else if (jmi == ihm.jmi_couper) {
				System.out.println("couper");
			} else if (jmi == ihm.jmi_copier) {
				System.out.println("copier");
			} else if (jmi == ihm.jmi_coller) {
				System.out.println("coller");
			} else if (jmi == ihm.jmi_rechercher) {
				System.out.println("rechercher");
			} else System.out.println("inconnu");
			
		}
	}
}