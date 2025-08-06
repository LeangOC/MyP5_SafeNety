# MyP5_SafeNety

- main : projet généré depuis Spring-Boot avec la dépendance web.
      L’annotation @SpringBootApplication combine trois annotations :
            @Configuration : indique qu’il s’agit d’une classe de configuration.
            @EnableAutoConfiguration : active la configuration automatique de Spring Boot.
            @ComponentScan : active la détection automatique des composants (@Service, @Controller, @Repository...).
      C’est cette classe qui démarre tout le contexte Spring et lance le serveur embarqué (par défaut Tomcat) sur le port 8080.

- dev1 : Utilisation de API Rest http://localhost:8080/hello avec la notation:
        @RestController

- dev2 : model mvc pour l'url http://localhost:8080/
- tation?stationNumber==<station_number>
          qui affiche juste l'adresse correspondante.

- dev3 : mise en place des tests FirestationServiceTest FirestationControllerTest  pour dev2
          avec JUnits et Rapport JaCoco 

- dev4 : Mise place de Logging (traces d’exécution) avec Log4j ( logs/firestation.log)

- solution_1 : généralisation contenu fichier json avec trois entités ( firestation avec firestation et MedicalRecord" )

- solution_2 : Préparation multualisation des classes pour les Alerts. Affichage donnés avec multi critères"

- solution_3 : Améliorer la couverture des tests

- solution_4 : 
    🔸/childAlert?address=<address>
    Renvoie la liste des enfants (≤ 18 ans) et autres membres du foyer.
    curl "http://localhost:8080/childAlert?address=1509%20Culver%20St"

    🔸/phoneAlert?firestation=<station_number>
    Liste des numéros de téléphone des habitants couverts par une caserne.
    curl "http://localhost:8080/phoneAlert?firestation=3"

   🔸 /fire?address=<address>
    Infos des habitants d’une adresse (âge, antécédents, téléphone, caserne).
    curl "http://localhost:8080/fire?address=1509%20Culver%20St"

   🔸 /flood/stations?stations=<station1,station2,...>
    Liste des foyers couverts par des stations : habitants + infos médicales.
    curl "http://localhost:8080/flood/stations?stations=1,2,3"

    🔸 /personInfo?lastName=<lastName>
    Infos complètes (âge, adresse, antécédents, email) pour une famille.
    curl "http://localhost:8080/personInfo?lastName=Boyd"


   🔸 /communityEmail?city=<city>
    Cette url doit retourner les adresses mail de tous les habitants de la ville
    curl "http://localhost:8080/communityEmail?city=Culver"

- solution_5 : "Tests Unitaires pour les Urls"

- solution_6 : "Mise en place de logger requête  HTTP GET request to firestation?stationNumber=2"
            
    
- solution_7 : 
- ## 📌 1. Endpoints API REST : POST PUT et DELETE

### 🔹 `/person`

| Méthode | Action            | Exemple `curl`                                                                                                                                                                                                                                   |
|--------|-------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST   | Ajouter une personne | `curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Dupont", "lastName":"BON", "address":"12 Rue Saint Juste", "city":"IVRY", "zip":"94200", "phone":"0123456789", "email":"DupondBON@email.com"}' http://localhost:8080/person` |
| PUT    | Mettre à jour      | `curl -X PUT -H "Content-Type: application/json" -d '{"firstName":"Dupont", "lastName":"BON", "address":"11 Rue Saint Juste", "city":"IVRY", "zip":"94200", "phone":"0123456789", "email":"DupondBON@email.com"}' http://localhost:8080/person`  |
| DELETE | Supprimer          | `curl -X DELETE "http://localhost:8080/person?firstName=Dupont&lastName=BON"`                                                                                                                                                                    |

- solution_8 
### 🔹 `/firestation`
| Méthode | Action                | Exemple `curl` |
|--------|-----------------------|----------------|
| POST   | Ajouter un mapping    | `curl -X POST -H "Content-Type: application/json" -d '{"address":"1509 Culver St","station":"3"}' http://localhost:8080/firestation` |
| PUT    | Mettre à jour         | `curl -X PUT -H "Content-Type: application/json" -d '{"address":"1509 Culver St","station":"4"}' http://localhost:8080/firestation` |
| DELETE | Supprimer un mapping  | `curl -X DELETE "http://localhost:8080/firestation?address=1509 Culver St"` ou `?station=3` | A faire sous Postman

- solution_9
  ### 🔹 /medicalRecord`
| Méthode | Action             | Exemple `curl` |
|--------|--------------------|----------------|
| POST   | Ajouter un mapping | curl -X POST -H "Content-Type: application/json" -d '{"firstName": "Dupond","lastName": "BON","birthdate": "1990-01-01","medications": ["aspirin:100mg"],"allergies": ["pollen"]}' http://localhost:8080/medicalRecord |
| PUT    | Mettre à jour      | curl -X PUT -H "Content-Type: application/json" -d '{"firstName": "Dupond","lastName": "BON","birthdate": "1990-01-01","medications": ["ibuprofen:200mg"],"allergies": ["pollen", "dust"]}' http://localhost:8080/medicalRecord|
| DELETE | Supprimer person   | curl -X DELETE "http://localhost:8080/medicalRecord?firstName=Dupond&lastName=BON"

- solution_10
### Passer en mode DTO pour la Méthode DELETE de l'endpoint :  /person
  DELETE | Supprimer person | curl -X DELETE -H "Content-Type: application/json" -d  '{"firstName": "Dupond","lastName": "BON"}' http://localhost:8080/person  

- solution_11
### Gestion des exceptions : Ajout PersonNotFoundException  dans le méthode deletePersonne de PersonService
DELETE | Supprimer person   | curl -X DELETE -H "Content-Type: application/json" -d  '{"firstName": "Dupond","lastName": "BON"}' http://localhost:8080/person
Aucune personne trouvée avec le nom : Dupond BON


- solution_12
- ### Implémentation des Tests Junit5 Service
- ### Rapport de couverture Jacoco = 90%

solution_13
- ### Implémentation des Spring Tests pour les contrôleurs
- ### Rapport de couverture Jacoco = 93%

Solution_14 
-  ### Mise en place des logs (info,debug et error) en capturant les codes de retour Http

Solution_15
### logs avec récupération des corps de requêtes