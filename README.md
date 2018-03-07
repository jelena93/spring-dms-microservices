spring-dms-microservices

api-gateway [mvn spring-boot:run]

http://localhost:8888/app-name/profile

install elasticsearch ingest-attachment

curl -XDELETE localhost:9200/documents

DELETE _ingest/pipeline/attachment

PUT _ingest/pipeline/attachment
{
  "description": "Document attachment pipeline",
  "processors" : [
    {
      "attachment" : {
        "field" : "content",
        "target_field" : "attachment", 
        "indexed_chars" : -1, 
        "ignore_missing" : true,  
      }
    }
  ]
}