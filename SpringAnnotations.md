### @SpringBootApplication :
    Il indique que c'est la classe principale de Spring Boot.
    c’est une annotation composite qui combine 3 annotations clés.
    - @Configuration :  indique que cette classe contient une configuration Spring.
    - @EnableAutoConfiguration : devine et configure automatiquement les composants nécessaires (web, JPA, sécurité, etc.).
    - @ComponentScan : dit à Spring de scanner les packages à partir de ce point

### @RestController :
    Il sert pour créer des API REST (plus généralement dans Spring MVC)
    Il combine ces deux annotations :
    - @Controller (qui désigne une classe comme un contrôleur Spring MVC)
    - @ResponseBody (les valeurs de retour écrites dans la réponse HTTP, généralement au format JSON ou XML)



### @Component : 
    Pour qu’une classe soit déclarée en tant que bean : classe service ou métier (businessService) 
    qui va chercher les données ( la classe HelloWorld qui détient le message Hello World).

### @Autowired : 
    Pour qu’un bean soit injecté dans un attribut : classe qui utilise l'attribut service bean 
    ( classe principale HelloworldApplication)

