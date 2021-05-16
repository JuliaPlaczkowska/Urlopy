<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <a class="navbar-brand" href="index.html">Urlopik.pl</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor02">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">

        </li>
        <li class="nav-item">

        </li>

  </nav>
  <br/>

  <br/>
    <br/>
      <br/>
    <p >

    </p>

  <table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID urlopu</th>
      <th scope="col">Imię nazwisko</th>
      <th scope="col">Rok zatrudnienia</th>
      <th scope="col">Dni urlopu</th>
      <th scope="col">Stan</th>
      <th scope="col">Termin urlopu</th>
      <th scope="col">Nowy termin urlopu</th>

    </tr>
  </thead>
  <tbody>
<c:forEach var="tmpUrlop" items="${urlopy}">

  <c:url var="acceptLink" value="KierownikServlet">
    <c:param name="command" value="ACCEPT"></c:param>
    <c:param name="urlopID" value="${tmpUrlop.id}"></c:param>
    <c:param name="zaakceptowane" value="${false}"></c:param>
    <c:param name="stan" value="${tmpUrlop.stan}"></c:param>
  </c:url>

  <c:url var="rejectLink" value="KierownikServlet">
    <c:param name="command" value="REJECT"></c:param>
    <c:param name="urlopID" value="${tmpUrlop.id}"></c:param>
    <c:param name="zaakceptowane" value="${false}"></c:param>
    <c:param name="stan" value="delete"></c:param>
  </c:url>

    <tr class="table-light">
      <th scope="row">${tmpUrlop.id}</th>
      <td>${tmpUrlop.nazwisko}</td>
      <td>${tmpUrlop.rok}</td>
      <td>${tmpUrlop.dni}</td>
      <td>${tmpUrlop.stan}</td>
      <td>${tmpUrlop.data_poczatek}<br>${tmpUrlop.data_koniec}</td>
      <td>${tmpUrlop.data_poczatek_new}<br>${tmpUrlop.data_koniec_new}</td>


      <td><a href="${acceptLink}" class="btn btn-success" role="button"> Zatwierdź</a></td>
      <td><a href="${rejectLink}" class="btn btn-danger" role="button"> Odrzuć</a></td>
    </tr>
</c:forEach>

  </tbody>
</table>
<br/>
<br/>
    <c:url var="mainLink" value="KierownikServlet">
      <c:param name="command" value="MAIN"></c:param>
    </c:url>
    <a href="${mainLink}" class="btn btn-primary" role="button"> cofnij</a></button>
<a href="index.html#index" class="btn btn-primary" role="button"> wyloguj się</a></button> <br/><br/>
    <c:if test="${not empty odpowiedz}">
      <script>
        window.addEventListener("load",function(){
          alert("${odpowiedz}");
        })
      </script>
    </c:if>
  </body>
</html>
