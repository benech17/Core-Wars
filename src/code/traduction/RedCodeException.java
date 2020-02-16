package traduction;

import javax.swing.JOptionPane;


public class RedCodeException extends RuntimeException{

	public RedCodeException(String message) {
		System.out.println(message);
		JOptionPane jop3 = new JOptionPane();
                jop3.showMessageDialog(null, "Attention à bien lire la documentation , vous avez commis une erreur de syntaxe !", "Erreur de syntaxe", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
		
	}

	
}
