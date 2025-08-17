Les requêtes de tests de fonctionnalité sont soumis depuis POSTMAN :

### GET http://localhost:8080/firestation?stationNumber=<station_number>
Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers
correspondante. Donc, si le numéro de station = 1, elle doit renvoyer les habitants
couverts par la station numéro 1. La liste doit inclure les informations spécifiques
suivantes : prénom, nom, adresse, numéro de téléphone. De plus, elle doit fournir un
décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
moins) dans la zone desservie.
- GET - http://localhost:8080/firestation?stationNumber=1

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

## http://localhost:8080/childAlert?address=<Address>
Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins)
habitant à cette adresse. La liste doit comprendre le prénom et le nom de famille de
chaque enfant, son âge et une liste des autres membres du foyer. S'il n'y a pas
d'enfant, cette url peut renvoyer une chaîne vide
- GET - http://localhost:8080/childAlert?address=1509%20Culver%20St

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

### http://localhost:8080/phoneAlert?firestation=<firestation_number>
Cette url doit retourner une liste des numéros de téléphone des résidents desservis
par la caserne de pompiers. Nous l'utiliserons pour envoyer des messages texte
d'urgence à des foyers spécifiques.
- GET - http://localhost:8080/phoneAlert?firestation=3

[
"841-874-6512",
"841-874-6513",
"841-874-6544",
"841-874-6874",
"841-874-8888",
"841-874-9888",
"841-874-6741"
]


### http://localhost:8080/fire?address=<address>
Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le
numéro de la caserne de pompiers la desservant. La liste doit inclure le nom, le
numéro de téléphone, l'âge et les antécédents médicaux (médicaments, posologie et
allergies) de chaque personne.
- GET - http://localhost:8080/fire?address=1509 Culver St

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

### http://localhost:8080/flood/stations?stations=<a list ofstation_numbers>
Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette
liste doit regrouper les personnes par adresse. Elle doit aussi inclure le nom, le
numéro de téléphone et l'âge des habitants, et faire figurer leurs antécédents
médicaux (médicaments, posologie et allergies) à côté de chaque nom.
- GET - http://localhost:8080/flood/stations?stations=1,2

{
"households": {
"951 LoneTree Rd": [
{
"firstName": "Eric",
"lastName": "Cadigan",
"phone": "841-874-7458",
"age": 80,
"medications": [
"tradoxidine:400mg"
],
"allergies": []
}
],
"908 73rd St": [
{
"firstName": "Reginold",
"lastName": "Walker",
"phone": "841-874-8547",
"age": 45,
"medications": [
"thradox:700mg"
],
"allergies": [
"illisoxian"
]
},
{
"firstName": "Jamie",
"lastName": "Peters",
"phone": "841-874-7462",
"age": 43,
"medications": [],
"allergies": []
}
],
"947 E. Rose Dr": [
{
"firstName": "Brian",
"lastName": "Stelzer",
"phone": "841-874-7784",
"age": 49,
"medications": [
"ibupurin:200mg",
"hydrapermazol:400mg"
],
"allergies": [
"nillacilan"
]
},
{
"firstName": "Shawna",
"lastName": "Stelzer",
"phone": "841-874-7784",
"age": 45,
"medications": [],
"allergies": []
},
{
"firstName": "Kendrik",
"lastName": "Stelzer",
"phone": "841-874-7784",
"age": 11,
"medications": [
"noxidian:100mg",
"pharmacol:2500mg"
],
"allergies": []
}
],
"644 Gershwin Cir": [
{
"firstName": "Peter",
"lastName": "Duncan",
"phone": "841-874-6512",
"age": 24,
"medications": [],
"allergies": [
"shellfish"
]
}
],
"892 Downing Ct": [
{
"firstName": "Sophia",
"lastName": "Zemicks",
"phone": "841-874-7878",
"age": 37,
"medications": [
"aznol:60mg",
"hydrapermazol:900mg",
"pharmacol:5000mg",
"terazine:500mg"
],
"allergies": [
"peanut",
"shellfish",
"aznol"
]
},
{
"firstName": "Warren",
"lastName": "Zemicks",
"phone": "841-874-7512",
"age": 40,
"medications": [],
"allergies": []
},
{
"firstName": "Zach",
"lastName": "Zemicks",
"phone": "841-874-7512",
"age": 8,
"medications": [],
"allergies": []
}
],
"29 15th St": [
{
"firstName": "Jonanathan",
"lastName": "Marrack",
"phone": "841-874-6513",
"age": 36,
"medications": [],
"allergies": []
}
]
}
}



### http://localhost:8080/personInfo?lastName=<lastName>
Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents
médicaux (médicaments, posologie et allergies) de chaque habitant. Si plusieurs
personnes portent le même nom, elles doivent toutes apparaître.
- GET - http://localhost:8080/personInfo?lastName=Marrack

[
{
"firstName": "Jonanathan",
"lastName": "Marrack",
"address": "29 15th St",
"email": "drk@email.com",
"age": 36,
"medications": [],
"allergies": []
}
]



### http://localhost:8080/communityEmail?city=<city>
Cette url doit retourner les adresses mail de tous les habitants de la ville
- http://localhost:8080/communityEmail?city=Culver

[
"jaboyd@email.com",
"drk@email.com",
"tenz@email.com",
"tcoop@ymail.com",
"lily@email.com",
"soph@email.com",
"ward@email.com",
"zarc@email.com",
"reg@email.com",
"jpeter@email.com",
"aly@imail.com",
"bstel@email.com",
"ssanw@email.com",
"clivfd@ymail.com",
"gramps@email.com"
]