
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
      </ul>
    </div>

  </nav>
  <br/>
    liczba dni urlopu do wykorzystania: ${dni}
  <br/>
    <br/>
      <br/>
    <p >
      (to będzie popup) Przesłano prośbę o przyznanie urlopu
    </p>

  <table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Data początkowa</th>
      <th scope="col">Data końcowa</th>

    </tr>
  </thead>
  <tbody>


    <tr class="table-light">
      <th scope="row">${urlopID}</th>
      <td>${poczatek}</td>
      <td>${koniec}</td>
      <td><form action="PracownikServlet" method="get" >

        <input type="hidden" name="command" value="UPDATE">
        <input type="hidden" name="urlopID" value="${urlopID}">
        <input type="hidden" name="poczatek" value="${poczatek}">
        <input type="hidden" name="koniec" value="${koniec}">

        <label for="poczatek">Data początkowa</label>
        <input name="poczatek_new" type="date" class="form-control" id="poczatek" aria-describedby="poczatek" placeholder="poczatek" required = "required">
        <label for="koniec">Data końcowa</label>
        <input name="koniec_new" type="date" class="form-control" id="koniec" aria-describedby="koniec" placeholder="koniec" required = "required">


        <button type="submit" class="btn btn-success">Modyfikuj</button>
      
      </form>
      </td>

    </tr>

  </tbody>
  </table>
      <td>

        <%-- definiowanie linkow--%>
        <c:url var="deleteLink" value="PracownikServlet">
          <c:param name="command" value="DELETE"></c:param>
          <c:param name="urlopID" value="${urlopID}"></c:param>
        </c:url>

        <a href="${deleteLink}" class="btn btn-danger" role="button"> Usuń</a>
      </td>

<br/>
<br/>
<a href="index.html#index" class="btn btn-primary" role="button"> wyloguj się</a></button> <br/><br/>

    <c:url var="modLink" value="PracownikServlet">
      <c:param name="command" value="LIST"></c:param>
      <c:param name="stan" value="${null}"></c:param>
      <c:param name="zaakceptowane" value="true"></c:param>
    </c:url>
    <a href="${modLink}" class="btn btn-primary" role="button"> cofnij</a></button>

    <c:url var="mainLink" value="PracownikServlet">
      <c:param name="command" value="MAIN"></c:param>
    </c:url>
    <a href="${mainLink}" class="btn btn-primary" role="button"> profil </a></button>
    <c:if test="${not empty odpowiedz}">
      <script>
        window.addEventListener("load",function(){
          alert("${odpowiedz}");
        })
      </script>
    </c:if>
  </body>
</html>
