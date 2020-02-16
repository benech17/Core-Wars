package tournois;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class WarriorsNamesDialog extends JDialog {

	private ArrayList<String> names;
	private ArrayList<JTextField> texts;
	private JButton okButton;
	private JButton cancelButton;

	public WarriorsNamesDialog(JFrame parent, String title, boolean modal, int nbrWarriors) {
		super(parent, title, modal);

		this.names = new ArrayList<>();
		this.okButton = new JButton("OK");
		this.cancelButton = new JButton("Annuler");

		this.texts = new ArrayList<>();
		this.setLayout(new GridLayout(nbrWarriors+2, 2));
		for(int i=0; i<nbrWarriors; i++) {
			this.getContentPane().add(new JLabel("Programme "+i));
			texts.add(new JTextField(""));
			this.getContentPane().add(texts.get(i));
		}

		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int j=0; j<texts.size(); j++) {
					names.add(texts.get(j).getText());
				}
				WarriorsNamesDialog.this.setVisible(false);
			}
		});
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WarriorsNamesDialog.this.setVisible(false);
			}
		});
		this.getContentPane().add(this.cancelButton);
		this.getContentPane().add(this.okButton);
		this.setSize(500, 50*nbrWarriors);
		this.setResizable(false);
		this.setVisible(true);
	}

	public ArrayList<String> getNames() {return this.names; }
}