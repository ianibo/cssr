<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="cssrstyle"/>
    <title>CSSR</title>
  </head>
  <body>

   <div class="container">
     <div class="row">
       <div class="col-lg-12">
         <h1>CSSR</h1>
       </div>
     </div>
     <div class="row">
       <div class="col-lg-12">
         <g:form>
           Search:
           <input id="q" name="q" type="text" class="large" value="${params.q}"/><button name="submit" class="btn">Search</button>
         </g:form>
       </div>
     </div>
   </div>

   <div class="container">
     <div class="row">
       <div class="col-lg-12 well" style="text-align:center;">

         <g:form action="index" method="get">
         </g:form>

         <g:if test="${resultsTotal != null}">
           Search returned ${resultsTotal}
         </g:if>
         <g:else>
           Please enter criteria above (* to search all)
         </g:else>
       </div>
     </div>
     <div class="row">
       <div class="col-lg-2">
         <div class="facetFilter">
           <g:each in="${facets}" var="facet">
             <div>
               <h3 class="open"><a href="">${facet.key}</a></h3>
               <ul>
                 <g:each in="${facet.value}" var="fe">
                   <li>${fe.display}:${fe.count}</li>
                 </g:each>
               </ul>
             </div>
           </g:each>
         </div>
       </div>
       <div class="col-lg-10">
         <div id="resultsarea">
           <table class="table table-striped table-bordered">
             <thead>
               <tr>
                 <th>School</th>
               </tr>
             </thead>
             <tbody>
               <g:each in="${hits}" var="hit">
                 <tr>
                   <td> <g:link controller='home' action='schhome' params="${[la:hit.source.laShortcode,sch:hit.source.schoolShortcode]}">${hit.source.name}</g:link> </td>
                 </tr>
               </g:each>
             </tbody>
           </table>
         </div>
       </div>
     </div>
  </body>
</html>
