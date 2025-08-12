
### Spring Framework
Quoi ? Spring est considéré comme un conteneur dit « léger ».
       Par rapport aux conteneurs Servlet ou Web   : Tomcat
But : Concevoir une application évolutive,robuste et performante.
     - Code évolutive : Dépendency injection, il s'occupe de l'instanciation des objets
                        pour réduire le couplage de nos classes.
     - Performance : Configurer au lieu de développer d'où de gain de temps
     - composants : bases de données, traiter requêtes http, sécurité de l'appli
Analogie : Un cadre de travail pour développer notre application.
           Un magasin spécialisé pour les composants d'application Java.

### Spring Boot
Quoi? : C’est un composant très particulier de Spring Framework, dans la mesure où il nous permet 
de mettre en œuvre tous les autres ( Spring Web, Spring Data ....).

### Les principes SOLID
But: concevoir un code plus facile à :
    - comprendre
    - modifier
    - tester
signification:
    - « S » correspond au principe single responsibility (responsabilité unique)
    - « O » correspond au principe open/closed (ouvert/fermé)
    - « L » correspond au principe de substitution de Liskov
    - « I » correspond au principe interface segregation (ségrégation des interfaces).
    - « D » correspond au principe dependency inversion (inversion des dépendances).

### MVC 
But : Appliquer le principe « S » du système SOLID en séparant les responsabilités.

Signification : Un motif d'architecture logicielle.Il scinde les responsabilités du système en trois parties distinctes :
    - « M  » le Modèle contient des informations d'état, les données.
    - « V » la Vue contient les éléments d'interactions avec l'utilisateur.
    - « C » le Contrôleur veille à ce que l'enchaînement des étapes s'effectue correctement.

Quant aux autres principes, ils sont également appliqués. 
Par exemple, la vue étant séparée, il est facile d'ajouter de nouveaux éléments d'interface utilisateur 
sans avoir à modifier le modèle ou le contrôleur : le principe "O" 


### DTO ( Data Transfer Object)
But: objet qui transporte des données entre des différentes couches de API  
    - sans risquer d’exposer des informations sensibles ( Ex : mot de passe )
    - que les informations nécessaires ( Ex : nom et prénom sans le reste (age,adresse ....)) 
