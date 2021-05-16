
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
  <head>
    <link rel="shortcut icon" href="favicon.ico" />

    <meta charset="utf-8">
    <title>Urlop</title>
<link rel="stylesheet" href="index.css" >

  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Urlopik.pl</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor02">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="plan-pracownik.jsp#planpracownik">Planowanie Urlopu
            <span class="sr-only">(current)</span>
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="mod-pracownik.jsp#modpracownik">Historia urlopów</a>
        </li>

  </nav>
    <h1></h1>


    <p >
      liczba dni urlopu do wykorzystania: ${DniUrlopu}
    </p>
  </p>

      <p class="image"><div style="text-align: center"><img src="images/resort.png" alt="resort" width="150" height="150" position="absolute" top="80px"></p>



<br/>

<p >

  <form action="PracownikServlet" method="get">

        <input type="hidden" name="command" value="SENDURLOP"/>

    <label for="poczatek">Data początkowa</label>
    <input name="poczatek" type="date" class="form-control" id="poczatek" aria-describedby="poczatek" placeholder="poczatek" required = "required">
    <label for="koniec">Data końcowa</label>
    <input name="koniec" type="date" class="form-control" id="koniec" aria-describedby="koniec" placeholder="koniec" required = "required">
  <br/>

    <button type="submit" class="btn btn-primary">Zaplanuj urlop</button>
  </form>
  <p >
    (to będzie popup) Przesłano prośbę o przyznanie urlopu
  </p>
</p>

      <c:if test="${not empty odpowiedz}">
      <script>
        window.addEventListener("load",function(){
          alert("${odpowiedz}");
        })
      </script>
      </c:if>

<br/>
<br/>
<br/>
<br/>
<a href="index.html#index" class="btn btn-primary" role="button"> wyloguj się</a></button>
      <br/><br/>
      <c:url var="mainLink" value="PracownikServlet">
        <c:param name="command" value="MAIN"></c:param>
      </c:url>
      <a href="${mainLink}" class="btn btn-primary" role="button"> cofnij </a></button>
  </body>
</html>
