@Grapes([
    @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.14'),
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.1'),
    @Grab(group='xerces', module='xercesImpl', version='2.9.1') ])
 
import groovyx.net.http.*
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.*
import org.apache.http.entity.mime.*
import org.apache.http.entity.mime.content.*
import java.nio.charset.Charset
import static groovy.json.JsonOutput.*
 
def the_base_url = "http://education.data.gov.uk"
// /sparql/education/query?query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0D%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0D%0APREFIX+sch-ont%3A+%3Chttp%3A%2F%2Feducation.data.gov.uk%2Fdef%2Fschool%2F%3E%0D%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0D%0A%0D%0ASELECT+%3Fname+%3Flat+%3Flon+%3FlaUri+%3FlaName+%3Furn+%3Fiip+%3FadmissionsPolicy+%3FstatLowAge+%3FstatHighAge+%7B%0D%0A%3Fs+a+sch-ont%3ASchool.%0D%0A%3Fs+rdfs%3Alabel+%3Fname+.%0D%0A%3Fs+%3Chttp%3A%2F%2Fwww.w3.org%2F2003%2F01%2Fgeo%2Fwgs84_pos%23lat%3E+%3Flat.%0D%0A%3Fs+%3Chttp%3A%2F%2Fwww.w3.org%2F2003%2F01%2Fgeo%2Fwgs84_pos%23lat%3E+%3Flon.%0D%0A%3Fs+sch-ont%3AlocalAuthority+%3FlaUri+.%0D%0A%3FlaUri+rdfs%3Alabel+%3FlaName.%0D%0A%3Fs+sch-ont%3AuniqueReferenceNumber+%3Furn+.%0D%0A%3Fs+sch-ont%3AinvestorInPeople+%3Fiip.%0D%0A%3Fs+sch-ont%3AadmissionsPolicy+%3FadmissionsPolicy+.%0D%0A%3Fs+sch-ont%3AstatutoryLowAge+%3FstatLowAge+.%0D%0A%3Fs+sch-ont%3AstatutoryHighAge+%3FstatHighAge+.%0D%0A%7D+%0D%0A&output=json&stylesheet=xml-to-html.xsl&force-accept=text%2Fplain"
 
processTopLevel(the_base_url);
 
def processTopLevel(base_url) {
  println "Loading page"
  def http = new HTTPBuilder( base_url )
 
  // perform a GET request, expecting JSON response data
  http.request( GET, JSON ) {
    uri.path = '/sparql/education/query'
    uri.query = [ query:"""
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX sch-ont: <http://education.data.gov.uk/def/school/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
 
SELECT ?name ?lat ?lon ?laUri ?laName ?urn ?iip ?admissionsPolicy ?statLowAge ?statHighAge {
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
        println "  binding... ${binding.name}"
      }
    }
 
    // handler for any failure status code:
    response.failure = { resp ->
      println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
    }
  }
}
 
