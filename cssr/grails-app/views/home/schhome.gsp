<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <r:require modules="cssrstyle"/>
    <title>CSSR</title>
    <g:if test="${school.source.lat && school.source.lon}">
      <meta property="ICBM" name="ICBM" content="${school.source.lat}, ${school.source.lon}" />
      <meta property="geo.position" name="geo.position" content="${school.source.lat}, ${school.source.lon}" />
      <meta property="og:latitude" content="${school.source.lat}" />
      <meta property="og:longitude" content="${school.source.lon}" />
      <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    </g:if>

  </head>
  <body>

   <div class="container">
     <div class="row">
       <div class="col-lg-12">
         <h1>CSSR ${school?.source.name}</h1>
           (
           <g:link action="report" params="${[la:school.source.laShortcode,sch:school.source.schoolShortcode]}">Fill out a report for this school</g:link> )
           <a href="">Charter</a> <a href="">nn students</a>
       </div>
     </div>

     <div class="row">
       <div class="col-lg-7">

         <p>
           ${hits} School: <pre>${school?.source}</pre> 
         </p>

         <dl class="dl-horizontal">
           <dt>Name</dt>
           <dd>${school?.source.name}</dd>
           <dt>From</dt>
           <dd>${school?.source.statLowAge}</dd>
           <dt>To</dt>
           <dd>${school?.source.statHighAge}</dd>
         </dl>

         <div class="container-fluid">

           <div class="row-fluid">
             <div class="col-lg-4">
               <div class="well">Grades...</div>
             </div>
             <div class="col-lg-4">
               <div class="well">SSR...</div>
             </div>
             <div class="col-lg-4">
               <div class="well">Review...</div>
             </div>
           </div>

           <div class="row-fluid">
             <div class="col-lg-4">
               <div class="well">Student diversity....</div>
             </div>
             <div class="col-lg-4 ">
               <div class="well">Staff....<br/>
                 <a href="">John smith</a> Teaches at this school
               </div>
             </div>
             <div class="col-lg-4 ">
               <div class="well">Highlights....</div>
             </div>
           </div>

         </div>

         <br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
         <div class="container-fluid">
           <div class="row-fluid">
             <div class="well">
               Comments....
             </div>
             <div class="well">
               Comments....
             </div>
             <div class="well">
               Comments....
             </div>
             <div class="well">
               Comments....
             </div>
           </div>
         </div>

       </div>

       <div class="col-lg-3">
         <div id="rightpanel" style="width:250px;border:1px solid black;">
           <div id="map" style="width: 250px; height: 250px;"></div>
           <div style="text-align: center; margin-top:15px; width: 250px;"></div>
         </div>

         <br/>

         <div class="well">
           Schools nearby
         </div>

         <br/>

         <div class="well">
           Some ads maybe
         </div>
       </div>

     </div>
   </div>
  
    <g:if test="${school.source.lat && school.source.lon }">
      <script type="text/javascript">
      //<![CDATA[

      function map2() {
        var myLatlng = new google.maps.LatLng(${school.source.lat},${school.source.lon});

        var myOptions = {
           zoom: 15,
           center: myLatlng,
           mapTypeId: google.maps.MapTypeId.ROADMAP
        }

        var map = new google.maps.Map(document.getElementById("map"), myOptions);

        var marker = new google.maps.Marker({
             position: myLatlng,
             map: map,
             title:"${school.source.name}"
        });
        marker.setMap(map);
      }

      map2();
      //]]>
      </script>
    </g:if>

  </body>
</html>
