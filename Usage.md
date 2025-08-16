Les requêtes de tests de fonctionnalité sont soumis depuis POSTMAN :

http://localhost:8080/firestation?stationNumber=<station_number>
Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers
correspondante. Donc, si le numéro de station = 1, elle doit renvoyer les habitants
couverts par la station numéro 1. La liste doit inclure les informations spécifiques
suivantes : prénom, nom, adresse, numéro de téléphone. De plus, elle doit fournir un
décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
moins) dans la zone desservie.
### GET - http://localhost:8080/firestation?stationNumber=1
{
"adultCount": 5,
"residents": [
{
"address": "644 Gershwin Cir",
"firstName": "Peter",
"lastName": "Duncan",
"phone": "841-874-6512"
},
{
"address": "908 73rd St",
"firstName": "Reginold",
"lastName": "Walker",
"phone": "841-874-8547"
},
{
"address": "908 73rd St",
"firstName": "Jamie",
"lastName": "Peters",
"phone": "841-874-7462"
},
{
"address": "947 E. Rose Dr",
"firstName": "Brian",
"lastName": "Stelzer",
"phone": "841-874-7784"
},
{
"address": "947 E. Rose Dr",
"firstName": "Shawna",
"lastName": "Stelzer",
"phone": "841-874-7784"
},
{
"address": "947 E. Rose Dr",
"firstName": "Kendrik",
"lastName": "Stelzer",
"phone": "841-874-7784"
}
],
"childCount": 1
}

http://localhost:8080/childAlert?address=<address>
Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins)
habitant à cette adresse. La liste doit comprendre le prénom et le nom de famille de
chaque enfant, son âge et une liste des autres membres du foyer. S'il n'y a pas
d'enfant, cette url peut renvoyer une chaîne vide
### GET - http://localhost:8080/childAlert?address=1509%20Culver%20St
{
"children": [
{
"firstName": "Tenley",
"lastName": "Boyd",
"age": 13
},
{
"firstName": "Roger",
"lastName": "Boyd",
"age": 7
}
],
"otherHouseholdMembers": [
{
"lastName": "Boyd",
"firstName": "John"
},
{
"lastName": "Boyd",
"firstName": "Jacob"
},
{
"lastName": "Boyd",
"firstName": "Felicia"
}
]
}

### GET - http://localhost:8080/phoneAlert?firestation=3
[
"841-874-6512",
"841-874-6513",
"841-874-6544",
"841-874-6874",
"841-874-8888",
"841-874-9888",
"841-874-6741"
]

### GET http://localhost:8080/fire?address=1509 Culver St
{
"stationNumber": "3",
"residents": [
{
"allergies": [
"nillacilan"
],
"firstName": "John",
"lastName": "Boyd",
"phone": "841-874-6512",
"medications": [
"aznol:350mg",
"hydrapermazol:100mg"
],
"age": 41
},
{
"allergies": [],
"firstName": "Jacob",
"lastName": "Boyd",
"phone": "841-874-6513",
"medications": [
"pharmacol:5000mg",
"terazine:10mg",
"noznazol:250mg"
],
"age": 36
},
{
"allergies": [
"peanut"
],
"firstName": "Tenley",
"lastName": "Boyd",
"phone": "841-874-6512",
"medications": [],
"age": 13
},
{
"allergies": [],
"firstName": "Roger",
"lastName": "Boyd",
"phone": "841-874-6512",
"medications": [],
"age": 7
},
{
"allergies": [
"xilliathal"
],
"firstName": "Felicia",
"lastName": "Boyd",
"phone": "841-874-6544",
"medications": [
"tetracyclaz:650mg"
],
"age": 39
}
]
}