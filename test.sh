#!/bin/bash
curl -X DELETE http://localhost:9200/test
 
echo;
curl -X POST http://localhost:9200/test -d '{
  "mappings" : {
    "document" : {
      "properties" : {
        "content" : {
          "type" : "attachment",
          "fields" : {
            "content"  : { "store" : "yes" },
            "author"   : { "store" : "yes" },
            "title"    : { "store" : "yes", "analyzer" : "english"},
            "date"     : { "store" : "yes" },
            "keywords" : { "store" : "yes", "analyzer" : "keyword" },
            "_name"    : { "store" : "yes" },
            "_content_type" : { "store" : "yes" }
          }
        }
      }
    }
  }
}'
 
echo;

echo '>>> Index the document'
echo "{\"_name\"    : \"1.pdf\",\"content\"  : \"$(openssl base64 -in 1.pdf)\"}" >> test.file
curl -i -X PUT http://localhost:9200/test/document/1 -d @test.file
rm -rf test.file

echo;
curl -X POST http://localhost:9200/test/_refresh
 
echo; echo ">>> Search for author 'John'"
curl "http://localhost:9200/test/_search?pretty=true&fields=*"

