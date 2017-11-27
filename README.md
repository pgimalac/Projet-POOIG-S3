# projetInfo

## TODO

- [x] commencer
- [ ] écrire le jeu de base (possiblement sans Listener,... en donnant directement à l'affichage les informations)
- [ ] affichage console
- [ ] écrire le jeu de base avec listeners,...
- [ ] rectifier l'affichage console
- [ ] affichage graphique
- [ ] création interactive du jeu (options, plateau,...)
- [ ] possibilité de sauvegarder / de charger une partie
- [ ] finir de programmer
- [ ] mettre une visibilité minimale sur chaque variable, méthode, classe,...
- [ ] représenter la hierarchie des classes et leurs interactions avec UML (ou autre)
- [ ] ecrire le rapport

## Fonctionnement théorique du programme final
Pour lancer le programme il faut executer le fichier Jouer(.class) ...............à écrire..................

## A partir de là cela ne concerne plus le fonctionnement ou l'avancement du projet mais plutot les étapes de création

### Fausses pistes
##### (il est possible qu'il y ait plusieurs fausses pistes sur la même chose ou qu'une fausse piste redevienne valide plus loin...)

- je pensais rendre le jeu et le plateau *iterable* pour pouvoir écrire quelque chose comme `for (Case c : plateau){ ... }` ou `for(Joueur j : jeu){ ... }` mais je me suis rendu compte que je n'allais jamais lire toutes les cases du plateau les unes après les autres (ou alors que si je le faisais ce serait depuis le plateau directement) et donc que ce serait inutile. Pour la classe Jeu il n'y a rien qui interagit avec le jeu au dessus de celui ci (uniquement une classe qui le crée, le lance et le sauvegarde), donc que je n'aurais jamais à lire la liste des joueurs depuis l'extérieur de la classe Jeu
- je pensais faire un seul jeu extrêmement personnalisable qui aurait une liste d'options qui définiraient le fonctionnement de celui ci. C'est faisable mais inutilement compliqué, j'ai décidé de garder l'idée des options et de la liste d'option qui caractérise un jeu mais en déléguant le fonctionnement du jeu à une sous classe en cas de modification du fonctionnement. Ainsi les *petits paramètres* comme le nombre de dés, les valeurs possibles de ceux-ci, les conditions de victoire,... sont modifiables en créant une classe implémentant Option (OptionVictoire,...) alors que le fonctionnement du jeu nécéssite une classe héritant de Jeu redéfinissant `jouerUnTour`.
- petite modification/précision : il ne suffit pas d'avoir une liste d'options et d'ajouter des options en fonction des besoins, on peut faire ça pour les options qui concernent les conditions de victoire ou les conditions sous lesquelles un joueur peut jouer (pour toute chose qui serait booléenne) en donnant à l'option le jeu et en récupérant en sortie un booleen qui exprimerait le faire que la partie soit finie ou qu'un joueur puisse jouer. Mais en ce qui concerne les option de jeu (du fonctionnement du jeu), on doit forcément avoir un comportement par défaut sans quoi on ne saura pas ce que l'on fait (par exemple si l'on a une option pour le nombre de dés que l'on utilise et quelles sont leurs valeurs possibles, même sans mettre l'option on doit bien savoir que l'on a un dé prenant des valeurs de 1 à 6), la liste d'options devra donc comprendre toutes les options qui intéragissent sur la manière dont un tour se joue (voire même toutes les options selon comment on fait le jeu !)
- même plus d'option en tant que classe, on fait uniquement les deux classes `JeuOie` et `JeuNumeri` qui héritent de `Jeu` et qui 

### Pensées et difficultés

- la différence gênante entre le jeu de l'oie et le numéri est que dans le premier le joueur n'a qu'un jeton donc pas de choix à faire au moment du jeu, alors que dans le second le joueur possède 6 jetons et a un choix à faire pour jouer, le jeton qui bouge n'est pas directement décidé par le dé. Cette distinction empèche de représenter ces deux jeux comme un seul avec des options différentes mais oblige à faire deux systèmes de jeu différents.