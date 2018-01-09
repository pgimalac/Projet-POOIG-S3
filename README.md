# Projet de POO-IG

## Compilation et execution
#### Compilation
Pour compiler les fichiers du projet il suffit de compiler le fichier *Jouer.java* situé à la racine ; pour être sûr de compiler tous les fichiers on peut executer *javac @fichiers.txt* où fichiers.txt est situé à la racine qui contient la liste des fichiers *.java* du projet en adresse relative.

#### Execution
Le fichier à executer pour lancer le programme est le fichier *Jouer.java* situé à la racine du projet, l'interface lancée dépend du contenu du paramètre *args* du main : si ce paramètre contient uniquement le mot *gui* (avec ou sans majuscules), l'interface lancée est l'interface graphique. Sinon le programme se lancera en interface textuelle (console).

## Organisation et fichiers à regarder
#### Organisation du projet
Les fichiers sont rangés dans des dossiers (qui sont aussi les packages et sous-packages) aux noms assez explicites pour trouver ce que l'on cherche.
Ci-dessous se trouve l'architecture du projet :
(merci à Maxime Flin qui m'a fait découvrir dans son README les caractères pour représenter cette architecture)

├── assets      							  Images utilisées dans le projet
├── README.md
├── sauvegardes								  Dossier contenant les sauvegardes du jeu
├── Jouer.java                                Classe principale du projet
├── fichiers.txt                              Liste des fichiers .java
├── jeu
    ├── affichage                                  
    │   ├── .java
    │   ├── .java
    │   ├── .java
    │   ├── .java
    │   ├── .java
    │   ├── .java
    │   ├── .java
    │   └── .java
    ├── events                                 
    │   ├── .java
    │   ├── .java
    │   └── .java
    ├── exceptions                                
    │   ├── .java
    │   ├── .java
    │   └── .java
    ├── listeners                                
    │   ├── .java
    │   ├── .java
    │   └── .java
    ├── options                                
    │   ├── .java
    │   ├── .java
    │   └── .java
    ├── plateau                                
    │   ├── .java
    │   ├── .java
    │   └── .java
    ├──                                   
    ├──                                   
    ├──                                   
    ├──                                   


#### Fichiers à regarder
- Le fichier *Jouer.java* est le fichier à executer, il ne contient qu'un bloc main et lance une des deux interfaces possibles du programme.
- 
- 
- 
- 
- 