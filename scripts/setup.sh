curl -XPUT 'http://localhost:9200/cssr/'

curl -XPUT 'http://localhost:9200/cssr/school/1' -d '{
    "user" : "kimchy",
    "post_date" : "2009-11-15T14:12:12",
    "message" : "trying out Elastic Search"
}'
