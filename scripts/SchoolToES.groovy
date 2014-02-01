@Grapes([
    @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.14'),
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.1'),
    @Grab(group='xerces', module='xercesImpl', version='2.9.1'),
    @Grab(group='org.elasticsearch', module='elasticsearch-lang-groovy', version='1.5.0') ])

 
import groovyx.net.http.*
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.*
import org.apache.http.entity.mime.*
import org.apache.http.entity.mime.content.*
import java.nio.charset.Charset
import static groovy.json.JsonOutput.*
import org.elasticsearch.groovy.client.GClient 
import org.elasticsearch.groovy.node.GNode 
import org.elasticsearch.groovy.node.GNodeBuilder 
import static org.elasticsearch.groovy.node.GNodeBuilder.* 
 
def the_base_url = "http://education.data.gov.uk"

org.elasticsearch.groovy.common.xcontent.GXContentBuilder.rootResolveStrategy = Closure.DELEGATE_FIRST;

 
GNodeBuilder nodeBuilder = nodeBuilder(); 

nodeBuilder.settings { 
    node { 
        client = true 
    } 
    cluster { 
        name = "iihubble" 
    } 
} 

println("Get node...");
GNode node = nodeBuilder.node() 
println("Process");
processTopLevel(the_base_url, node);
println("Stop node");
node.stop().close() 
 
def processTopLevel(base_url, esnode) {

  org.elasticsearch.groovy.client.GClient esclient = esnode.getClient()
  println("client: ${esclient}");


  println "Loading page"
  def http = new HTTPBuilder( base_url )
 
  println("Get...");
  // perform a GET request, expecting JSON response data
  http.request( GET, JSON ) {
    uri.path = '/sparql/education/query'
    uri.query = [ query:"""
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX sch-ont: <http://education.data.gov.uk/def/school/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
 
SELECT ?s ?name ?lat ?lon ?laUri ?laName ?urn ?iip ?admissionsPolicy ?statLowAge ?statHighAge {
?s a sch-ont:School.
?s rdfs:label ?name .
?s <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?lat.
?s <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?lon.
?s sch-ont:localAuthority ?laUri .
?laUri rdfs:label ?laName.
?s sch-ont:uniqueReferenceNumber ?urn .
?s sch-ont:investorInPeople ?iip.
?s sch-ont:admissionsPolicy ?admissionsPolicy .
?s sch-ont:statutoryLowAge ?statLowAge .
?s sch-ont:statutoryHighAge ?statHighAge .
} 
""",
                  output:'json']


    // headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
  
    // response handler for a success response code:
    response.success = { resp, json ->
      println resp.statusLine
  
      // parse the JSON response object:
      json.results.bindings.each { binding ->
        println "  binding... ${binding.name.value}"

        def la_shortcode=binding.laName.value.trim().toLowerCase().replaceAll("\\p{Punct}","").trim().replaceAll("\\W","_")
        def school_shortcode=binding.name.value.trim().toLowerCase().replaceAll("\\p{Punct}","").trim().replaceAll("\\W","_")

        def idx_record=[
          _id:"sch:${binding.urn.value}",
          uri:binding.s.value,
          name:binding.name.value,
          lat:binding.lat.value,
          lon:binding.lon.value,
          laUri:binding.laUri.value,
          laName:binding.laName.value,
          statLowAge:binding.statLowAge.value,
          statHighAge:binding.statHighAge.value,
          laShortcode:la_shortcode,
          schoolShortcode:school_shortcode
        ]

        println("Indexing ${idx_record}");

        def future = esclient.index {
          index "cssr"
          type "school"
          id idx_record['_id']
          source idx_record
        }

        future.get()


        println("Indexed respidx:${future.response.index}/resptp:${future.response.type}/respid:${future.response.id}")
      }
    }
 
    // handler for any failure status code:
    response.failure = { resp ->
      println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
    }
  }
}
 
