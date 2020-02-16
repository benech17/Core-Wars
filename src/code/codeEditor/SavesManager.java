package codeEditor;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

import javax.swing.JFileChooser;

import tournois.Guerrier;

public class SavesManager {

	public static HashMap<String, Guerrier> guerriers = new HashMap<String, Guerrier>();	
    //Pour utiliser correctement cette classe il faut crier un dossier CoreWar et dans ce dossier
    //CoreWar creer un sous-repertoire Guerrier où on stockera nos programmes

	public SavesManager(){
	}

	public static HashMap<String, Guerrier> getGuerriers(){
		String[]t;
		t=allWarriors();
		for(int i=0;i<t.length;i++){
			t[i]=fileNameWithoutExtension(t[i]);
			guerriers.put( t[i],new Guerrier( t[i] ,loadCode(t[i]) ) );
		}
		return guerriers;
	}


    public static void saveCode(String nom_du_code, String lecode) { //sauvegarde un programme
	String s = "Guerrier/" + nom_du_code + ".cw";
	File file1 = new File(s);

	if(file1.exists()) {
	    System.out.println("Un programme qui porte le meme nom a deja été creé !");
	    guerriers.put(nom_du_code,new Guerrier(nom_du_code,lecode));
	} else {
	    if(saveString(file1,lecode)){
	    guerriers.put(nom_du_code,new Guerrier(nom_du_code,lecode));	
	    System.out.println("Le programme a été rajouté avec succés !"); }
	}
    }

    public static void saveCodeGUI(String lecode, Component interfaceGraphiqueUtilisée){
   
	JFileChooser tmp=new JFileChooser("./Guerrier/");
	int wantOpen=tmp.showSaveDialog(interfaceGraphiqueUtilisée);
	File f=tmp.getSelectedFile();
	if(f==null || wantOpen!=JFileChooser.APPROVE_OPTION);
	if(f.exists()) {
		String s=f.getName();
		guerriers.put(s,new Guerrier(s,lecode));
	    System.out.println("Un programme qui porte le meme nom a deja été creé !");
	}else{
		String s=f.getName();
	    if(saveString(f,lecode)){

	    guerriers.put(s,new Guerrier(s,lecode));	
	    System.out.println("Le programme a été rajouté avec succés !"); }
	}
    }

    public static boolean saveString(File f, String content){
		try {
		    Files.write(f.toPath(), content.getBytes(StandardCharsets.UTF_8));
		}catch(IOException e) {
		    return false;
		}
		return true;
    }

    public static String loadCode(String nom_du_code) { //.cw pour corewar, charge un programme
	String s = "Guerrier/" + nom_du_code + ".cw";
	File file1 = new File(s);
	String res = "";

	if(file1.exists()) {
	    try {
		res=loadString(file1);
	    }catch(IOException e){
		e.printStackTrace();
	    }
			
	    return res;
	}	
		
	else return "Le guerrier n'existe pas !";
    }

    public static String loadCodeGUI(Component interfaceGraphiqueUtilisée){	
	JFileChooser tmp=new JFileChooser("./Guerrier/");
	int wantOpen=tmp.showOpenDialog(interfaceGraphiqueUtilisée);
	File f=tmp.getSelectedFile();
	if(f==null || wantOpen!=JFileChooser.APPROVE_OPTION) return "Le guerrier n'existe pas !";
	if(f.exists()) {
	    try {
		return loadString(f);
	    }catch(IOException e){
		e.printStackTrace();
	    }
	}	
	return "Le guerrier n'existe pas !";
    }

    public static String loadString(File f) throws IOException{
	return new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
    }
    
    public static void deleteWarriorGUI(Component interfaceGraphiqueUtilisée){	
	JFileChooser tmp=new JFileChooser("./Guerrier/");
	int wantOpen=tmp.showDialog(interfaceGraphiqueUtilisée,"Delete");
	File f=tmp.getSelectedFile();
	if(f==null || wantOpen!=JFileChooser.APPROVE_OPTION) return;
	if(f.exists()) {
	    f.delete();
	    String s=f.getName();
	    guerriers.remove(s);
	    System.out.println("Le programme a été supprimé !");
	}else{
	    System.out.println("Le programme n'existe pas !");
	}
    }

    public static void deleteWarrior(String nom_du_code) { //supprime un programme
	String s = "Guerrier/" + nom_du_code + ".cw";
	File file1 = new File(s);

	if(file1.exists()) {
	    file1.delete();
	    guerriers.remove(nom_du_code);
	    System.out.println("Le programme a été supprimé !");
	}else
	    System.out.println("Le programme n'existe pas !");
    }

    public static String[] allWarriors() { //nous retourne la liste des guerriers deja sauvegardé 
	File repertoire = new File("Guerrier/");//pour pouvoir dans l'interface pouvoir faire une liste deroulante
	File[] files = repertoire.listFiles();//pour charger les programmes

	if(files == null) return new String [0]; 

	int taille=files.length;
	String[] res = new String[taille];
	for(int i=0; i<taille; i++) {
	    res[i] = files[i].getName();	
	}

	return res;

    }

    public static void affiche(String[] s) {
		
	for(int i=0; i<s.length; i++){
	    System.out.println(s[i]);
	}
    }

    private static String fileNameWithoutExtension(String s){
	String[] t=s.split("\\.");
	if(t.length==0) return s; //pas d'extension
	StringBuilder res=new StringBuilder();
	for(int i=0;i<t.length-1;i++){
	    res.append(t[i]);
	}
	return res.toString();
    }
}
