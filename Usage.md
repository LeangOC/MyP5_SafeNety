Les requêtes de tests de fonctionnalité sont soumis depuis POSTMAN :
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

