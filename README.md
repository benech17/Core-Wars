# Core War
Core War is a programming game where assembly programs try to destroy each other in the memory of a simulated computer. The programs (or warriors) are written in a special language called Redcode, and run by a program called MARS (Memory Array Redcode Simulator).

Both Redcode and the MARS environment are much simplified and abstracted compared to ordinary computer systems. This is a good thing, since CW programs are written for performance, not for clarity. If the game used an ordinary assembly language, there might be two or three people in the world capable of writing an effective and durable warrior, and even they wouldn't probably be able to understand it fully. It would certainly be challenging and full of potential, but it'd probably take years to reach even a moderate level of skill. 

More informations at [Core War guide ](http://vyznev.net/corewar/guide.html "Core War tutorial")

# How to use : Core War

## Lancer le programme
Ces deux scripts bash permettent dans un premier temps de  compiler les fichiers .java contenus dans le repertoire "Code/" et génère les .class dans le repertoire "Class/" puis dans un second temps de lancer l'execution du programme. \
`$ ./compile.sh && ./execute.sh `

## Implementer un warrior en redcode
Commencez par ajouter un nouvel onglet de saisie : allez dans "Fichier"->"Nouveau Programme" puis donnez un nom à votre warrior.
 Une fois l'onglet créé, vous avez 2 options :
  * écrire un nouveau code en RedCode 94.
    * Pour comprendre quelle version précise de RedCode nous avons choisis d'implementer,veuillez vous réferer au fichier "SpecificationsRedcode.txt" .
    * Dans ce cas, vous pouvez le sauvegarder afin de le retrouver lors d'une autre game. 
  * importer un code déjà écrit, pour cela cliquez dans la barre d'outils sur le bouton **"IMPORTER"** puis selectionnez le fichier contenant votre code. Il est alors affiché dans votre nouvel onglet de saisie.
 

## Préparer le core
On définit la taille de la mémoire, le nombre de gagnants à la fin d'un run ou encore la version de RedCode(84 ou 94) dans l'onglet **Core**

## Charger ses codes dans le core
  Pour chaque warrior implémenté, nous devons le lancer dans le core et pour cela,on clique sur **"charger"** 
  Ensuite, donnez une couleur d'affichage dans le core à votre warrior.

## Lancer le run
Une fois tous vos programmes chargés et votre core prèt, vous pouvez lancer le run en cliquant sur le bouton "RUN" de la barre d'outil.
Il y a 3 modes de run :
 *  NORMAL : Les instructions s'execute une à une avec une vitesse controllée par le slide vitesse.
 *  ANALYSE : Les instructions sont executées à chaque clic sur le bouton "SUIVANT" de la barre d'outil.
 *  FAST : Les instructions sont executées avec la vitesse maximale de la machine.
