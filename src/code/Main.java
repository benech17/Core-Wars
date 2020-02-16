import controller.Controller;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/** La classe principale.
*/
public class Main {

	/** La méthode principale. Elle créer un {@link controller.Controller}.
	* @see controller.Controller
	*/
	public static void main(String[] args){
		 try{
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("../code/theme.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
	    clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println("Can't play music: theme.wav");
        }
		Controller controller = new Controller();
	}
}
