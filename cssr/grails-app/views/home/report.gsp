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
         <h1>CSSR : Report on ${school.source.name}</h1>

<div class="well">
What was / is the quality of the teaching? *
Please give us an overall score of 1-5<br/>
<input type="radio" value="1">1<br/>
<input type="radio" value="2">2<br/>
<input type="radio" value="3">3<br/>
<input type="radio" value="4">4<br/>
<input type="radio" value="5">5<br/>
Select a value from a range from 1,Not so good, to 5,Very good,.	
</div>

<div class="well">
  How good was the teaching in:
  <table class="table table-striped">
      <g:each in="${['English', 'Maths', 'Sport', 'Science', 'Language', 'Technology']}" var="s">
      <tr>
        <td>${s}</td>
        <g:each in="${['Not so good','Poor','ok','Better than Average', 'Good']}" var="r">
           <td><input type="radio" value="${r}">${r}</td>
        </g:each>
      </tr>
      </g:each>
  </table>
</div>

<div class="well">
Other important factors *

  <table class="table table-striped">
      <g:each in="${['My child felt safe at this school','My child made good progress at this school','My child recieved appropriate homework for their age', 'This school was well led and managed', 'This school responded well to any concerns I raise', 'I receive information from the school about my child’s progress', 'Would you recommend this school to another parent?']}" var="s" >
      <tr>
        <td>${s}</td>
        <g:each in="${['Dont know','Strongly disagree','Disagree','Agree', 'Strongly agree']}" var="r">
           <td><input type="radio" value="${r}">${r}</td>
        </g:each>
      </tr>
      </g:each>
  </table>
</div>


<div class="well">
is there any feedback you would like to give us?

<input type="textarea" rows="5"/>
 
</div>

<input type="submit"/>

         </div>
       </div>
     </div>
     

  </body>
</html>
