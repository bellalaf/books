curl -X POST -d '{"text": "Winnie the Poooh"}' -H "Content-Type: application/json" http://localhost:8080/

curl -X POST -d '{"text": "50 Shades of Grey"}' -H "Content-Type: application/json" http://localhost:8080/

curl -X POST -d '{"text": "Kotlin for Dummies"}' -H "Content-Type: application/json" http://localhost:8080/

### Get all the books
curl -X GET http://localhost:8080