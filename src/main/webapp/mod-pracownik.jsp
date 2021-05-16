
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
        <a class="nav-link" href="plan-pracownik.html#planpracownik">Planowanie Urlopu
          <span class="sr-only">(current)</span>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="mod-pracownik.jsp#modpracownik">Historia urlopów</a>
      </li>
    </ul>
</nav>
<br/>
liczba dni urlopu do wykorzystania: ${dni}
<br/>
<br/>
<br/>

<table class="table table-hover">
  <thead>
  <tr>
    <th scope="col">ID</th>
    <th scope="col">Data początkowa</th>
    <th scope="col">Data końcowa</th>
    <th scope="col">Stan</th>
    <th scope="col">Zaakceptowano</th>

  </tr>
  </thead>
  <tbody>

<c:forEach var="tmpUrlop" items="${urlopy}">

  <c:url var="updateLink" value="PracownikServlet">
    <c:param name="command" value="MODIFY"></c:param>
    <c:param name="urlopID" value="${tmpUrlop.id}"></c:param>
    <c:param name="poczatek" value="${tmpUrlop.data_poczatek}"></c:param>
    <c:param name="koniec" value="${tmpUrlop.data_koniec}"></c:param>
  </c:url>

  <c:url var="deleteLink" value="PracownikServlet">
    <c:param name="command" value="DELETE"></c:param>
    <c:param name="urlopID" value="${tmpUrlop.id}"></c:param>
  </c:url>



  <tr class="table-light">
    <th scope="row">${tmpUrlop.id}</th>
    <td>${tmpUrlop.data_poczatek}</td>
    <td>${tmpUrlop.data_koniec}</td>
    <td>${tmpUrlop.stan}</td>
    <td>${tmpUrlop.zaakceptowane}</td>
    <td><a href="${updateLink}" class="btn btn-success" role="button"> Modyfikuj</a></td>
    <td><a href="${deleteLink}" class="btn btn-danger" role="button"> Usuń</a></td>
  </tr>
</c:forEach>

  </tbody>
</table>
<br/>
<br/>
<a href="index.html#index" class="btn btn-primary" role="button"> wyloguj się</a></button>
<c:url var="mainLink" value="PracownikServlet">
  <c:param name="command" value="MAIN"></c:param>
</c:url>
<a href="${mainLink}" class="btn btn-primary" role="button"> cofnij </a></button>
<br/><br/>

<c:if test="${not empty odpowiedz}">
  <script>
    window.addEventListener("load",function(){
      alert("${odpowiedz}");
    })
  </script>
</c:if>

</body>
</html>
