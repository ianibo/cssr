package cssr

import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsClassUtils

class HomeController {
  
  def springSecurityService
  def ESWrapperService

  // @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() { 
    def result = [:]
    if ( params.q ) {
      org.elasticsearch.groovy.node.GNode esnode = ESWrapperService.getNode()
      org.elasticsearch.groovy.client.GClient esclient = esnode.getClient()
      try {
        if ( params.q && params.q.length() > 0) {
          params.q = params.q.replace('"',"'")
          params.q = params.q.replace('[',"(")
          params.q = params.q.replace(']',")")
          params.max = Math.min(params.max ? params.int('max') : 10, 100)
          params.offset = params.offset ? params.int('offset') : 0
  
          def query_str = buildQuery(params);
  
          log.debug("Searching for ${query_str}");

          def search = esclient.search{
                         indices "cssr"
                         types "school"
                         source {
                           from = params.offset
                           size = params.max
                           query {
                             query_string (query: query_str)
                           }
                           facets {
                             'Component Type' {
                               terms {
                                 field = 'componentType'
                               }
                             }
                           }
                         }
                       }

          result.hits = search.response.hits

          if(search.response.hits.maxScore == Float.NaN) { //we cannot parse NaN to json so set to zero...
            search.response.hits.maxScore = 0;
          }

          result.resultsTotal = search.response.hits.totalHits
        }
      }
      catch ( Exception e ) {
        log.error("Problem",e);
      }

    }

    result
  }

  def schhome() {
    println("schhome ${params}");
    def result = [:]
    result.school = getSchool(params.la, params.sch)
    result
  }

  def report() {
    println("schhome ${params}");

    def result = [:]
    result.school = getSchool(params.la, params.sch)
    result
  }

  private String buildQuery(params) {
    'name:'+params.q

  }

  def getSchool(la, sch) {

    def result = null

    org.elasticsearch.groovy.node.GNode esnode = ESWrapperService.getNode()
    org.elasticsearch.groovy.client.GClient esclient = esnode.getClient()

    def search = esclient.search {
                         indices "cssr"
                         types "school"
                         source {
                           from = params.offset
                           size = params.max
                           query {
                             query_string (query: 'laShortcode:"'+la+'" AND schoolShortcode:"'+sch+'"')
                           }
                           facets {
                             'Component Type' {
                               terms {
                                 field = 'componentType'
                               }
                             }
                           }
                         }
                       }

    if ( search.response.hits.totalHits == 1 ) {
      result = search.response.hits.hits[0]
    }

    result
  }
}
