JSF 2 en action
========

Sujet

Recoder certaines fonctionnalités du site du XKE de Xebia <http://intranet.xebia.fr/xke/> afin de découvrir JSF2 dans un environnement JEE6.

**Stack**: _Java7, JEE6, Jboss7, JPA2, JSF2, PrettyFaces, Omnifaces, Bootstrap, OpenId4java,  Bean validation, maven_

## Démarrage

Pour lancer son serveur, on utilisera le plugin maven **jboss-as** :
 
  mvn clean package jboss-as:run

L’application est ensuite accessible à l'adresse suivante : http://localhost:8080/xebia-jsf-xke/

Lorsque le serveur est démarré, il suffit d’utiliser les tâches ant fournit dans le fichier `build.xml` afin de redéployer la webapp ou de recharger à chaud les fichiers (.xhtml, .css, .js, …) sur le serveur :

  ant reload-webapp (default)
  ant redeploy

## Pre-requis 

Les messages à inclure dans les pages sont fournis dans le bundle **messages**, accessible via l’expression EL `#{messages[‘keyInTheBundle’]}`.

Les classes CSS à utiliser sont celles de bootstrap afin de coller à l’existant.

Les entités et DAOs seront fournis.

## Vos objectifs

### Compléter le template du site (main.xhtml)

Il faut inclure les JS et CSS dans le header de la page. La librairie JQuery incluse est celle qu’embarque **PrimeFaces**.

Il faut afficher un lien ayant pour label le nom de l’application et redirigeant vers la home page

Il faut créer un drop-down menu qui inclura 2 liens, le premier permettant de créer un slot et le deuxième permettant de voir la liste des slots

Le nom et le prénom de l’utilisateur courant doit être affiché en haut à droite du bandeau

Il faut permettre l’insertion d’un contenu principal dans la section `content` 

  **Tips**: _insert, outputStylesheet, #{messages}_

### Mise en place de la home

On souhaite maintenant afficher la liste des slots pour les 3 prochains mois à venir. On utilisera le template `main.xhtml` complété précédemment pour présenter cette liste.

On affichera également au dessus de la liste des slots, un slot choisis aléatoirement.

Il faudra créer le premier **ManagedBean** que l’on nommera `SlotController` pour pré-charger les données.

  **Tips**: _ManagedBean, ViewScoped, @URLAction, composition, define, repeat, dataGrid_

### Afficher le détail d’un slot

Créer une page permettant d’afficher le détail d’un slot, de la même manière que pour la home on utilisera le template. Les données du slot seront affichés sous forme de tableau.

Rajouter le mapping et l’action nécessaire au bon fonctionnement de la page.

On pensera à rajouter les liens sur la home permettant d’afficher le détail d'un slot..

  **Tips**: _@URLMappings, outputText, convertDateTime, link, pretty:link, rendered_

### Création d’un slot

Créer une page qui servira de formulaire de création pour un slot. Mapping et action seront bien entendu nécessaire.

Ajouter des messages de succès ou d’échec pour la création. Ces messages proviendront de la validation coté Hibernate (JSR 303) sur les entités.  

  **Tips**: _@URLMappings, outputText, convertDateTime, link, pretty:link, rendered_

### Modification/Suppresion d’un slot

L’utilisateur depuis la page de détails d’un slot aura un boutton d’action permettant d’éditer celui-ci ou de le supprimer.

**Contrainte**: le formulaire d’édition et de création doivent être les mêmes, pas de copier/coller sur une autre vue.

### Poster un commentaire

Il faut pouvoir poster des commentaires pour un Slot donné. 

Pour cela, il faut créer un **ManagedBean** que l’on nommera `Commentontroller` par exemple, et créer une vue `comment/create.xhtml` afin d’inclure un formulaire pour poster son commentaire.

Cette vue sera incluse ensuite dans la vue de détails d’un slot.

### Afficher la liste des commentaires

De même que précédemment en dessous du détail d’un slot et au dessus du formulaire de création d’un commentaire il faudra afficher la liste des commentaires.

Si il vous reste du temps, vous pouvez AJAXifier le post d’un commentaire et rafraîchir la liste.

## Liens Utiles

* Facelets : <http://javaserverfaces.java.net/nonav/docs/2.1/vdldocs/facelets/index.html>
* PrettyFaces : <http://ocpsoft.org/docs/prettyfaces/3.3.3/en-US/html/>
* PrimeFaces : <http://www.primefaces.org/showcase/ui/home.jsf>
* OmniFaces : <http://showcase-omnifaces.rhcloud.com/>
