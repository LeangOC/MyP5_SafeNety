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


            
    

