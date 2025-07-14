# MyP5_SafeNety

- main : projet généré depuis Spring-Boot avec la dépendance web.
      L’annotation @SpringBootApplication combine trois annotations :
            @Configuration : indique qu’il s’agit d’une classe de configuration.
            @EnableAutoConfiguration : active la configuration automatique de Spring Boot.
            @ComponentScan : active la détection automatique des composants (@Service, @Controller, @Repository...).
      C’est cette classe qui démarre tout le contexte Spring et lance le serveur embarqué (par défaut Tomcat) sur le port 8080.

- dev1 : Utilisation de API Rest http://localhost:8080/hello avec la notation:
        @RestController

- dev2 : model mvc pour l'url http://localhost:8080/firestation?stationNumber==<station_number>
          qui affiche juste l'adresse correspondante.

- dev3 : mise en place des tests FirestationServiceTest FirestationControllerTest  pour dev2
          avec JUnits et Rapport JaCoco 

- dev4 : Mise place de Logging (traces d’exécution) avec Log4j ( logs/firestation.log)

- solution_1 : généralisation contenu fichier json avec trois entités ( firestation avec firestation et MedicalRecord" )