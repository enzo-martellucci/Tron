# README

## Introduction
Ce projet implémente un jeu inspiré de Tron, développé en Java avec une architecture orientée objet et des couches bien définies pour faciliter la maintenance et l'extensibilité. Le projet inclut des contrôleurs, des entités, une logique de jeu et des repositories pour interagir avec la base de données.

## Structure du Projet
Le projet est organisé comme suit :

- **Controllers** : Gèrent la logique métier et les interactions avec les vues.
- **Entities** : Modélisent les objets principaux utilisés dans le jeu.
- **Game** : Contient la logique du jeu et les stratégies IA.
- **Repositories** : Fournissent des interfaces pour accéder aux données persistantes.
- **Ressources** : Comprend les fichiers FXML pour les vues, les styles CSS, les images, et les scripts SQL.

## Détails de l'Architecture

### Controllers
Les contrôleurs orchestrent les interactions entre la logique métier et l'interface utilisateur. Voici les principaux contrôleurs :

- **GameController** : Gère les interactions liées à la logique de jeu.
- **HomeController** : Responsable de la gestion de l'accueil.
- **LeaderboardController** : Affiche les classements des joueurs.
- **LoginController** : Gère l'authentification des utilisateurs.
- **RegisterController** : Permet l'inscription de nouveaux utilisateurs.
- **SceneController** : Gère le changement de scènes.
- **SelectionController** : Permet aux utilisateurs de choisir des options comme les motos ou les skins.
- **ShopController** : Responsable de la boutique dans le jeu.

### Entities
Les entités représentent les modèles principaux pour les données utilisées dans le jeu. Elles incluent :

- **Game** : Représente une partie.
- **Player** : Modélise un joueur (humain ou IA).
- **AIPlayer** : Représente un joueur IA.
- **Moto** : Définit les motos utilisées dans le jeu.
- **Inventory** : Contient les items possédés par un joueur.
- **Skin** : Représente un skin de moto ou de joueur.

### Game
La logique du jeu est implémentée dans ce module. Les classes importantes incluent :

- **Tron** : Contient les règles et la gestion des parties.
- **Cell** et **Position** : Représentent les éléments fondamentaux de la grille.
- **Rider** : Définit le comportement des motos.
- **Constants** : Définit les types de cellules, les directions, et les niveaux de difficulté.
- **Strategy** : Contient des implémentations pour différents niveaux de stratégie IA (facile, moyen, difficile).

### Repositories
Les repositories permettent d'accéder et de manipuler les données persistantes dans une base de données. Voici les principaux :

- **AIPlayerRepository** : Gestion des joueurs IA.
- **HumanPlayerRepository** : Gestion des joueurs humains.
- **InventoryRepository** : Gestion des inventaires.
- **MotoRepository** : Gestion des motos.
- **SkinRepository** : Gestion des skins.

## Configuration

1. **Dépendances** : Vérifiez les dépendances dans `pom.xml` (si vous utilisez Maven).
2. **Base de données** : Le projet utilise un fichier SQLite (`tron.db`). Les scripts SQL nécessaires pour configurer la base de données sont inclus dans `src/main/resources/database/scripts/`.
3. **Ressources** : Les ressources (images, styles, polices) se trouvent dans `src/main/resources/assets/`.

## Lancement

1. Compilez le projet avec Maven ou un IDE compatible (IntelliJ, Eclipse).
2. Assurez-vous que les ressources et la base de données sont correctement configurées.
3. Exécutez la classe principale `TronApplication` pour démarrer l'application.

## Contributions
Les contributions sont les bienvenues. Veuillez ouvrir une pull request ou soumettre des issues pour signaler des problèmes ou proposer des améliorations.

## Licence
Ce projet est sous licence [MITE](LICENSE).

---

Pour toute question, veuillez contacter l'équipe de développement.

