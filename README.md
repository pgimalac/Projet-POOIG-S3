# projetInfo


## TODO

- [x] commencer
- [ ] écrire le jeu de base
- [ ] affichage console
- [ ] affichage graphique
- [ ] création interactive du jeu (options, plateau,...)
- [ ] possibilité de sauvegarder / de charger une partie
- [ ] finir de programmer
- [ ] mettre une visibilité minimale sur chaque variable, méthode, classe,...
- [ ] représenter la hierarchie des classes et leurs interactions avec UML (ou autre)
- [ ] ecrire le rapport

## Fonctionnement théorique du programme final




## Fausses pistes

- je pensais rendre le jeu et le plateau *iterable* pour pouvoir écrire quelque chose comme `for (Case c : plateau){ ... }` ou `for(Joueur j : jeu){ ... }` mais je me suis rendu compte que je n'allais jamais lire toutes les cases du plateau les unes après les autres (ou alors que si je le faisais ce serait depuis le plateau directement) et donc que ce serait inutile. Pour la classe Jeu il n'y a rien qui interagit avec le jeu au dessus de celui ci (uniquement une classe qui le crée, le lance et le sauvegarde), donc que je n'aurais jamais à lire la liste des joueurs depuis l'extérieur de la classe Jeu
- je pensais faire un seul jeu extrêmement personnalisable qui aurait une liste d'options qui définiraient le fonctionnement de celui ci. C'est faisable mais inutilement compliqué, j'ai décidé de garder l'idée des options et de la liste d'option qui caractérise un jeu mais en déléguant le fonctionnement du jeu à une sous classe en cas de modification du fonctionnement. Ainsi les *petits paramètres* comme le nombre de dés, les valeurs possibles de ceux-ci, les conditions de victoire,... sont modifiables en créant une classe implémentant Option (OptionVictoire,...) alors que le fonctionnement du jeu nécéssite une classe héritant de Jeu redéfinissant `jouerUnTour`.


## Pensées et difficultés

- la différence gênante entre le jeu de l'oie et le numéri est que dans le premier le joueur n'a qu'un jeton donc pas de choix à faire au moment du jeu, alors que dans le second le joueur possède 6 jetons et a un choix à faire pour jouer, le jeton qui bouge n'est pas directement décidé par le dé. Cette distinction empèche de représenter ces deux jeux comme un seul avec des options différentes mais oblige à faire deux systèmes de jeu différents.