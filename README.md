spring-dms-microservices

api-gateway [mvn spring-boot:run]

install elasticsearch ingest-attachment

curl -XPUT 'localhost:9200/_ingest/pipeline/attachment' -d'
{
  "description" : "Extract attachment information",
  "processors" : [
    {
      "attachment" : {
        "field" : "content"
      }
    }
  ]
}'

curl -XDELETE localhost:9200/documents