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
         <h1>CSSR</h1>
       </div>
     </div>

     <div class="row">
       <div class="col-lg-7">


         <p>School home ${params}</p>
         <p>
           ${hits} School: <pre>${school?.source}</pre> <g:link action="report" params="${[la:school.source.laShortcode,sch:school.source.schoolShortcode]}">Report</g:link>
         </p>
       </div>

       <div class="col-lg-3">
         <div id="rightpanel" style="float:right; width:250px;border:1px solid black;">
           <div id="map" style="width: 250px; height: 250px;"></div>
           <div style="text-align: center; margin-top:15px; width: 250px;"></div>
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
