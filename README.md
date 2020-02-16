# How to use : Core War

Sommaire :
0) Lancer le programme
1) Les fichiers et repertoires
2) Ecrire du redcode
3) préparer le core
4) charger ses codes dans le core
5) Lancer le run
6) Sauvegader vos codes
7) Les modes de run

---------------------------------

## 0. Lancer le programme
	En ligne de commande,on compile d'abord avec l'executable "compile.sh" puis on lance l'executable "execute.sh".


## 1. Les fichiers et repertoires
    Le repertoire principale est "src/", il contient deux repertoires :
	) Le repertoire "code/" : Il contient tous les fichiers de code (.java).
	) Le repertoire "Class/" : Il contient tous les fichier .class générer lors de la compilations des fichiers .java. par "compile.sh".
	
    On retrouve aussi deux executables:
	) Le fichier "compile.sh" : Contient un script bash qui lance la compilation des fichiers de codes java (.java) contenus dans le repertoire "code/" et génere les fichiers    .class dans le repertoire "Class/".
	) Le fichier "execute.sh" : Il contient un script bash qui lance l'execution du programme.



## 2. Ecrire du redcode
    - Commencez par ajouter un nouvel onglet de saisie : allez dans "Fichier"->"Nouveau Programme" puis donnez un nom à votre programme.
	- Une fois l'onglet créé, vous avez 2 options :
	   	- écrire un nouveau code en RedCode 94.
		- importer un code déjà écrit, pour cela cliquez dans la barre d'outils sur le bouton "IMPORTER" puis selectionnez le fichier contenant votre code. Il est alors affiché dans votre nouvel onglet de saisie.


## 3. préparer le core
	- definir la taille de la mémoire : menu "Core"->"Taille de la mémoire"
	- definir le nombre de gagnant : menu "Core"->"Nombre de gagnants. c'est le nombre de programme restant dans le core à la fin d'un run.
	- definir la version de redcode utilisée : menu "Core"->"Version de redcode"

## 4. charger ses codes dans le core
	Pour charger un de vos code dans le core, cliquez sur le bouton "CHARGER" de la barre d'outil après avoir selectionné l'onglet du code que vous voulez charger.
	Ensuite, donnez une couleur d'affichage à votre programme.

## 5. Lancer le run
	Une fois tous vos programmes chargés et votre core prèt, vous pouvez lancer le run en cliquant sur le bouton "RUN" de la barre d'outil.


## 6. Sauvegader vos codes
	Pour sauvegarder un code, selectionner son onglet, puis cliquer sur le bouton "SAUVEGARDER" de la barre d'outil et selectionnez l'emplacement où le sauvegarder.


## 7. Les modes de run
	Il y a 3 mode de run :
		NORMAL : Les instructions s'execute une à une avec une vitesse controllée par le slide vitesse.
		ANALYSE : Les instructions sont executées à chaque clic sur le bouton "SUIVANT" de la barre d'outil.
		FAST : Les instructions sont executées avec la vitesse maximale de la machine.
