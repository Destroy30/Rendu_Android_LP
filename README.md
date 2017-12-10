# Chatman
Rendu pour le TP android de la LP

Chatman est une application permettant de discuter avec les différents utilisateurs de cette application

## Fonctionnalités

### Ecran de chargement (Splash Screen) (Bonus)

Un ecran de charment est lancé pendant que l'application se charge pour faire patienter l'utilisateur

![chargement](https://user-images.githubusercontent.com/22858977/33801506-81598de6-dd5d-11e7-9053-7bfc01292310.png)

### Enregistrement du compte courant

La première page lancée est une page de connexion.
Elle permet de rentrer son login et son email pour se connecter

![compte](https://user-images.githubusercontent.com/22858977/33801498-7fcc8dac-dd5d-11e7-9aa7-51dc14dcbace.png)

### Detection email incorrect (Bonus)

L'application permet de détecter si l'email entré n'a pas un format valide, affiche un message d'rreur et attend que l'utilisateur corrige sa saisie.
De même, on ne peut aps entrer un nom ou un email vide

![detection erreur](https://user-images.githubusercontent.com/22858977/33801499-7fea1660-dd5d-11e7-9126-f797d84f753d.png)

### Ecran de chargmeent du chat (Bonus)

Le chat peut mettre un certain temps à charger, lors de la première connexion notament.
Au lieu de laisser une page blanche, l'application affiche une animation et un texte de chargement

![chargement chat](https://user-images.githubusercontent.com/22858977/33801520-0a18d768-dd5e-11e7-8128-1f3f502cb34a.png)

### Discussion

L'application permet de s'envoyer des messages et d'en reçevoir

### Reprage des messages de l'utilisateur

Les messages de l'utilisateurs sont placés à droite, ceux des autres à gauche

### Affiche du gravatar

Chaque membre connecté par son email possède un gravtar (ou si l'email n'est pas associé ou reconnue, un avatar par défaut)

### Date relative des messages

Les messages possèdent une indication temporelle relative

![chat base](https://user-images.githubusercontent.com/22858977/33801497-7fa75582-dd5d-11e7-9116-ba380a1b7c4f.png)

### Supression de messages

Rester appuyer un certain temps sur un message permet d'ouvrir une boîte de dialogue pour supprimer le message

Boîte de dialogue :

![delete dialog](https://user-images.githubusercontent.com/22858977/33801501-8027659c-dd5d-11e7-8617-81875f4d7a85.png)

Après :

![deleted](https://user-images.githubusercontent.com/22858977/33801502-8048b972-dd5d-11e7-89a0-bba19b6d1583.png)

### Envoi et recepetion de GIF(s) ou d'images simples

L'application permet, au clavier équipé de cette fonctionnalité, d'envoyer des images ou des gif(s), néamoins cette fonctionnalité n'est **disponnible que sur l'API 25**

L'application permet bien entendu d'afficher l'image (ou le gif) reçu *(peut importe l'API cette fois-ci)*


![gif](https://user-images.githubusercontent.com/22858977/33801503-8074b57c-dd5d-11e7-996e-cb7dce47b4ca.png)

### Menu et options

Un menu est disponnible afin de proposer deux options

![menu](https://user-images.githubusercontent.com/22858977/33801500-8008644e-dd5d-11e7-9a52-c95a5d34aec0.png)

La première permet de se déconnecter et de supprimer les informations sauvegardées (nom/email)

La seconde **(Bonus)** permet de se créer un gravatar, elle redirige vers le site en question

![gravatar site](https://user-images.githubusercontent.com/22858977/33801505-811844c6-dd5d-11e7-98ff-23b3594b8a39.png)







